<template>

    <v-card outlined>
        <v-card-title>
            PubApprove
        </v-card-title>

        <v-card-text>
            <Number label="WriterId" v-model="value.writerId" :editMode="editMode"/>
            <Number label="BookId" v-model="value.bookId" :editMode="editMode"/>
            <String label="Title" v-model="value.title" :editMode="editMode"/>
            <String label="Content" v-model="value.content" :editMode="editMode"/>
            <String label="CoverUrl" v-model="value.coverUrl" :editMode="editMode"/>
            <String label="Title" v-model="value.title" :editMode="editMode"/>
            <String label="Content" v-model="value.content" :editMode="editMode"/>
            <String label="CoverUrl" v-model="value.coverUrl" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="pubApprove"
            >
                PubApprove
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
    name: 'PubApproveCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.writerId = 0;
        this.value.bookId = 0;
        this.value.title = '';
        this.value.content = '';
        this.value.coverUrl = '';
        this.value.title = '';
        this.value.content = '';
        this.value.coverUrl = '';
    },
    watch: {
    },
    methods: {
        pubApprove() {
            this.$emit('pubApprove', this.value);
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

