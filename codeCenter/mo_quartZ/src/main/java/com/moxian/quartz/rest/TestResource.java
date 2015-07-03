package com.moxian.quartz.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import com.moxian.common.bo.BoUtil;
import com.moxian.common.exception.MoxianRestException;
import com.moxian.quartz.domain.InputVo;
import com.moxian.quartz.service.testService;
import com.moxian.quartz.util.SpringUtil;
import com.moxian.quartz.vo.PersonAge;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @ClassName: RegResource
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年3月30日 下午4:33:49
 *
 */

@Slf4j
@Path(TestResource.BASE_PATH)
@Api(value = TestResource.BASE_PATH, description = "Reg for APP API")
public class TestResource {
	
	
	public final static String BASE_PATH = "quartz";

	private static final String NOT_FOUND_FORMAT = "Reg with ID '%s' not found";
	
	@Inject
	private SpringUtil springUtil;
	
	@Inject
	private PersonAge personAge;

	 private final testService testService;

	 @Inject
	 public TestResource(@SuppressWarnings("SpringJavaAutowiringInspection")
     final @NonNull testService testService){
		 this.testService = testService;
	 }

	/**
	 * 
	* @Title: reg 
	* @param: UserRegVo payload
	* @Description: 基本用户注册接口
	* @return BoUtil
	 * @throws IOException 
	 * @throws MoxianRestException 
	 * @throws Exception 
	 */
	 
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Regist an abstractUser")
	public BoUtil reg(final @NotNull @Valid InputVo playload){
		
		throw new MoxianRestException("sam test","mx1000002");
	}
	
	@Path("learn")
	@GET
	@ApiOperation("just for a test")
	public BoUtil quartzTest(){
		log.info("======quartzTest=======");
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		boUtil.setMsg(springUtil.getString());
		return boUtil;
//		BoUtil boUtil = BoUtil.builder().code("success").data("success").data(new Date()).msg(msg)
	}
	
	@Path("readc")
	@GET
	@ApiOperation("just for a test")
	public BoUtil quartzReadConfig(){
		log.info("======quartzTest=======");
		BoUtil boUtil = BoUtil.getDefaultTrueBo();
		boUtil.setMsg("test"+personAge.getAge());
		return boUtil;
	}

	
	
}