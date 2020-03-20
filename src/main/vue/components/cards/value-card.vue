<template>
    <div id="card" class="content-card card-3-column value-card" card-id="" img="">
        <div id="amount" class="amount">-</div>
        <img id="icon" src="" alt="-" class="icon">
        <progress id="progress-bar" class="progress-bar" max="100" value="0"></progress>
        <div id="title" class="title">Title</div>
        <div id="delta" class="delta">+0.0%</div>
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
            const gradients = ['blue-green', 'yellow-red', 'purple-orange', 'orange-yellow'];

            let card = document.getElementById('card');
            let amount = document.getElementById('amount');
            let icon = document.getElementById('icon');
            let progressBar = document.getElementById('progress-bar');
            let title = document.getElementById('title');
            let delta = document.getElementById('delta');
            let value;

            value = card.getAttribute('img');
            if(value !== null)
                icon.src = value;

            setRandomGradient(progressBar);

            let id = card.getAttribute('card-id').toLowerCase().replace(/ /g, '-');
            card.id = id + '-card';
            amount.id = id + '-card-amount';
            icon.id = id + '-card-icon';
            progressBar.id = id + '-card-progress';
            title.id = id + '-card-title';
            delta.id = id + '-card-delta';

            function setRandomGradient(element) {
                element.className += ' progress-bar-' + gradients[Math.round(Math.random() * (gradients.length - 1))];
            }

            window.loadValueCard = function(cardId, data) {
                let title = document.getElementById(cardId + '-card-title');
                let amount = document.getElementById(cardId + '-card-amount');
                let progressBar = document.getElementById(cardId + '-card-progress');
                let delta = document.getElementById(cardId + '-card-delta');

                if(data.amount !== null)
                    amount.innerText = data.amount;
                if(data.progress !== null)
                    progressBar.value = data.progress;
                if(data.title !== null)
                    title.innerText = data.title;
                if(data.delta !== null){
                    if(data.delta > 0){
                        delta.innerText = '+' + data.delta + '% ⬆';
                        delta.className += ' delta-plus';
                    }
                    else if(data.delta < 0){
                        delta.innerText = data.delta + '% ⬇';
                        delta.className += ' delta-minus';
                    }
                    else {
                        delta.innerText = data.delta + '%';
                    }
                }
            }
        }
    }
</script>
<style scoped   >
    .value-card{
        height: 100px;
        display: grid;
        grid-template-columns: auto auto auto;
        grid-template-rows: 40px 20px 40px;
        grid-template-areas:
                "amount . icon"
                "progress-bar progress-bar progress-bar"
                "title . delta";
    }

    .amount{
        grid-area: amount;

        text-align: left;
        line-height: 40px;
        font-weight: bolder;
    }

    .icon{
        grid-area: icon;
        justify-self: end;
        height: 20px;
        margin-top: 10px;
    }

    .progress-bar{
        grid-area: progress-bar;
        width: auto;
        height: 2px;
        margin: 9px 0px 9px 0px;
    }

    .progress-bar {
        border: none;
        background: white;
    }

    .progress-bar-blue-green::-webkit-progress-value {
        background: linear-gradient(to right, blue, green);
    }

    .progress-bar-yellow-red::-webkit-progress-value {
        background: linear-gradient(to right, yellow, red);
    }

    .progress-bar-purple-orange::-webkit-progress-value {
        background: linear-gradient(to right, purple, orange);
    }

    .progress-bar-orange-yellow::-webkit-progress-value {
        background: linear-gradient(to right, #432100, yellow);
    }

    .progress-bar::-webkit-progress-bar {
        background: #242526;
        filter: brightness(1.35);
    }

    .title{
        grid-area: title;

        text-align: left;
        line-height: 40px;
    }

    .delta{
        grid-area: delta;

        text-align: right;
        line-height: 40px;
        font-weight: bolder;
    }

    .delta-plus{
        color: green;
    }

    .delta-minus{
        color: red;
    }
</style>