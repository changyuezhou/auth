package com.system.auth.dao;

import com.system.auth.model.CustomArchAuthority;
import com.system.auth.model.CustomArchAuthorityKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface CustomArchAuthorityMapper {
    @Delete({
        "delete from t_custom_arch_authority",
        "where custom_arch_id = #{customArchId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(CustomArchAuthorityKey key);

    @Insert({
        "insert into t_custom_arch_authority (custom_arch_id, auth_id, ",
        "create_user_id, update_time, ",
        "create_time)",
        "values (#{customArchId,jdbcType=VARCHAR}, #{authId,jdbcType=VARCHAR}, ",
        "#{createUserId,jdbcType=VARCHAR}, #{updateTime,jdbcType=BIGINT}, ",
        "#{createTime,jdbcType=BIGINT})"
    })
    int insert(CustomArchAuthority record);

    @InsertProvider(type=CustomArchAuthoritySqlProvider.class, method="insertSelective")
    int insertSelective(CustomArchAuthority record);

    @Select({
        "select",
        "custom_arch_id, auth_id, create_user_id, update_time, create_time",
        "from t_custom_arch_authority",
        "where custom_arch_id = #{customArchId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="custom_arch_id", property="customArchId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="auth_id", property="authId", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="create_user_id", property="createUserId", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.BIGINT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.BIGINT)
    })
    CustomArchAuthority selectByPrimaryKey(CustomArchAuthorityKey key);

    @UpdateProvider(type=CustomArchAuthoritySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CustomArchAuthority record);

    @Update({
        "update t_custom_arch_authority",
        "set create_user_id = #{createUserId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=BIGINT},",
          "create_time = #{createTime,jdbcType=BIGINT}",
        "where custom_arch_id = #{customArchId,jdbcType=VARCHAR}",
          "and auth_id = #{authId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(CustomArchAuthority record);
}