import Vue from 'vue'
import Router from 'vue-router'
import Welcome from '@/components/Welcome'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'Welcome',
            component: Welcome
        },
        {
            path: '/test/bgg-autocomplete',
            name: 'TestBggAutocomplete',
            component: () => import('@/views/TestBggAutocomplete')
        },
        {
            path: '/test/qrcode-reader',
            name: 'TestQrcodeReader',
            component: () => import('@/views/TestQrcodeReader')
        }
    ]
})
