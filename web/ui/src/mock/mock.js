import Mock from 'mockjs';
 const vehicle = Mock.mock(
 '/api/user/auth_list','post', (req, res) => {
    return  {
        "msg": "测试文字,获取所有权限列表",
        "code": 0,
        "data": {
            "list":[
                {"auth_name":"2-7","auth_f_id":4,"auth_f_name":"4","auth_id":7},
                {"auth_name":"2-6","auth_f_id":4,"auth_f_name":"4","auth_id":6},
                {"auth_name":"2-5","auth_f_id":4,"auth_f_name":"4","auth_id":5},
                {"auth_name":"2","auth_f_id":0,"auth_f_name":"0","auth_id":4},
                {"auth_name":"1-3","auth_f_id":1,"auth_f_name":"1","auth_id":3},
                {"auth_name":"1-2","auth_f_id":1,"auth_f_name":"1","auth_id":2},
                {"auth_name":"1","auth_f_id":0,"auth_f_name":"0","auth_id":1}
                ]
        }
    }
})
 const user = Mock.mock(
 '/api/user','get', (req, res) =>{
    return  {
        code: 0,
        data:{
            id:1,
           sex:1,
            age:25,
            createTime:'2017-04-01'
        },
        msg:'查询成功'
    }
} )

 export default { vehicle,user }