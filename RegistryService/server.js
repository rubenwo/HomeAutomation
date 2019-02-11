const express = require('express')
const app = express()
// Root endpoint
app.get('/', (req, res) => {
    res.send("Welcome to the home page...")
})
// Get all available devices
app.get('/devices', (req, res) => {
    //TODO: Load from file & send response
    var json = [
        {
            "identifier": "id1",
            "name": "Device 1",
            "device_type": "switch",
            "controller_name": "controller-1",
            "room": {
                "identifier": "bedroom",
                "name": "Someone's Bedroom"
            }
        }
    ]

    res.send(json)
})

// Add a new device
app.post('/devices', (req, res) => {
    //TODO: Append to file & send response
})

// Get specific device details
app.get('/device/:identifier', (req, res) => {
    //TODO: Load from file & send response
})

// Delete a specific device
app.delete('/device/:identifier', (req, res) => {
    //TODO: Delete from file & send response
})

// Get all available rooms
app.get('/rooms', (req, res) => {
    //TODO: Load from file & send response
})

// Add a new room
app.post('/rooms', (req, res) => {
    //TODO: Append to file & send response
})

// Get specific room details
app.get('/room/:identifier', (req, res) => {
    //TODO: Load from file & send response
})

// Delete a specific room
app.delete('/room/:identifier', (req, res) => {
    //TODO: Delete from file & send response
})

app.listen(80, () => {
    console.log("Registry Service listening on port 80")
})