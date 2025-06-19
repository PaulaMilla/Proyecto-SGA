#!/bin/bash

# Sustituye con los puertos asignados que te den por correo
FRONTEND_PORT=31000
GATEWAY_PORT=31001
INVENTARIOS_PORT=31002
USUARIOS_PORT=31003
VENTAS_PORT=31004

docker network create sga-net || true

docker rm -f frontend api-gateway inventarios usuarios ventas 2>/dev/null

docker run -d --name frontend --network sga-net -p $FRONTEND_PORT:80 antoniayevenes1901/frontend:latest
docker run -d --name api-gateway --network sga-net -p $GATEWAY_PORT:8080 antoniayevenes1901/api-gateway:latest
docker run -d --name inventarios --network sga-net -p $INVENTARIOS_PORT:8080 antoniayevenes1901/inventarios-service:latest
docker run -d --name usuarios --network sga-net -p $USUARIOS_PORT:8080 antoniayevenes1901/usuarios-service:latest
docker run -d --name ventas --network sga-net -p $VENTAS_PORT:8080 antoniayevenes1901/ventas-service:latest
