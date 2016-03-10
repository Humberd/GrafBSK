package main

import (
	"net/http"
	"handlers"
	"log"
)

func main() {
	log.Println("Starting server...")
	http.ListenAndServe(":8000", handlers.NewRouter())
}

