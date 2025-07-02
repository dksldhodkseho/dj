<template>

    <v-card outlined>
        <v-card-title>
            SelectBestSeller
        </v-card-title>

        <v-card-text>
            <Number label="BestsellerId" v-model="value.bestsellerId" :editMode="editMode"/>
            <Number label="BookId" v-model="value.bookId" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="selectBestSeller"
            >
                SelectBestSeller
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
    name: 'SelectBestSellerCommand',
    components:{
    },
    props: {},
    data: () => ({
        editMode: true,
        value: {},
    }),
    created() {
        this.value.bestsellerId = 0;
        this.value.bookId = 0;
    },
    watch: {
    },
    methods: {
        selectBestSeller() {
            this.$emit('selectBestSeller', this.value);
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

