<template>
  <div class="content">
    <div class="md-layout" v-bind:key="device.controller_name" v-for="device in devices">
      <div class="md-layout-item md-medium-size-50 md-xsmall-size-100 md-size-25">
        <a v-bind:href="'#/settings/'+device.identifier">
        <stats-card data-background-color="green">
          <template slot="header">
            <md-icon>store</md-icon>
          </template>
          <template slot="content">
            <label class="switch">
              <input type="checkbox">
              <span class="slider round"></span>
            </label>
            <h3 class="title">{{device.name}}</h3>
            <h4>{{device.device_type}}</h4>
          </template>

          <template slot="footer">
            <div class="stats">
              <md-icon>date_range</md-icon>Last 24 Hours
            </div>
          </template>
        </stats-card>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import { StatsCard } from "@/components";
import { apiClient } from "../main";




export default {
  components: {
    StatsCard,
  },

  async created() {
    let id = this.$route.params.id;
    let device = await apiClient.fetchDevices(id);
    let devices = await apiClient.fetchDevices();
    if (devices) {
      this.devices = devices;
    }
  },
  data() {
    return {
      devices: [],
      responsiveOptions: [
        [
          "screen and (max-width: 640px)",
          {
            seriesBarDistance: 5,
            axisX: {
              labelInterpolationFnc: function(value) {
                return value[0];
              }
            }
          }
        ]
      ]
    };
  },
  methods:{
div_show()
            {

            },
myFunction() {
 
  
}
  }
};
</script>

<style scoped>
/* The switch - the box around the slider */
.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

/* Hide default HTML checkbox */
.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

/* The slider */
.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: 0.4s;
  transition: 0.4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: 0.4s;
  transition: 0.4s;
}

input:checked + .slider {
  background-color: #2196f3;
}

input:focus + .slider {
  box-shadow: 0 0 1px #2196f3;
}

input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}

.popup {
  position: relative;
  display: inline-block;
  cursor: pointer;
}

/* The actual popup (appears on top) */
.popup .popuptext {
  visibility: hidden;
  width: 160px;
  background-color: #555;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 8px 0;
  position: absolute;
  z-index: 1;
  bottom: 125%;
  left: 50%;
  margin-left: -80px;
}

/* Popup arrow */
.popup .popuptext::after {
  content: "";
  position: absolute;
  top: 100%;
  left: 50%;
  margin-left: -5px;
  border-width: 5px;
  border-style: solid;
  border-color: #555 transparent transparent transparent;
}

/* Toggle this class when clicking on the popup container (hide and show the popup) */
.popup .show {
  visibility: visible;
  -webkit-animation: fadeIn 1s;
  animation: fadeIn 1s
}

/* Add animation (fade in the popup) */
@-webkit-keyframes fadeIn {
  from {opacity: 0;} 
  to {opacity: 1;}
}

@keyframes fadeIn {
  from {opacity: 0;}
  to {opacity:1 ;}
}
</style>
