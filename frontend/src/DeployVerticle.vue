<template>
    <modal title="Deploy Verticle" effect="fade" v-model="display" @ok="display = false">
    
    </modal>
</template>

<script>
export default {
    data() {
        return {
            display: false
        }
    },
    methods: {
        displayModal() {
            this.display = true;
        }
    }
}
</script>
