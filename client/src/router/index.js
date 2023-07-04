import Vue from 'vue'
import Router from 'vue-router'
import Welcome from '@/components/Welcome'

import TestBggAutocomplete from '@/views/TestBggAutocomplete'
import TestQrcodeReader from '@/views/TestQrcodeReader'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'Welcome',
            component: Welcome
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('@/views/Login.vue')
        },
        {
            path: '/test/bgg-autocomplete',
            name: 'TestBggAutocomplete',
            component: TestBggAutocomplete
        },
        {
            path: '/test/qrcode-reader',
            name: 'TestQrcodeReader',
            component: TestQrcodeReader
        }
    ]
})
