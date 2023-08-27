<template>
  <v-app>
    <v-app-bar
        app
        color="primary"
        dark
    >
      <div class="d-flex align-center">
        <v-img
            alt="Vuetify Logo"
            class="shrink mr-2"
            contain
            src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
            transition="scale-transition"
            width="40"
        />

        <v-img
            alt="Vuetify Name"
            class="shrink mt-1 hidden-sm-and-down"
            contain
            min-width="100"
            src="https://cdn.vuetifyjs.com/images/logos/vuetify-name-dark.png"
            width="100"
        />
      </div>

      <v-spacer></v-spacer>

      <v-btn
          href="https://github.com/vuetifyjs/vuetify/releases/latest"
          target="_blank"
          text
      >
        <span class="mr-2">Latest Release</span>
        <v-icon>mdi-open-in-new</v-icon>
      </v-btn>
      <UserDropdownLoginButtonToggle/>
    </v-app-bar>

    <v-main>
      <router-view/>
    </v-main>
    <v-snackbar
        id="snackbar"
        v-model="show_snackbar"
    >
      {{ text }}
      <template v-slot:action="{ attrs }">
        <v-btn
            text
            v-bind="attrs"
            @click="show_snackbar = false"
        >
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </v-app>
</template>

<script>

import UserDropdownLoginButtonToggle from "@/components/UserDropdownLoginButtonToggle.vue";
import {bus} from "@/main";

export default {
  name: 'App',
  components: {UserDropdownLoginButtonToggle},

  data: () => ({
    show_snackbar: false,
    text: '',
  }),
  created() {
    bus.$on('showSnackbar', (text) => {
      this.show_snackbar = true
      this.text = text
    })
  }
}
</script>
