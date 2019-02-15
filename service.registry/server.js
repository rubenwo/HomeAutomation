const express = require('express')
const app = express()
app.use(express.json())

const devices = require('./devices.json')
const rooms = require('./rooms.json')
const fs = require('fs')
const path = require('path');


console.log(devices)
console.log(rooms)

//TODO: Handle errors & Send status
//TODO: Verify body from request (check for duplicates and valid format)


function createError(status, content) {
    let error = {
        "status": status,
        "error": content
    }
    return error
}

// Root endpoint
app.get('/', (req, res) => {
    res.sendStatus(200)
    res.sendFile(path.join(__dirname + '/index.html'));
})

// Get all available devices
app.get('/devices', (req, res) => {
    res.sendStatus(200)
    res.send(devices)
})

// Add a new device
app.post('/devices', (req, res) => {
    let json = req.body
    devices.push(json)
    fs.writeFileSync('./devices.json', JSON.stringify(devices), 'utf8')
    res.sendStatus(201)
    res.send(devices)
})

// Get specific device details
app.get('/devices/:identifier', (req, res) => {
    let id
    try {
        id = req.params['identifier']
    } catch (err) {
        console.log('In catch')
        console.error(err)
    }
    for (let i = 0; i < devices.length; i++) {
        if (devices[i].identifier === id) {
            res.sendStatus(200)
            res.send(devices[i])
            return
        }
    }
    res.sendStatus(404)
})

// Delete a specific device
app.delete('/devices/:identifier', (req, res) => {
    let id
    let found = false
    try {
        id = req.params['identifier']
    } catch (err) {
        console.log('In catch')
        console.error(err)
    }
    for (let i = 0; i < devices.length; i++) {
        if (devices[i].identifier === id) {
            devices.splice(i, 1)
            found = true
        }
    }
    if (found) {
        fs.writeFileSync('devices.json', JSON.stringify(devices), 'utf8')
        res.sendStatus(204)
    } else {
        res.sendStatus(404)
    }
})

// Get all available rooms
app.get('/rooms', (req, res) => {
    res.sendStatus(200)
    res.send(rooms)
})

// Add a new room
app.post('/rooms', (req, res) => {
    let json = req.body
    rooms.push(json)
    fs.writeFileSync('rooms.json', JSON.stringify(rooms), 'utf8')
    res.sendStatus(201)
    res.send(rooms)
})

// Get specific room details
app.get('/rooms/:identifier', (req, res) => {
    let id
    try {
        id = req.params['identifier']
    } catch (err) {
        console.log('In catch')
        console.error(err)
    }
    for (let i = 0; i < rooms.length; i++) {
        if (rooms[i].identifier === id) {
            res.sendStatus(200)
            res.send(rooms[i])
            return
        }
    }
    res.sendStatus(404)
})

// Delete a specific room
app.delete('/rooms/:identifier', (req, res) => {
    let id
    let found = false
    try {
        id = req.params['identifier']
    } catch (err) {
        console.log('In catch')
        console.error(err)
    }
    for (let i = 0; i < rooms.length; i++) {
        if (rooms[i].identifier === id) {
            rooms.splice(i, 1)
            found = true
        }
    }
    if (found) {
        fs.writeFileSync('rooms.json', JSON.stringify(rooms), 'utf8')
        res.sendStatus(204)
    } else {
        res.sendStatus(404)
    }
})

app.listen(80, () => {
    console.log("Registry Service listening on port 80")
})