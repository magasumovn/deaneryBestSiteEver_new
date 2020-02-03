<template>
    <v-data-table
            :headers="performancesHeaders"
            :items="performances"
            sort-by="id"
            class="elevation-1"
    >
        <template v-slot:top>
            <v-toolbar flat color="white">
                <v-spacer/>
                <v-dialog v-model="performancesDialog" max-width="500px">
                    <template v-slot:activator="{ on }">
                        <v-btn color="primary" dark class="mb-2" v-on="on">Добавить оценку</v-btn>
                    </template>
                    <v-card>
                        <v-card-title>
                            <span class="headline">{{ performancesFormTitle }}</span>
                        </v-card-title>

                        <v-card-text>
                            <v-container>
                                <v-row>
                                    <v-col cols="12" sm="6" md="4">
                                        <v-select
                                                v-model="editedPerformance.student"
                                                :items="studentNames"
                                                label="Студент"
                                                :disabled="editedPerformanceIndex > -1"
                                        />
                                    </v-col>
                                    <v-col cols="12" sm="6" md="4">
                                        <v-select
                                                v-model="editedPerformance.subject"
                                                :items="subjectNames"
                                                label="Предмет"
                                                :disabled="editedPerformanceIndex > -1"
                                        />
                                    </v-col>
                                    <v-col cols="12" sm="6" md="4">
                                        <v-text-field
                                                v-model="editedPerformance.semesterNumber"
                                                :disabled="editedPerformanceIndex > -1"
                                                      label="Номер семестра"/>
                                    </v-col>
                                    <v-col cols="12" sm="6" md="4">
                                        <v-text-field v-model="editedPerformance.mark"
                                                      label="Оценка"/>
                                    </v-col>
                                </v-row>
                            </v-container>
                        </v-card-text>

                        <v-card-actions>
                            <v-spacer/>
                            <v-btn color="primary darken-1" text @click="performanceClose">Cancel</v-btn>
                            <v-btn color="primary darken-1" text @click="performanceSave">Save</v-btn>
                        </v-card-actions>
                    </v-card>
                </v-dialog>
            </v-toolbar>
        </template>
        <template v-slot:item.action="{ item }">
            <v-icon
                    small
                    class="mr-2"
                    @click="editPerformance(item)"
            >
                edit
            </v-icon>
            <v-icon
                    small
                    @click="deletePerformance(item)"
            >
                delete
            </v-icon>
        </template>
    </v-data-table>
</template>

<script>
    import performancesApi from "../api/performance";

    export default {
        name: "PerformancesTable",
        props: ['performances', 'subjectNames', 'studentNames'],
        data() {
            return {
                performancesDialog: false,
                performancesHeaders: [
                    {
                        text: 'Имя студента',
                        value: 'student.studentName'
                    },
                    {
                        text: 'Название предмета',
                        value: 'subject.subjectName'
                    },
                    {
                        text: 'Номер семестра',
                        value: 'semesterNumber'
                    },
                    {
                        text: 'Оценка',
                        value: 'mark'
                    },
                    {
                        text: 'Действия',
                        value: 'action'
                    }
                ],
                editedPerformanceIndex: -1,
                editedPerformance: {
                    student: {},
                    subject: {},
                    semesterNumber: '',
                    mark: ''
                },
                defaultPerformance: {
                    student: {},
                    subject: {},
                    semesterNumber: '',
                    mark: ''
                }
            }
        },
        computed: {
            performancesFormTitle() {
                return this.editedPerformanceIndex === -1 ? 'Новая оценка' : 'Редактировать';
            }
        },
        watch: {
            performancesDialog(val) {
                val || this.performanceClose()
            },
        },
        methods: {
            performanceClose() {
                this.performancesDialog = false;
                this.editedPerformanceIndex = -1;
                this.editedPerformance = Object.assign({}, this.defaultPerformance);
            },
            performanceSave() {
                if (this.editedPerformanceIndex > -1) {
                    Object.assign(this.performances[this.editedPerformanceIndex], this.editedPerformance);
                    performancesApi.update(this.editedPerformance);
                } else {
                    performancesApi.save(this.editedPerformance).then(result =>
                        result.json().then(data => this.performances.push(data))
                    );
                }
                this.performanceClose();
            },
            editPerformance(item) {
                this.editedPerformanceIndex = this.performances.indexOf(item);
                this.editedPerformance = Object.assign({}, item);
                this.performancesDialog = true;
            },
            deletePerformance(item) {
                const index = this.performances.indexOf(item);
                let isDeleted = confirm('Удалить оценку?') && this.performances.splice(index, 1);
                if (isDeleted) {
                    performancesApi.remove(item);
                }
            },
        },
    }
</script>

<style scoped>

</style>