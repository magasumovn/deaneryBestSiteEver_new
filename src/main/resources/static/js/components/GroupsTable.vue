<template>
    <v-data-table
            :headers="groupsHeaders"
            :items="groups"
            @click:row="test"
            sort-by="id"
            class="elevation-1"
    >
        <template v-slot:top>
            <v-toolbar flat color="white">
                <v-spacer/>
                <v-dialog v-model="groupsDialog" max-width="500px">
                    <template v-slot:activator="{ on }">
                        <v-btn color="primary" dark class="mb-2" v-on="on">Новая группа</v-btn>
                    </template>
                    <v-card>
                        <v-card-title>
                            <span class="headline">{{ groupsFormTitle }}</span>
                        </v-card-title>

                        <v-card-text>
                            <v-container>
                                <v-row>
                                    <v-col cols="12" sm="6" md="4">
                                        <v-text-field v-model="editedGroup.groupName"
                                                      label="Название группы"/>
                                    </v-col>
                                </v-row>
                            </v-container>
                        </v-card-text>

                        <v-card-actions>
                            <v-spacer/>
                            <v-btn color="primary darken-1" text @click="groupsClose">Cancel</v-btn>
                            <v-btn color="primary darken-1" text @click="groupsSave">Save</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </v-toolbar>
        </template>
        <template v-slot:item.action="{ item }">
            <v-icon
                    small
                    class="mr-2"
                    @click="editGroup(item)"
            >
                edit
            </v-icon>
            <v-icon
                    small
                    @click="deleteGroup(item)"
            >
                delete
            </v-icon>
        </template>
    </v-data-table>
</template>

<script>
    import groupsApi from "../api/group";

    export default {
        name: "GroupsTable",
        props: ['groups'],
        data() {
            return {
                groupsDialog: false,
                groupsHeaders: [
                    {
                        text: 'Номер группы',
                        value: 'groupNumber'
                    },
                    {
                        text: 'Название группы',
                        value: 'groupName'
                    },
                    {
                        text: 'Действия',
                        value: 'action'
                    }
                ],
                editedGroupIndex: -1,
                editedGroup: {
                    groupName: ''
                },
                defaultGroup: {
                    groupName: ''
                }
            }
        },
        computed: {
            groupsFormTitle() {
                return this.editedGroupIndex === -1 ? 'Новая группа' : 'Редактировать';
            }
        },
        watch: {
            groupsDialog(val) {
                val || this.groupsClose()
            },
        },
        methods: {
            groupsClose() {
                this.groupsDialog = false;
                this.editedGroupIndex = -1;
                this.editedGroup = Object.assign({}, this.defaultGroup);
            },
            groupsSave() {
                if (this.editedGroupIndex > -1) {
                    Object.assign(this.groups[this.editedGroupIndex], this.editedGroup);
                    groupsApi.update(this.editedGroup);
                } else {
                    groupsApi.save(this.editedGroup).then(result =>
                        result.json().then(data => this.groups.push(data))
                    );
                }
                this.groupsClose();
            },
            editGroup(item) {
                this.editedGroupIndex = this.groups.indexOf(item);
                this.editedGroup = Object.assign({}, item);
                this.groupsDialog = true;
            },
            deleteGroup(item) {
                const index = this.groups.indexOf(item);
                let isDeleted = confirm('Удалить группу ?') && this.groups.splice(index, 1);
                if (isDeleted) {
                    groupsApi.remove(item.groupNumber);
                }
            },
            test(value) {
                console.log(value   );
            }
        },
    }
</script>

<style scoped>

</style>