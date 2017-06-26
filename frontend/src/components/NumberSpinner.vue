<script>
export default {
    props: {
        min: {
            type: Number,
            default: 0
        },
        max: {
            type: Number,
            default: 999
        },
        step: {
            type: Number,
            default: 1
        },
        value: Number
    },
    methods: {
        plus() {
            this.$emit('input', Math.max(this.min, this.value - this.step));
        },
        minus() {
            this.$emit('input', Math.min(this.max, this.value + this.step));
        },
        onWheel(e) {
            e.deltaY > 0 ? this.plus() : this.minus()
        }
    }
}
</script>

<template>
    <div class="input-group number-spinner">
        <span class="input-group-btn">
            <button class="btn btn-default btn-minus" @click="minus()">
                <span>-</span>
            </button>
        </span>
        <input type="text" class="form-control" v-model="value" @keydown.up="plus" @keydown.down="minus" @wheel="onWheel">
        <div class="input-group-btn">
            <button class="btn btn-default btn-plus" @click="plus()">
                <span>+</span>
            </button>
        </div>
    </div>
</template>

<style lang="scss">
.number-spinner {
    width: 88px;
}
</style>