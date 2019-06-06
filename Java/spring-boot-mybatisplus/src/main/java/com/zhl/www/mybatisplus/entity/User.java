/**
 * 
 */
package com.zhl.www.mybatisplus.entity;

import lombok.Data;

/**
 * User类
 *
 * @author zhanghuanliang
 * @email zhanghuanliang@boco.com.cn
 * @version v1.0
 * @time 2019年6月6日 下午2:49:40
 * @modify <BR/>
 *         修改内容：<BR/>
 *         修改人员：<BR/>
 *         修改时间：<BR/>
 */
@Data
public class User {

	private Long id;
	private String name;
	private Integer age;
	private String email;

}
