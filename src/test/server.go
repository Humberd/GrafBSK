package main

import (
	"net/http"
	"/handlers"
	"log"
        "os"
)

func main() {
	log.Println("Starting server...")
	http.ListenAndServe(":"+os.Getenv("HTTP_PLATFORM_PORT"), handlers.NewRouter())
}

