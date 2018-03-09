package com.system.auth.dao;

import com.system.auth.model.Authority;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface AuthorityMapper {
    @Delete({
        "delete from t_authority",
        "where auth_id = #{authId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer authId);

    @Insert({
        "insert into t_authority (auth_id, auth_name, ",
        "auth_f_id, system_id, ",
        "auth_level, auth_f_tree, ",
        "description, create_user_id, ",
        "update_time, create_time)",
        "values (#{authId,jdbcType=INTEGER}, #{authName,jdbcType=VARCHAR}, ",
        "#{authFId,jdbcType=INTEGER}, #{systemId,jdbcType=INTEGER}, ",
        "#{authLevel,jdbcType=INTEGER}, #{authFTree,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR}, #{createUserId,jdbcType=INTEGER}, ",
        "#{updateTime,jdbcType=BIGINT}, #{createTime,jdbcType=BIGINT})"
    })
    int insert(Authority record);

    @InsertProvider(type=AuthoritySqlProvider.class, method="insertSelective")
    int insertSelective(Authority record);

    @Select({
        "select",
        "auth_id, auth_name, auth_f_id, system_id, auth_level, auth_f_tree, description, ",
        "create_user_id, update_time, create_time",
        "from t_authority",
        "where auth_id = #{authId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="auth_id", property="authId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="auth_name", property="authName", jdbcType=JdbcType.VARCHAR),
        @Result(column="auth_f_id", property="authFId", jdbcType=JdbcType.INTEGER),
        @Result(column="system_id", property="systemId", jdbcType=JdbcType.INTEGER),
        @Result(column="auth_level", property="authLevel", jdbcType=JdbcType.INTEGER),
        @Result(column="auth_f_tree", property="authFTree", jdbcType=JdbcType.VARCHAR),
        @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.INTEGER),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    Authority selectByPrimaryKey(Integer authId);

    @UpdateProvider(type=AuthoritySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Authority record);

    @Update({
        "update t_authority",
        "set auth_name = #{authName,jdbcType=VARCHAR},",
          "auth_f_id = #{authFId,jdbcType=INTEGER},",
          "system_id = #{systemId,jdbcType=INTEGER},",
          "auth_level = #{authLevel,jdbcType=INTEGER},",
          "auth_f_tree = #{authFTree,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR},",
          "create_user_id = #{createUserId,jdbcType=INTEGER},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where auth_id = #{authId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Authority record);
}