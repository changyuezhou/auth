export default {
    state: {
        show: false,
        text: "操作成功"
    },
    mutations: {
        change_global_toast_text(state, text) {
            state.text = text
        },

        show_global_toast(state) {
            state.show = true
        },

        close_global_toast(state) {
            state.text = "操作成功"
            state.show = false
        }
    },
    actions: {
        showToast(context, text = "操作成功") {
            if (context.state.show) {
                clearTimeout(window.toastTimer)
                context.commit('close_global_toast')
                context.commit('change_global_toast_text', text)
                context.commit('show_global_toast')
                window.toastTimer = setTimeout(() => {
                    context.commit('close_global_toast')
                }, 2000)
            } else {
                context.commit('change_global_toast_text', text)
                context.commit('show_global_toast')
                window.toastTimer = setTimeout(() => {
                    context.commit('close_global_toast')
                }, 2000)
            }
        }
    }
}