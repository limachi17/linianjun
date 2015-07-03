package com.moxian.common.login.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auth implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private long userId;
	
	private String token;
}
