const express = require('express')
const app = express()
const hue_connector = require('./hue_connector.js')


let url = "http://127.0.0.1/api"
let token = "" //<username>

const conn = new hue_connector.Connector()
conn.pair(url, e => {
    console.log("token: " + e)
    token = e
    url += "/" + token + "/"
    console.log(url)
})


// Documentation endpoint
app.get('/hue', (req, res) => {
    res.sendFile(path.join(__dirname + '/index.html'));
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
app.get('/hue/lamps', (req, res) => { //endpoint

    //let username = 'testtest'
    let URL = url + '/' + token + '/lights'

    // http://127.0.0.1/api/token/lights

    conn.sendRequest(
        URL,
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
    let URL = url + '/' + token + "/lights/" + id // + "/state/"
    conn.sendRequest(
        URL,
        null,
        (err, resp, body) => {
            console.log(body)
            res.send(body)
        }
    )
  })

// Change lamp properties. Identifier identifies a lamp.
app.post('/hue/lamp/:identifier', (req, res) => {
    let id = req.params['identifier']
    let URL = url + '/' + token + "/lights/" + id + "/state/"

    //BODY arguments:
    let state = req.params['state']     // 0 or 1
    let bri = req.params['brightness']  // value between 0 and 254
    let hue = req.params['hue']         // value between 0 and 65535
    let sat = req.params['saturation']  // value between 0 and 254

    let body = {
        "on": + state,
        "bri": + bri,
        "hue": + hue,
        "sat": + sat
    }

    conn.sendRequest(
        URL,
        body,
        (err, resp, body) => {
            console.log(body);
            res.send(body);
        }
    )
})

// Change light state. Identifier identifies a lamp.
app.post('/hue/lamp/:identifier', (req, res) => {
    let id = req.params['identifier'] // lamp ID
    let state = req.params['state'] // lamp status 0 of 1

    let body = {
        "on": + state
    }

    conn.sendRequest(
        URL,
        body,
        (err, resp, body) => {
            console.log(body);
            res.send(body);
        }
    )
})

app.listen(80, () => {
    console.log("Hue Service listening on port 80")
})