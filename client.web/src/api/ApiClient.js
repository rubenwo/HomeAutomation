import axios from "axios";
//TODO: Implement async/await in fetch functions
export default class ApiClient {

    constructor(apiGatewayUrl) {
        this.apiGatewayUrl = apiGatewayUrl;
    }
    sendRequest(url, method, body) {
        console.log(url, method, body)
    }

    fetchDevices() {
        axios.get(this.apiGatewayUrl + "/registry/devices")
            .then(res => console.log(res.data.devices))
            .catch(err => console.log(err));
    }

    fetchRooms() {
        axios.get(this.apiGatewayUrl + "/registry/rooms")
            .then(res => console.log(res.data.rooms))
            .catch(err => console.log(err));
    }
}