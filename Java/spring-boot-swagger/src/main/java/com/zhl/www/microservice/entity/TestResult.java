/**
 * 
 */
package com.zhl.www.microservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "测试接口返回结果")
public class TestResult {
	@ApiModelProperty(value = "测试")
	private String test;

	/**
	 * @return the test
	 */
	public String getTest() {
		return test;
	}

	/**
	 * @param test
	 *            the test to set
	 */
	public void setTest(String test) {
		this.test = test;
	}

	/**
	 * 
	 */
	public TestResult() {
		// TODO Auto-generated constructor stub
	}

	public TestResult(String test) {
		super();
		this.test = test;
	}

}
