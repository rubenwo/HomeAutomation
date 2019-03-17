<template>
  <div class="content"></div>
</template>

<script>
import { StatsCard } from "@/components";
import { apiClient } from "../main";

export default {
  components: {
    StatsCard
  },
  created() {
    apiClient.fetchDevices();
  },
  data() {
    return {
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
  }
};
</script>
