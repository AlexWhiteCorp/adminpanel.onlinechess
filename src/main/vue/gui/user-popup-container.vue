<template>
    <div id="user-popup-container" class="popup-container">
        <div class="popup-container-header">
            <img class="popup-container-user-icon" src="/images/user-icon.png" alt="">
            <div class="popup-container-user-name" v-text="nickname">Nickname</div>
            <div class="popup-container-user-role" v-text="role">Role</div>
            </div>
        <div class="popup-container-item">
            <img id="logout-item-icon" src="/images/buttons/logout.png" alt="">
            <a id="logout-item" >Logout</a>
            <form name="logoutForm" action="/logout" method="post" hidden>
                <input id="token-field" hidden value="_csrf">
                <input hidden type="submit" value="Sign Out"/>
            </form>
        </div>

    </div>
</template>
<script>
    export default {
        data(){
            return{
                'nickname' : ServerData.nickname,
                'role' : ServerData.role
            }
        },
        methods: {
            push(path){
                this.$router.push(path);
            }
        },
        mounted(){
            document.getElementById('logout-item').onclick = function(){
                let token = $("meta[name='_csrf']").attr("content");
                $.ajax({
                    type: 'POST',
                    url: '/logout',
                    data: JSON.stringify({}),
                    headers: {
                        'X-CSRF-TOKEN': token
                    },
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function(data){
                        document.location.href = '/login';
                    },
                    error: function(error) {
                        document.location.href = '/login';
                    }
                });
            };

            let userPopupContainer = document.getElementById('user-popup-container');

            let showingUserPopupContainer = false;

            userPopupContainer.style.top = '-2px';
            userPopupContainer.style.minWidth = 250 + 'px';
            userPopupContainer.childNodes[0].style.minHeight = '45px';
            userPopupContainer.style.right = 0 + 'px';
            userPopupContainer.style.display = 'none';

            document.getElementById('user-icon').onclick = showUserPopupContainer;

            function showUserPopupContainer() {
                if(!showingUserPopupContainer){
                    showingUserPopupContainer = true;
                    userPopupContainer.style.display = 'block';
                    var timer = setInterval(function() {
                        userPopupContainer.style.minHeight = userPopupContainer.offsetHeight + 5 + 'px';
                        userPopupContainer.style.maxHeight = userPopupContainer.offsetHeight + 5 + 'px';
                        if(userPopupContainer.offsetHeight >= 100){
                            userPopupContainer.style.minHeight = '100px';
                            userPopupContainer.style.maxHeight = '100px';
                            clearInterval(timer);
                            window.onclick = function(e){
                                e = e || window.event;
                                if(getClickedElement(e) != 'user-popup-container'){
                                    hideUserPopupContainer();
                                    window.onclick = null;
                                }
                            }
                        }
                    }, 5);
                }

                return false;
            }

            function hideUserPopupContainer() {
                if(showingUserPopupContainer){
                    showingUserPopupContainer = false;
                    var timer = setInterval(function() {
                        userPopupContainer.style.minHeight = userPopupContainer.offsetHeight - 40 + 'px';
                        userPopupContainer.style.maxHeight = userPopupContainer.offsetHeight - 40 + 'px';
                        if(userPopupContainer.offsetHeight <= 45){
                            userPopupContainer.style.display = 'none';
                            userPopupContainer.style.minHeight = '30px';
                            userPopupContainer.style.maxHeight = '30px';
                            clearInterval(timer);
                        }
                    }, 5);
                }
                return false;
            }

            function getClickedElement(e){
                if (navigator.userAgent.match('MSIE') || navigator.userAgent.match('Gecko')) {
                    var elem = document.elementFromPoint(e.clientX,e.clientY);
                } else {
                    var elem = document.elementFromPoint(e.pageX,e.pageY);
                }
                return elem.id;
            }
        }
    }
</script>
<style>
    .popup-container{
        position: fixed;
        z-index: 7;
        background: #18191a;
        min-width: 250px;
        max-width: 250px;
        min-height: 30px;
        max-height: 30px;
        padding: 10px 0px 5px 0px;

        border: 2px solid #313233;
        border-radius: 5px;
        border-top-left-radius: 0px;
        border-top-right-radius: 0px;
        box-shadow: 0px 5px 15px #242526;

        overflow: hidden;

        color: #e4e6eb;
        font-size: 18px;
        line-height: 18px;
    }

    .popup-container-header{
        min-height: 45px;
        max-height: 45px;
        border-bottom: 1px solid #313233;
        padding: 0px 0px 5px 15px;

        display: grid;
        grid-gap: 0px;
        grid-template-columns: 40px auto;
        grid-template-rows: 20px;
        grid-template-areas: "icon nickname"
                             "icon role";

    }

    .popup-container-user-icon{
        grid-area: icon;
        float: left;
        width: 40px;
    }

    .popup-container-user-name{
        grid-area: nickname;
        float: left;
        margin: 6px 0px 0px 12px;

        font-size: 20px;
    }

    .popup-container-user-role{
        grid-area: role;
        float: left;
        margin: 6px 0px 0px 12px;

        color: #6f7576;
        font-size: 14px;
    }

    .popup-container-item{
        height: 50px;
        padding: 10px 10px 10px 25px;
        cursor: pointer;
        display: grid;
        grid-template-columns: 40px auto;
        grid-template-areas: "icon text";

        line-height: 40px;
    }

    .popup-container-item:hover{
        background: #1c1d1f;
        filter: brightness(1.5);
    }

    #logout-item{
        grid-area: text;
        color: #e4e6eb;
        text-decoration: none;
    }

    #logout-item-icon{
        grid-area: icon;
        height: 20px;
        margin: 10px 10px 10px 10px;
    }
</style>