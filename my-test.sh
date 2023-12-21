#!/bin/bash

curl -X POST http://localhost:8080/book \
  -d '{"bookName": "TDD by example", "author": "Kent Beck"}' \
  -H 'Content-Type: application/json'

curl -X PUT http://localhost:8080/book/1 \
  -d '{"bookName": "Clean Code", "author": "Robert C. Martin"}' \
  -H 'Content-Type: application/json'

curl http://localhost:8080/books \
  -H 'Content-Type: application/json'
