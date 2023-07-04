<template>
  <v-form @submit.prevent="login">
    <v-container>
      <h1>Sign In</h1>
      <v-alert v-if="loginFailed" id="login_failed" type="error">Invalid login.</v-alert>
      <v-text-field id="username" v-model="userDetails.username" label="Email address" type="email"/>
      <v-text-field id="password" v-model="userDetails.password" label="Password" type="password"/>
      <v-btn id="submit_login" type="submit">Sign In</v-btn>
    </v-container>
  </v-form>
</template>
<script>
import {bus} from '@/main'
import {checkResponseStatus} from '@/util/handlers'

export default {
  name: "Login",
  data() {
    return {
      userDetails: {
        username: "",
        password: ""
      },
      loginFailed: false
    };
  },
  methods: {
    login: function () {
      fetch(`${process.env.VUE_APP_SERVER_URL}/api/login`, {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.userDetails)
      }).then(checkResponseStatus).then(response => {
        localStorage.auth = JSON.stringify(response);
        bus.$emit('loginStateChange', true);
        this.$router.push('/');
        this.$forceUpdate();
      }).catch(error => {
        if (error.response.status === 401) {
          this.loginFailed = true
        } else {
          console.error(error); // eslint-disable-line no-console
        }
      });
    }
  }
}
</script>
