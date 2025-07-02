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
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="writerApproveDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Admin')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>작가승인
                </v-btn>
                <v-dialog v-model="writerApproveDialog" width="500">
                    <WriterApprove
                        @closeDialog="writerApproveDialog = false"
                        @writerApprove="writerApprove"
                    ></WriterApprove>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="writerRejectDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Admin')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>작가거절
                </v-btn>
                <v-dialog v-model="writerRejectDialog" width="500">
                    <WriterReject
                        @closeDialog="writerRejectDialog = false"
                        @writerReject="writerReject"
                    ></WriterReject>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="pubApproveDialog = true" class="contrast-primary-text" small color="primary" >
                    <v-icon small>mdi-minus-circle-outline</v-icon>출간승인
                </v-btn>
                <v-dialog v-model="pubApproveDialog" width="500">
                    <PubApprove
                        @closeDialog="pubApproveDialog = false"
                        @pubApprove="pubApprove"
                    ></PubApprove>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="pubRejectDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Admin')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>출간거절
                </v-btn>
                <v-dialog v-model="pubRejectDialog" width="500">
                    <PubReject
                        @closeDialog="pubRejectDialog = false"
                        @pubReject="pubReject"
                    ></PubReject>
                </v-dialog>
            </div>
            <PublicationApprovedManagement @search="search" style="margin-bottom: 10px; background-color: #ffffff;"></PublicationApprovedManagement>
            <div class="mb-5 text-lg font-bold"></div>
            <div class="table-responsive">
                <v-table>
                    <thead>
                        <tr>
                        <th>Id</th>
                        <th>ApprovalStatus</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(val, idx) in value" 
                            @click="changeSelectedRow(val)"
                            :key="val"  
                            :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;':''"
                        >
                            <td class="font-semibold">{{ idx + 1 }}</td>
                            <td class="whitespace-nowrap" label="ApprovalStatus">{{ val.approvalStatus }}</td>
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
                        <div style="color:white; font-size:17px; font-weight:700;">Writer 등록</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <Writer :offline="offline"
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
                        <div style="color:white; font-size:17px; font-weight:700;">Writer 수정</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <div>
                            <Number label="WriterId" v-model="selectedRow.writerId" :editMode="true"/>
                            <String label="ApprovalStatus" v-model="selectedRow.approvalStatus" :editMode="true"/>
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
    name: 'writerGrid',
    mixins:[BaseGrid],
    components:{
    },
    data: () => ({
        path: 'writers',
        writerApproveDialog: false,
        writerRejectDialog: false,
        pubApproveDialog: false,
        pubRejectDialog: false,
    }),
    watch: {
    },
    methods:{
        async writerApprove(params){
            try{
                var path = "writerApprove".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','WriterApprove 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.writerApproveDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async writerReject(params){
            try{
                var path = "writerReject".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','WriterReject 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.writerRejectDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async pubApprove(params){
            try{
                var path = "pubApprove".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','PubApprove 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.pubApproveDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async pubReject(params){
            try{
                var path = "pubReject".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','PubReject 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.pubRejectDialog = false
            }catch(e){
                console.log(e)
            }
        },
    }
}

</script>