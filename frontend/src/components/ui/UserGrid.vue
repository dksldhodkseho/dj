<template>
    <v-container>
        <v-snackbar
            v-model="snackbar.status"
            :timeout="snackbar.timeout"
            :color="snackbar.color"
        >
            
            <v-btn style="margin-left: 80px;" text @click="snackbar.status = false">
                Close
            </v-btn>
        </v-snackbar>
        <div class="panel">
            <div class="gs-bundle-of-buttons" style="max-height:10vh;">
                <v-btn @click="addNewRow" @class="contrast-primary-text" small color="primary">
                    <v-icon small style="margin-left: -5px;">mdi-plus</v-icon>등록
                </v-btn>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="openEditDialog()" class="contrast-primary-text" small color="primary">
                    <v-icon small>mdi-pencil</v-icon>수정
                </v-btn>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="registerDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('User')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>사용자등록
                </v-btn>
                <v-dialog v-model="registerDialog" width="500">
                    <Register
                        @closeDialog="registerDialog = false"
                        @register="register"
                    ></Register>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="subscribeDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('User')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>구독요청
                </v-btn>
                <v-dialog v-model="subscribeDialog" width="500">
                    <Subscribe
                        @closeDialog="subscribeDialog = false"
                        @subscribe="subscribe"
                    ></Subscribe>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="writerQuestDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('User')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>작가요청
                </v-btn>
                <v-dialog v-model="writerQuestDialog" width="500">
                    <WriterQuest
                        @closeDialog="writerQuestDialog = false"
                        @writerQuest="writerQuest"
                    ></WriterQuest>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="cancelSubscriptionDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>구독 취소요청
                </v-btn>
                <v-dialog v-model="cancelSubscriptionDialog" width="500">
                    <CancelSubscription
                        @closeDialog="cancelSubscriptionDialog = false"
                        @cancelSubscription="cancelSubscription"
                    ></CancelSubscription>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="chargePointDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>포인트충전요청
                </v-btn>
                <v-dialog v-model="chargePointDialog" width="500">
                    <ChargePoint
                        @closeDialog="chargePointDialog = false"
                        @chargePoint="chargePoint"
                    ></ChargePoint>
                </v-dialog>
            </div>
            <UserInfoList @search="search" style="margin-bottom: 10px; background-color: #ffffff;"></UserInfoList>
            <div class="mb-5 text-lg font-bold"></div>
            <div class="table-responsive">
                <v-table>
                    <thead>
                        <tr>
                        <th>Id</th>
                        <th>Email</th>
                        <th>Nickname</th>
                        <th>PasswordHash</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(val, idx) in value" 
                            @click="changeSelectedRow(val)"
                            :key="val"  
                            :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;':''"
                        >
                            <td class="font-semibold">{{ idx + 1 }}</td>
                            <td class="whitespace-nowrap" label="Email">{{ val.email }}</td>
                            <td class="whitespace-nowrap" label="Nickname">{{ val.nickname }}</td>
                            <td class="whitespace-nowrap" label="PasswordHash">{{ val.passwordHash }}</td>
                            <v-row class="ma-0 pa-4 align-center">
                                <v-spacer></v-spacer>
                                <Icon style="cursor: pointer;" icon="mi:delete" @click="deleteRow(val)" />
                            </v-row>
                        </tr>
                    </tbody>
                </v-table>
            </div>
        </div>
        <v-col>
            <v-dialog
                v-model="openDialog"
                transition="dialog-bottom-transition"
                width="35%"
            >
                <v-card>
                    <v-toolbar
                        color="primary"
                        class="elevation-0 pa-4"
                        height="50px"
                    >
                        <div style="color:white; font-size:17px; font-weight:700;">User 등록</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <User :offline="offline"
                            :isNew="!value.idx"
                            :editMode="true"
                            :inList="false"
                            v-model="newValue"
                            @add="append"
                        />
                    </v-card-text>
                </v-card>
            </v-dialog>
            <v-dialog
                v-model="editDialog"
                transition="dialog-bottom-transition"
                width="35%"
            >
                <v-card>
                    <v-toolbar
                        color="primary"
                        class="elevation-0 pa-4"
                        height="50px"
                    >
                        <div style="color:white; font-size:17px; font-weight:700;">User 수정</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <div>
                            <Number label="UserId" v-model="selectedRow.userId" :editMode="true"/>
                            <String label="Email" v-model="selectedRow.email" :editMode="true"/>
                            <String label="Nickname" v-model="selectedRow.nickname" :editMode="true"/>
                            <String label="PasswordHash" v-model="selectedRow.passwordHash" :editMode="true"/>
                            <v-divider class="border-opacity-100 my-divider"></v-divider>
                            <v-layout row justify-end>
                                <v-btn
                                    width="64px"
                                    color="primary"
                                    @click="save"
                                >
                                    수정
                                </v-btn>
                            </v-layout>
                        </div>
                    </v-card-text>
                </v-card>
            </v-dialog>
        </v-col>
    </v-container>
</template>

<script>
import { ref } from 'vue';
import { useTheme } from 'vuetify';
import BaseGrid from '../base-ui/BaseGrid.vue'


export default {
    name: 'userGrid',
    mixins:[BaseGrid],
    components:{
    },
    data: () => ({
        path: 'users',
        registerDialog: false,
        subscribeDialog: false,
        writerQuestDialog: false,
        cancelSubscriptionDialog: false,
        chargePointDialog: false,
    }),
    watch: {
    },
    methods:{
        async register(params){
            try{
                var path = "register".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','Register 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.registerDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async subscribe(params){
            try{
                var path = "subscribe".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','Subscribe 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.subscribeDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async writerQuest(params){
            try{
                var path = "writerQuest".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','WriterQuest 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.writerQuestDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async cancelSubscription(params){
            try{
                var path = "cancelSubscription".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','CancelSubscription 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.cancelSubscriptionDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async chargePoint(params){
            try{
                var path = "chargePoint".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','ChargePoint 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.chargePointDialog = false
            }catch(e){
                console.log(e)
            }
        },
    }
}

</script>