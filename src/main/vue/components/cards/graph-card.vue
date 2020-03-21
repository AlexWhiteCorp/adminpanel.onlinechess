<template>
    <div id="card" class="content-card card-9-column" card-id="" api-path="" var-name="">
        <div class="graph-card">
            <div id="graph-card-header" class="graph-card-header">
                <div id="title" class="title">CPU load</div>
                <div class="space"></div>
                <select name="" id="time-range" class="time-range">
                    <option value="0">Today</option>
                    <option value="1">Yesterday</option>
                    <option value="7">Week</option>
                    <option value="28">Month</option>
                </select>
                <div id="scale-button-plus" class="option-button">+</div>
                <div id="scale-button-minus" class="option-button">-</div>
                <div id="options-button" class="option-button">...</div>
            </div>
            <div id="graph-card-content" class="graph-card-content scrollable">
                <div id="in-graph-card-content" class="in-graph-card-content">
                    <canvas id="graph" class="graph"></canvas>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import Chart from 'chart.js'

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
            const MILISEC_IN_DAY = 24 * 60 * 60 * 1000;
            const MILISEC_IN_HOUR = 60 * 60 * 1000;

            Chart.defaults.global.elements.point.radius = 0;
            Chart.defaults.global.elements.point.hitRadius = 5;
            Chart.defaults.global.elements.point.hoverBorderWidth = 4;
            Chart.defaults.global.elements.line.cubicInterpolationMode = 'monotone';

            let card = document.getElementById('card');
            let title = document.getElementById('title');
            let timeRange = document.getElementById('time-range');
            let scaleButtonPlus = document.getElementById('scale-button-plus');
            let scaleButtonMinus = document.getElementById('scale-button-minus');
            let optionsButton = document.getElementById('options-button');
            let graphCardContent = document.getElementById('graph-card-content');
            let inGraphCardContent = document.getElementById('in-graph-card-content');

            const apiPath = card.getAttribute('api-path');
            const varName = card.getAttribute('var-name');
            const name = card.getAttribute('name');

            title.innerText = name;

            let ctx = document.getElementById('graph').getContext('2d');
            let chart;

            timeRange.onchange = function(){
                let select = this;
                axios
                    .get(window.getRestPath(apiPath), { params: {'range' : select.value}})
                    .then(function(responce){
                        console.log(responce.data);

                        let dayStartTime = new Date().getTime() - new Date().getTime() % MILISEC_IN_DAY - 2 * 60 * 60 * 1000;

                        switch (parseInt(select.value)) {
                            case 0:{
                                chart = loadChart(name, 'hour', 'H:mm',
                                    dayStartTime, dayStartTime + MILISEC_IN_DAY, MILISEC_IN_HOUR, responce.data);
                                break;
                            }
                            case 1:{
                                chart = loadChart(name, 'hour', 'H:mm',
                                    dayStartTime - MILISEC_IN_DAY, dayStartTime, MILISEC_IN_HOUR, responce.data);
                                break;
                            }
                            case 7:{
                                chart = loadChart(name, 'day', 'D:MM:YY',
                                    dayStartTime - 7 * MILISEC_IN_DAY, dayStartTime, MILISEC_IN_DAY, responce.data);
                                break;
                            }
                            case 28:{
                                chart = loadChart(name, 'day', 'D:MM:YY',
                                    dayStartTime - 28 * MILISEC_IN_DAY, dayStartTime, MILISEC_IN_DAY, responce.data);
                                break;
                            }
                            default:{
                                console.log('Error!');
                            }
                        }
                    });

            };

            scaleButtonPlus.onclick = function(){
                switch (parseInt(timeRange.value)) {
                    case 0:{
                        setChartScale(chart, 1.5);
                        break;
                    }
                    case 1:{
                        setChartScale(chart, 1.5);
                        break;
                    }
                    case 7:{
                        setChartScale(chart, 2.0);
                        break;
                    }
                    case 28:{
                        setChartScale(chart, 8.0);
                        break;
                    }
                    default:{
                        console.log('Error!');
                    }
                }
                chart.resize();
            };

            scaleButtonMinus.onclick = function(){
                setChartScale(chart, 1.0);
                chart.resize();
            };

            timeRange.onchange();

            let id = card.getAttribute('card-id').toLowerCase().replace(/ /g, '-');
            card.id = id + '-card';
            title.id = id + '-card-title';
            timeRange.id = id + '-card-amount';
            scaleButtonPlus.id = id + '-card-icon';
            scaleButtonMinus.id = id + '-card-progress';
            optionsButton.id = id + '-card-title';
            graphCardContent.id = id + '-card-graph-content';
            inGraphCardContent.id = id + '-card-in-graph-content';
            document.getElementById('graph').id = id + '-card-ctx';

            function loadChart(title, timeUnit, displayFormat, intervalStart, intervalEnd, intervalStep, data) {
                if(chart !== undefined)chart.destroy();

                chart = new Chart(ctx, {
                    type: 'line',
                    data: {},
                    options: {
                        maintainAspectRatio: false,
                        responsive: true,
                        lineTension: 1,
                        scales: {
                            xAxes: [{
                                type: 'time',
                                time: {
                                    unit: timeUnit,
                                    displayFormats: {
                                        hour: displayFormat
                                    }
                                }

                            }],
                            yAxes: [{
                                ticks: {
                                    min: 0,
                                }
                            }]
                        },
                        legend: {
                            display: false
                        },
                    }
                });

                let timeForLabels = []; //miliseconds

                for(let i = intervalStart; i <= intervalEnd; i += intervalStep){
                    timeForLabels.push(new Date(i));
                }

                loadChartDate(chart, title, timeForLabels, data);
                setChartScale(chart, 1.0);

                return chart;
            }

            function loadChartDate(chart, title, labels, data) {
                let lastDate = 0;
                let dataset = [];

                for(let id in data){
                    if(lastDate !== 0 && data[id].date - lastDate > 5 * 60 * (1000 + 1)){
                        let t = new Date(lastDate);
                        dataset.push({x: t, y: 0});

                        t = new Date(data[id].date);
                        dataset.push({x: t, y: 0});
                    }

                    let time = new Date(data[id].date);
                    dataset.push({x: time, y: data[id][varName]});

                    lastDate = data[id].date;
                }

                dataset.map(e => +e.y);

                chart.data = {
                    labels: labels,
                    datasets: [{
                        label: title,
                        data: dataset,
                        borderWidth: 1,
                        backgroundColor: '#1e546c'
                    }],
                };
            }

            function setChartScale(chart, scale) {
                let width, height;
                if(!isSmallScreen()){
                    width = graphCardContent.offsetWidth;
                    height = graphCardContent.offsetHeight;
                }
                else{
                    width = 912;
                    height = graphCardContent.offsetHeight;
                }
                inGraphCardContent.style.width = width * scale + 'px';

                chart.canvas.parentNode.style.width = width * scale + 'px';
                chart.canvas.parentNode.style.height = height - 20 + 'px';
                chart.update();

            }
        }
    }
</script>
<style scope>
    .graph-card{
        padding: 0px 10px 0px 0px;

        display: grid;
        grid-template-columns: 100%;
        grid-template-rows: 40px 250px;
        grid-template-areas:
                "graph-card-header"
                "graph-card-content";
    }

    .graph-card-header{
        grid-area: graph-card-header;

        display: grid;
        grid-template-columns: auto auto 100px 25px 25px 25px;
        grid-template-areas:
                "title space time-range button-1 button-2 options";
    }

    .graph-card-content{
        grid-area: graph-card-content;

        overflow-x: scroll;
        overflow-y: hidden;
    }

    .title{
        grid-area: title;

        text-align: left;
        line-height: 40px;
    }

    .space{
        grid-area: space;
    }


    .time-range{
        background: transparent;
        width: 80px;
        grid-area: time-range;
        margin: auto;
        border: none;
        outline: none;

        color: #e4e6eb;
        letter-spacing: 1px;
        font-weight: bold;
    }

    .time-range option{
        background-color: #18191a;
        border: none;
        outline: none;
        padding: 2px 5px 2px 5px;

        color: #6f7576;
    }

    .time-range option:hover{
        color: red;
    }

    .option-button{
        margin: auto;
        cursor: pointer;

        text-align: center;
        font-size: 20px;
        font-weight: bold;
    }

    .option-button:hover{
        font-size: 22px;
    }

    #scale-button-plus{
        grid-area: button-1;
    }

    #scale-button-minus{
        grid-area: button-2;
    }

    #options-button{
        grid-area: options;
        margin-top: 3px;
    }

    .graph{
    }
</style>