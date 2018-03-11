package com.system.auth.dao;

import com.system.auth.model.CustomArch;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface CustomArchMapper {
    @Delete({
        "delete from t_custom_arch",
        "where custom_arch_id = #{customArchId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String customArchId);

    @Insert({
        "insert into t_custom_arch (custom_arch_id, custom_arch_name, ",
        "custom_arch_f_id, platform_id, ",
        "flag, custom_arch_level, ",
        "custom_arch_f_tree, description, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{customArchId,jdbcType=VARCHAR}, #{customArchName,jdbcType=VARCHAR}, ",
        "#{customArchFId,jdbcType=VARCHAR}, #{platformId,jdbcType=VARCHAR}, ",
        "#{flag,jdbcType=INTEGER}, #{customArchLevel,jdbcType=INTEGER}, ",
        "#{customArchFTree,jdbcType=VARCHAR}, #{description,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=VARCHAR}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(CustomArch record);

    @InsertProvider(type=CustomArchSqlProvider.class, method="insertSelective")
    int insertSelective(CustomArch record);

    @Select({
        "select",
        "custom_arch_id, custom_arch_name, custom_arch_f_id, platform_id, flag, custom_arch_level, ",
        "custom_arch_f_tree, description, create_user_id, update_time, create_time",
        "from t_custom_arch",
        "where custom_arch_id = #{customArchId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="custom_arch_id", property="customArchId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="custom_arch_name", property="customArchName", jdbcType=JdbcType.VARCHAR),
        @Result(column="custom_arch_f_id", property="customArchFId", jdbcType=JdbcType.VARCHAR),
        @Result(column="platform_id", property="platformId", jdbcType=JdbcType.VARCHAR),
        @Result(column="flag", property="flag", jdbcType=JdbcType.INTEGER),
        @Result(column="custom_arch_level", property="customArchLevel", jdbcType=JdbcType.INTEGER),
        @Result(column="custom_arch_f_tree", property="customArchFTree", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    CustomArch selectByPrimaryKey(String customArchId);

    @UpdateProvider(type=CustomArchSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CustomArch record);

    @Update({
        "update t_custom_arch",
        "set custom_arch_name = #{customArchName,jdbcType=VARCHAR},",
          "custom_arch_f_id = #{customArchFId,jdbcType=VARCHAR},",
          "platform_id = #{platformId,jdbcType=VARCHAR},",
          "flag = #{flag,jdbcType=INTEGER},",
          "custom_arch_level = #{customArchLevel,jdbcType=INTEGER},",
          "custom_arch_f_tree = #{customArchFTree,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where custom_arch_id = #{customArchId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(CustomArch record);
}