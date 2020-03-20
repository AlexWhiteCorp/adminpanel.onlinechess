<template>
    <div id="active-notification" class="active-notification">
        <div id="active-notification-close" class="active-notification-close">×</div>
        <div id="active-notification-message" class="active-notification-message"></div>
    </div>
</template>
<script>
    export default {
        data(){
            return{}
        },
        mounted(){
            let component = this;
            let activeNotification = document.getElementById('active-notification');
            let activeNotificationMessage = document.getElementById('active-notification-message');
            let targetPosition;

            activeNotification.style.right = '10px';
            targetPosition = document.getElementsByClassName('header-wrapper')[0].offsetHeight + 7;

            document.getElementById('active-notification').onclick = function() {
                hide();
            };

            window.showActiveNotification = function(type, message) {
                if(type === 'success'){
                    activeNotification.style.color = '#01ae1f';
                    activeNotification.style.borderColor = '#007715';
                    activeNotificationMessage.style.background = 'rgba(0, 94, 27, 0.6)';
                }
                else if(type === 'error'){
                    activeNotification.style.color = '#c73232';
                    activeNotification.style.borderColor = '#c73232';
                    activeNotificationMessage.style.background = 'rgba(216, 121, 121, 0.1)';
                }
                activeNotification.style.display = 'block';

                activeNotificationMessage.innerText = message;
                var start = Date.now();
                var timer = setInterval(function() {
                    var timePassed = Date.now() - start;
                    if(timePassed > targetPosition * 3) {
                        activeNotification.style.top = targetPosition + 'px';
                        clearInterval(timer);
                        setTimeout(hide, 2500);
                        return;
                    }
                    draw(timePassed);
                }, 20);
            };

            window.ActiveNotification = {
                SUCCESS : 'success',
                ERROR : 'error'
            };

            function hide() {
                activeNotification.style.display = 'none';
                activeNotification.style.top = '-300px';
            }

            function draw(timePassed) {
                activeNotification.style.top = timePassed / 3 + 'px';
            }

        }
    }
</script>
<style>
    .active-notification{
        position: fixed;
        z-index: 10;
        top: -300px;
        min-width: 250px;
        max-width: 250px;
        min-height: 50px;
        max-height: 300px;
        border: 2px solid;
        border-radius: 5px;
    }

    .active-notification-message{
        float: left;
        min-height: 50px;
        margin: -30px 0px 0px 0px;
        padding: 10px 15px 10px 15px;

        text-align: center;
        font-size: 22px;
    }

    .active-notification-close{
        margin: -12px 4px 0px 0px;
        padding: 3px;
        cursor: pointer;

        text-align: right;
        font-size: 28px;
    }
</style>