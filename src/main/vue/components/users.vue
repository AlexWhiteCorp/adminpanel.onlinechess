<template>
    <div class="in-content-wrapper">
        <users-crud-card card-id="users-crud"/>
    </div>
</template>
<script>
    import usersCrudCrad from 'components/cards/users-crud-card.vue'

    export default {
        data(){
            return{
            }
        },
        computed: {
        },
        components: {
            'users-crud-card' : usersCrudCrad
        },
        methods: {
        },
        mounted(){
        }
    }
</script>
<style scope>
</style>