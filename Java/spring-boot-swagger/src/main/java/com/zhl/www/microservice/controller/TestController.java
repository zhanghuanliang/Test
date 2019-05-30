/**
 * 
 */
package com.zhl.www.microservice.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zhl.www.microservice.entity.TestParameter;
import com.zhl.www.microservice.entity.TestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("Test")
@Api(tags = "TestController 接口")
public class TestController {

	@PostMapping("testPost")
	@ApiOperation(value = "Post请求", notes = "Post请求测试接口")
	public TestResult testPost(TestParameter parameter) {
		return new TestResult("test");
	}

	@PostMapping("testPostJson")
	@ApiOperation(value = "Post Json格式数据请求", notes = "Post Json格式数据请求测试接口")
	public TestResult testPostJson(@RequestBody TestParameter parameter) {
		return new TestResult("test");
	}

	@PostMapping("testGet")
	@ApiOperation(value = "Get数据请求", notes = "Get数据请求测试接口")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "para1", value = "参数1", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "para2", value = "参数2", required = false, dataType = "Long", paramType = "query"),
			@ApiImplicitParam(name = "para3", value = "参数3", required = false, dataType = "Date", paramType = "query") })
	public TestResult testGet(String para1, Long para2, Date para3) {
		return new TestResult("test");
	}

}
