const request = require('request')

class Connector {
    pair(callback) {

        let url = "http://127.0.0.1/api"
        let body = {
            "devicetype": "NodeJS-Controller"
        }

        this.sendRequest(url, "POST", body, (err, resp, body) => {
            if (body[0].success)
                callback(body[0].success.username)
        })
    }

    sendRequest(url, method, body, callback) {
        console.log("Sending request")
        switch (method) {
            case "POST":
                request.post(
                    url,
                    { json: body },
                    (err, resp, body) => callback(err, resp, body)
                )
                break;
            case "GET":
                request.get(
                    url,
                    { json: body },
                    (err, resp, body) => callback(err, resp, body)
                )
                break;
            case "PUT":
                request.put(
                    url,
                    { json: body },
                    (err, resp, body) => callback(err, resp, body)
                )
                break;
        }
    }
}

module.exports.Connector = Connector
