const express = require('express');
const app = express();

app.get('/', (req, res) => {
    res.send("Hello World! You're on the main page")
});

app.listen(80, () => {
    console.log("API listening on port 80...")
})