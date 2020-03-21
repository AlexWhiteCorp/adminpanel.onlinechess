<template>
    <div id="header-wrapper" class="header-wrapper header-grid-cell">
        <img src="/images/dashboard-icon.png" alt="Icon" id="dashboard-switch" class="dashboard-icon">

        <div id="load-timer" class="load-time">Reload at: 0 sec</div>

        <div class="user-icon-div">
            <img src="/images/user-icon.png" alt="User" id="user-icon" class="user-icon">
        </div>
    </div>
</template>
<script>
    export default {
        data(){
            return{
            }
        },
        computed: {
        },
        components: {
        },
        methods: {
        },
        mounted(){
            let header = document.getElementById('header-wrapper');
            window.header = header;
            let loadTimer = document.getElementById('load-timer');
            window.setLoadTime = function (sec) {
                loadTimer.innerText = 'Reload at: ' + sec + ' sec';
            };

            document.getElementById('dashboard-switch').onclick = function(){
                if(window.isShowDashboard()){
                    window.hideDashboard();
                }
                else{
                    window.showDashboard();
                }
            };
        }
    }
</script>
<style scope>
    .header-wrapper{
        background-color: #242526;
        height: auto;
        border-bottom: 1px solid #313233;
        border-left: 1px solid #18191a;
        border-right: 1px solid #18191a;

        display: grid;
        grid-template-columns: 60px auto 150px 80px;
        grid-template-areas: "dashboard-icon . load-time user-icon";
    }

    .header-grid-cell {
       grid-area: header;
    }

    .dashboard-icon{
        height: 14px;
        padding: 23px;
        cursor: pointer;

        grid-area: dashboard-icon;
    }

    .load-time{
        grid-area: load-time;
        margin: auto 0 auto 0;

        color: #e4e6eb;
    }

    .user-icon-div{
        grid-area: user-icon;
    }

    .user-icon{
        height: 40px;
        margin: 10px 30px 10px 10px;
        cursor: pointer;
    }

    @media screen and (max-width: 1025px){
        .header-wrapper{
            position: fixed;
            z-index: 100;
            top: 0;
            left: 0;
            width: 100%;
        }
    }
</style>