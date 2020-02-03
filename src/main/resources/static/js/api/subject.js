import Vue from 'vue'

const subjects = Vue.resource('/subject/{id}');

export default {
    get: () => subjects.get(),
    getSubjectById: id => subjects.get({id}),
    save: subject => subjects.save({}, subject),
    update: subject => subjects.update({id: subject.subjectID}, subject),
    remove: id => subjects.remove({id}),
}