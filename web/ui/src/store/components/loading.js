export default {
    state: {
        show: false
    },
    mutations: {

        SHOW_LOADING(state) {
            state.show = true
        },

        HIDE_LOADING(state) {
            state.show = false
        }
    }
}