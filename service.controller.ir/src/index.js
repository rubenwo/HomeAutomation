const express = require('express')
const delay = require('delay')
const app = express()
const cors = require('cors')

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
        await delay(100)
        console.log("off")
        await delay(100)
    }
}

app.listen(80, () => {
    console.log("IR Controller Service is listening on port 80...")
})