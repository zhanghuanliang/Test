/**
 * 
 */
package com.zhl.www.microservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "测试参数类")
public class TestParameter {
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

}
