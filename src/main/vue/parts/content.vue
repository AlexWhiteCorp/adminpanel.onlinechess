<template>
    <div id="content-wrapper" class="content-wrapper content-grid-cell scrollable">
        <transition name="fade">
            <keep-alive>
                <router-view></router-view>
            </keep-alive>
        </transition>
        <footer-wrapper/>
    </div>
</template>
<script>
    import footerWrapper from 'parts/footer.vue'

    export default {
        data(){
            return{
            }
        },
        computed: {
        },
        components: {
            'footer-wrapper' : footerWrapper
        },
        methods: {
        },
        mounted(){
            const Component = this;

            resizeContent();

            let content = document.getElementById('content-wrapper');

            let keys = {37: 1, 38: 1, 39: 1, 40: 1};

            function preventDefault(e) {
                e = e || content.event;
                if (e.preventDefault)
                    e.preventDefault();
                e.returnValue = false;
            }

            function preventDefaultForScrollKeys(e) {
                if (keys[e.keyCode]) {
                    preventDefault(e);
                    return false;
                }
            }

            function disableScroll() {
                if (content.addEventListener) // older FF
                    content.addEventListener('DOMMouseScroll', preventDefault, false);
                content.addEventListener('wheel', preventDefault, {passive: false}); // Disable scrolling in Chrome
                content.onwheel = preventDefault; // modern standard
                content.onmousewheel = content.onmousewheel = preventDefault; // older browsers, IE
                content.ontouchmove  = preventDefault; // mobile
                content.onkeydown  = preventDefaultForScrollKeys;
            }

            function enableScroll() {
                if (content.removeEventListener)
                    content.removeEventListener('DOMMouseScroll', preventDefault, false);
                content.removeEventListener('wheel', preventDefault, {passive: false}); // Enable scrolling in Chrome
                content.onmousewheel = content.onmousewheel = null;
                content.onwheel = null;
                content.ontouchmove = null;
                content.onkeydown = null;
            }
        }
    }
</script>
<style>
    .content-wrapper{
        background-color: #18191a;
        width: auto;

        display: grid;
        grid-gap: 0px;
        grid-template-columns: 100%;
        grid-template-rows: auto 45px;

        text-align: center;
    }

    .in-content-wrapper{
        box-sizing: border-box;
        padding: 10px;
        display: grid;
        grid-gap: 0px;
        grid-template-columns: 8.33333% 8.33333% 8.33333% 8.33333% 
                               8.33333% 8.33333% 8.33333% 8.33333%
                               8.33333% 8.33333% 8.33333% 8.33333%;
        justify-content: center;
        align-content: start;
    }

    .content-grid-cell {
        grid-area: content;
    }

    .content-card{
        justify-self: stretch;
        background: #242526;
        width: auto;
        min-height: 100px;
        margin: 15px;
        padding: 5px 25px 5px 25px;
        border: 1px solid #262727;
        border-radius: 5px;

        color: #e4e6eb;
        font-size: 14px;
        letter-spacing: 1px;
    }

    .card-1-column{
        grid-column: span 1;
    }

    .card-2-column{
        grid-column: span 2;
    }

    .card-3-column{
        grid-column: span 3;
    }

    .card-4-column{
        grid-column: span 4;
    }

    .card-5-column{
        grid-column: span 5;
    }

    .card-6-column{
        grid-column: span 6;
    }

    .card-7-column{
        grid-column: span 7;
    }

    .card-8-column{
        grid-column: span 8;
    }

    .card-9-column{
        grid-column: span 9;
    }

    .card-10-column{
        grid-column: span 10;
    }

    .card-11-column{
        grid-column: span 11;
    }

    .card-12-column{
        grid-column: span 12;
    }

    @media screen and (max-width: 1024px){
        .in-content-wrapper{
            grid-template-columns: 16.6667% 16.6667% 16.6667% 16.6667% 16.6667% 16.6667%;
        }

        .card-3-column{
            grid-column: span 3;
        }

        .card-4-column{
            grid-column: span 2;
        }

        .card-5-column,
        .card-6-column,
        .card-7-column,
        .card-8-column,
        .card-9-column,
        .card-10-column,
        .card-11-column,
        .card-12-column{
            grid-column: span 6;
        }
    }

    @media screen and (max-width: 641px){
        .content-wrapper{
        }

        .in-content-wrapper{
            grid-template-columns: 50% 50%;
        }

        .card-1-column,
        .card-2-column,
        .card-3-column,
        .card-4-column,
        .card-5-column,
        .card-6-column,
        .card-7-column,
        .card-8-column,
        .card-9-column,
        .card-10-column,
        .card-11-column,
        .card-10-column{
            grid-column: span 2;
        }
    }

    @media screen and (max-width: 1025px){
        .content-card{
            margin-left: 0px;
            margin-right: 0px;
        }
    }
</style>