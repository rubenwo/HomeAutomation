<template>
  <md-toolbar md-elevation="0" class="md-transparent">
    <div class="md-toolbar-row">
      <div class="md-toolbar-section-start">
        <h3 class="md-title">{{ $route.name }}</h3>
      </div>
      <div class="md-toolbar-section-end">
        <md-button
          class="md-just-icon md-simple md-toolbar-toggle"
          :class="{ toggled: $sidebar.showSidebar }"
          @click="toggleSidebar"
        >
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </md-button>

        <div class="md-collapse">
          <md-list>
            <md-list-item href="#/">
              <i class="material-icons">dashboard</i>
              <p class="hidden-lg hidden-md">Dashboard</p>
            </md-list-item>

            <md-list-item href="#" class="dropdown">
              <drop-down>
                <a slot="title" class="dropdown-toggle" data-toggle="dropdown">
                  <i class="material-icons">notifications</i>
                  <span class="notification">{{notifications.length}}</span>
                  <p class="hidden-lg hidden-md">Notifications</p>
                </a>
                <ul
                  class="dropdown-menu dropdown-menu-right"
                  v-bind:key="notication.identifier"
                  v-for="notification in notifications"
                >
                  <li>
                    <a href="#/notifications">{{notification.name}}</a>
                  </li>
                </ul>
              </drop-down>
            </md-list-item>
          </md-list>
        </div>
      </div>
    </div>
  </md-toolbar>
</template>

<script>
export default {
  components: {},
  data() {
    return { notifications: [] };
  },
  methods: {
    toggleSidebar() {
      this.$sidebar.displaySidebar(!this.$sidebar.showSidebar);
    },
    addNotification(notification) {
      this.notifications.push(notification);
    }
  }
};
</script>

<style lang="css"></style>
