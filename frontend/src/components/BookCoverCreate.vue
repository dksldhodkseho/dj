<template>

    <v-card outlined>
        <v-card-title>
            BookCoverCreate
        </v-card-title>

        <v-card-text>
            <Number label="RequestId" v-model="value.requestId" :editMode="editMode"/>
            <Number label="BookId" v-model="value.bookId" :editMode="editMode"/>
            <String label="Prompt" v-model="value.prompt" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="bookCoverCreate"
            >
                BookCoverCreate
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
    name: 'BookCoverCreateCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.requestId = 0;
        this.value.bookId = 0;
        this.value.prompt = '';
    },
    watch: {
    },
    methods: {
        bookCoverCreate() {
            this.$emit('bookCoverCreate', this.value);
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

