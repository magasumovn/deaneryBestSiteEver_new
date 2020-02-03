import Vue from 'vue'

const groups = Vue.resource('/group/{id}');

export default {
    get: () => groups.get(),
    getGroupById: id => groups.get({id}),
    save: group => groups.save({}, group),
    update: group => groups.update({id: group.groupNumber}, group),
    remove: id => groups.remove({id}),
}