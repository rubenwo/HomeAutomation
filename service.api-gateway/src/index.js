const express = require('express')
const proxy = require('http-proxy-middleware')
const web_proxy = require('express-http-proxy')('http://client.web')
const app = express()

// Register path to registry service
app.use('/registry*', proxy({
    target: 'http://service.registry',
    changeOrigin: true
}))

// Register path to hue controller service
app.use('/hue*', proxy({
    target: 'http://service.controller.hue',
    changeOrigin: true
}))

// Register path to infrared controller service.
//TODO: change target
app.use('/ir_controller', proxy({
    target: 'http://0.0.0.0',
    changeOrigin: true
}))

// Register path to event-bus service (websocket)
const wsProxy = proxy('ws://service.event-bus/event-bus/sub', {
    changeOrigin: true
})
app.use(wsProxy)

// Register path to event-bus service (update status via POST)
app.use('/event-bus/update', proxy({
    target: 'http://service.event-bus',
    changeOrigin: true,
}))

// Register path to web service
app.all('/*', (req, res, next) => {
    console.log('Redirecting to web client service...')
    web_proxy(req, res, next)
})

app.on('upgrade', wsProxy.upgrade)

app.listen(80, () => {
    console.log('Started api-gateway service on port 80...')
})
