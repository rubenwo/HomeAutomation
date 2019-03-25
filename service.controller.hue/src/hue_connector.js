const request = require('request')

class Connector {

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
            case "DELETE":
                request.delete(
                    url,
                    { json: body},
                    (err, resp, body) => callback(err, resp, body)
                )
        }
    }
}

module.exports.Connector = Connector
