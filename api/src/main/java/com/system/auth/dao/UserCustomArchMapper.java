package com.system.auth.dao;

import com.system.auth.model.UserCustomArch;
import com.system.auth.model.UserCustomArchKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface UserCustomArchMapper {
    @Delete({
        "delete from t_user_custom_arch",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and custom_arch_id = #{customArchId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(UserCustomArchKey key);

    @Insert({
        "insert into t_user_custom_arch (user_id, custom_arch_id, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{userId,jdbcType=VARCHAR}, #{customArchId,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=VARCHAR}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(UserCustomArch record);

    @InsertProvider(type=UserCustomArchSqlProvider.class, method="insertSelective")
    int insertSelective(UserCustomArch record);

    @Select({
        "select",
        "user_id, custom_arch_id, create_user_id, update_time, create_time",
        "from t_user_custom_arch",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and custom_arch_id = #{customArchId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="custom_arch_id", property="customArchId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    UserCustomArch selectByPrimaryKey(UserCustomArchKey key);

    @UpdateProvider(type=UserCustomArchSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserCustomArch record);

    @Update({
        "update t_user_custom_arch",
        "set create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where user_id = #{userId,jdbcType=VARCHAR}",
          "and custom_arch_id = #{customArchId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(UserCustomArch record);
}