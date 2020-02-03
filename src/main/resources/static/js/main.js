import Vue from 'vue';
import Vuetify from 'vuetify';
import '@babel/polyfill';
import 'api/resource'
import router from 'router/router';
import index from 'pages/index.vue';
import store from 'store/store';
import 'vuetify/dist/vuetify.min.css';

Vue.use(Vuetify)

new Vue({
    el: '#app',
    vuetify: new Vuetify(),
    store,
    router,
    render: a => a(index)
})
