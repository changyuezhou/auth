<template>
  <div class="container">
      <v-date-picker v-model="from" :endAt="fromEndAt"></v-date-picker>
      <span><i class="middleSpace"></i>至<i class="middleSpace"></i></span>
      <v-date-picker v-model="to" :startAt="toStartAt" :endAt="new Date()"></v-date-picker>
  </div>
</template>


<script>
export default {
    props:{
        dateRange:{
            type:Object,
            default(){
                return {
                    from:(()=>{
                        let dd = new Date()
                        let date = dd.getDate()-7
                        dd.setDate(date)
                        return dd
                    })(),
                    to:new Date()
                }
            }
        }
    },
    data(){
        return {
                from:null,
                to:null
        }
    },
    computed:{
        fromEndAt(){
            let dd = this.to?new Date(this.to):new Date(Date.now())
            return dd
        },
        toStartAt(){
            let dd = this.from?new Date(this.from):null
            return dd
        }
    },
    watch:{
        from(cur,old){
            let submitData = {
                from:new Date(new Date(this.from).toLocaleDateString()),
                to:new Date(new Date(new Date(this.to).toLocaleDateString()).getTime()+24*60*60*1000-1)
            }
            this.$emit('on-change',submitData)
        },
        to(cur,old){
            let submitData = {
                from:new Date(new Date(this.from).toLocaleDateString()),
                to:new Date(new Date(new Date(this.to).toLocaleDateString()).getTime()+24*60*60*1000-1)
            }
            this.$emit('on-change',submitData)
        }
    },
    mounted(){
        this.dateRange.from = new Date(this.dateRange.from)   
        this.dateRange.to = new Date(this.dateRange.to)   
        this.from = new Date(this.dateRange.from.toLocaleDateString())
        this.to = new Date(new Date(this.dateRange.to.toLocaleDateString()).getTime()+24*60*60*1000-1)
        this.$emit('on-change',{
            from:this.from,
            to:this.to
        })
    }
}
</script>

<style lang="less" scoped>
    .container{
        display:inline-block;
        .middleSpace{
            display: inline-block;
            height: 1px;
            width:10px;
        }
    }
</style>

