<template>
  <div>
    <UserDropdown v-if="loginState"/>
    <v-btn v-else id="open_login" href="/#/login/">Login</v-btn>
  </div>
</template>
<script>
import {bus} from '@/main'
import UserDropdown from "@/components/UserDropdown.vue";

export default {
  name: "UserDropdownLoginButtonToggle",
  components: {UserDropdown},
  data() {
    return {
      loginState: localStorage.auth != null
    }
  },
  created() {
    bus.$on('loginStateChange', () => {
      this.loginState = localStorage.auth != null
    })
  }
}
</script>
