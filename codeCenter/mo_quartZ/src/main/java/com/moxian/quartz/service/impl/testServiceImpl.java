/**
 * @Title: testServiceImpl.java
 * @Package com.moxian.quartz.service.impl
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月22日 下午4:39:21
 * @version Moxian M1 V1.0
 */
package com.moxian.quartz.service.impl;

import com.moxian.common.exception.MoxianServiceException;
import com.moxian.quartz.service.testService;

/**
 * @ClassName: testServiceImpl
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月22日 下午4:39:21
 *
 */
public class testServiceImpl implements testService{

	@Override
	public String test(String str) {
		throw new MoxianServiceException("error message test sam", "MxTestCode");
	}

	@Override
	public String test2() {
		throw new RuntimeException("test2");
	}

}
