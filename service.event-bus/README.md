# Event-bus service

## Usage

### Send update from a controller
**Definition**

`POST /update`

**Arguments**

- `"identifier":string` a globally unique identifier for a device.
- `"name":string` a friendly name for a device
- `"type":string` the controller type. eg. "hue-controller"
- `"data":object` arbitrary controller-specific information about the device. Specified individually per controller documentation.

**Response**

- 422: couldn't decode the body.
- 200: update posted succesfully to server.


### Subscribe to websocket updates
**Definition**

`GET /sub`

**Response**

Opens a websocket connection. The websocket forwards the update information mentioned above to subscribed connections. 

```json
{
    "identifier": "id1",
    "name": "Device 1",
    "type": "hue-controller",
    "data": {
       
    }
}
```
