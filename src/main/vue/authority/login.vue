<template>
    <div>
        <a href="/"><img src="/images/logo.png" alt="" id="login-form-div-logo" class="login-form-div-logo"></a>
        <div id="login-form-div" class="login-form-div">
            <form id="login-form">
                <div class="formDivTitle">Login</div>
                <input id="l-email" class="inputField" name="nickname" type="text" placeholder="Nickname" spellcheck="false"><br>
                <input id="l-password" class="inputField" name="password" type="password" placeholder="Password" spellcheck="false"><br>
                <div id="errorMessage" class="errorMessage"></div>
                <div class="inputButtonDiv">
                    <input id="l-submit" class="inputButton" name="button" value="Log in" type="submit">
                </div>
            </form>
        </div>

        <div id="loading-icon" class="lds-dual-ring"></div>
        </div>
    </div>
</template>
<script>
    import VueResource from 'vue-resource'

    export default {
        methods: {
            push(path){
                this.$router.push(path)
            },
            process: function(e, p, f){
                const d = { nickname : e, password : p};

                this.$resource('/login').save(d, {}).then(function (responce) {
                    console.log(responce.data);
                    f(responce.data.error);
                });
            }
        },
        mounted(){
            let formDiv = document.getElementById('login-form-div');
            formDiv.style.maxWidth = document.body.offsetWidth - 4 + 'px';
            formDiv.style.left = (window.innerWidth - formDiv.offsetWidth) / 2 + 'px';
            formDiv.style.top = (window.innerHeight - formDiv.offsetHeight) / 2 + 'px';
            document.body.style.minHeight = formDiv.offsetHeight + 50 + 'px';


            let formDivLogo = document.getElementById('login-form-div-logo');
            formDivLogo.style.left = (window.innerWidth - formDivLogo.offsetWidth) / 2 + 'px';
            formDivLogo.style.top = (window.innerHeight - formDiv.offsetHeight - formDivLogo.offsetHeight) / 2 + 'px';

            let loadingIcon = document.getElementById('loading-icon');
            loadingIcon.style.left = formDivLogo.offsetLeft - 5 + 'px';
            loadingIcon.style.top = formDivLogo.offsetTop - 5 + 'px';
            showLoadinIcon(false);
            
            let email = document.getElementById('l-email');
            let password = document.getElementById('l-password');
            if(document.referrer.indexOf('login') === -1 && document.referrer.indexOf('registration') === -1)
                localStorage.setItem('url', document.referrer);

            if(localStorage.getItem('login_email') != null) email.value = localStorage.getItem('login_email');

            document.getElementById('l-submit').onclick = function(){
                if(email.value === '') {
                    document.getElementById('errorMessage').innerText = 'Enter your email, please!';
                    return false;
                }
                if(password.value === ''){
                    document.getElementById('errorMessage').innerText = 'Enter your password, please!';
                    return false;
                }
                localStorage.setItem('login_email', email.value);

                let token = $("meta[name='_csrf']").attr("content");
                $.ajax({
                    type: 'POST',
                    url: '/login',
                    data: JSON.stringify(
                        {
                            nickname : email.value,
                            password : password.value
                        }
                    ),
                    headers: {
                        'X-CSRF-TOKEN': token
                    },
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function(data){
                        showLoadinIcon(false);
                        if(data.error === ''){
                            document.location.href = '/adminpanel';
                        }
                        else {
                            document.getElementById('errorMessage').innerText = data.error;
                            password.value = '';
                        }
                    }
                });
                showLoadinIcon(true);

                return false;
            };

            function showLoadinIcon(show) {
                loadingIcon.style.display = show ? 'block' : 'none';
            }
        }
    }
</script>
<style>
    body{
        background-color: #18191a;
        background: linear-gradient(rgba(0, 0, 0, 0.95), rgba(0, 0, 0, 0.8)), url(/images/login-background.jpeg);
        background-size: cover;
        background-position: center;
    }

    .formDivTitle{
        margin: 0px 0px 40px 0px;
        filter: brightness(1.3);

        color: #e4e6eb;
        text-align: center;
        font-size: 28px;
        font-weight: bold;
    }

    .login-form-div{
        position: absolute;
        background: rgba(255, 255, 255, 0.015);
        width: 300px;
        max-width: 100%;
        border: solid 2px #080c08;
        padding: 45px 30px 30px 30px;
        overflow: hidden;

        font-size: 22px;
        line-height: 28px;
        white-space: nowrap;
    }

    .login-form-div-logo{
        position: absolute;
        background: #001914;
        z-index: 10;
        width: 50px;
        height: 50px;
        border-radius: 100px;
        -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
    }

    .inputField{
        background-color: #030e0c;
        width: 90%;
        height: 30px;
        margin: 0px 0px 20px 0px;
        padding: 3px 10px 3px 10px;
        border: solid 1px #080c08;
        outline: none;

        font-size: 17px;
        font-weight: bold;
        color: #e4e6eb;
        text-align: center;
    }


    .inputField::placeholder{
        font-size: 14px;
        color: #6f7576;
        font-weight: normal;
    }

    .errorMessage{
        margin: 0px 0px 15px 0px;

        color: #c73232;
        text-align: center;
    }

    .inputButtonDiv{
        float: left;
        width: 100%;

        text-align: center;
    }

    .inputButton{
        background-color: rgba(20, 143, 25, 0.08);
        width: 50%;
        height: 34px;
        margin: 0px 0px 20px 0;
        padding: 5px 10px 5px 10px;
        border: 2px solid #113412;
        cursor: pointer;
        outline: none;
        filter: brightness(1.4);

        font-size: 18px;
        font-weight: bold;
        color: #226625;
        text-align: center;
    }

    .inputButton:hover{
        filter: brightness(1.6);
    }

    .lds-dual-ring {
        position: absolute;
        width: 62px;
        height: 62px;
    }
    .lds-dual-ring:after {
        content: " ";
        display: block;
        width: 50px;
        height: 50px;
        border-radius: 50%;
        border: 6px solid;
        border-color: #113412 transparent #113412 transparent;
        animation: lds-dual-ring 1.2s linear infinite;
    }

    @keyframes lds-dual-ring {
        0% {
            transform: rotate(0deg);
        }
        100% {
            transform: rotate(360deg);
        }
    }
</style>