<template>
  <div class="dialog-wrap" ref="dialogWrap" :style="{'display':isShow?'block':'none'}">
    <div class="dialog-cover" v-if="isShow" @click="closeMySelf"></div>
    <div class="content-wrap">
      <transition name="drop">
        <div class="dialog-content" ref="dialogContent" v-show="isShow" :style="vStyle">
          <p class="dialog-close" :class="{'hasHead':hasHead}" @click="closeMySelf"></p>
          <slot>empty</slot>
        </div>
      </transition>
    </div>
  
  </div>
</template>


<script>
export default {
  props: {
    isShow: {
      type: Boolean,
      default: false
    },
    hasHead: {
      type: Boolean,
      default: false
    },
    vStyle: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      h: 1080
    }
  },
  methods: {
    closeMySelf() {
      this.$emit("on-dialog-close")
    }
  }
}
</script>



<style scoped lang="less">
.drop-enter-active {
  transition: all .5s ease;
}

.drop-leave-active {
  transition: all .3s ease;
}

.drop-enter {
  /*transform: translateY(-500px);*/
  opacity: 0
}

.drop-leave-active {
  /*transform: translateY(-500px);*/
  opacity: 0
}

.dialog-wrap {
  position: fixed;
  width: 100%;
  height: 100%;
  z-index: 1000;
  top: 0;
  left: 0;
}

.dialog-cover {
  background: #000;
  opacity: .3;
  position: absolute;
  z-index: 5;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
.content-wrap{
    position: absolute;
    height: 100%;
    width: 100%;
    overflow-y: auto;
}
.dialog-content {
  position: absolute; // max-height: 50%;
  background: #fff;
  top: 20%;
  left: 50%;
  transform: translateX(-50%);
  z-index: 10;
  border-radius: 3px;
  line-height: 1.6;
}

.dialog-close {
  position: absolute;
  background: url('../../assets/images/icons/cancel.png') no-repeat center center;
  right: 10px;
  top: 13px;
  width: 16px;
  height: 16px;
  text-align: center;
  cursor: pointer;
}

.dialog-close.hasHead {
  background-image: url('../../assets/images/icons/cancel-white.png')
}
</style>
