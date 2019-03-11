# Infrared Controller

## Usage

### Send command to controller
**Definition**

`POST /ir_controller/command`

**Arguments**

- `"command":string` the command the infrared LED should emit.
- `"device":string` the device type. the controller uses this to determine which encoding table to use for emitting the command.

If the identifier already exists, the existing device will be overwritten.

**Response**

- 422: couldn't decode the body.
- 200: succesfully received command.


### Get all known encoding tables from the controller.
**Definition**

`GET /ir_controller/encoding_tables`

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