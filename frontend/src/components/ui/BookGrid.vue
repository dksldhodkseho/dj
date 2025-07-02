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
                <v-btn style="margin-left: 5px;" @click="writeDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Writer')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>작성
                </v-btn>
                <v-dialog v-model="writeDialog" width="500">
                    <Write
                        @closeDialog="writeDialog = false"
                        @write="write"
                    ></Write>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="deleteDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Writer')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>삭제
                </v-btn>
                <v-dialog v-model="deleteDialog" width="500">
                    <Delete
                        @closeDialog="deleteDialog = false"
                        @delete="delete"
                    ></Delete>
                </v-dialog>
                <v-btn style="margin-left: 5px;" @click="publishRequestDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Writer')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>출간요청
                </v-btn>
                <v-dialog v-model="publishRequestDialog" width="500">
                    <PublishRequest
                        @closeDialog="publishRequestDialog = false"
                        @publishRequest="publishRequest"
                    ></PublishRequest>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="viewBookDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('User')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>도서열람
                </v-btn>
                <v-dialog v-model="viewBookDialog" width="500">
                    <ViewBook
                        @closeDialog="viewBookDialog = false"
                        @viewBook="viewBook"
                    ></ViewBook>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="selectBookCoverDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Writer')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>표지선택
                </v-btn>
                <v-dialog v-model="selectBookCoverDialog" width="500">
                    <SelectBookCover
                        @closeDialog="selectBookCoverDialog = false"
                        @selectBookCover="selectBookCover"
                    ></SelectBookCover>
                </v-dialog>
                <v-btn style="margin-left: 5px;" @click="requestCoverGenerationDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Writer')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>표지요청
                </v-btn>
                <v-dialog v-model="requestCoverGenerationDialog" width="500">
                    <RequestCoverGeneration
                        @closeDialog="requestCoverGenerationDialog = false"
                        @requestCoverGeneration="requestCoverGeneration"
                    ></RequestCoverGeneration>
                </v-dialog>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="updateDialog = true" class="contrast-primary-text" small color="primary" :disabled="!hasRole('Writer')">
                    <v-icon small>mdi-minus-circle-outline</v-icon>수정
                </v-btn>
                <v-dialog v-model="updateDialog" width="500">
                    <Update
                        @closeDialog="updateDialog = false"
                        @update="update"
                    ></Update>
                </v-dialog>
            </div>
            <BookDetailInfo @search="search" style="margin-bottom: 10px; background-color: #ffffff;"></BookDetailInfo>
            <BookList @search="search" style="margin-bottom: 10px; background-color: #ffffff;"></BookList>
            <div class="mb-5 text-lg font-bold"></div>
            <div class="table-responsive">
                <v-table>
                    <thead>
                        <tr>
                        <th>Id</th>
                        <th>Title</th>
                        <th>Content</th>
                        <th>WriterNickname</th>
                        <th>WriterId</th>
                        <th>CoverUrl</th>
                        <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(val, idx) in value" 
                            @click="changeSelectedRow(val)"
                            :key="val"  
                            :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;':''"
                        >
                            <td class="font-semibold">{{ idx + 1 }}</td>
                            <td class="whitespace-nowrap" label="Title">{{ val.title }}</td>
                            <td class="whitespace-nowrap" label="Content">{{ val.content }}</td>
                            <td class="whitespace-nowrap" label="WriterNickname">{{ val.writerNickname }}</td>
                            <td class="whitespace-nowrap" label="WriterId">{{ val.writerId }}</td>
                            <td class="whitespace-nowrap" label="CoverUrl">{{ val.coverUrl }}</td>
                            <td class="whitespace-nowrap" label="Status">{{ val.status }}</td>
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
                        <div style="color:white; font-size:17px; font-weight:700;">Book 등록</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <Book :offline="offline"
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
                        <div style="color:white; font-size:17px; font-weight:700;">Book 수정</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <div>
                            <Number label="BookId" v-model="selectedRow.bookId" :editMode="true"/>
                            <String label="Title" v-model="selectedRow.title" :editMode="true"/>
                            <String label="Content" v-model="selectedRow.content" :editMode="true"/>
                            <String label="WriterNickname" v-model="selectedRow.writerNickname" :editMode="true"/>
                            <Number label="WriterId" v-model="selectedRow.writerId" :editMode="true"/>
                            <String label="CoverUrl" v-model="selectedRow.coverUrl" :editMode="true"/>
                            <String label="Status" v-model="selectedRow.status" :editMode="true"/>
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
    name: 'bookGrid',
    mixins:[BaseGrid],
    components:{
    },
    data: () => ({
        path: 'books',
        writeDialog: false,
        deleteDialog: false,
        publishRequestDialog: false,
        viewBookDialog: false,
        selectBookCoverDialog: false,
        requestCoverGenerationDialog: false,
        updateDialog: false,
    }),
    watch: {
    },
    methods:{
        async write(params){
            try{
                var path = "write".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','Write 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.writeDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async delete(params){
            try{
                var path = "delete".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','Delete 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.deleteDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async publishRequest(params){
            try{
                var path = "publishRequest".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','PublishRequest 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.publishRequestDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async viewBook(params){
            try{
                var path = "viewBook".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','ViewBook 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.viewBookDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async selectBookCover(params){
            try{
                var path = "selectBookCover".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','SelectBookCover 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.selectBookCoverDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async requestCoverGeneration(params){
            try{
                var path = "requestCoverGeneration".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','RequestCoverGeneration 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.requestCoverGenerationDialog = false
            }catch(e){
                console.log(e)
            }
        },
        async update(params){
            try{
                var path = "update".toLowerCase();
                var temp = await this.repository.invoke(this.selectedRow, path, params)
                // 스넥바 관련 수정 필요
                // this.$EventBus.$emit('show-success','Update 성공적으로 처리되었습니다.')
                for(var i = 0; i< this.value.length; i++){
                    if(this.value[i] == this.selectedRow){
                        this.value[i] = temp.data
                    }
                }
                this.updateDialog = false
            }catch(e){
                console.log(e)
            }
        },
    }
}

</script>