package main

import (
	"net/http"
	"devtools.wi.pb.edu.pl/bitbucket/scm/vil/vilib1/handlers"
	"log"
)

func main() {
	log.Println("Starting server...")
	http.ListenAndServe(":"+os.Getenv("HTTP_PLATFORM_PORT"), handlers.NewRouter())
}

