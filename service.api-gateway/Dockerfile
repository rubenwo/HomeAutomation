# ---- Base Node ----
FROM node:latest AS base
# Create app dir
WORKDIR /app

# ---- Dependencies ----
FROM base AS dependencies
# Wildcard to ensure package.json AND package-lock.json are copied
COPY ./service.api-gateway/package*.json ./
# install dependencies
RUN npm install

# ---- Copy Files/Build ----
FROM dependencies AS build
WORKDIR /app
COPY ./service.api-gateway/src /app
# Build react/vue/angular bundle static files uncomment if applicable
# RUN npm run build

# ---- Release with Alpine ----
FROM node:8.9-alpine AS release
# Create app dir
WORKDIR /app

COPY --from=dependencies /app/package.json ./
# Install app dependencies
RUN npm install --only=production
COPY --from=build /app ./
CMD ["node", "index.js"]