const express = require('express')
const app = express()
const hue_connector = require('./hue_connector.js')

let url = "http://127.0.0.1/api" //TODO: change url to get it from client
let token = "" //<username>

var lamp_ids = []
var group_ids = []
var schedule_ids = []

const conn = new hue_connector.Connector()

app.use(express.json());

//To do's:

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

        if (body[0].error) {
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
            res.status(200).json(lights)
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

    if (contains(lamp_ids, id)) {
        conn.sendRequest(
            URL,
            "GET",
            null,
            (err, resp, body) => {

                let json = JSON.parse(body);

                // Check of het een array is dan error anders is het een object
                if (json[0]) {
                    response.isValid = false
                    response.description = json[0].error.description
                    status(400).json(response);
                } else {
                    response.isValid = true
                    response.description = json
                    res.status(200).json(response)
                }
            }
        )
    } else {
        console.log("ID: ", id, "does not exist\r\n")
        console.log("Try one of these: ")
        lamp_ids.forEach(element => {
            console.log(element)
        });
        res.sendStatus(404); //Not found
    }
})

// Change lamp properties. Identifier identifies a lamp.

// Parameters:
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

    if (contains(lamp_ids, id)) {
        conn.sendRequest(
            URL,
            "PUT",
            body,
            (err, resp, body) => {

                if (body[0].error) {
                    response.isValid = false
                    response.description = body[0].error.description
                    res.statusCode(400).json(response);
                } else {
                    response.isValid = true
                    response.description = body[0].success
                    res.status(200).json(response);
                    //object returnen
                }
            }
        )
    } else {
        console.log("ID: ", id, "does not exist\r\n")
        console.log("Try one of these: ")
        lamp_ids.forEach(element => {
            console.log(element)
        });
        res.sendStatus(404); //Not found
    }
})


//=============================================
//                   Groups
//=============================================

//Get all groups
app.get('/hue/groups', (req, res) => {
    let URL = url + '/' + token + '/groups'

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

            //Check response, check if empty, if any schedule's exist
            if (json[0]) {
                response.isValid = false
                response.description = json[0].error.description
                res.status(400).json(response)
            } else {
                response.isValid = true
                response.description = json

                console.log("Json response of: /hue/groups: \r\n", response.description)

                let keys = Object.keys(response.description)

                group_ids.length = 0;
                keys.forEach(element => {
                    group_ids.push(element);
                });

                res.status(200).json(response)
            }
        }
    )
})

//!!!!!!!!DOES NOT WORK!!!!!!!!!!!!
//Create a new group
// app.post('/hue/groups', (req, res) => {
//     let URL = url + '/' + token + '/groups'

//     let response = {
//         isValid: false,
//         description: ''
//     }

//     // Format for body:
//     // {
//     //     "name": "Living room",
//     //     "type": "Room",
//     //     "classs": "Living room",
//     //     "lights": [
//     //         "3",
//     //         "4"
//     //     ]
//     // }

//     let name = req.body["name"]     //true or false
//     let type = req.body['type']
//     let classs = req.body['class']
//     let lights = req.body['lights']

//     let body = {
//         "name": name,
//         "type": type,
//         "class": classs,
//         "lights": lights
//     }

//     conn.sendRequest(URL, "POST", body, (err, resp, body) => {
//         console.log("BODY: ", body)
//         let json = JSON.parse(body)
//         console.log("JSON: ", json)
//         if(body[0].error) {
//             response.isValid = false
//             response.description = json[0].error.description
//             res.status(400).json(response)
//         } else {
//             var obj = json[0]
//             response.isValid = true
//             response.description = json
//             res.status(200).json(obj)

//             console.log("Json response of: CREATE /hue/groups/: \r\n", response.description)
//         }
//     })
// })

//Get group attributes
app.get('/hue/groups/:identifier', (req, res) => {
    let id = req.params['identifier']
    let URL = url + '/' + token + '/groups/' + id

    let response = {
        isValid: false,
        description: ''
    }

    if (contains(group_ids, id)) {
        console.log("Group ID exists!");
        conn.sendRequest(
            URL,
            "GET",
            null,
            (err, resp, body) => {

                let json = JSON.parse(body);

                //Check response, check if empty, if any schedule with this <ID> exists
                if (json[0]) {
                    response.isValid = false
                    response.description = json[0].error.description
                    res.status(400).json(response)
                } else {
                    response.isValid = true
                    response.description = json

                    console.log("Json response of: GET /hue/groups/<identifier>: \r\n", response.description)
                    res.status(200).json(response)
                }
            }
        )
    } else {
        console.log("ID: ", id, "does not exist\r\n")
        console.log("Try one of these: ")
        group_ids.forEach(element => {
            console.log(element)
        });
        res.sendStatus(404);
    }
})

//Set group attributes
//Allows the user to modify the name, light and class membership of a group.
//To be continued...


//Set group state
//To be continued...



//Delete group:
//Deletes a specific group from the bridge
app.delete('/hue/groups/:identifier', (req, res) => {
    let id = req.params['identifier']
    let URL = url + '/' + token + '/groups/' + id

    let response = {
        isValid: false,
        description: ''
    }

    if (contains(group_ids, id)) {
        console.log("Group ID exists!");
        conn.sendRequest(
            URL,
            "DELETE",
            null,
            (err, resp, body) => {
                let json = JSON.parse(body);

                //Check response, check if empty, if any schedule with this <ID> exists
                if (json[0]) {
                    response.isValid = false
                    response.description = json[0].error.description
                    res.status(400).json(response)
                } else {
                    response.isValid = true
                    response.description = json
                    res.status(200).json(response)
                    console.log("Json response of: DELETE /hue/groups/<identifier>: \r\n", response.description)
                    console.log(json.success);
                    res.sendStatus(204);
                }
            }
        )
    } else {
        console.log("ID: ", id, "does not exist, group is NOT deleted!\r\n")
        console.log("Try one of these: ")
        group_ids.forEach(element => {
            console.log(element)
        });
        res.sendStatus(404); //Not found
    }
})

//=============================================
//                  Schedules                       
//=============================================

//Get all schedules of a Hue Bridge
app.get('/hue/schedules', (req, res) => {
    let URL = url + '/' + token + '/schedules'

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

            //Check response, check if empty, if any schedule's exist
            if (json[0]) {
                response.isValid = false
                response.description = json[0].error.description
                res.status(400).json(response)
            } else {
                response.isValid = true
                response.description = json

                console.log("Json response of: /hue/schedules: \r\n", response.description)

                let keys = Object.keys(response.description)

                schedule_ids.length = 0;
                keys.forEach(element => {
                    schedule_ids.push(element);
                });

                var index = 0
                for (index; index < keys.length; index++) {
                    let object_keys = Object.keys(keys[index])
                    keys.forEach(element => {
                        var obj = util.
                            console.log(element.params);
                    });
                    // console.log("Attributes of object ID: ", index);
                    // for(index_2; index_2 < object_keys.length; index_2++) {
                    //     console.log("")
                    // }
                }
                res.status(200).json(response)
            }
        }
    )
})

//Get all attributes of a specific schedule

//Parameters:
//id: to obtain all the available id's use method: /hue/schedules.
app.get('/hue/schedules/:identifier', (req, res) => {
    let id = req.params['identifier']
    let URL = url + '/' + token + '/schedules/' + id

    let response = {
        isValid: false,
        description: ''
    }

    if (contains(schedule_ids, id)) {
        console.log("Schedule ID exists!");
        conn.sendRequest(
            URL,
            "GET",
            null,
            (err, resp, body) => {

                let json = JSON.parse(body);

                //Check response, check if empty, if any schedule with this <ID> exists
                if (json[0]) {
                    response.isValid = false
                    response.description = json[0].error.description
                    res.status(400).json(response)
                } else {
                    response.isValid = true
                    response.description = json
                    res.status(200).json(response)

                    console.log("Json response of: GET /hue/schedules/<identifier>: \r\n", response.description)
                }
            }
        )
    } else {
        console.log("ID: ", id, "does not exist\r\n")
        console.log("Try one of these: ")
        schedule_ids.forEach(element => {
            console.log(element)
        });
        res.sendStatus(404);
    }
})

//Set schedule attributes:
//Allows the user to change attributes of a schedule.

//Parameters:
//name: name for the schedule. (string)
//description: description of the schedule. (string)
//command: command to execute when the scheduled event occurs. 
//          Command must be valid, otherwise a error will be thrown
//localtime: time for the schedule (string)
//status: status of the schedule. (string)
//autodelete: if schedule gets removed automatically when the schedule is expired. If true, it will get removed
//              if false, the schedule gets disabled.
app.put('/hue/schedules/:identifier', (req, res) => {
    let id = req.params['identifier']
    let URL = url + '/' + token + '/schedules/' + id

    let name = req.body["name"]     //true or false
    let description = req.body['description']
    let command = req.body['command']
    let localtime = req.body['localtime']
    let status = req.body['status']
    let autodelete = req.body['autodelete']

    let body = {
        "name": name,
        "description": description,
        "command": command,
        "localtime": localtime,
        "status": status,
        "autodelete": autodelete
    }

    let response = {
        isValid: false,
        description: ''
    }

    if (contains(schedule_ids, id)) {
        console.log("Schedule ID exists!");
        conn.sendRequest(URL, "PUT", body, (err, resp, body) => {
            if (body[0].error) {
                response.isValid = false
                response.description = body[0].error.description
                res.status(400).json(response);
            } else {
                response.isValid = true
                response.description = body[0].success
                res.status(200).json(response);
            }
            console.log("Json response of: SET /hue/schedules/<identifier>: \r\n", response.description)
        }
        )
    } else {
        console.log("ID: ", id, "does not exist\r\n")
        console.log("Try one of these: ")
        schedule_ids.forEach(element => {
            console.log(element)
        });
        res.sendStatus(404);
    }
})


//Delete schedule:
//Deletes a schedule from the bridge.

//Parameters:
//id: to obtain all the available id's use method: /hue/schedules.
app.delete('/hue/schedules/:identifier', (req, res) => {
    let id = req.params['identifier']
    let URL = url + '/' + token + '/schedules/' + id

    let response = {
        isValid: false,
        description: ''
    }

    if (contains(schedule_ids, id)) {
        console.log("Schedule ID exists!");
        conn.sendRequest(
            URL,
            "DELETE",
            null,
            (err, resp, body) => {
                let json = JSON.parse(body);

                //Check response, check if empty, if any schedule with this <ID> exists
                if (json[0]) {
                    response.isValid = false
                    response.description = json[0].error.description
                    res.status(400).json(response)
                } else {
                    response.isValid = true
                    response.description = json
                    console.log("Json response of: DELETE /hue/schedules/<identifier>: \r\n", response.description)
                    console.log(json.success);
                    res.status(204).json(response)
                }
            }
        )
    } else {
        console.log("ID: ", id, "does not exist, schedule is NOT deleted!\r\n")
        console.log("Try one of these: ")
        schedule_ids.forEach(element => {
            console.log(element)
        });
        res.sendStatus(404);
    }
})


//========================================
//              Other methods
//========================================

//Contains method; checks if a array contains a specific object
function contains(arr, valueOrObject) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] === valueOrObject) {
            return true;
        }
    }
    return false;
}


//Opens a listen connection on port 8000
app.listen(80, () => {
    console.log("Hue Service listening on port 80")
})