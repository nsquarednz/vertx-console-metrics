<template>
    <modal class="deploy-verticle" title="Deploy Verticle" effect="fade" v-model="show" @ok="show = false">
        <div style="width: 100%; margin-right: 16px">
            <v-input v-model="verticleClass" label="Verticle Class" help="Enter verticle class to deploy" required>
            </v-input>
        </div>
        <div style="float: right">
            <label class="control-label">Verticle Type</label>
            <dropdown :text="verticleType" style="display: block; width: 84px">
                <li>
                    <a v-for="availableType in verticleTypes" @click="verticleType = availableType">{{ availableType }}</a>
                </li>
            </dropdown>
        </div>
        <div slot="modal-footer" class="modal-footer" style="padding-top: 0; margin-top: 0">
            <button type="button" class="btn btn-default" @click="show = false">Cancel</button>
            <button type="button" class="btn btn-primary" @click="deployVerticle" :disabled="!inputsAreValid">Deploy</button>
        </div>
    </modal>
</template>

<style lang="scss">
.deploy-verticle .modal-body {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
}
</style>

<script>
export default {
    data() {
        return {
            show: false,
            verticleClass: '',
            verticleType: 'Standard',
            verticleTypes: ['Standard', 'Worker']
        }
    },
    methods: {
        displayModal() {
            this.show = true;
        },
        deployVerticle() {

        }
    },
    computed: {
        inputsAreValid() {
            return this.verticleClass.length > 0;
        }
    }
}
</script>
