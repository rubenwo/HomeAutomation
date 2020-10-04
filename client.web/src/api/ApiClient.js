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

    async fetchRooms() {
        let response = await axios.get(this.apiGatewayUrl + "/registry/rooms")
        return response.data.rooms;
    }

    async fetchDeviceByID(id){
        let response = await axios.get(this.apiGatewayUrl + "/registry/devices/"+id)
        return response.data;
    }

    async getUser(){
        let response = await axios.get(this.apiGatewayUrl + "/hue/pair")
        return response.data.user;
    }

    async fetchLamps(){
        let response = await axios.get(this.apiGatewayUrl + "/hue/lamps");
        return response.data.lamps;
    }

    async fetchLampId(){
        let response = await axios.get(this.apiGatewayUrl + "/hue/lamp/:identifier");
        return response.data.lamp;
    }
    
    async changeLampId(id){
        let response = await axios.put(this.apiGatewayUrl + "/hue/lamp/:identifier")
        return response.data.lamp.id;
    }

    async changeStateLamp(state){
        let response = await axios.put(this.apiGatewayUrl + "/hue/lamp/:identifier")
        return response.data.lamp.state;
    }

    async changeIrName(name){
        let response = await axios.put(this.apiGatewayUrl + "/ir_controller/encoding_tables")
        return response.data.ir.name;
    }

    async fetchIrName(){
        let response = await axios.get(this.apiGatewayUrl + "/ir_controller/encoding_tables")
        return response.data.ir;
    }

    async changeIrCommand(command){
        let response = await axios.put(this.apiGatewayUrl + "/ir_controller/encoding_tables")
        return response.data.ir.command;
    }

    async fetchIrCommand(){
        let response = await axios.get(this.apiGatewayUrl + "/ir_controller/encoding_tables")
        return response.data.ir.command;
    }

    //TODO: HUE & IR
}