<template>
    <div id="main-wrapper" class="wrapper">
        <header-wrapper/>
        <active-notification/>
        <user-popup-container/>
        <dashboard/>
        <content-wrapper/>
    </div>
</template>
<script>
    import headerWrapper from 'parts/header.vue'
    import contentWrapper from 'parts/content.vue'

    import dashBoard from 'components/dashboard.vue'
    import activeNotification from 'components/active-notification.vue'
    import userPopupContainer from 'gui/user-popup-container.vue'

    export default {
        data(){
            return{
            }
        },
        computed: {
        },
        components: {
            'headerWrapper' : headerWrapper,
            'active-notification' : activeNotification,
            'user-popup-container' : userPopupContainer,
            'dashboard' : dashBoard,
            'content-wrapper' : contentWrapper,
        },
        methods: {
            push(path){
                this.$router.push(path);
            }
        },
        mounted(){
            let component = this;
            if(document.location.pathname === '/adminpanel' || document.location.pathname === '/') component.push('/adminpanel/overview');
            let mainWrapper = document.getElementById('main-wrapper');
            window.mainWrapper = mainWrapper;

            document.body.style.minHeight = window.innerHeight + 'px';
            document.body.className = 'scrollable';
        }
    }
</script>
<style>
    .wrapper {
        position: relative;
        background: #18191a;
        min-height: 100%;
        display: grid;
        grid-gap: 0px;
        grid-template-columns: 250px auto;
        grid-template-rows: 60px auto;
        grid-template-areas:
                "dashboard header"
                "dashboard content";
    }

    @media screen and (max-width: 1024px){
        .wrapper{
            grid-template-columns: auto;
            grid-template-areas:
                    "header"
                    "content";
        }
    }
</style>