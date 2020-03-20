import Vue from 'vue'
import App from 'pages/AdminPanel.vue'
import router from 'routers/router-adminpanel'

Vue.config.devtools = true;

let app = new Vue({
    el: '#adminpanel',
    router,
    render: a => a(App)
});