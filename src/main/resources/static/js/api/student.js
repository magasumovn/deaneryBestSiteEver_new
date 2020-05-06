import Vue from 'vue'

const students = Vue.resource('/student/{id}');

export default {
    get: () => students.get(),
    getByGroup: (groupName) => students.get({groupName}),
    getByStudentName: (studentName) => students.get({studentName}),
    getStudentById: id => students.get({id}),
    save: student => students.save({}, student),
    update: student => students.update({id: student.studentID}, student),
    remove: id => students.remove({id}),
}