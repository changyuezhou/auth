package com.system.auth.bean;

import com.system.auth.model.ext.UserView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)

public class UserQueryByPrimaryKeyResponseTest extends ResponseMessage<UserView> {
}
