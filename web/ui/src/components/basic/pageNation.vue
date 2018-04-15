<!-- 表格分页组件 -->
<template>
    <nav class="container">
        <ul class="pagination pull-left">
            <li><span> 总共 {{ total }}  条数据 </span></li>
            <!--<li><span> 每页显示 {{ display }}  条数据 </span></li>-->
            <!--<li><span> 共 {{ page }} 页 </span></li>-->
            <!--<li><span> 当前第 {{ current }} 页 </span></li>-->
        </ul>
        <ul class="pagination pull-right">
            <li class="chose-display">
                每页显示
                <div class="display-box">
                    <div @click.stop="showChoseDisplayNumber=!showChoseDisplayNumber" class="display-show-box">{{display}}</div>
                    <ul v-show="showChoseDisplayNumber" class="display-selection-box">
                        <li v-for="(item,index) in showChoseDisplayList" @click="setDisplay(item)" :key="index">{{item}}</li>
                    </ul>
                    <span class="caret"></span>
                </div>     
            </li>
            <li :class="{'disabled': current == 1}" class="firstPage">
                <a href="javascript:;" @click="setCurrent(1)">首页 </a>
            </li>
            <li :class="{'disabled': current == 1}" class="prevPage">
                <a href="javascript:;" @click="setCurrent(current - 1)">上一页 </a>
            </li>
            <li v-for="(p,i) in grouplist" :class="{'active': current == p.val}" class="pages" :key="i">
                <a href="javascript:;" @click="setCurrent(p.val)"> {{ p.text }} </a>
            </li>
            <li :class="{'disabled': current == page}" class="nextPage">
                <a href="javascript:;" @click="setCurrent(current + 1)">下一页</a>
            </li>
            <li :class="{'disabled': current == page}" class="lastPage">
                <a href="javascript:;" @click="setCurrent(page)">尾页</a>
            </li>
            <!--<li class="chosePage">
                跳转至<input type="number" v-model.number="jumpIndex" @blur="jumpToPage" @keyup.enter="jumpToPage">
            </li>-->
        </ul>
    </nav>
</template>


<script>
    import {eventBus} from '../../common/eventBus.js'
    export default {
        /**
     * [pagination 分页组件]
     * @param  {Number} total         [数据总条数]
     * @param  {Number} display     [每页显示条数 default:10]
     * @param  {Number} current     [当前页码 default:1]
     * @param  {Number} pagegroup     [分页条数(奇数) default:5]
     * @param  {Event} pagechange     [页码改动时 dispatch ]
     * @return {[type]}   [description]
     */
    
        props: {
            total: {            // 数据总条数
                type: Number,
                default: 10
            },
            display: {            // 每页显示条数
                type: Number,
                default: 10
            },
            current: {            // 当前页码
                type: Number,
                default: 1
            },
            pagegroup: {        // 分页条数 -- 奇数
                type: Number,
                default: 5,
                coerce:function(v){
                    v = v > 0 ? v : 5;
                    return v % 2 === 1 ? v : v + 1;
                }
            }
        },
        data(){
            return {
                jumpIndex:1,
                showChoseDisplayNumber:false,
                showChoseDisplayList:[100,50,20,10]
            }
        },
        computed: {
            page:function() { // 总页数
                return Math.ceil(this.total / this.display);
            },
            grouplist:function(){ // 获取分页页码
                var len = this.page , temp = [], list = [], count = Math.floor(this.pagegroup / 2) ,center = this.current;
                if( len <= this.pagegroup ){
                    while(len--){ temp.push({text:this.page-len,val:this.page-len});};
                    return temp;
                }
                while(len--){ temp.push(this.page - len);};
                var idx = temp.indexOf(center);                
                (idx < count) && ( center = center + count - idx); 
                (this.current > this.page - count) && ( center = this.page - count);
                temp = temp.splice(center - count -1, this.pagegroup);                
                do {
                    var t = temp.shift();
                    list.push({
                        text: t,
                        val: t
                    });
                }while(temp.length);                
                if( this.page > this.pagegroup ){
                    (this.current > count + 1) && list.unshift({ text:'...',val: list[0].val - 1 });
                    (this.current < this.page - count) && list.push({ text:'...',val: list[list.length - 1].val + 1 });
                }
                return list;
            }
        },
        mounted(){
            eventBus.$on("reset-components",()=>{
                this.showChoseDisplayNumber = false;
            })
        },
        methods: {
            jumpToPage(){
                if(this.jumpIndex>this.page){
                    this.jumpIndex = this.page
                }else if(this.jumpIndex<=0){
                    this.jumpIndex = 1
                }
                this.setCurrent(this.jumpIndex)
            },
            setCurrent: function(idx) {
                if( this.current != idx && idx > 0 && idx < this.page + 1) {
                    // this.current = idx;
                    this.$emit('pagechange',idx);                    
                }                
            },
            setDisplay(pageSize){
                if(pageSize===this.display){
                    return 
                }
                this.$emit("pageSizeChange",pageSize)
            }
        }
    }

</script>

<style scoped lang="less">
    .container{
        margin-top:10px;
        width:98%;
        .pagination{
            li{
                float: left;
            }
        }
        .pull-right{
            >li.chose-display{
                padding:0 5px 0 5px;
                border:none;
                >.display-box{
                    float:right;
                    width:50px;
                    height: 28px;
                    margin-left:5px;
                    position:relative;
                    >.display-show-box{
                        width:100%;
                        height: 100%;
                        text-align:center;
                        cursor:pointer;
                        border: 1px solid #cfcfcf;
                    }
                    >.display-selection-box{
                        position:absolute;
                        left:0;
                        bottom:28px;
                        width:100%;
                        border: 1px solid #cfcfcf;
                        border-bottom: none;
                        background-color: #fff;
                        >li{
                            float:none;
                            width:100%;
                            height: 28px;
                            text-align:center;
                            cursor:pointer;
                        }
                    }
                    >.caret{
                        right:2px;
                        bottom:42%;
                        border-top: 0;
                        border-bottom: 4px dashed;
                        border-right: 4px solid transparent;
                        border-left: 4px solid transparent;
                        width: 0;
                        height: 0;
                        vertical-align: middle;
                        position:absolute;
                    }
                }
            }
            >li{
                padding-left:8px;
                padding-right: 8px; 
                height: 28px;
                line-height: 28px;
                border: 1px solid #cfcfcf;
                border-radius: 2px;
            }
            >li:not(:last-child){
                margin-right:6px;
            }
            >li:not(.disabled):hover{
                border-color: #2f9bfe;
                a{
                    color:#2f9bfe
                }
            }  
            >li.active{
                border-color: #1daaf1;
                background-color: #309cff;
                a{
                    color:#fff;
                }
            }
            >li.active:hover{
                border-color: #1daaf1;
                background-color: #309cff;
                a{
                    color:#fff;
                }
            }
            >li.chosePage{
                border: none;
                >input{
                    height: 28px;
                    width: 40px;
                    border: 1px solid #cfcfcf;
                    margin-left: 2px;
                    padding:0 3px 0 3px;
                    vertical-align: top;
                }
            }
        }
    }
</style>