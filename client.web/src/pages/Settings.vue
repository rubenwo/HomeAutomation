<template>
  <div class="content">
    <div class="md-layout-item md-medium-size-50 md-xsmall-size-100 md-size-25">
      <vue-form-generator :schema="schema" :model="model" :options="formOptions"></vue-form-generator>
    </div>
  </div>
</template>

<script>
import { apiClient } from "../main";
import VueFormGenerator from "vue-form-generator";
export default {
  components: {
    "vue-form-generator": VueFormGenerator.component
  },
  async created() {
    let id = this.$route.params.id;
    let device = await apiClient.fetchDeviceByID(id);
    this.device = device;
    this.model.id = this.device.identifier;
    this.model.name= this.device.name;
    this.model.type = this.device.device_type;
    this.model.controller_name = this.device.controller_name;
    this.model.ip_address = this.device.ip_address;
    this.model.room_identifier = this.device.room_identifier;
    console.log(this.device)
  },
  data() {
    return {
      device: {},
      model: {
        id: "",
        name: "",
        type: "",
        controller_name: "",
        ip_address: "",
        room_identifier: ""
      },
      schema: {
        fields: [
          {
            type: "input",
            inputType: "text",
            label: "ID (disabled text field)",
            model: "id",
            readonly: true,
            disabled: true
          },
          {
            type: "input",
            inputType: "text",
            label: "Name",
            model: "name",
            placeholder: "name",
            featured: true,
            required: true
          },
          {
            type: "input",
            inputType: "text",
            label: "Type",
            model: "type",
            readonly: true,
            disabled: true
          }, {
            type: "input",
            inputType: "text",
            label: "Controller Name",
            model: "controller_name",
            placeholder: "name",
            featured: true,
            required: true
          }, {
            type: "input",
            inputType: "text",
            label: "IP Address",
            model: "ip_address",
            readonly: true,
            disabled: true
          }, {
            type: "input",
            inputType: "text",
            label: "Room Identifier",
            model: "room_identifier",
            placeholder: "room",
            featured: true,
            required: true
          },
        ]
      },
      formOptions: {
        validateAfterLoad: true,
        validateAfterChanged: true,
        validateAsync: true
      }
    };
  }
};
</script>