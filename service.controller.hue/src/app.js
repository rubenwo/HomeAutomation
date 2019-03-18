const express = require('express')
const app = express()
const hue_connector = require('./hue_connector.js')

let url = "http://127.0.0.1/api"
let token = "" //<username>

const conn = new hue_connector.Connector()

app.use(express.json());

// Documentation endpoint
app.get('/hue', (req, res) => {
    res.sendFile(path.join(__dirname + '/index.html'));
})


//Pair function to pair the lamps to the application
let getUser = app.get('/hue/pair', (req, res) => {
    
    let body = {
        "devicetype": "NodeJS-Controller"
    }

    conn.sendRequest(url, "POST", body, (err, resp, body) => {

        let response = {
            isValid: false,
            description: ''
        }

        if(body[0].error) {
            response.isValid = false
            response.description = body[0].error.description
            res.status(400).json(response)
        } else {
            token = body[0].success.username
            response.isValid = true
            response.description = token;
            res.status(200).json(response);
            
        }
    })
})

// Get all lamps connected to the Philips Hue Bridge. Comes with all the parameters of each lamp
app.get('/hue/lamps', (req, res) => { //endpoint

    //let username = 'testtest'
    let URL = url + '/' + token + '/lights'

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
            for (let key in json) {
                let obj = json[key]
                obj.id = key
                lights.lights.push(obj);
            }
            res.send(lights)
            console.log(lights);
        }
    )
})

// Get the details of a specific lamp. Identifier is used to identify a lamp.
app.get('/hue/lamp/:identifier', (req, res) => {
    let id = req.params['identifier']
    let URL = url + '/' + token + "/lights/" + id

    let response = {
        isValid: false,
        description: ''
    }

    conn.sendRequest(
        URL,
        "GET",
        null,
        (err, resp, body) => {

            let json = JSON.parse(body);

            // Check of het een array is dan error anders is het een object
            if(json[0]) {
                response.isValid = false
                response.description = json[0].error.description
                res.status(400).json(response);
            } else {
                response.isValid = true
                response.description = json
                res.status(200).json(response)
            }
        }
    )
  })

// Change lamp properties. Identifier identifies a lamp.

// Params:
// On: stands for the state of the light
// Bri: stands for the brightness of the light; value must be between 0 and 254 (int)
// Hue: stands for the colour of the light; value must be between 0 and 65535 (int)
// Sat: stands for the saturaion of the light; value must be between 0 and 254 (int)
app.put('/hue/lamp/:identifier', (req, res) => {
    let id = req.params['identifier']
    let URL = url + '/' + token + "/lights/" + id + "/state/" 

    let on = req.body["on"]     //true or false
    let bri = req.body['bri']
    let hue = req.body['hue']
    let sat = req.body['sat']

    let body = {
        "on": on,
        "bri": bri,
        "hue": hue,
        "sat": sat
    }

    let response = {
        isValid: false,
        description: ''
    }

    conn.sendRequest(
        URL,
        "PUT",
        body,
        (err, resp, body) => {
        
            if(body[0].error) {
                response.isValid = false
                response.description = body[0].error.description
                res.status(400).json(response);
            } else {
                response.isValid = true
                response.description = body[0].success
                res.status(400).json(response);
            }
        }
    )
})

//Opens a listen connection on port 8000
app.listen(8000, () => {
    console.log("Hue Service listening on port 8000")
})