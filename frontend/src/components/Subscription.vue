<template>
    <div>
        <Number
            label="UserId"
            v-model="value.userId"
            :editMode="editMode"
        />
        <String
            label="SubscriptionStatus"
            v-model="value.subscriptionStatus"
            :editMode="editMode"
        />
        <Date
            label="SubscriptionExpiryDate"
            v-model="value.subscriptionExpiryDate"
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
    name: 'Subscription',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "subscriptions",
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
