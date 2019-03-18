const notifications = []
export default class EventConsumer {
    constructor(url, store) {
        this.url = url;
        this.store = store;
    }

    listen(callback) {
        this.socket = new WebSocket(this.url);
        this.socket.onmessage = event => {
            console.log(event.data);
            notifications.push(JSON.parse(event.data));
            callback();
        }
    }
    getNotifications() {
        return notifications;
    }

    removeNotification() {
        
    }
}