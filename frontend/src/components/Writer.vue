<template>
    <div>
        <Number
            label="WriterId"
            v-model="value.writerId"
            :editMode="editMode"
        />
        <String
            label="ApprovalStatus"
            v-model="value.approvalStatus"
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
    name: 'Writer',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "writers",
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
