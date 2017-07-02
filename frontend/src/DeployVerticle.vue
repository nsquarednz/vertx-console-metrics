
<style lang="scss">
.deploy-verticle .modal-body {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    padding-bottom: 0;

    div {
        margin-right: 16px;

        &:last-child {
            margin-right: 0;
        }
    }

    .control-col {
        .btn-group {
            display: block;
        }
        .btn-group, .bootstrap-select .btn {
            width: 88px;
        }
    }
}
</style>

<template>
    <modal class="deploy-verticle" title="Deploy Verticle" effect="fade" v-model="show" @ok="show = false" large>
        <div style="width: 100%">
            <bs-input v-model="verticleClass" label="Verticle" help="Enter verticle to deploy" required></bs-input>
        </div>
        <!-- TODO: Convert to switches -->
        <div class="control-col">
            <label class="control-label">Instances</label>
            <number-spinner v-model="instances" :min="1"></number-spinner>
        </div>
        <div class="control-col">
            <label class="control-label">Verticle Type</label>
            <v-select v-model="verticleType" :options="['Standard', 'Worker']"></v-select>
        </div>
        <div class="control-col">
            <label class="control-label">HA</label>
            <v-select v-model="ha" :options="['Enabled', 'Disabled']"></v-select>
        </div>
        <div slot="modal-footer" class="modal-footer" style="padding-top: 0; margin-top: 0">
            <button type="button" class="btn btn-default" @click="show = false">Close</button>
            <button type="button" class="btn btn-primary" @click="deployVerticle" :disabled="!inputsAreValid">Deploy</button>
        </div>
    </modal>
</template>

<script>
export default {
    data() {
        return {
            show: false,
            inProgress: false,
            verticleClass: '',
            verticleType: 'Standard',
            ha: 'Disabled',
            instances: 1
        }
    },
    methods: {
        displayModal() {
            this.show = true;
        },
        deployVerticle() {
            const options = {
                name: this.verticleClass,
                isWorker: this.verticleType === 'Worker',
                ha: this.ha === 'Enabled',
                instances: this.instances
            };
            this.inProgress = true;
            this.$http.post(window.location.pathname + '/verticle/deploy', options)
                .then(response => response.json())
                .then(response => {
                    this.inProgress = false;
                    console.log(JSON.stringify(response));
                });
            this.show = false;
        }
    },
    computed: {
        inputsAreValid() {
            return !this.inProgress && this.verticleClass.length > 0;
        }
    }
}
</script>
