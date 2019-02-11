const express = require('express')
const app = express()

app.get('/', (req, res) => {
    res.send("Welcome to the home page...")
})

app.listen(80, () => {
    console.log("Registry Service listening on port 80")
})