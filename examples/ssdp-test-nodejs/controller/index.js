const ssdp = require('@achingbrain/ssdp')
const bus = ssdp()

var usn = 'urn:schemas-upnp-org:service:ggggg:1'

bus.discover(usn)

bus.on('discover:' + usn, service => {
    // receive a notification about a service
    console.log(service)
    console.log("URL: " + service.details.URLBase)
    bus.on('update:' + service.UDN, service => {
        // receive a notification when that service is updated - nb. this will only happen
        // after the service max-age is reached and if the service's device description
        // document has changed
    })
})