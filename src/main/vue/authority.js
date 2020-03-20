import Vue from 'vue'
import VueResource from 'vue-resource'
import App from 'pages/Authority.vue'

Vue.use(VueResource);
Vue.config.devtools = true;

let app = new Vue({
    el: '#authority',
    render: a => a(App)
});