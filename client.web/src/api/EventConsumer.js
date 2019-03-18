export default class EventConsumer {
    constructor(url, store) {
        this.url = url;
        this.store = store;
    }


    listen() {
        this.socket = new WebSocket(this.url);
        this.socket.onmessage = event => {
            console.log(event.data);
        }
    }
}