<template>
  <div>
    <h1>Devices</h1>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Devices",
  components: {},
  created() {
    axios
      .get("http://localhost/devices")
      .then(res => console.log(res))
      .catch(err => console.log(err));
  }
};
</script>

<style scoped>
</style>
