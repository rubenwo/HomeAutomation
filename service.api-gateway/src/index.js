const express = require('express')
const httpProxy = require('express-http-proxy')
const app = express()

// Proxies to services
const hue_controller_service_proxy = httpProxy('http://service.controller.hue')
const event_bus_service_proxy = httpProxy('http://service.event-bus')
const registry_service_proxy = httpProxy('http://service.registry')
const web_client_proxy = httpProxy('http://client.web')
//const ir_controller_proxy = ...

app.use((req, res, next) => {
    // TODO: my authentication logic
    next()
})

// Hue controller routes


// Registry service routes
app.all('/devices*', (req, res, next) => {
    console.log('Redirecting to registry service...')
    registry_service_proxy(req, res, next)
})

app.all('/rooms*', (req, res, next) => {
    console.log('Redirecting to registry service...')
    registry_service_proxy(req, res, next)
})

// Event-bus service routes


// Web client routes

app.listen(80, () => {
    console.log("Started api-gateway on port 80...")
})