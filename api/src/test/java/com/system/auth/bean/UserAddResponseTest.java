package com.system.auth.bean;

import com.system.auth.model.response.UserAddResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)

public class UserAddResponseTest extends ResponseMessage<UserAddResponse> {
}
