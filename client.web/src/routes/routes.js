import DashboardLayout from "@/pages/Layout/DashboardLayout.vue";

import Dashboard from "@/pages/Dashboard.vue";
import Devices from "@/pages/Devices.vue";
import Notifications from "@/pages/Notifications.vue";
import Settings from "@/pages/Settings.vue";
const routes = [
  {
    path: "/",
    component: DashboardLayout,
    redirect: "/dashboard",
    children: [
      {
        path: "dashboard",
        name: "Dashboard",
        component: Dashboard
      },
      {
        path: "devices",
        name: "Devices",
        component: Devices
      },
      {
        path:"settings/:id",
        name:"Settings",
        component: Settings
      },
      {
        path: "notifications",
        name: "Notifications",
        component: Notifications
      }
    ]
  }
];

export default routes;
