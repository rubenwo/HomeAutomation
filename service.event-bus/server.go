package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"

	"github.com/gorilla/mux"
)

var h *hub

//Status is a struct used for sending and receiving updates from controllers
type Status struct {
	Identifier string      `json:"identifier, omitempty"`
	Name       string      `json:"name, omitempty"`
	Type       string      `json:"type, omitempty"`
	Data       interface{} `json:"data, omitempty"`
}

func main() {
	h = newHub()
	router := mux.NewRouter()
	router.HandleFunc("/event-bus/update", updateEndpoint).Methods("POST")
	router.Handle("/event-bus/sub", wsHandler{h: h})
	log.Fatal(http.ListenAndServe(":80", router))
}

func updateEndpoint(w http.ResponseWriter, r *http.Request) {
	var s Status
	if err := json.NewDecoder(r.Body).Decode(&s); err != nil {
		log.Println(err)
		w.WriteHeader(422)
		return
	}
	fmt.Println(s)
	data, err := json.Marshal(s)
	w.WriteHeader(200)
	if err != nil {
		log.Println(err)
	}
	for c := range h.connections {
		c.send <- data
	}
}
