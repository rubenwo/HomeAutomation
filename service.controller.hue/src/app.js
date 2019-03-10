const express = require('express')
const app = express()
const hue_connector = require('./hue_connector.js')


let url = "http://127.0.0.1/api"
let token = ""

const conn = new hue_connector.Connector()
conn.pair(url, e => {
    console.log("token: " + e)
    token = e
    url += "/" + token + "/"
    console.log(url)
})


// Documentation endpoint
app.get('/hue', (req, res) => {
    res.sendFile(path.join(__dirname + './index.html'));
})


app.get('/hue/pair', (req, res) => {
    conn.pair(url, e => {
        console.log("token: " + e)
        token = e
        url = "http://127.0.0.1/api/" + token + "/"
        console.log(url)
    })
})

// Get all lamps connected to the Philips Hue Bridge
app.get('/hue/lamps', (req, res) => {
    conn.sendRequest(
        url,
        "GET",
        null,
        (err, resp, body) => {
            let json = JSON.parse(body)
            console.log(json)
            let lights = {
                "lights": []
            }
            for (let key in json.lights) {
                lights.lights.push(json.lights[key])
            }
            res.send(lights)
        }
    )
})

// Get specific lamp details. Identifier identifies a lamp.
app.get('/hue/lamp/:identifier', (req, res) => {
    let id = req.params['identifier']

    let URL = url + "/lights/" + id + "/state/"
    conn.sendRequest(
        URL,
        null,
        (err, resp, body) => {
            console.log(body)
        }
    )
})

// Change lamp properties. Identifier identifies a lamp.
app.post('/hue/lamp/:identifier', (req, res) => {
    let id = req.params['identifier']


    let body = {

    }
    let URL = url + "/lights/" + id + "/state/"

    conn.sendRequest(
        url,
        body,
        (err, resp, body) => {

        }
    )
})

// Get all rooms configured on the Philips Hue Bridge
app.get('/hue/rooms', (req, res) => {

})

// Create a new room on the Philips Hue Bridge
app.post('/hue/rooms', (req, res) => {

})

app.listen(80, () => {
    console.log("Hue Service listening on port 80")
})