
# API Gateway

## Usage

### Hue Controller
**Definition**

- All routes starting with "/hue" will be forwarded to the hue controller endpoints

**Response**

- Defined in service.controller.hue

### Registry Service
**Definition**

- All routes starting with "/registry" will be forwarded to the registry service endpoints

**Response**

- Defined in service.registry

### IR Controller
**Definition**

- All routes starting with "/ir_controller" will be forwarded to infrared controller endpoints

**Response**

- Defined in service.controller.ir

### Event-bus Service
**Definition**

- All routes starting with "/event-bus" will be forwarded to event-bus service endpoints

**Response**

- Defined in service.event-bus


### Other requests
**Definition**

- All other requests are forwarded to the static web server

**Response**

- Web pages