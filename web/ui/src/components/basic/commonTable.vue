<template>
    <div class="container">
        <table>
            <thead>
                <tr>
                    <td v-for="(item,index) in columns" 
                        :style="{'width':item.width?(item.width):'auto'}" 
                        :key="index">{{item.label}}
                    </td>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item,index) in rows" :key="index">
                    <td v-for="(v,i) in computedBodyColumns" :key="i">
                        {{item[v.id]}}
                    </td>
                    <td v-if="options.hasOperation">
                        <slot :data="item"></slot>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        props:{
            columns:{
                type:Array,
                default(){
                    return [
                        {"label":"编号","id":"number"},
                        {"label":"登录账户名","id":"account"},
                        {"label":"姓名","id":"name"},
                        {"label":"手机号","id":"mobile"},
                        {"label":"所属角色","id":"character"},
                        {"label":"操作","id":"operation"}
                    ]
                }
            },
            rows:{
                type:Array,
                default(){
                    return [
                        {   
                            "userId":11,
                            "number":1,
                            "account":"jane",
                            "name":"卡卡西",
                            "mobile":"18888888888",
                            "character":"销售组"
                        }
                    ]
                }
            },
            options:{
                type:Object,
                default(){
                    return {
                        "hasOperation":true
                    }
                }
            }
        },
        data(){
            return {
                        
            }
        },
        computed:{
            computedBodyColumns(){
                if(this.options.hasOperation){
                    return this.columns.filter((item)=>{
                        return item.id!="operation"
                    })
                }else{
                    return this.columns
                }
            }
        }
    }

</script>
<style scoped lang="less">
    .container{
        table{
            width: 98%;
            text-align: center;
            border: 1px solid #b3c3c9;
            thead{
                tr{
                    height: 50px;
                    background-color: #e3edf8;
                    td:not(:last-child){
                        border-right: 1px solid #b3c3c9;
                    }
                }
                
            }
            tbody{
                tr{
                    height:50px;
                    td{
                        border-right: 1px solid #b3c3c9;
                        border-top: 1px solid #b3c3c9;
                    }
                    td:last-child{
                        border-right: none;
                    }
                }
                tr:hover{
                    background-color: #f1f7fd;
                }
            }
        }
    }
</style>