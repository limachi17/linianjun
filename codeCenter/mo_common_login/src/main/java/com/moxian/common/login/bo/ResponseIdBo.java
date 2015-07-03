/**
 * @Title: ResponseIdBo.java
 * @Package com.moxian.com.sso.bo
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年3月31日 下午1:14:43
 * @version Moxian M1 V1.0
 */
package com.moxian.common.login.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * @ClassName: ResponseIdBo
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年3月31日 下午1:14:43
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseIdBo {
	
	private String id;

}