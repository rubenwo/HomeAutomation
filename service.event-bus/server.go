package main

import (
	"encoding/json"
	"log"
	"net/http"

	"github.com/gorilla/mux"
)

var h *hub

type Status struct {
	Identifier string      `json:"identifier, omitempty"`
	Name       string      `json:"name, omitempty"`
	Type       string      `json:"type, omitempty"`
	Data       interface{} `json:"data, omitempty"`
}

func main() {
	h = newHub()
	router := mux.NewRouter()
	router.HandleFunc("/update", updateEndpoint).Methods("POST")
	router.Handle("/ws", wsHandler{h: h})
	log.Fatal(http.ListenAndServe(":80", router))
}

func updateEndpoint(w http.ResponseWriter, r *http.Request) {
	var s Status
	if err := json.NewDecoder(r.Body).Decode(&s); err != nil {
		log.Println(err)
		w.WriteHeader(400)
		return
	}
	data, err := json.Marshal(s)

	if err != nil {
		log.Println(err)
	}
	for c := range h.connections {
		c.send <- data
	}
}
