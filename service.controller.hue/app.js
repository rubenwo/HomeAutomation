const express = require('express')
const app = express()
const hue_connector = require('./hue_connector.js')

const conn = new hue_connector.Connector()
conn.pair(e => {
    console.log("username: " + e)
})