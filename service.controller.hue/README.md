# Philips Hue Controller

**URL**
- http://ip:port/api

## Usage of this program


### Get documentation
**Definition**

`GET /`

**Response**
Returns the documentation in an html document.

### Pair a user to a Philips Hue Bridge
**Definitions**

`POST /hue/pair`

**Arguments**
- `"devicetype":string` the name of the controller you want to pair to.  
**Note** You have to press the link button first!

**Response**
- On success: 
    {
        "isValid": true,
        "description": "token/username"
    }

- On failure: 
    {
        "isValid": false,
        "description": "link button not pressed"
    }

### Get all Philips Hue Lamps
**Definitions**
`GET /hue/lamps`

**Response**
- Gets a JSON response with a object in it. This object contains a array of Light objects. These are all the lights with its attributes in it. 

    {
        "lights": [
        {object},
        {object},
        {object}
        ]
    } 

    Object structure:
        {
            "modelid": "LCT001",
            "name": "Hue Lamp 1",
            "swversion": "65003148",
            "state": {
                "xy": [
                    0,
                    0
                ],
                "ct": 0,
                "alert": "none",
                "sat": 254,
                "effect": "none",
                "bri": 254,
                "hue": 4444,
                "colormode": "hs",
                "reachable": true,
                "on": true
            },
            "type": "Extended color light",
            "pointsymbol": {
                "1": "none",
                "2": "none",
                "3": "none",
                "4": "none",
                "5": "none",
                "6": "none",
                "7": "none",
                "8": "none"
            },
            "uniqueid": "00:17:88:01:00:d4:12:08-0a",
            "id": "1"
        }


### Get a specific Philips Hue Lamp
**Definitions**
`GET /hue/lamp/<id>`

**Arguments**
- `"id":int` the ID of the lamp you want to request

**Response**
- On success: Returns a big JSON object. This object consists a array with lamp objects. These objects each consist of lamp attributes such as; on, brightness, hue etc.

    let response = {
        isValid: false,
        description: ''
    }


    {
        "isValid": true,
        "description": {
            "isValid": true,
            "description": {
                "modelid": "LCT001",
                "name": "Hue Lamp 1",
                "swversion": "65003148",
                    "state": {
                    "xy": [
                        0,
                        0
                    ],
                        "ct": 0,
                        "alert": "none",
                        "sat": 254,
                        "effect": "none",
                        "bri": 254,
                        "hue": 4444,
                        "colormode": "hs",
                        "reachable": true,
                        "on": true
                },
                "type": "Extended color light",
                 "pointsymbol": {
                    "1": "none",
                    "2": "none",
                    "3": "none",
                    "4": "none",
                    "5": "none",
                    "6": "none",
                    "7": "none",
                    "8": "none"
                },
            "uniqueid": "00:17:88:01:00:d4:12:08-0a"
            }
        }
    }
    
    On failure: if the ID cannot be found
    {
        "isValid": false,
        "description": "resource, /lights/ID, not available"
    }

### Set a specific Philips Hue Lamp
**Definition**
`PUT /lamp/<id>/state/`

**Arguments**
- `"id":int` the ID of the lamp you want to request

    Arguments for the lamps:
    - `"on":boolean` the state you want to put the light in
    - `"bri":int` Brightness, value between 0 and 254
    - `"hue":int` Hue color, value between 0 and 65535 in Hue.
    - `"sat":int` Saturation, value between 0 and 254

**Response**
- If the response is valid:

    let response = {
        isValid: true,
        description: '/lights/<id>/state/etc.'
    }

- Else e.g. 'invalid value, <value>, for parameter, <param>
    {
        "isValid": false,
        "description": "invalid value, 20000 , for parameter, sat"
    }


### Send command to controller
**Definition**
`POST /lamp/:identifier`

**Arguments**

- `"command":string` the command the infrared LED should emit.
- `"device":string` the device type. the controller uses this to determine which encoding table to use for emitting the command.

**Response**

- 422: couldn't decode the body.
- 200: succesfully received command.

