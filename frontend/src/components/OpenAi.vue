<template>
    <div>
        <Number
            label="RequestId"
            v-model="value.requestId"
            :editMode="editMode"
        />
        <Number
            label="BookId"
            v-model="value.bookId"
            :editMode="editMode"
        />
        <String
            label="Prompt"
            v-model="value.prompt"
            :editMode="editMode"
        />
        <String
            label="CoverUrl"
            v-model="value.coverUrl"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'OpenAi',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "openAis",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
