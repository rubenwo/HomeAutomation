# Philips Hue Controller

## Usage


### Get documentation
**Definition**

`GET /`

**Response**
Returns the documentation in an html document.



### Pair with a Philips Hue Bridge
**Definitions**

`GET /pair`



### Send command to controller
**Definition**

`POST /lamp/:identifier`

**Arguments**

- `"command":string` the command the infrared LED should emit.
- `"device":string` the device type. the controller uses this to determine which encoding table to use for emitting the command.

**Response**

- 422: couldn't decode the body.
- 200: succesfully received command.

