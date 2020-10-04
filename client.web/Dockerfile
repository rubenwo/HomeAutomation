# ---- Base Node ----
FROM node:latest AS base
# Create app dir
WORKDIR /app

# ---- Dependencies ----
FROM base AS dependencies
# Wildcard to ensure package.json AND package-lock.json are copied
COPY ./client.web/package*.json ./
# install dependencies
RUN npm install

# ---- Copy Files/Build ----
FROM dependencies AS build
WORKDIR /app
COPY ./client.web/ /app/
# Build vue bundle static files
RUN npm run build

# ---- Release with nginx:alpine for minimal image
FROM nginx:alpine
COPY ./client.web/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html