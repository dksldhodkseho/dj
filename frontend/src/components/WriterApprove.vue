<template>

    <v-card outlined>
        <v-card-title>
            WriterApprove
        </v-card-title>

        <v-card-text>
            <Number label="WriterId" v-model="value.writerId" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="writerApprove"
            >
                WriterApprove
            </v-btn>
            
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>

export default {
    name: 'WriterApproveCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.writerId = 0;
    },
    watch: {
    },
    methods: {
        writerApprove() {
            this.$emit('writerApprove', this.value);
        },
        close() {
            this.$emit('closeDialog');
        },
        change() {
            this.$emit("update:modelValue", this.value);
        },
    }
}
</script>

