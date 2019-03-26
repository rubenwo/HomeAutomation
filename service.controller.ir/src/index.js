const express = require('express')
const delay = require('delay')
const app = express()
const cors = require('cors')
const Gpio = require('onoff').Gpio

const LED = new Gpio(4, 'out')


app.use(cors())
app.use(express.json())

app.get("/ir_controller/encoding_tables", (req, res) => {
    console.log("In /ir_controller/encoding_tables")
    res.sendStatus(200)
})

app.post("/ir_controller/command", (req, res) => {
    console.log("In /ir_controller/command")
    let data = req.body
    console.log(data)
    let json = {
        "command": data["command"],
        "devuce": data["device"]
    }
    blink()
    res.sendStatus(200)
})

async function blink() {
    for (let i = 0; i < 5; i++) {
        console.log("on")
        LED.writeSync(1)
        await delay(100)
        console.log("off")
        LED.writeSync(0)
        await delay(100)
    }
}

app.listen(80, () => {
    blink()
    console.log("IR Controller Service is listening on port 80...")
})
