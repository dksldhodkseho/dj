<template>
    <div>
        <Number
            label="BestsellerId"
            v-model="value.bestsellerId"
            :editMode="editMode"
        />
        <String
            label="BookId"
            v-model="value.bookId"
            :editMode="editMode"
        />
        <Number
            label="ViewCount"
            v-model="value.viewCount"
            :editMode="editMode"
        />
        <String
            label="SelectedStatus"
            v-model="value.selectedStatus"
            :editMode="editMode"
        />
        <Date
            label="SelectedAt"
            v-model="value.selectedAt"
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
    name: 'BestSeller',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "bestSellers",
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
