package com.system.auth.dao;

import com.system.auth.model.AdminUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface AdminUserMapper {
    @Delete({
        "delete from t_admin_user",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer userId);

    @Insert({
        "insert into t_admin_user (user_id, user_name, ",
        "password, status, ",
        "mobile_number, contact_name, ",
        "system_id, description, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{userId,jdbcType=INTEGER}, #{userName,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, ",
        "#{mobileNumber,jdbcType=VARCHAR}, #{contactName,jdbcType=VARCHAR}, ",
        "#{systemId,jdbcType=INTEGER}, #{description,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=INTEGER}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(AdminUser record);

    @InsertProvider(type=AdminUserSqlProvider.class, method="insertSelective")
    int insertSelective(AdminUser record);

    @Select({
        "select",
        "user_id, user_name, password, status, mobile_number, contact_name, system_id, ",
        "description, create_user_id, update_time, create_time",
        "from t_admin_user",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
        @Result(column="mobile_number", property="mobileNumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="contact_name", property="contactName", jdbcType=JdbcType.VARCHAR),
        @Result(column="system_id", property="systemId", jdbcType=JdbcType.INTEGER),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    AdminUser selectByPrimaryKey(Integer userId);

    @UpdateProvider(type=AdminUserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AdminUser record);

    @Update({
        "update t_admin_user",
        "set user_name = #{userName,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "mobile_number = #{mobileNumber,jdbcType=VARCHAR},",
          "contact_name = #{contactName,jdbcType=VARCHAR},",
          "system_id = #{systemId,jdbcType=INTEGER},",
          "description = #{description,jdbcType=VARCHAR},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where user_id = #{userId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AdminUser record);
}