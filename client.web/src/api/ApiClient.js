import axios from "axios";

export default class ApiClient {

    constructor(apiGatewayUrl) {
        this.apiGatewayUrl = apiGatewayUrl;
    }
    sendRequest(url, method, body) {
        console.log(url, method, body)
    }

    fetchDevices() {
        axios.get(this.apiGatewayUrl + "/registry/devices")
            .then(res => console.log(res))
            .catch(err => console.log(err));
    }

    fetchRooms() {
        axios.get(this.apiGatewayUrl + "/registry/rooms")
            .then(res => console.log(res))
            .catch(err => console.log(err));
    }
}