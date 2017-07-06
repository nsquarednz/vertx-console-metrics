
<style lang="scss">
.deploy-verticle .modal-body .modal-row {
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
        .btn-group,
        .bootstrap-select .btn {
            width: 88px;
        }
    }
}

.deploy-verticle .deployment-config {
    margin-bottom: 0;

    textarea {
        resize: vertical;
        font-family: Menlo, Monaco, Consolas, monospace;
    }
}
</style>

<template>
    <modal class="deploy-verticle" title="Deploy Verticle" effect="fade" v-model="show" @ok="show = false" large>
        <div class="modal-row">
            <div style="width: 100%">
                <bs-input v-model="verticleClass" label="Verticle" help="Enter verticle to deploy (required)" required></bs-input>
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
        </div>
        <bs-input class="deployment-config" v-model="deployConfig" type="textarea" label="Deployment Config" help="Enter JSON deployment config (optional)" error="Invalid JSON entered" :pattern="isValidJson"></bs-input>
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
            instances: 1,
            deployConfig: ''
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
                instances: this.instances,
                deployConfig: this.deployConfig.trim() === '' ? {} : JSON.parse(this.deployConfig)
            };
            this.inProgress = true;
            this.$http.post(window.location.pathname + '/verticle/deploy', options)
                .then(response => response.json())
                .then(response => {
                    this.inProgress = false;
                    console.log(JSON.stringify(response));
                });
            this.show = false;
        },
        isValidJson(text) {
            if (text.trim() === '') return true;
            try {
                JSON.parse(text);
            } catch (e) {
                return false;
            }
            return true;
        }
    },
    computed: {
        inputsAreValid() {
            return /*!this.inProgress &&*/ this.verticleClass.length > 0 && this.isValidJson(this.deployConfig);
        }
    }
}
</script>
