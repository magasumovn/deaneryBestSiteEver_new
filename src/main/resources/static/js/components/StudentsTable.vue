<template>
    <v-data-table
            :headers="studentsHeaders"
            :items="students"
            sort-by="id"
            class="elevation-1"
    >
        <template v-slot:top>
            <v-toolbar flat color="white">
                <v-spacer/>
                <v-dialog v-model="studentsDialog" max-width="500px">
                    <template v-slot:activator="{ on }">
                        <v-btn color="primary" dark class="mb-2" v-on="on">Добавить студента</v-btn>
                    </template>
                    <v-card>
                        <v-card-title>
                            <span class="headline">{{ studentsFormTitle }}</span>
                        </v-card-title>

                        <v-card-text>
                            <v-container>
                                <v-row>
                                    <v-col cols="12" sm="6" md="4">
                                        <v-select
                                                v-model="editedStudent.group"
                                                :items="groupNames"
                                                label="Группа"
                                        />
                                    </v-col>
                                    <v-col cols="12" sm="6" md="4">
                                        <v-text-field v-model="editedStudent.studentName"
                                                      label="Имя студента"/>
                                    </v-col>
                                </v-row>
                            </v-container>
                        </v-card-text>

                        <v-card-actions>
                            <v-spacer/>
                            <v-btn color="primary darken-1" text @click="studentClose">Cancel</v-btn>
                            <v-btn color="primary darken-1" text @click="studentSave">Save</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </v-toolbar>
        </template>
        <template v-slot:item.action="{ item }">
            <v-icon
                    small
                    class="mr-2"
                    @click="editStudent(item)"
            >
                edit
            </v-icon>
            <v-icon
                    small
                    @click="deleteStudent(item)"
            >
                delete
            </v-icon>
        </template>
    </v-data-table>
</template>

<script>
    import studentsApi from "../api/student";

    export default {
        name: "StudentsTable",
        props: ['students', 'groupNames'],
        data() {
            return {
                studentsDialog: false,
                studentsHeaders: [
                    {
                        text: 'ID студента',
                        value: 'studentID'
                    },
                    {
                        text: 'Имя студента',
                        value: 'studentName'
                    },
                    {
                        text: 'Название группы',
                        value: 'group.groupName'
                    },
                    {
                        text: 'Действия',
                        value: 'action'
                    }
                ],
                editedStudentIndex: -1,
                editedStudent: {
                    studentName: '',
                    group: {}
                },
                defaultStudent: {
                    groupName: '',
                    group: {}
                }
            }
        },
        computed: {
            studentsFormTitle() {
                return this.editedStudentIndex === -1 ? 'Новый студент' : 'Редактировать';
            }
        },
        watch: {
            studentsDialog(val) {
                val || this.studentClose()
            },
        },
        methods: {
            studentClose() {
                this.studentsDialog = false;
                this.editedStudentIndex = -1;
                this.editedStudent = Object.assign({}, this.defaultStudent);
            },
            studentSave() {
                if (this.editedStudentIndex > -1) {
                    Object.assign(this.students[this.editedStudentIndex], this.editedStudent);
                    studentsApi.update(this.editedStudent);
                } else {
                    studentsApi.save(this.editedStudent).then(result =>
                        result.json().then(data => this.students.push(data))
                    );
                }
                this.studentClose();
            },
            editStudent(item) {
                this.editedStudentIndex = this.students.indexOf(item);
                this.editedStudent = Object.assign({}, item);
                this.studentsDialog = true;
            },
            deleteStudent(item) {
                const index = this.students.indexOf(item);
                let isDeleted = confirm('Удалить студента ?') && this.students.splice(index, 1);
                if (isDeleted) {
                    studentsApi.remove(item.studentID);
                }
            },
        },
    }
</script>

<style scoped>

</style>