# Infrared Controller

## Usage

### Send command to controller
**Definition**

`POST /command`

**Arguments**

- `"command":string` the command the infrared LED should emit.
- `"device":string` the device type. the controller uses this to determine which encoding table to use for emitting the command.
- `"deviceID":string` a device id for the smart remote so the server knows which controller its communicating with with.

If the identifier already exists, the existing device will be overwritten.

**Response**

- 422: couldn't decode the body.
- 200: succesfully received command.


### Get all known encoding tables from the controller.
**Definition**

`GET /encoding_tables`

**Response**
 returns a encoding table if the request was succesfull.

```json
{
    "tables":[
        {
            "name":"kpn settop box",
            "commands":[
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "UP",
                "DOWN",
                "LEFT",
                "RIGHT" 
            ]
        }
    ]
}
```