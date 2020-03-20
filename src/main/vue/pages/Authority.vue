<template>
    <div id="main-wrapper" class="wrapper">
        <login/>
    </div>
</template>
<script>
    import login from 'authority/login.vue'

    export default {
        data(){
            return{
            }
        },
        computed: {
        },
        components: {
            'login' : login
        },
        methods: {
            push(path){
                this.$router.push(path);
            }
        },
        mounted(){
            let component = this;

            document.body.style.minHeight = window.innerHeight + 'px';
            document.body.className = 'scrollable';
        }
    }
</script>
<style>

</style>