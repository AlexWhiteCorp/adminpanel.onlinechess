<template>
    <div id="dashboard" class="dashboard-wrapper dashboard-grid-cell">
        <div id="dashboard-head" class="dashboard-head">
            <div class="in-dashboard-head row">
                <img src="/images/logo.png" class="dashboard-head-logo">
                <div class="dashboard-head-title">Admin Panel</div>
            </div>
        </div>
        <div id="dashboard-body" class="dashboard-body scrollable">

        </div>
    </div>
</template>
<script>
    export default {
        data(){
            return{
            }
        },
        methods: {
            push(path){
                this.$router.push('/adminpanel/' + path);
            }
        },
        mounted(){
            let DEFAULT_ITEM = 'default-item';
            let SELECT_ITEM = 'select-item';
            let component = this;

            const ITEMS = [
                [DEFAULT_ITEM, 'Overview'],
                [DEFAULT_ITEM, 'Users'],
                [DEFAULT_ITEM, 'Clubs']
                        ];

            let dashboard = document.getElementById('dashboard');
            let dashboardHead = document.getElementById('dashboard-head');
            let dashboardBody = document.getElementById('dashboard-body');
            window.dashboard = dashboard;

            resizeDashboard();

            loadDashboardMenuItems(ITEMS);
            //customiseSelect();

            function loadDashboardMenuItems(items) {
                for(let i = 0; i < items.length; i++){
                    if(items[i][0] === DEFAULT_ITEM){
                        loadDefaultItem(items[i][1]);
                        continue;
                    }
                }
            }

            function loadDefaultItem(itemName) {
                let item = document.createElement('div');
                item.id = itemName.toLowerCase();
                item.className = 'dashboard-item default-item row';

                let itemIcon = document.createElement('img');
                itemIcon.src = '/images/' + itemName.toLowerCase() + '-icon.png';
                itemIcon.className = 'dashboard-item-icon';
                item.appendChild(itemIcon);

                let itemTitle = document.createElement('div');
                itemTitle.className = 'dashboard-item-title';
                itemTitle.innerText = itemName;
                item.appendChild(itemTitle);

                item.onclick = function(){
                    let path = document.location.pathname;
                    if(path.indexOf(item.id) === -1)
                        component.push(item.id);
                };

                dashboardBody.appendChild(item);
            };

            function resizeDashboard(){
                dashboardHead.style.minHeight = document.getElementById('header-wrapper').clientHeight - 1 + 'px';
                dashboardHead.style.maxHeight = document.getElementById('header-wrapper').clientHeight - 1 + 'px';

                dashboardBody.style.minHeight = window.innerHeight - dashboardHead.offsetHeight - 15 + 'px';
                dashboardBody.style.maxHeight = window.innerHeight - dashboardHead.offsetHeight - 15 + 'px';
            }

            window.showDashboard = function() {
                resizeDashboard();

                let timer, timePassed, start = Date.now();

                timer = setInterval(function() {
                    timePassed = Date.now() - start;
                    if(timePassed > 250){
                        if(!window.isSmallScreen())window.mainWrapper.style.gridTemplateColumns = "250px auto";
                        window.dashboard.style.left = "0px";
                        clearInterval(timer);
                        window.onclick = function(e){
                            if(window.isSmallScreen()){
                                e = e || window.event;
                                if(window.getClickedElement(e) !== window.dashboard.id){
                                    hideDashboard();
                                    window.onclick = null;
                                }
                            }
                        };
                        return;
                    }
                    if(!window.isSmallScreen()) window.mainWrapper.style.gridTemplateColumns = timePassed + "px auto";
                    window.dashboard.style.left = (timePassed - 250) + "px"
                }, 20);
            };

            window.hideDashboard = function() {
                let timer, timePassed, start = Date.now();

                timer = setInterval(function() {
                    timePassed = Date.now() - start;
                    if(timePassed > 250){
                        if(!window.isSmallScreen())window.mainWrapper.style.gridTemplateColumns = "0px auto";
                        window.dashboard.style.left = "-250px";
                        clearInterval(timer);
                        return;
                    }
                    if(!window.isSmallScreen()) window.mainWrapper.style.gridTemplateColumns = (250 - timePassed) + "px auto";
                    window.dashboard.style.left = -timePassed + "px";
                }, 20);
            };

            window.isShowDashboard = function() {
                return window.dashboard.offsetLeft > -250;
            };

            function customiseSelect() {
                let x, i, j, selElmnt, a, b, c;
                /*look for any elements with the class "custom-select":*/
                x = document.getElementsByClassName("select-item");
                for (i = 0; i < x.length; i++) {
                    selElmnt = x[i].getElementsByTagName("select")[0];
                    /*for each element, create a new DIV that will act as the selected item:*/
                    a = document.createElement("DIV");
                    a.setAttribute("class", "select-selected");
                    a.innerHTML = selElmnt.options[selElmnt.selectedIndex].innerHTML;
                    x[i].appendChild(a);
                    /*for each element, create a new DIV that will contain the option list:*/
                    b = document.createElement("DIV");
                    b.setAttribute("class", "select-items select-hide");
                    for (j = 1; j < selElmnt.length; j++) {
                        /*for each option in the original select element,
                        create a new DIV that will act as an option item:*/
                        c = document.createElement("DIV");
                        c.innerHTML = selElmnt.options[j].innerHTML;
                        c.addEventListener("click", function(e) {
                            /*when an item is clicked, update the original select box,
                            and the selected item:*/
                            var y, i, k, s, h;
                            s = this.parentNode.parentNode.getElementsByTagName("select")[0];
                            h = this.parentNode.previousSibling;
                            for (i = 0; i < s.length; i++) {
                                if (s.options[i].innerHTML == this.innerHTML) {
                                    s.selectedIndex = i;
                                    h.innerHTML = this.innerHTML;
                                    y = this.parentNode.getElementsByClassName("same-as-selected");
                                    for (k = 0; k < y.length; k++) {
                                        y[k].removeAttribute("class");
                                    }
                                    this.setAttribute("class", "same-as-selected");
                                    break;
                                }
                            }
                            h.click();
                        });
                        b.appendChild(c);
                    }
                    x[i].appendChild(b);
                    a.addEventListener("click", function(e) {
                        /*when the select box is clicked, close any other select boxes,
                        and open/close the current select box:*/
                        e.stopPropagation();
                        closeAllSelect(this);
                        this.nextSibling.classList.toggle("select-hide");
                        this.classList.toggle("select-arrow-active");
                    });
                }
                function closeAllSelect(elmnt) {
                    /*a function that will close all select boxes in the document,
                    except the current select box:*/
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
                /*if the user clicks anywhere outside the select box,
                then close all select boxes:*/
                document.addEventListener("click", closeAllSelect);
            }
        }
    }
</script>
<style scope>
    .dashboard-wrapper{
        position: fixed;
        z-index: 200;
        left: 0;
        top: 0;
        width: 250px;
        background-color: #18191a;
        background: linear-gradient(rgba(0, 0, 0, 0.95), rgba(0, 0, 0, 0.75)), url(/images/dashboard-background.png);
        border: 1px solid #262727;
        border-left: none;
    }

    .dashboard-grid-cell{
        grid-area: dashboard;
    }

    .dashboard-head{
        width: auto;
        height: auto;
        overflow: hidden;

        border-bottom: 1px solid #262727;
    }

    .in-dashboard-head{
        width: fit-content;
        margin-left: auto;
        margin-right: auto;

    }

    .dashboard-head-logo{
        float: left;
        height: 40px;
        padding: 10px 10px 10px 0px;
    }

    .dashboard-head-title{
        float: left;

        font-size: 21px;
        color: #e4e6eb;
        line-height: 60px;
    }

    .dashboard-body{
        width: auto;
        padding: 10px 0px 3px 0px;
        overflow-y: auto;
    }

    .dashboard-item{
        width: auto;
        margin: 0px 3px 0px 13px;
        padding: 5px 0px 5px 10px;

        color: #6f7576;
        font-weight: 500;
    }

    .dashboard-item:hover{
        background-color: #001914;
        margin-left: 10px;
        border-left: 3px solid white;

        color: #e4e6eb;
        font-weight: 100;
    }

    .dashboard-item:hover img{
        filter: opacity(.5) drop-shadow(0 0 0 white) brightness(1.5);
    }

    .dashboard-item-icon{
        float: left;
        height: 30px;
        filter: opacity(.5) drop-shadow(0 0 0 #6f7576);

        font-size: 16px;
        font-weight: bold;
    }

    .dashboard-item-title{
        float: left;
        margin-left: 10px;

        letter-spacing: 1px;
        line-height: 35px;
        font-size: 16px;
    }

    .default-item{
        min-height: 35px;
        max-height: 35px;
        overflow: hidden;
    }


    .select-item {
        position: relative;
    }

    .select-item select {
        display: none; /*hide original SELECT element:*/
    }

    .select-selected:hover{
    }

    /*style the arrow inside the select element:*/
    .select-selected:after {
        position: absolute;
        content: "";
        top: 20px;
        right: 10px;
        width: 0;
        height: 0;
        border: 6px solid transparent;
        border-color: #6f7576 transparent transparent transparent;
    }

    /*point the arrow upwards when the select box is open (active):*/
    .select-selected.select-arrow-active:after {
        border-color: transparent transparent #e4e6eb transparent;
        top: 13px;
        filter: brightness(1.25);
    }

    /*style the items (options), including the selected item:*/
    .select-items div,.select-selected {
        padding: 8px 16px;
        border: 1px solid transparent;
        border-color: transparent transparent rgba(0, 0, 0, 0.1) transparent;
        cursor: pointer;
        user-select: none;
    }

    /*style items (options):*/
    .select-items {
        position: absolute;
        background-color: #242526;
        top: 100%;
        left: 0;
        right: 0;
        z-index: 99;
    }

    /*hide the items when the select box is closed:*/
    .select-hide {
        display: none;
    }

    .select-items div:hover, .same-as-selected {
        background-color: rgba(0, 0, 0, 0.1);
    }

    @media screen and (max-width: 1024px){
        .dashboard-wrapper{
            left: -250px;
        }
    }
</style>