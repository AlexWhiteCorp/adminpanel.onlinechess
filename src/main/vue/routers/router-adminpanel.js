import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

import overview from 'components/overview.vue'
import users from 'components/users.vue'
import clubs from 'components/clubs.vue'


const routes = [
    { path: '/adminpanel/overview', component: overview },
    { path: '/adminpanel/users', component: users },
    { path: '/adminpanel/clubs', component: clubs }
];

export default new VueRouter({
    mode: 'history',
    routes
})