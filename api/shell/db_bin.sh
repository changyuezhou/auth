#!/usr/bin/env bash

HOST="127.0.0.1"
PORT=3306
USER="root"
PASSWORD="suxin@2017"
DATABASE="db_auth"
TIMESTAMP=`date +%s`
DATE=`date +%Y-%m-%d`

LOG="./${DATE}_LOG"

function MD5() {
  VALUE=$1

  MD5_NUM=`echo -n ${VALUE}|md5sum|cut -d ' ' -f1`

  echo "${MD5_NUM}"
}

function InitialUser() {
    USER_ID=$1
    USER_NAME=$2
    USER_PASSWORD=$3
    SQL="insert into t_user(user_id, user_name, password, status, mobile_number, contact_name, description, create_user_id, update_time, create_time) value('${USER_ID}', '${USER_NAME}', '${USER_PASSWORD}', 1, '13818732800', '管理员', '', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    MYSQL="mysql -h $HOST -P $PORT -D $DATABASE -u $USER -p$PASSWORD --default-character-set=utf8 -A -N"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG
}

function InitialSystem() {
    SYSTEM_ID=$1
    USER_ID=$2
    SQL="insert into t_system(system_id, system_name, description, create_user_id, update_time, create_time) value('${SYSTEM_ID}', '权限管理系统', '', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    MYSQL="mysql -h $HOST -P $PORT -D $DATABASE -u $USER -p$PASSWORD --default-character-set=utf8 -A -N"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG
}

function InitialAuthority() {
    SYSTEM_ID=$1
    USER_ID=$2

    MYSQL="mysql -h $HOST -P $PORT -D $DATABASE -u $USER -p$PASSWORD --default-character-set=utf8 -A -N"

    ####################################################################################################################
    AUTH_ID=`MD5 ${USER_ID}111`

    SQL="insert into t_authority(auth_id, auth_name, url, auth_f_id, auth_level, system_id,  description, create_user_id, update_time, create_time) value('${AUTH_ID}', '授权管理', '', '${AUTH_ID}', 0, '${SYSTEM_ID}', '', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    ####################################################################################################################
    AUTH_F_ID="${AUTH_ID}"
    AUTH_ID=`MD5 ${AUTH_ID}`

    SQL="insert into t_authority(auth_id, auth_name, url, auth_f_id, auth_level, system_id,  description, create_user_id, update_time, create_time) value('${AUTH_ID}', '系统管理', '/systemMng', '${AUTH_F_ID}', 1, '${SYSTEM_ID}', '', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    ####################################################################################################################
    AUTH_ID=`MD5 ${AUTH_ID}`

    SQL="insert into t_authority(auth_id, auth_name, url, auth_f_id, auth_level, system_id,  description, create_user_id, update_time, create_time) value('${AUTH_ID}', '权限管理', '/authorityMng', '${AUTH_F_ID}', 1, '${SYSTEM_ID}', '', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    ####################################################################################################################
    AUTH_ID=`MD5 ${AUTH_ID}`

    SQL="insert into t_authority(auth_id, auth_name, url, auth_f_id, auth_level, system_id,  description, create_user_id, update_time, create_time) value('${AUTH_ID}', '平台管理', '/platformMng', '${AUTH_F_ID}', 1, '${SYSTEM_ID}', '', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    ####################################################################################################################
    AUTH_ID=`MD5 ${AUTH_ID}`

    SQL="insert into t_authority(auth_id, auth_name, url, auth_f_id, auth_level, system_id,  description, create_user_id, update_time, create_time) value('${AUTH_ID}', '组管理', '/groupMng', '${AUTH_F_ID}', 1, '${SYSTEM_ID}', '', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    ####################################################################################################################
    AUTH_ID=`MD5 ${AUTH_ID}`

    SQL="insert into t_authority(auth_id, auth_name, url, auth_f_id, auth_level, system_id,  description, create_user_id, update_time, create_time) value('${AUTH_ID}', '角色管理', '/roleMng', '${AUTH_F_ID}', 1, '${SYSTEM_ID}', '', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    ####################################################################################################################
    AUTH_ID=`MD5 ${AUTH_ID}`

    SQL="insert into t_authority(auth_id, auth_name, url, auth_f_id, auth_level, system_id,  description, create_user_id, update_time, create_time) value('${AUTH_ID}', '组织管理', '/orgMng', '${AUTH_F_ID}', 1, '${SYSTEM_ID}', '', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    ####################################################################################################################
    AUTH_ID=`MD5 ${AUTH_ID}`

    SQL="insert into t_authority(auth_id, auth_name, url, auth_f_id, auth_level, system_id,  description, create_user_id, update_time, create_time) value('${AUTH_ID}', '用户管理', '/userMng', '${AUTH_F_ID}', 1, '${SYSTEM_ID}', '', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    ####################################################################################################################
    AUTH_ID=`MD5 ${AUTH_ID}`

    SQL="insert into t_authority(auth_id, auth_name, url, auth_f_id, auth_level, system_id,  description, create_user_id, update_time, create_time) value('${AUTH_ID}', '系统配置', '', '${AUTH_ID}', 0, '${SYSTEM_ID}', '', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    ####################################################################################################################
    AUTH_F_ID="${AUTH_ID}"
    AUTH_ID=`MD5 ${AUTH_ID}`

    SQL="insert into t_authority(auth_id, auth_name, url, auth_f_id, auth_level, system_id,  description, create_user_id, update_time, create_time) value('${AUTH_ID}', '修改密码', '/changePassword', '${AUTH_F_ID}', 1, '${SYSTEM_ID}', '', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG
}

function InitialPlatform() {
    PLATFORM_ID=$1
    SECRET_KEY=$2
    SYSTEM_ID=$3
    USER_ID=$4
    SQL="insert into t_platform(platform_id, platform_name, secret_key, platform_domain, system_id, description, create_user_id, update_time, create_time) value('${PLATFORM_ID}', '权限管理平台', '${SECRET_KEY}', 'www.authority.com', '${SYSTEM_ID}', '', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    MYSQL="mysql -h $HOST -P $PORT -D $DATABASE -u $USER -p$PASSWORD --default-character-set=utf8 -A -N"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG
}

function InitialRole() {
    ROLE_ID=$1
    PLATFORM_ID=$2
    USER_ID=$3
    SQL="insert into t_role(role_id, role_name, platform_id, description, create_user_id, update_time, create_time) value('${ROLE_ID}', '管理员', '${PLATFORM_ID}', '', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    MYSQL="mysql -h $HOST -P $PORT -D $DATABASE -u $USER -p$PASSWORD --default-character-set=utf8 -A -N"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG
}

function InitialRoleAuthority() {
    ROLE_ID=$1
    USER_ID=$2

    SQL="select auth_id from t_authority"

    RECORDS=`echo ${SQL} | ${MYSQL} --skip-column-names`

    MYSQL="mysql -h $HOST -P $PORT -D $DATABASE -u $USER -p$PASSWORD --default-character-set=utf8 -A -N"

    for RECORD in $RECORDS; do
        SQL="insert into t_role_authority(role_id, auth_id, create_user_id, update_time, create_time) value('${ROLE_ID}', '${RECORD}', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

        value=`echo ${SQL} | ${MYSQL} --skip-column-names`

        echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG
    done
}

function InitialUserRole() {
    USER_ID=$1
    ROLE_ID=$2

    SQL="insert into t_user_role(user_id, role_id, create_user_id, update_time, create_time) value('${USER_ID}', '${ROLE_ID}', '${USER_ID}', ${TIMESTAMP}, ${TIMESTAMP})"

    MYSQL="mysql -h $HOST -P $PORT -D $DATABASE -u $USER -p$PASSWORD --default-character-set=utf8 -A -N"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG
}

function InitialDB() {
    USER_NAME=$1
    USER_PASSWORD=`MD5 $2`

    USER_ID=`MD5 ${USER_NAME}`
    SYSTEM_ID=`MD5 ${USER_ID}`
    PLATFORM_ID=`MD5 ${SYSTEM_ID}`
    SECRET_KEY=`MD5 ${PLATFORM_ID}"123456"`
    ROLE_ID=`MD5 ${PLATFORM_ID}`

    InitialUser ${USER_ID} ${USER_NAME} ${USER_PASSWORD}
    InitialSystem ${SYSTEM_ID} ${USER_ID}
    InitialAuthority ${SYSTEM_ID} ${USER_ID}
    InitialPlatform ${PLATFORM_ID} ${SECRET_KEY} ${SYSTEM_ID} ${USER_ID}
    InitialRole ${ROLE_ID} ${PLATFORM_ID} ${USER_ID}
    InitialRoleAuthority ${ROLE_ID} ${USER_ID}
    InitialUserRole ${USER_ID} ${ROLE_ID}

    echo "PlatformID: ${PLATFORM_ID} SECRET KEY: ${SECRET_KEY}"
}

function DeleteDB() {
    SQL="delete from t_user"

    MYSQL="mysql -h $HOST -P $PORT -D $DATABASE -u $USER -p$PASSWORD --default-character-set=utf8 -A -N"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    SQL="delete from t_system"

    MYSQL="mysql -h $HOST -P $PORT -D $DATABASE -u $USER -p$PASSWORD --default-character-set=utf8 -A -N"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    SQL="delete from t_authority"

    MYSQL="mysql -h $HOST -P $PORT -D $DATABASE -u $USER -p$PASSWORD --default-character-set=utf8 -A -N"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    SQL="delete from t_platform"

    MYSQL="mysql -h $HOST -P $PORT -D $DATABASE -u $USER -p$PASSWORD --default-character-set=utf8 -A -N"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    SQL="delete from t_role"

    MYSQL="mysql -h $HOST -P $PORT -D $DATABASE -u $USER -p$PASSWORD --default-character-set=utf8 -A -N"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    SQL="delete from t_role_authority"

    MYSQL="mysql -h $HOST -P $PORT -D $DATABASE -u $USER -p$PASSWORD --default-character-set=utf8 -A -N"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG

    SQL="delete from t_user_role"

    MYSQL="mysql -h $HOST -P $PORT -D $DATABASE -u $USER -p$PASSWORD --default-character-set=utf8 -A -N"

    value=`echo ${SQL} | ${MYSQL} --skip-column-names`

    echo "[${DATE} INFO][SQL:${SQL}, VALUE:${value}]" >> $LOG
}

OP=$1

if [ "${OP}" == "INITIAL" ] ; then
    InitialDB $2 $3
    exit
fi

if [ "${OP}" == "DELETE" ] ; then
    DeleteDB
    exit
fi


