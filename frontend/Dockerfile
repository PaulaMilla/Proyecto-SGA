# Etapa 1: Construcción de Angular
FROM node:18 AS build

WORKDIR /app
COPY . .

RUN npm install
RUN npm run build --prod  # o ng build --configuration=production

# Etapa 2: Imagen de producción con NGINX
FROM nginx:alpine

COPY --from=build /app/dist/frontend /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf