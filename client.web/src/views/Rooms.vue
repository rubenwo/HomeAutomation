<template>
  <div>
    <h1>Rooms</h1>
  </div>
</template>
<script>
import axios from "axios";
export default {
  name: "Rooms",
  components: {},
  created() {
    axios
      .get("http://localhost/rooms")
      .then(res => console.log(res))
      .catch(err => console.log(err));
  }
};
</script>

<style scoped>
</style>
