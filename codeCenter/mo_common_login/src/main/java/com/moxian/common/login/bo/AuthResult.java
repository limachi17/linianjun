package com.moxian.common.login.bo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResult {

    private long id;

    private long userId;

    private String token;

    private Date lastLoginTime;
}