<template>
  <div>
    <v-menu offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-btn id="user_dropdown" v-bind="attrs" v-on="on">{{ username }}</v-btn>
      </template>
      <v-list>
        <v-list-item v-if="is_admin_or_committee" id="create_copy" href="/#/createCopy">
          <v-list-item-title>Create Library Items</v-list-item-title>
        </v-list-item>
        <v-list-item v-if="is_admin" id="create_user" href="/#/createUser">
          <v-list-item-title>Create User</v-list-item-title>
        </v-list-item>
        <v-list-item v-if="is_admin" id="list_users" href="/#/listUsers">
          <v-list-item-title>List Users</v-list-item-title>
        </v-list-item>
        <v-list-item id="password_change" href="/#/changePassword">
          <v-list-item-title>Change Password</v-list-item-title>
        </v-list-item>
        <v-list-item id="logout" href="#" v-on:click="logout()">
          <v-list-item-title>Logout</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </div>
</template>
<script>
import {bus} from '@/main'

function auth() {
  return JSON.parse(localStorage.auth)
}

export default {
  name: "UserDropdown",
  methods: {
    logout: function () {
      localStorage.removeItem("auth")
      bus.$emit('loginStateChange', false);
      if (this.$route.path !== '/') this.$router.push('/');
      this.$forceUpdate();
      fetch(`${process.env.VUE_APP_SERVER_URL}/logoff`, {
        method: 'POST',
        redirect: 'manual'
      }).catch(error => {
        console.error(error); // eslint-disable-line no-console
      });
    }
  },
  computed: {
    username: function () {
      return auth().username
    },
    is_admin: function () {
      return auth().roles.includes('ROLE_ADMIN')
    },
    is_admin_or_committee: function () {
      return auth().roles.includes('ROLE_ADMIN') || auth().roles.includes('ROLE_COMMITTEE')
    }
  }
}
</script>
