<template>
  <v-form ref="form" @submit.prevent="change_password" v-model="valid">
    <v-container>
      <h1>Change Password</h1>
      <v-text-field
          id="new_password"
          v-model="new_password"
          autocomplete="off"
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :rules="[rules.required, rules.min]"
          :type="show ? 'text' : 'password'"
          label="New password"
          hint="At least 5 characters"
          @click:append="show = !show"
      ></v-text-field>
      <v-text-field
          id="confirm_new_password"
          v-model="confirm_new_password"
          autocomplete="off"
          :rules="[rules.match]"
          :type="show ? 'text' : 'password'"
          label="Confirm new password"
      ></v-text-field>
      <v-btn id="submit_change_password" type="submit" :disabled="!valid">Change My Password</v-btn>
    </v-container>
  </v-form>
</template>
<script>
import {bus} from "@/main";

export default {
  name: "ChangePassword",
  data() {
    return {
      valid: true,
      show: false,
      new_password: "",
      confirm_new_password: "",
      rules: {
        required: v => !!v || 'Required.',
        min: v => v.length >= 5 || 'Min 5 characters',
        match: v => v === this.new_password || 'Passwords must match'
      }
    };
  },
  methods: {
    change_password: function () {
      if (this.$refs.form.validate()) {
        this.$router.push('/')
        this.$forceUpdate()
        bus.$emit('showSnackbar', "Password Update Successful")
      }
    }
  }
}
</script>