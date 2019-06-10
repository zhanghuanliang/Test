# Swagger（API文档）

标签（空格分隔）： 随手笔记

---
 
[TOC]


### 一、关于 Swagger

Swagger能成为最受欢迎的REST APIs文档生成工具之一，有以下几个原因：

 - Swagger 可以生成一个具有互动性的API控制台，开发者可以用来快速学习和尝试API。
 - Swagger 可以生成客户端SDK代码用于各种不同的平台上的实现。
 - Swagger 文件可以在许多不同的平台上从代码注释中自动生成。
 - Swagger 有一个强大的社区，里面有许多强悍的贡献者。
 


Swagger 文档提供了一个方法，使我们可以用指定的 JSON 或者 YAML 摘要来描述你的 API，包括了比如 names、order 等 API 信息。

你可以通过一个文本编辑器来编辑 Swagger 文件，或者你也可以从你的代码注释中自动生成。各种工具都可以使用 Swagger 文件来生成互动的 API 文档。

> 注意：用 Swagger 文件生成互动的 API 文档是最精简的，它展示了资源、参数、请求、响应。但是它不会提供你的API如何工作的其他任何一个细节。
 
### 二、SpringBoot整合Swagger2


#### 1、添加Maven依赖
```
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger2</artifactId>
	<version>2.6.1</version>
</dependency>

<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger-ui</artifactId>
	<version>2.6.1</version>
</dependency>
```
#### 2、Swagger配置类

```
package cn.saytime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author zh
 * @ClassName cn.saytime.Swgger2
 * @Description
 * @date 2017-07-10 22:12:31
 */
@Configuration
public class Swagger2 {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("cn.saytime.web"))
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("springboot利用swagger构建api文档")
				.description("简单优雅的restfun风格，http://blog.csdn.net/saytime")
				.termsOfServiceUrl("http://blog.csdn.net/saytime")
				.version("1.0")
				.build();
	}
}
```
- 说明：
> @Configuration注解该类，等价于XML中配置beans；
@Bean标注方法等价于XML中配置bean

#### 3、Application.class 加注解
```
package cn.saytime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringbootSwagger2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSwagger2Application.class, args);
	}
}
```
> 注解@EnableSwagger2 表示开启Swagger

#### 4、Restful 接口

```
package cn.saytime.web;

import cn.saytime.bean.JsonResult;
import cn.saytime.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zh
 * @ClassName cn.saytime.web.UserController
 * @Description
 */
@RestController
public class UserController {

	// 创建线程安全的Map
	static Map<Integer, User> users = Collections.synchronizedMap(new HashMap<Integer, User>());

	/**
	 * 根据ID查询用户
	 * @param id
	 * @return
	 */
	@ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
	@RequestMapping(value = "user/{id}", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getUserById (@PathVariable(value = "id") Integer id){
		JsonResult r = new JsonResult();
		try {
			User user = users.get(id);
			r.setResult(user);
			r.setStatus("ok");
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");
			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}

	/**
	 * 查询用户列表
	 * @return
	 */
	@ApiOperation(value="获取用户列表", notes="获取用户列表")
	@RequestMapping(value = "users", method = RequestMethod.GET)
	public ResponseEntity<JsonResult> getUserList (){
		JsonResult r = new JsonResult();
		try {
			List<User> userList = new ArrayList<User>(users.values());
			r.setResult(userList);
			r.setStatus("ok");
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");
			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@ApiOperation(value="创建用户", notes="根据User对象创建用户")
	@ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
	@RequestMapping(value = "user", method = RequestMethod.POST)
	public ResponseEntity<JsonResult> add (@RequestBody User user){
		JsonResult r = new JsonResult();
		try {
			users.put(user.getId(), user);
			r.setResult(user.getId());
			r.setStatus("ok");
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");

			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}

	/**
	 * 根据id删除用户
	 * @param id
	 * @return
	 */
	@ApiOperation(value="删除用户", notes="根据url的id来指定删除用户")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
	@RequestMapping(value = "user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<JsonResult> delete (@PathVariable(value = "id") Integer id){
		JsonResult r = new JsonResult();
		try {
			users.remove(id);
			r.setResult(id);
			r.setStatus("ok");
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");

			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}

	/**
	 * 根据id修改用户信息
	 * @param user
	 * @return
	 */
	@ApiOperation(value="更新信息", notes="根据url的id来指定更新用户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long",paramType = "path"),
			@ApiImplicitParam(name = "user", value = "用户实体user", required = true, dataType = "User")
	})
	@RequestMapping(value = "user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<JsonResult> update (@PathVariable("id") Integer id, @RequestBody User user){
		JsonResult r = new JsonResult();
		try {
			User u = users.get(id);
			u.setUsername(user.getUsername());
			u.setAge(user.getAge());
			users.put(id, u);
			r.setResult(u);
			r.setStatus("ok");
		} catch (Exception e) {
			r.setResult(e.getClass().getName() + ":" + e.getMessage());
			r.setStatus("error");

			e.printStackTrace();
		}
		return ResponseEntity.ok(r);
	}

	@ApiIgnore//使用该注解忽略这个API
	@RequestMapping(value = "/hi", method = RequestMethod.GET)
	public String  jsonTest() {
		return " hi you!";
	}
}
```
#### 5、Swagger2文档


> 启动SpringBoot项目，访问 http://localhost:8080/swagger-ui.html


#### 6、Swagger注解

##### `swagger`通过注解表明该接口会生成文档，包括接口名、请求方法、参数、返回信息的等等。
> 
- @Api：修饰整个类，描述Controller的作用
- @ApiOperation：描述一个类的一个方法，或者说一个接口
- @ApiParam：单个参数描述
- @ApiModel：用对象来接收参数
- @ApiProperty：用对象接收参数时，描述对象的一个字段
- @ApiResponse：HTTP响应其中1个描述
- @ApiResponses：HTTP响应整体描述
- @ApiIgnore：使用该注解忽略这个API
- @ApiError ：发生错误返回的信息
- @ApiImplicitParam：一个请求参数
- @ApiImplicitParams：多个请求参数
> 

`补充`
```
https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X#quick-annotation-overview
```

### 三、Swagger详解

#### 1、@ApiImplicitParam
![image.png-42.8kB][1]


#### 2、@ApiModelProperty

```
用于方法，字段； 表示对model属性的说明或者数据操作更改 
value–字段说明 
name–重写属性名字 
dataType–重写属性类型 
required–是否必填 
example–举例说明 
hidden–隐藏
```





  [1]: http://static.zybuluo.com/zhanghuanliang/qsmhducgjk4g5cibpu3qt7xz/image.png