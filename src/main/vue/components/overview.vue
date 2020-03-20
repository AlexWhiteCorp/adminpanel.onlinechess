<template>
    <div class="in-content-wrapper">
        <value-card card-id="1" img="/images/overview-icon.png"></value-card>
        <value-card card-id="2" img="/images/overview-icon.png"></value-card>
        <value-card card-id="3" img="/images/overview-icon.png"></value-card>
        <value-card card-id="4" img="/images/overview-icon.png"></value-card>

        <graph-card card-id="5" name="Players Online" api-path="snapshot" var-name="playersOnline"></graph-card>
        <graph-card card-id="6" name="CPU Usage" api-path="snapshot" var-name="cpuUsage"></graph-card>
        <graph-card card-id="7" name="RAM Usage" api-path="snapshot" var-name="ramUsage"></graph-card>

        <div class="content-card card-7-column">5</div>
        <div class="content-card card-5-column">6</div>

        <div class="content-card card-6-column">7</div>
        <div class="content-card card-6-column">8</div>

        <div class="content-card card-4-column">9</div>
        <div class="content-card card-4-column">10</div>
        <div class="content-card card-4-column">11</div>
    </div>
</template>
<script>
    import valueCard from 'components/cards/value-card.vue'
    import graphCard from 'components/cards/graph-card.vue'

    const axios = require('axios').default;

    export default {
        data(){
            return{
            }
        },
        computed: {
        },
        components: {
            'value-card' : valueCard,
            'graph-card' : graphCard
        },
        methods: {},
        mounted(){
            const DATA_LOAD_TIME = 5 * 1000;
            let component = this;

            requestInfo();

            function requestInfo() {
                setLoadTime(DATA_LOAD_TIME / 1000);
                axios
                    .get(window.getRestPath('overview'))
                    .then(function (responce) {
                        loadInfo(responce.data);
                        let timer, timePassed, start = Date.now();
                        timer = setInterval(function() {
                            timePassed = Date.now() - start;
                            if(timePassed > DATA_LOAD_TIME){
                                clearInterval(timer);
                                requestInfo();
                                return;
                            }
                            setLoadTime(Math.round((DATA_LOAD_TIME - timePassed) / 1000));
                        }, 1000);
                    });
            }

            function loadInfo(data) {
                console.log(data['value-cards']);
                for(let cardId in data['value-cards']){
                    loadValueCard(cardId, data['value-cards'][cardId]);
                }
            }
        }
    }
</script>
<style scope>
</style>