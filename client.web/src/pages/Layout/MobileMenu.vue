<template>
  <ul class="nav nav-mobile-menu">
    <li>
      <a href="#/dashboard" class="dropdown-toggle" data-toggle="dropdown">
        <i class="material-icons">dashboard</i>
        <p>Dashboard</p>
      </a>
    </li>
    <li>
      <drop-down>
        <a slot="title" class="dropdown-toggle" data-toggle="dropdown">
          <i class="material-icons">notifications</i>
          <span class="notification">1</span>
          <p>Notifications</p>
        </a>
        <ul class="dropdown-menu dropdown-menu-right">
          <li>
            <a href="#/notifications">Test notification</a>
          </li>
        </ul>
      </drop-down>
    </li>
  </ul>
</template>
<script>
export default {};
</script>
