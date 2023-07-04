import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './assets/css/bootstrap.css'
import './assets/css/grails.css'
import './assets/css/main.css'
import vuetify from './plugins/vuetify'

Vue.config.productionTip = false

export const bus = new Vue();

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    components: {App},
    vuetify,
    template: '<App/>'
})
