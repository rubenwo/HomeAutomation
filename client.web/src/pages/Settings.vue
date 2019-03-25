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
    console.log(this.device)
  },
  data() {
    return {
      device: {},
      model: {
        id: 0,
        time: "",
        date: "",
        timezone: [],
        timesync: true,
        volume: "",
        treble: "",
        bass: "",
        ip_address: "",
        port: 0,
        route: "",
        alarm_time: "",
        re_arm: false,
        audio_type: [],
        alarm_radio_channel: [],
        factory_reset: false
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
            label: "Time",
            model: "time",
            placeholder: "00:00",
            featured: true,
            required: true
          },
          {
            type: "input",
            inputType: "text",
            label: "Date",
            model: "date",
            placeholder: "yy/mm/dd",
            featured: true,
            required: true
          },
          {
            type: "select",
            label: "Timezone",
            model: "timezone",
            values: [
              0,
              1,
              2,
              3,
              4,
              5,
              6,
              7,
              8,
              9,
              10,
              11,
              12,
              -1,
              -3,
              -4,
              -5,
              -6,
              -7,
              -8,
              -9,
              -10,
              -11,
              -12
            ],
            required: true
          },
          {
            type: "checkbox",
            label: "Timesync",
            model: "timesync",
            default: true
          },
          {
            type: "input",
            inputType: "text",
            label: "Volume",
            model: "volume",
            placeholder: 0,
            featured: true,
            required: true
          },
          {
            type: "input",
            inputType: "text",
            label: "Treble",
            model: "treble",
            placeholder: 0,
            featured: true,
            required: true
          },
          {
            type: "input",
            inputType: "text",
            label: "Bass",
            model: "bass",
            placeholder: 0,
            featured: true,
            required: true
          },
          {
            type: "input",
            inputType: "text",
            label: "Radio IP Address",
            model: "ip_address",
            placeholder: "123.123.123.123",
            featured: true,
            required: true
          },
          {
            type: "input",
            inputType: "text",
            label: "Radio Port",
            model: "port",
            placeholder: 80,
            featured: true,
            required: true
          },
          {
            type: "input",
            inputType: "text",
            label: "Radio route",
            model: "route",
            placeholder: "/",
            featured: true,
            required: true
          },
          {
            type: "input",
            inputType: "text",
            label: "Alarm Time",
            model: "alarm_time",
            placeholder: "00:00",
            featured: true,
            required: true
          },
          {
            type: "checkbox",
            label: "Re Arm",
            model: "re_arm",
            default: false
          },
          {
            type: "select",
            label: "Alarm Audio Type",
            model: "audio_type",
            values: ["Beep", "Radio"],
            required: true
          },
          {
            type: "select",
            label: "Alarm Radio channel",
            model: "alarm_radio_channel",
            values: ["C-rock", "Jazz", "Aardschok"],
            required: true
          },
          {
            type: "checkbox",
            label: "Factory Reset",
            model: "factory_reset",
            default: false
          }
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