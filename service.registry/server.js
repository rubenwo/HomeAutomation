const express = require('express')
const app = express()
app.use(express.json())

const devices = require('./devices.json')
const rooms = require('./rooms.json')
const fs = require('fs')
const path = require('path');


console.log(devices)
console.log(rooms)
// Root endpoint
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname + '/index.html'));
})

// Get all available devices
app.get('/devices', (req, res) => {
    res.send(devices)
})

// Add a new device
app.post('/devices', (req, res) => {
    //TODO: Verify body from request (look for duplicates and valid format)
    let json = req.body
    devices.push(json)
    fs.writeFileSync('./devices.json', JSON.stringify(devices), 'utf8')
    res.send(devices)
})

// Get specific device details
app.get('/device/:identifier', (req, res) => {
    let id = req.params['identifier']
    console.log(id)
    res.send(id)
    //TODO: Load from file & send response
})

// Delete a specific device
app.delete('/device/:identifier', (req, res) => {
    let id
    try {
        id = req.params['identifier']
    } catch (err) {
        console.log('In catch')
        console.error(err)
    }
    for (let i = 0; i < devices.length; i++) {
        if (devices[i].identifier === id) {
            devices.splice(i, 1)
        }
    }
    fs.writeFileSync('devices.json', JSON.stringify(devices), 'utf8')
    res.send(devices)
})

// Get all available rooms
app.get('/rooms', (req, res) => {
    res.send(rooms)
})

// Add a new room
app.post('/rooms', (req, res) => {
    let json = req.body
    rooms.push(json)
    fs.writeFileSync('rooms.json', JSON.stringify(rooms), 'utf8')
    res.send(rooms)
    //TODO: Append to file & send response
})

// Get specific room details
app.get('/room/:identifier', (req, res) => {
    let id = req.params['identifier']
    console.log(id)
    res.send(id)
    //TODO: Load from file & send response
})

// Delete a specific room
app.delete('/room/:identifier', (req, res) => {
    let id = req.params['identifier']
    console.log(id)
    res.send(id)
    //TODO: Delete from file & send response
})

app.listen(80, () => {
    console.log("Registry Service listening on port 80")
})