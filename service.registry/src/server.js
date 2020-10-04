const express = require('express')
const cors = require('cors')
const app = express()
const Ajv = require('ajv')
const ajv = new Ajv()
const request = require('request')

// Import redis client
const redis = require('redis')
// Import bluebird
const bluebird = require('bluebird')
// Make redis client asynchronous.
bluebird.promisifyAll(redis);


app.use(express.json())
app.use(cors())

let devices// = require('./devices.json')
let rooms// = require('./rooms.json')
const fs = require('fs')
const path = require('path');
const deviceSchema = {
    "type": "object",
    "properties": {
        "identifier": { "type": "string" },
        "name": { "type": "string" },
        "device_type": { "type": "string" },
        "controller_name": { "type": "string" },
        "ip_address": { "type": "string" },
        "room_identifier": { "type": "string" }
    },
    "required": ["identifier", "name", "device_type", "controller_name", "ip_address", "room_identifier"]
}
const roomSchema = {
    "type": "object",
    "properties": {
        "identifier": { "type": "string" },
        "name": { "type": "string" },
        "devices": {
            "type": "array",
            "items": {
                "type": "object",
                "properties": {
                    "identifier": { "type": "string" },
                    "name": { "type": "string" },
                    "device_type": { "type": "string" },
                    "controller_name": { "type": "string" },
                    "ip_address": { "type": "string" },
                },
                "required": ["identifier", "name", "device_type", "controller_name", "ip_address"]
            },
        }
    },
    "required": ["identifier", "name", "devices"]
}

setTimeout(() => {
    // Creates redis client. host is ip-address of redis server. port is port number of the redis server.
    client = redis.createClient({
        host: "service.db",
        port: "6379"
    })
    // Prints the error whenever the redis client returns an error.
    client.on('error', err => {
        console.log(err)
        request.post(
            "http://service.event-bus/event-bus/update",
            {
                json: {
                    "error": "Database went down! Possible data loss!"
                }
            },
            (err, resp, body) => console.log(resp)
        )
        client.quit();
    })
    initFromDatabase();

}, 10000)

//initFromFiles("rooms")
//initFromFiles("devices")

//TODO: Handle errors & Send status
function createError(status, content) {
    let error = {
        "status": status,
        "error": content
    }
    return error
}

function writeToDatabase(key, value) {
    if (!key) {
        return "can't save without key"
    }
    client.set(key, JSON.stringify(value), redis.print)
}

// Initialize from files on disk based on key. 
// @key is a string (rooms or devices)
function initFromFiles(key) {
    console.log("Loading from files...")
    if (key === 'rooms') {
        rooms = require('./rooms.json')
        console.log(rooms)
        writeToDatabase("rooms", rooms)
    }
    if (key === 'devices') {
        devices = require('./devices.json')
        console.log(devices)
        writeToDatabase("devices", devices)
    }
}

// Initialize rooms and devices from database. Fallback is json files on disk
function initFromDatabase() {
    console.log("Loading from database...")
    client.getAsync("rooms").then(res => {
        if (res !== null) {
            rooms = JSON.parse(res)
            console.log(rooms)
        } else {
            initFromFiles("rooms")
        }
    }).then(
        client.getAsync("devices").then(res => {
            if (res !== null) {
                devices = JSON.parse(res)
                console.log(devices)
            } else {
                initFromDatabase("devices")
            }
        })
    )
}

// Root endpoint
app.get('/registry', (req, res) => {
    //res.statusCode(200)
    res.sendFile(path.join(__dirname + '/index.html'));
})

// Get all available devices
app.get('/registry/devices', (req, res) => {
    // res.sendStatus(200)
    const devs = {
        devices: devices
    }
    res.send(devs)
})

// Add a new device
app.post('/registry/devices', (req, res) => {
    let data = req.body
    let valid = ajv.validate(deviceSchema, data);
    if (!valid) console.log(ajv.errors);
    if (valid) {
        let json = {
            "identifier": data["identifier"],
            "name": data["name"],
            "device_type": data["device_type"],
            "controller_name": data["controller_name"],
            "ip_address": data["ip_address"],
            "room_identifier": data["room_identifier"]
        }
        devices.push(json)
        //fs.writeFileSync('./devices.json', JSON.stringify(devices), 'utf8')
        writeToDatabase("devices", devices)

        //res.setHeader("201", "Create device")
        res.status(201).json(devices)
    } else {
        res.sendStatus(422)
    }
})

// Get specific device details
app.get('/registry/devices/:identifier', (req, res) => {
    let id
    try {
        id = req.params['identifier']
    } catch (err) {
        console.log('In catch')
        console.error(err)
    }
    for (let i = 0; i < devices.length; i++) {
        if (devices[i].identifier === id) {
            //  res.setHeader(200)
            res.status(200).json(devices[i])
            return
        }
    }
    res.sendStatus(404)
})

// Delete a specific device
app.delete('/registry/devices/:identifier', (req, res) => {
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
        //fs.writeFileSync('devices.json', JSON.stringify(devices), 'utf8')
        writeToDatabase("devices", devices)
        res.sendStatus(204)
    } else {
        res.sendStatus(404)
    }
})

// Get all available rooms
app.get('/registry/rooms', (req, res) => {
    const r = {
        rooms: rooms
    }
    res.send(r)
})

// Add a new room
app.post('/registry/rooms', (req, res) => {
    let data = req.body
    let valid = ajv.validate(roomSchema, data);
    if (!valid) console.log(ajv.errors);
    if (valid) {
        let json = {
            "identifier": data["identifier"],
            "name": data["name"],
            "devices": data["devices"]
        }
        rooms.push(json)
        //fs.writeFileSync('rooms.json', JSON.stringify(rooms), 'utf8')
        writeToDatabase("rooms", rooms)
        // res.setHeader("201", "Created room")
        res.status(201).json(rooms)
    } else {
        res.sendStatus(422)
    }
})

// Get specific room details
app.get('/registry/rooms/:identifier', (req, res) => {
    let id
    try {
        id = req.params['identifier']
    } catch (err) {
        console.log('In catch')
        console.error(err)
    }
    for (let i = 0; i < rooms.length; i++) {
        if (rooms[i].identifier === id) {
            // res.setHeader(200)
            res.status(200).json(rooms[i])
            return
        }
    }
    res.sendStatus(404)
})

// Delete a specific room
app.delete('/registry/rooms/:identifier', (req, res) => {
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
        // fs.writeFileSync('rooms.json', JSON.stringify(rooms), 'utf8')
        writeToDatabase("rooms", rooms)
        res.sendStatus(204)
    } else {
        res.sendStatus(404)
    }
})

app.listen(80, () => {
    console.log("Registry Service listening on port 80")
})