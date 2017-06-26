
<style lang="scss">
.deploy-verticle .modal-body {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;

    div {
        margin-right: 16px;

        &:last-child {
            margin-right: 0px;
        }
    }

    .dropdown {
        .btn-group {
            display: block;
        }

        .dropdown-toggle {
            width: 88px;
        }
    }
}
</style>

<template>
    <modal class="deploy-verticle" title="Deploy Verticle" effect="fade" v-model="show" @ok="show = false" large>
        <div style="width: 100%">
            <v-input v-model="verticleClass" label="Verticle Class" help="Enter verticle class to deploy" required>
            </v-input>
        </div>
        <!-- TODO: Convert to switches -->
        <div class="dropdown">
            <label class="control-label">Instances</label>
            <number-spinner v-model="instances" :min="1"></number-spinner>
        </div>
        <div class="dropdown">
            <label class="control-label">Multithreading</label>
            <dropdown :text="multithreaded">
                <li>
                    <a v-for="availableType in ['Enabled', 'Disabled']" :key="availableType" @click="multithreaded = availableType">{{ availableType }}</a>
                </li>
            </dropdown>
        </div>
        <div class="dropdown">
            <label class="control-label">Verticle Type</label>
            <dropdown :text="verticleType">
                <li>
                    <a v-for="availableType in ['Standard', 'Worker']" :key="availableType" @click="verticleType = availableType">{{ availableType }}</a>
                </li>
            </dropdown>
        </div>
        <div slot="modal-footer" class="modal-footer" style="padding-top: 0; margin-top: 0">
            <button type="button" class="btn btn-default" @click="show = false">Close</button>
            <button type="button" class="btn btn-primary" @click="deployVerticle" :disabled="!inputsAreValid">Deploy</button>
        </div>
    </modal>
</template>

<script>
import NumberSpinner from './components/NumberSpinner.vue';

export default {
    components: {
        'number-spinner': NumberSpinner
    },
    data() {
        return {
            show: false,
            inProgress: false,
            verticleClass: '',
            verticleType: 'Standard',
            multithreaded: 'Disabled',
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
                isWorker: this.verticleType === 'Worker'
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
