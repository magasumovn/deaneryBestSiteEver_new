<template>
    <v-data-table
            :headers="subjectsHeaders"
            :items="subjects"
            sort-by="id"
            class="elevation-1"
    >
        <template v-slot:top>
            <v-toolbar flat color="white">
                <v-col cols="2" class="mt-5">
                    <v-select
                            :items="courses"
                            v-model="selectedCourse"
                            label="Курс"
                            @change="courseChange"
                    ></v-select>
                </v-col>
                <v-spacer/>
                <v-dialog v-model="subjectsDialog" max-width="500px">
                    <template v-slot:activator="{ on }">
                        <v-btn color="primary" dark class="mb-2" v-on="on">Новый предмет</v-btn>
                    </template>
                    <v-card>
                        <v-card-title>
                            <span class="headline">{{ subjectsFormTitle }}</span>
                        </v-card-title>

                        <v-card-text>
                            <v-container>
                                <v-row>
                                    <v-col cols="12" sm="6" md="4">
                                        <v-text-field v-model="editedSubject.subjectName"
                                                      label="Название предмета"/>
                                    </v-col>
                                    <v-col cols="12" sm="6" md="4">
                                        <v-text-field v-model="editedSubject.courseName"
                                                      label="Курс"/>
                                    </v-col>
                                </v-row>
                            </v-container>
                        </v-card-text>

                        <v-card-actions>
                            <v-spacer/>
                            <v-btn color="primary darken-1" text @click="subjectsClose">Cancel</v-btn>
                            <v-btn color="primary darken-1" text @click="subjectsSave">Save</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </v-toolbar>
        </template>
        <template v-slot:item.action="{ item }">
            <v-icon
                    small
                    class="mr-2"
                    @click="editSubject(item)"
            >
                edit
            </v-icon>
            <v-icon
                    small
                    @click="deleteSubject(item)"
            >
                delete
            </v-icon>
        </template>
    </v-data-table>
</template>

<script>
    import subjectsApi from "../api/subject";

    export default {
        name: "SubjectsTable",
        data() {
            return {
                subjects: [],
                subjectsDialog: false,
                subjectsHeaders: [
                    {
                        text: 'Номер предмета',
                        value: 'subjectID'
                    },
                    {
                        text: 'Название предмета',
                        value: 'subjectName'
                    },
                    {
                        text: 'Курс',
                        value: 'courseName'
                    },
                    {
                        text: 'Действия',
                        value: 'action'
                    }
                ],
                editedSubjectIndex: -1,
                editedSubject: {
                    subjectName: '',
                    courseName: ''
                },
                defaultSubject: {
                    subjectName: '',
                    courseName: ''
                },
                courses: ['Все', '1', '2', '3', '4'],
                selectedCourse: 'Все'
            }
        },
        computed: {
            subjectsFormTitle() {
                return this.editedSubjectIndex === -1 ? 'Новый предмет' : 'Редактировать';
            }
        },
        watch: {
            subjectsDialog(val) {
                val || this.subjectsClose()
            },
        },
        methods: {
            subjectsClose() {
                this.subjectsDialog = false;
                this.editedSubjectIndex = -1;
                this.editedSubject = Object.assign({}, this.defaultSubject);
            },
            subjectsSave() {
                if (this.editedSubjectIndex > -1) {
                    Object.assign(this.subjects[this.editedSubjectIndex], this.editedSubject);
                    subjectsApi.update(this.editedSubject);
                } else {
                    subjectsApi.save(this.editedSubject).then(result =>
                        result.json().then(data => this.subjects.push(data))
                    );
                }
                this.subjectsClose();
            },
            editSubject(item) {
                this.editedSubjectIndex = this.subjects.indexOf(item);
                this.editedSubject = Object.assign({}, item);
                this.subjectsDialog = true;
            },
            deleteSubject(item) {
                const index = this.subjects.indexOf(item);
                let isDeleted = confirm('Удалить предмет ?') && this.subjects.splice(index, 1);
                if (isDeleted) {
                    subjectsApi.remove(item.subjectID);
                }
            },
            courseChange() {
                this.subjects = [];
                subjectsApi.getByCourse(this.selectedCourse).then(result => {
                    result.json().then(data => {
                        data.forEach(subject => {
                            this.subjects.push(subject);
                        })
                    })
                })
            }
        },
        created() {
            subjectsApi.get().then(result =>
                result.json().then(data => {
                    data.forEach(element => {
                        this.subjects.push(element);
                    });
                })
            );
        }
    }
</script>

<style scoped>

</style>