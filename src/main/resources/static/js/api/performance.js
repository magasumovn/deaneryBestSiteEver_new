import Vue from 'vue'

const performances = Vue.resource('/performance/{id}');

export default {
    get: studentID => performances.get({studentID}),
    getByMark: mark => performances.get({mark}),
    getByStudentName: studentName => performances.get({studentName}),
    save: performance => performances.save({}, performance),
    update: (performance) => performances.update({studentID: performance.student.studentID, subjectID: performance.subject.subjectID, semesterNumber: performance.semesterNumber}, performance),
    remove: (performance) => performances.remove({studentID: performance.student.studentID, subjectID: performance.subject.subjectID, semesterNumber: performance.semesterNumber}),
}