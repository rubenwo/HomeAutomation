import axios from "axios";
//TODO: Implement async/await in fetch functions
export default class ApiClient {

    constructor(apiGatewayUrl) {
        this.apiGatewayUrl = apiGatewayUrl;
    }

    sendRequest(url, method, body) {
        console.log(url, method, body)
    }

    async fetchDevices() {
        let response = await axios.get(this.apiGatewayUrl + "/registry/devices")
        return response.data.devices;
    }

    fetchRooms() {
        axios.get(this.apiGatewayUrl + "/registry/rooms")
            .then(res => console.log(res.data.rooms))
            .catch(err => console.log(err));
    }
}