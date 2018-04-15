export default {
    state: {
        show: false,
        text: "未知错误,请联系管理员"
    },
    mutations: {

        show_global_alert(state, text) {
            if (text) {
                state.text = typeof(text) == 'string' ? text : JSON.stringify(text)
            } else {
                state.text = "未知错误,请联系管理员"
            }
            state.show = true
        },

        close_global_alert(state) {
            if (state.show) {
                state.show = false
            }
        }
    }
}