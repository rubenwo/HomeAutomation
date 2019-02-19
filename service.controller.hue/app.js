const express = require('express')
const app = express()
const hue_connector = require('./hue_connector.js')

const conn = new hue_connector.Connector()
conn.pair(e => {
    console.log("username: " + e)
})


// Documentation endpoint
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname + '/index.html'));
})

// Get all lamps connected to the Philips Hue Bridge
app.get('/lamps', (req, res) => {

})

// Get specific lamp details. Identifier identifies a lamp.
app.get('/lamp/:identifier', (req, res) => {

})

// Change lamp properties. Identifier identifies a lamp.
app.post('/lamp/:identifier', (req, res) => {

})

// Get all rooms configured on the Philips Hue Bridge
app.get('/rooms', (req, res) => {

})

// Create a new room on the Philips Hue Bridge
app.post('/rooms', (req, res) => {

})