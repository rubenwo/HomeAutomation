// Import redis client
const redis = require('redis')
// Import bluebird
const bluebird = require('bluebird')
// Make redis client asynchronous.
bluebird.promisifyAll(redis);

// Creates redis client. host is ip-address of redis server. port is port number of the redis server.
client = redis.createClient({
    host: "localhost",
    port: "6379"
})

// Prints the error whenever the redis client returns an error.
client.on('error', err => {
    console.log(err)
})

// Saves key: "Hello" & value: "World" to redis server
client.set("Hello", "World", redis.print)


// Gets the value of key: "Hello" from the server asynchronously
client.getAsync('Hello').then(res => {
    console.log(res)
})
