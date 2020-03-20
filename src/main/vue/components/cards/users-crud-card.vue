<template>
    <div id="card" class="content-card card-9-column users-crud-card" card-id="" api-path="">
        <div class="crud-options-wrapper row">
            <div id="new-account-button" class="new-account-button option-button">
                <span>+</span>
                New Account
            </div>
            <div id="find-button" class="find-button option-button">
                <img src="/images/buttons/find.png" alt="">
                <span>Find</span>
            </div>
            <div id="save-changes-button" class="save-changes-button option-button">
                <span>S</span>
                Save
            </div>
        </div>
        <div class="users-table">
            <div id="users-table-filters" class="users-table-line users-table-filters">
                <input type="text" class="users-table-cell user-id new-record-field" placeholder="All"/>
                <input type="text" class="users-table-cell user-nickname new-record-field" placeholder="All"/>
                <input type="text" class="users-table-cell user-password new-record-field disabled-field"/>
                <div class="user-active custom-select">
                    <select>
                        <option value="ALL">All</option>
                        <option value="ACTIVE">ACTIVE</option>
                        <option value="NONACTIVE">NONACTIVE</option>
                        <option value="BANNED">BANNED</option>
                    </select>
                </div>
                <div class="user-role custom-select">
                    <select>
                        <option value="ALL">All</option>
                        <option value="PLAYER">PLAYER</option>
                        <option value="BOT">BOT</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>
                </div>
                <div class="users-table-cell user-last-login disabled-field"></div>
                <div id="filters-cancel" class="users-table-cell user-options">Cancel</div>
            </div>
            <div class="users-table-header users-table-line">
                <div class="users-table-cell">Id</div>
                <div class="users-table-cell">Nickname</div>
                <div class="users-table-cell">Password</div>
                <div class="users-table-cell">Status</div>
                <div class="users-table-cell">Role</div>
                <div class="users-table-cell">Last Login</div>
                <div class="users-table-cell">Options</div>
            </div>
            <div id="users-table-new-record" class="users-table-new-record users-table-line">
                <div class="users-table-cell user-id disabled-field"></div>
                <input type="text" class="users-table-cell user-nickname new-record-field" placeholder="Nickname"/>
                <input type="text" class="users-table-cell user-password new-record-field" placeholder="Password"/>
                <div class="user-active custom-select">
                    <select>
                        <option value="ACTIVE">ACTIVE</option>
                        <option value="NONACTIVE">NONACTIVE</option>
                        <option value="BANNED">BANNED</option>
                    </select>
                </div>
                <div class="user-role custom-select">
                    <select>
                        <option value="PLAYER">PLAYER</option>
                        <option value="BOT">BOT</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>
                </div>
                <div class="users-table-cell user-last-login disabled-field"></div>
                <div id="new-record-cancel" class="users-table-cell user-options">Cancel</div>
            </div>
            <div id="users-table-content" class="users-table-content scrollable"></div>
        </div>
    </div>
</template>
<script>
    const axios = require('axios').default;

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
            const gridData = 'data-v-dfba836a';

            const PLAYER = 'PLAYER';
            const ADMIN = 'ADMIN';
            const BOT = 'BOT';
            const BOT_MANAGER = 'BOT_MAN';

            const ACTIVE = 'ACTIVE';

            let card = document.getElementById('card');
            let usersTableContent = document.getElementById('users-table-content');
            let saveChangesButton = document.getElementById('save-changes-button');
            let findButton = document.getElementById('find-button');
            let newAccountButton = document.getElementById('new-account-button');

            let newRecordDiv = document.getElementById('users-table-new-record');
            let filtersDiv = document.getElementById('users-table-filters');

            let id = card.getAttribute('card-id').toLowerCase().replace(/ /g, '-');
            card.id = id + '-card';

            request('users', {}, function (data) {
                loadData(data);
            });

            card.style.minHeight = document.getElementById('content-wrapper').offsetHeight - 110 + 'px';
            card.style.maxHeight = document.getElementById('content-wrapper').offsetHeight - 110 + 'px';

            usersTableContent.style.minHeight = document.getElementById('content-wrapper').offsetHeight - 200 + 'px';
            usersTableContent.style.maxHeight = document.getElementById('content-wrapper').offsetHeight - 200 + 'px';

            function request(path, params, onResult) {
                axios
                    .get(window.getRestPath(path), {params: params})
                    .then(function(responce){
                        console.log(responce.data);

                        onResult(responce.data);
                    });
            }

            function loadData(data) {
                usersTableContent.innerText = '';

                for(let i in data){
                    localStorage.setItem(data[i]['id'], JSON.stringify(data[i]));

                    let record = document.createElement('div');
                    record.setAttribute(gridData, '');
                    record.id = 'user-id-' + data[i]['id'];
                    record.className = 'users-table-record users-table-line';

                    let userId = document.createElement('div');
                    userId.setAttribute(gridData, '');
                    userId.className = 'users-table-cell user-id';
                    userId.innerText = data[i]['id'];
                    record.appendChild(userId);

                    let userNickname = document.createElement('input');
                    userNickname.setAttribute(gridData, '');
                    userNickname.className = 'users-table-cell user-nickname';
                    userNickname.value = data[i]['nickname'];
                    userNickname.setAttribute('readonly', true);
                    record.appendChild(userNickname);

                    let userPassword = document.createElement('input');
                    userPassword.setAttribute(gridData, '');
                    userPassword.className = 'users-table-cell user-password';
                    userPassword.placeholder = '••••••••';
                    userPassword.setAttribute('readonly', true);
                    userPassword.setAttribute('type', 'password');
                    record.appendChild(userPassword);

                    let userStatus = document.createElement('div');
                    userStatus.setAttribute(gridData, '');
                    userStatus.className = 'user-active custom-select';
                    let userStatusSelect = createSelect(data[i]['id'], [ACTIVE, 'NONACTIVE', 'BANNED'], data[i]['active']);
                    userStatus.setAttribute('readonly', true);
                    userStatus.appendChild(userStatusSelect);
                    record.appendChild(userStatus);

                    let userRole = document.createElement('div');
                    userRole.setAttribute(gridData, '');
                    userRole.className = 'user-role custom-select';
                    let userRoleSelect = createSelect(data[i]['id'], [PLAYER, BOT, ADMIN], data[i]['role']);
                    userRole.setAttribute('readonly', true);
                    userRole.appendChild(userRoleSelect);
                    record.appendChild(userRole);

                    let userLastLogin = document.createElement('div');
                    userLastLogin.setAttribute(gridData, '');
                    userLastLogin.className = 'users-table-cell user-last-login';
                    if (data[i]['lastLogin'] !== null)
                        userLastLogin.innerText = moment(parseInt(data[i]['lastLogin'])).format('DD.MM.YYYY H:mm');
                    else
                        userLastLogin.innerText = '-';
                    record.appendChild(userLastLogin);

                    let userOptionEdit = document.createElement('div');
                    userOptionEdit.setAttribute(gridData, '');
                    userOptionEdit.setAttribute('user-id', data[i]['id']);
                    userOptionEdit.className = 'users-table-cell user-options';
                    userOptionEdit.innerText = 'Edit';

                    userOptionEdit.onclick = function(){
                        let userId = this.getAttribute('user-id');
                        let record = document.getElementById('user-id-' + userId);

                        record.childNodes[1].removeAttribute('readonly');
                        record.childNodes[2].removeAttribute('readonly');
                        record.childNodes[3].removeAttribute('readonly');
                        record.childNodes[4].removeAttribute('readonly');
                        record.childNodes[5].className += ' disabled-field';
                        record.childNodes[6].style.display = 'none';
                        record.childNodes[7].style.display = 'block';
                        record.className += ' edited-record';

                        saveChangesButton.onmouseover();
                    };

                    record.appendChild(userOptionEdit);

                    let userOptionCancel = document.createElement('div');
                    userOptionCancel.setAttribute(gridData, '');
                    userOptionCancel.setAttribute('user-id', data[i]['id']);
                    userOptionCancel.className = 'users-table-cell user-options';
                    userOptionCancel.innerText = 'Cancel';
                    userOptionCancel.style.display = 'none';

                    userOptionCancel.onclick = function(){
                        let userId = this.getAttribute('user-id');
                        let record = document.getElementById('user-id-' + userId);

                        record.childNodes[1].setAttribute('readonly', true);
                        record.childNodes[2].setAttribute('readonly', true);
                        record.childNodes[3].setAttribute('readonly', true);
                        record.childNodes[4].setAttribute('readonly', true);
                        record.childNodes[5].className = record.childNodes[5].className.replace(' disabled-field', '');
                        record.childNodes[6].style.display = 'block';
                        record.childNodes[7].style.display = 'none';
                        record.className = record.className.replace('edited-record', '');

                        let user = JSON.parse(localStorage.getItem(userId));
                        record.childNodes[0].innerText = user.id;
                        record.childNodes[1].value = user.nickname;
                        record.childNodes[2].placeholder = '••••••••';
                        record.childNodes[3].appendChild(createSelect(data[i]['id'], [ACTIVE, 'NONACTIVE', 'BANNED'], user.active));
                        record.childNodes[4].appendChild(createSelect(data[i]['id'], [PLAYER, BOT, ADMIN], user.role));
                        if (data[i]['lastLogin'] !== null)
                            userLastLogin.innerText = moment(parseInt(data[i]['lastLogin'])).format('DD.MM.YYYY H:mm');
                        else
                            userLastLogin.innerText = '-';

                        if(document.getElementsByClassName('edited-record').length === 0){
                            saveChangesButton.onmouseout();
                        }
                    };

                    record.appendChild(userOptionCancel);

                    usersTableContent.appendChild(record);
                }

                customiseSelect();
            }

            saveChangesButton.onclick = function () {
                if(newRecordDiv.style.height.indexOf('0') > 0){
                    let user = {
                        nickname : newRecordDiv.childNodes[2].value,
                        password : newRecordDiv.childNodes[4].value ,
                        active : newRecordDiv.childNodes[6].innerText,
                        role : newRecordDiv.childNodes[8].innerText
                    };

                    request('user/create', user, function (data) {
                        if(data.error === undefined){
                            showActiveNotification(ActiveNotification.SUCCESS, 'New User saved success!');
                            showNewRecord(false);
                            requestByFilters();
                        }
                        else {
                            showActiveNotification(ActiveNotification.ERROR, 'New User: ' + data.error);
                        }
                    });
                }

                let changedRecords = document.getElementsByClassName('edited-record');
                for(let i = 0; i < changedRecords.length; i++){
                    let record = changedRecords[i];
                    console.log(changedRecords);
                    record.className = record.className.replace('edited-record', '');
                    record.childNodes[1].setAttribute('readonly', true);
                    record.childNodes[2].setAttribute('readonly', true);
                    record.childNodes[3].setAttribute('readonly', true);
                    record.childNodes[4].setAttribute('readonly', true);
                    record.childNodes[5].className = record.childNodes[5].className.replace(' disabled-field', '');
                    record.childNodes[6].style.display = 'block';
                    record.childNodes[7].style.display = 'none';

                    let userId = record.id.replace('user-id-', '');

                    let user = {
                        id : record.childNodes[0].innerText,
                        nickname : record.childNodes[1].value,
                        password : record.childNodes[2].value !== '' ? record.childNodes[2].value : '••••••••',
                        active : record.childNodes[3].innerText,
                        role : record.childNodes[4].innerText
                    };

                    request('user/save', user, function (data) {
                        if(data.error === undefined){
                            showActiveNotification(ActiveNotification.SUCCESS, 'Id: ' + userId + ' - changes saved success!');
                        }
                        else {
                            showActiveNotification(ActiveNotification.ERROR, 'Id: ' + userId + ' - ' + data.error);
                            record.childNodes[7].click(); //cancel
                        }
                    });
                }
            };

            saveChangesButton.onmouseover = function () {
                saveChangesButton.style.width = '80px';
                saveChangesButton.style.paddingRight = '5px';
            };

            saveChangesButton.onmouseout = function () {
                if(document.getElementsByClassName('edited-record').length === 0 && newRecordDiv.style.height.indexOf('0') <= 0){
                    saveChangesButton.style.width = '28px';
                    saveChangesButton.style.paddingRight = '0px';
                }
            };

            newAccountButton.onclick = function () {
                showNewRecord(true);
            };

            newAccountButton.onmouseover = function () {
                newAccountButton.style.width = '145px';
                newAccountButton.style.paddingRight = '5px';
            };

            newAccountButton.onmouseout = function () {
                if(newRecordDiv.style.height.indexOf('0') <= 0){
                    newAccountButton.style.width = '28px';
                    newAccountButton.style.paddingRight = '0px';
                }
            };

            document.getElementById('new-record-cancel').onclick = function () {
                showNewRecord(false);
            };

            function showNewRecord(show) {
                if(show){
                    newRecordDiv.style.height = '40px';
                    newRecordDiv.style.overflow = 'unset';
                    saveChangesButton.onmouseover();
                    newAccountButton.onmouseover();
                }
                else{
                    newRecordDiv.style.height = '0px';
                    newRecordDiv.style.overflow = 'hidden';
                    newRecordDiv.childNodes[2].value = '';
                    newRecordDiv.childNodes[4].value = '';
                    saveChangesButton.onmouseout();
                    newAccountButton.onmouseout();
                }
            }

            findButton.onclick = function () {
                showFilters(true);
            };

            findButton.onmouseover = function () {
                findButton.style.width = '90px';
                findButton.style.paddingRight = '5px';
            };

            findButton.onmouseout = function () {
                if(filtersDiv.style.height.indexOf('0') <= 0){
                    findButton.style.width = '28px';
                    findButton.style.paddingRight = '0px';
                }
            };

            document.getElementById('filters-cancel').onclick = function () {
                showFilters(false);
                requestByFilters();
            };

            console.log(filtersDiv.childNodes);

            filtersDiv.childNodes[0].onchange = requestByFilters;
            filtersDiv.childNodes[2].onchange = requestByFilters;
            filtersDiv.childNodes[6].onchange = requestByFilters;
            filtersDiv.childNodes[8].onchange = requestByFilters;

            function showFilters(show) {
                if(show){
                    filtersDiv.style.height = '40px';
                    filtersDiv.style.overflow = 'unset';
                    findButton.onmouseover();
                }
                else{
                    filtersDiv.style.height = '0px';
                    filtersDiv.style.overflow = 'hidden';
                    filtersDiv.childNodes[0].value = '';
                    filtersDiv.childNodes[2].value = '';
                    filtersDiv.childNodes[4].value = '';
                    findButton.onmouseout();
                }
            }

            function requestByFilters() {
                let search = '';

                if(filtersDiv.childNodes[0].value !== '') search += 'id:' + filtersDiv.childNodes[0].value + ',';
                if(filtersDiv.childNodes[2].value !== '') search += 'nickname:' + filtersDiv.childNodes[2].value + ',';
                if(filtersDiv.childNodes[6].childNodes[1].innerText !== 'All') search += 'active:'
                    + filtersDiv.childNodes[6].childNodes[1].innerText + ',';
                if(filtersDiv.childNodes[8].childNodes[1].innerText !== 'All') search += 'role:'
                    + filtersDiv.childNodes[8].childNodes[1].innerText + ',';

                search = search.substring(0, search.length - 1);

                let params = {};
                if(search.length !== 0) params.search = search;

                request('users', params, function (data) {
                    if(data.error === undefined){
                        loadData(data);
                    }
                    else{
                        showActiveNotification(ActiveNotification.ERROR, 'Something went wrong...');
                    }
                })
            }

            function createSelect(userId, items, selectedItem) {
                let select = document.createElement('select');
                select.style.display = 'none';
                select.id = 'select-user-id-' + userId;
                for(let i = 0; i < items.length; i++){
                    let option = document.createElement('option');
                    option.value = items[i];
                    option.innerText = items[i];
                    if(option.value === selectedItem) option.setAttribute('selected', true);
                    select.appendChild(option);
                }
                return select;
            }

            function customiseSelect() {
                let x, i, j, selElmnt, a, b, c;
                /* Look for any elements with the class "custom-select": */
                x = document.getElementsByClassName("custom-select");
                for (i = 0; i < x.length; i++) {
                    if(x[i].childNodes.length > 1) continue;
                    selElmnt = x[i].getElementsByTagName("select")[0];
                    /* For each element, create a new DIV that will act as the selected item: */
                    a = document.createElement("div");
                    a.className = 'select-selected';
                    a.innerHTML = selElmnt.options[selElmnt.selectedIndex].innerHTML;
                    x[i].appendChild(a);
                    /* For each element, create a new DIV that will contain the option list: */
                    b = document.createElement("DIV");
                    b.className = 'select-items select-hide';
                    for (j = 0; j < selElmnt.length; j++) {
                        /* For each option in the original select element,
                        create a new DIV that will act as an option item: */
                        c = document.createElement("DIV");
                        c.innerHTML = selElmnt.options[j].innerHTML;
                        c.addEventListener("click", function(e) {
                            /* When an item is clicked, update the original select box,
                            and the selected item: */
                            let y, i, k, s, h;
                            s = this.parentNode.parentNode.getElementsByTagName("select")[0];
                            h = this.parentNode.previousSibling;
                            for (i = 0; i < s.length; i++) {
                                if (s.options[i].innerHTML === this.innerHTML) {
                                    s.selectedIndex = i;
                                    h.innerHTML = this.innerHTML;
                                    y = this.parentNode.getElementsByClassName("same-as-selected");
                                    for (k = 0; k < y.length; k++) {
                                        y[k].removeAttribute("class");
                                    }
                                    this.className = 'same-as-selected';
                                    break;
                                }
                            }

                            if(this.parentNode.parentNode.onchange !== null)this.parentNode.parentNode.onchange();
                            h.click();
                        });
                        b.appendChild(c);
                    }
                    x[i].appendChild(b);
                    a.addEventListener("click", function(e) {
                        if(this.parentNode.getAttribute('readonly') !== 'true'){
                            /* When the select box is clicked, close any other select boxes,
                                                       and open/close the current select box: */
                            e.stopPropagation();
                            closeAllSelect(this);
                            this.nextSibling.classList.toggle("select-hide");
                            this.classList.toggle("select-arrow-active");
                        }
                    });
                }

                /* If the user clicks anywhere outside the select box,
            then close all select boxes: */
                document.addEventListener("click", closeAllSelect);
            }

            function closeAllSelect(elmnt) {
                /* A function that will close all select boxes in the document,
                except the current select box: */
                var x, y, i, arrNo = [];
                x = document.getElementsByClassName("select-items");
                y = document.getElementsByClassName("select-selected");
                for (i = 0; i < y.length; i++) {
                    if (elmnt == y[i]) {
                        arrNo.push(i)
                    } else {
                        y[i].classList.remove("select-arrow-active");
                    }
                }
                for (i = 0; i < x.length; i++) {
                    if (arrNo.indexOf(i)) {
                        x[i].classList.add("select-hide");
                    }
                }
            }
        }
    }
</script>
<style scoped>
    .users-crud-card{
    }

    .crud-options-wrapper{

        margin: 5px 0px 10px 0px;
    }

    .option-button{
        float: right;
        width: 28px;
        height: 29px;
        margin: 0px 0px 0px 10px;
        border-radius: 7px;
        cursor: pointer;
        overflow: hidden;
        -webkit-transition: All 0.5s ease;

        text-align: left;
        font-size: 16px;
        font-weight: bold;
        line-height: 30px;
    }

    .option-button:hover{
        filter: brightness(1.1);
    }

    .save-changes-button{
        border: 1px solid rgba(219,208,0,0.49);
    }

    .save-changes-button span{
        background: rgba(219,208,0,0.49);
        padding: 3px 9px 4px 9px;
        border-radius: 5px;

        color: rgba(236,255,0,0.64);
        font-size: 20px;
        font-weight: bold;
    }

    .edited-record{
        background: rgba(219,208,0,0.09);
    }

    .find-button{
        border: 1px solid #db7500;
    }

    .find-button img{
        background: #db7500;
        width: 20px;
        height: 20px;
        padding: 5px 5px 6px 5px;
        border-radius: 4px;
    }

    .find-button span{
        position: relative;
        top: -10px;
        padding: 0px 10px 0px 10px;
    }

    .users-table-filters{
        height: 0px;
        overflow: hidden;
        -webkit-transition: All 0.5s ease;
    }

    .new-account-button{
        border: 1px solid #005e1b;
    }

    .new-account-button:hover{
        width: 155px;
    }

    .new-account-button span{
        background: #005e1b;
        padding: 3px 9px 4px 9px;
        border-radius: 5px;

        color: #00ff2d;
        font-size: 20px;
        font-weight: bold;
    }

    .users-table-new-record{
        background: rgba(219,208,0,0.09);
        height: 0px;
        overflow: hidden;
        -webkit-transition: All 0.5s ease;
    }

    .new-record-field::placeholder{
        color: #6f7576;
    }

    .users-table{
        overflow-x: auto;
    }

    .users-table-header{
        background: #18191a;
    }

    .users-table-header div{
    }

    .users-table-content{
        overflow-y: auto;
    }

    .users-table-record{

    }

    .users-table-line{
        display: grid;
        grid-gap: 0px;
        grid-template-columns: 60px auto 150px 140px 120px 150px 100px;
        grid-template-rows: 40px;
        grid-template-areas: "id nickname password active role last-login options";

        border: 1px solid #18191a;
    }

    .users-table-cell{
        background: inherit;
        border: none;
        overflow: auto;

        text-align: center;
        line-height: 40px;
    }

    input{
        font-size: 15px;
        color: #e4e6eb;
    }

    input::placeholder{
        font-size: 15px;
        color: #e4e6eb;
    }

    .user-id{
        grid-area: id;

        border-right: 1px solid #18191a;
    }
    .user-league{
        grid-area: league;
        background: url(/images/league-background.png);
        background-position: center;
        background-repeat: no-repeat;
        background-size: cover;
        width: 30px;
        height: 30px;
        margin: 5px 15px 5px 15px;
        border: none;
    }

    .user-league-icon{
        width: 58%;
    }

    .user-nickname{
        grid-area: nickname;

        border-left: 1px solid #18191a;
        border-right: 1px solid #18191a;
    }
    .user-password{
        grid-area: password;

        border-right: 1px solid #18191a;
    }
    .user-goblets{
        grid-area: goblets;

        border-right: 1px solid #18191a;
    }
    .user-active{
        grid-area: active;

        border-right: 1px solid #18191a;
    }
    .user-role{
        grid-area: role;

        border-right: 1px solid #18191a;
    }
    .user-last-login{
        grid-area: last-login;

        border-right: 1px solid #18191a;
    }
    .user-options{
        grid-area: options;
        cursor: pointer;
        overflow: hidden;
    }

    .disabled-field{
        box-shadow: inset 0px 0px 35px #18191a;
    }




    /* The container must be positioned relative: */
    .custom-select {
        background: inherit;
        position: relative;
    }

    .custom-select select{
        display: none; /*hide original SELECT element: */
    }

    .select-selected {
    }

    /* Style the arrow inside the select element: */
    .select-selected:after {
        position: absolute;
        content: "";
        top: 14px;
        right: 10px;
        width: 0;
        height: 0;
        border: 6px solid transparent;
        border-color: #fff transparent transparent transparent;
    }

    /* Point the arrow upwards when the select box is open (active): */
    .select-selected.select-arrow-active:after {
        border-color: transparent transparent #fff transparent;
        top: 7px;
    }

    /* style the items (options), including the selected item: */
    .select-items div,.select-selected {
        color: #ffffff;
        padding: 8px 16px;
        border: 1px solid transparent;
        border-color: transparent transparent rgba(0, 0, 0, 0.1) transparent;
    }

    /* Style items (options): */
    .select-items {
        position: absolute;
        top: 100%;
        left: 0;
        right: 0;
        z-index: 99;
    }

    /* Hide the items when the select box is closed: */
    .select-hide {
        display: none;
    }

    .select-items div:hover, .same-as-selected {
        background-color: rgba(0, 0, 0, 0.1);
    }

    @media screen and (max-width: 641px){
        .users-table-line{
            border: none;
            grid-template-columns: 60px 150px 150px 140px 120px 150px 100px;
        }

        .users-table-cell{
            border: 0.5px solid #18191a;
        }

        .custom-select{
            border: 0.5px solid #18191a;
        }

        .content-card{
            padding: 5px 0px 0px 0px;
        }

        .option-button{
            margin-top: 10px;
        }

        .new-account-button{
            margin-right: 5px;
        }
    }
</style>