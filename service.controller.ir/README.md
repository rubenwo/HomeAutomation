# Infrared Controller

## Usage

### Send command to controller
**Definition**

`POST /command`

**Arguments**

- `"command":string` the command the infrared LED should emit.
- `"device":string` the device type. the controller uses this to determine which encoding table to use for emitting the command.

**Response**

- 422: couldn't decode the body.
- 200: succesfully received command.


### Get all known encoding tables from the controller.
**Definition**

`GET /encoding_tables`

**Response**


```json
{
    "tables":[
        {
            "name":"kpn settop box",
            "commands":[
                "1",
                "2",
                "etc."
            ]
        }
    ]
}
```