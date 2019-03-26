const express = require('express')
const cors = require('cors')
const app = express()
const delay = require('delay')

const gpio = require('rpi-gpio')
const gpiop = gpio.promise;

const ledPin = 7

gpiop.setup(ledPin, gpio.DIR_OUT)
    .then(() => {
        return gpiop.write(ledPin, true)
    })
    .catch((err) => {
        console.log('Error: ', err.toString())
    })


app.use(cors)

app.get("/ir_controller/encoding_tables", (req, res) => {
    res.sendStatus(500)
})

app.post("/ir_controller/command", (req, res) => {
    console.log("Got a request in /command")
    let data = req.body
    let json = {
        "command": data["command"],
        "device": data["device"]
    }
        blink()
    res.sendStatus(200)
})

async function blink() {
    for (let i = 0; i < 5; i++) {
        gpio.write(ledPin, true)
        console.log('on')
        await delay(500)
        gpio.write(ledPin, false)
        console.log('off')
        await delay(500)
    }
}

app.listen(80, () => {
    blink()
    console.log('IR Controller service started listening on port 80...')
})