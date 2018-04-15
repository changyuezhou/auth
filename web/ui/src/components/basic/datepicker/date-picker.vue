<template>
  <div class="datepicker"
       :style="{'width': width + 'px','min-width': '120px'}"
       v-clickoutside="closePopup">
    <input readonly
          class="input"
          :class="{readonly:readonly}"
          :value="text"
          :placeholder="innerPlaceholder"
          ref="input"
          @click.prevent="togglePopup">
    <i class="input-icon input-icon__calendar" 
      @click.prevent="togglePopup" ></i>
    <div class="datepicker-popup"
         :style="position"
         ref="calendar"
         v-show="showPopup">
        <calendar-panel @select="closePopup" v-model="currentValue" :show="showPopup" :start-at="startAt" :end-at="endAt"></calendar-panel>   
    </div>
  </div>
</template>

<script>
import CalendarPanel from './calendar-panel.vue'

export default {
  components: { CalendarPanel },
  props: {
    format: {
      type: String,
      default: 'yyyy-MM-dd'
    },
    range: {
      type: Boolean,
      default: false
    },
    width: {
      type: [String, Number],
      default: 160
    },
    placeholder: String,
    value: null,
    startAt:null,
    endAt:null,
    readonly:{
      type:Boolean,
      default:false
    }
  },
  data () {
    return {
      showPopup: false,
      currentValue: this.value,
      position: null,
      ranges: [],// 快捷选项
      zh:{
        'days': ['日', '一', '二', '三', '四', '五', '六'],
        'months': ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
        'placeholder': {
          'date': '请选择日期'
        }
      }
    }
  },
  watch: {
    value: {
      handler (val) {
          this.currentValue = this.isValidDate(val) ? val : undefined 
      },
      immediate: true
    },
    currentValue (val) {
      if (val) {
        this.$emit('input', val)
      }
    },
    showPopup (val) {
      if (val) {
        this.$nextTick(this.displayPopup)
        // this.displayPopup()
      }
    }
  },
  computed: {
    innerPlaceholder () {
      return this.placeholder || this.zh.placeholder.date
    },
    text () {
      if (this.currentValue) {
        return this.stringify(this.currentValue)
      }
      return ''
    }
  },
  methods: {
    closePopup () {
      this.showPopup = false
    },
    togglePopup () {
      if(this.readonly){
        return
      }
      if (this.showPopup) {
        this.$refs.input.blur()
        this.showPopup = false
      } else {
        this.$refs.input.focus()
        this.showPopup = true
      }
    },
    formatDate (date, fmt) {
      const map = {
        'M+': date.getMonth() + 1, // 月份
        '[Dd]+': date.getDate(), // 日
        '[Hh]+': date.getHours(), // 小时
        'm+': date.getMinutes(), // 分
        's+': date.getSeconds(), // 秒
        'q+': Math.floor((date.getMonth() + 3) / 3), // 季度
        'S': date.getMilliseconds() // 毫秒
      }
      let str = fmt.replace(/[Yy]+/g, function (str) {
        return ('' + date.getFullYear()).slice(4 - str.length)
      })
      Object.keys(map).forEach((key) => {
        str = str.replace(new RegExp(key), function (str) {
          const value = '' + map[key]
          return str.length === 1 ? value : ('00' + value).slice(value.length)
        })
      })
      return str
    },
    stringify (date) {
      return this.formatDate(new Date(date), this.format)
    },
    isValidDate (date) {
      return !!new Date(date).getTime()
    },
    displayPopup () {
      const dw = document.documentElement.clientWidth
      const dh = document.documentElement.clientHeight
      const InputRect = this.$el.getBoundingClientRect()
      const PopupRect = this.$refs.calendar.getBoundingClientRect()
      this.position = {}
      if (dw - InputRect.left < PopupRect.width && InputRect.right < PopupRect.width) {
        this.position.left = 1 - InputRect.left + 'px'
      } else if (InputRect.left + InputRect.width / 2 <= dw / 2) {
        this.position.left = 0
      } else {
        this.position.right = 0
      }
      if (InputRect.top <= PopupRect.height + 1 && dh - InputRect.bottom <= PopupRect.height + 1) {
        this.position.top = dh - InputRect.top - PopupRect.height - 1 + 'px'
      } else if (InputRect.top + InputRect.height / 2 <= dh / 2) {
        this.position.top = '100%'
      } else {
        this.position.bottom = '100%'
      }
    }
  },
  directives: {
    clickoutside: {
      bind (el, binding, vnode) {
        el['@clickoutside'] = (e) => {
          if (!el.contains(e.target) && binding.expression && vnode.context[binding.expression]) {
            binding.value()
          }
        }
        document.addEventListener('click', el['@clickoutside'], true)
      },
      unbind (el) {
        document.removeEventListener('click', el['@clickoutside'], true)
      }
    }
  }
}
</script>


<style scoped>
.datepicker {
  position: relative;
  display: inline-block;
  color:#73879c;
  font: 14px/1.5 "Helvetica Neue", Helvetica, Arial, "Microsoft Yahei", sans-serif;
}

.datepicker * {
  box-sizing: border-box;
}


.datepicker-popup {
  position: absolute;
  width: 250px;
  margin-top: 1px;
  margin-bottom: 1px;
  border: 1px solid #d9d9d9;
  background-color: #fff;
  box-shadow: 0 6px 12px rgba(0, 0, 0, .175);
  z-index: 1000;
}

.range {
  width: 496px;
}

.input {
  display: inline-block;
  width: 100%;
  height: 40px;
  padding: 6px 40px 6px 10px;
  font-size: 14px;
  line-height: 1.4;
  color: #555;
  background-color: #fff;
  border: 1px solid #d2d2d2;
  border-radius: 2px;
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
}

.input-icon {
  top: 0;
  right: 0;
  position: absolute;
  width: 40px;
  height: 100%;
  color: #888;
  text-align: center;
  font-style: normal;
}
.input-icon::after{
  content:'';
  display: inline-block;
  width: 0;
  height: 100%;
  vertical-align: middle;
}
.input-icon__calendar{
  background-image: url('../../../assets/images/icons/datePicker.png');
  background-position: center;
  background-repeat: no-repeat;
}
.input-icon__close::before {
  content: '\2716';
  vertical-align: middle;
}

.datepicker-top {
  margin: 0 12px;
  line-height: 34px;
  border-bottom: 1px solid rgba(0, 0, 0, .05);
}

.datepicker-top>span {
  white-space: nowrap;
  cursor: pointer;
}

.datepicker-top>span:hover {
  color: #1284e7;
}

.datepicker-top>span:after {
  content: "|";
  margin: 0 10px;
  color: #48576a;
}
.input.readonly{
  background-color: #eee;
}
</style>
