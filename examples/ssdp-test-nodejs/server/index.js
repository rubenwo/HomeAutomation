const ssdp = require('@achingbrain/ssdp')
const bus = ssdp()

// advertise a service
bus.advertise({
    usn: 'urn:schemas-upnp-org:service:ggggg:1',
    interval: 1000,
    details: {
        URLBase: '192.168.2.100/api'
    }
})

