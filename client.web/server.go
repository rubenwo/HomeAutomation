package main

import (
	"fmt"
	"log"
	"net/http"
)

var fs = http.FileServer(http.Dir("public"))

func main() {
	http.HandleFunc("/", rootHandler)
	fmt.Println("Web Server is running...")
	log.Fatal(http.ListenAndServe(":80", nil))
}

func rootHandler(w http.ResponseWriter, req *http.Request) {
	fs.ServeHTTP(w, req)
}
