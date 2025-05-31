# Etapa 1: Build
FROM node:18 AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

# Etapa 2: Servir con Angular CLI
FROM node:18 AS runner
WORKDIR /app
COPY --from=builder /app ./
RUN npm install -g @angular/cli

# Exponer el puerto que utiliza ng serve
EXPOSE 4200

# Comando para iniciar el servidor de desarrollo
CMD ["ng", "serve", "--host", "0.0.0.0"]
