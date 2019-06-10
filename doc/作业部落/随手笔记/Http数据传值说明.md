# Http数据传值说明

标签（空格分隔）： 说明文档 随手笔记

---
[TOC]

###  一、WebServer服务Controller层，client层，service服务Controller层

#### 1.1、Get Http请求后端设置

```
	/**
	 * 模糊查询参数
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param vendorId
	 * @param parameterCategory
	 * @param parameterLevel
	 * @param zhName
	 * @return
	 */
	@RequestMapping(value = "selectLikeParameter")
	public ResponseMessage selectLikeParameter(
			@RequestParam(value = "pageNum", required = false, defaultValue = "-1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false, defaultValue = "0") Integer pageSize,
			@RequestParam("vendorId") Integer vendorId, @RequestParam("parameterCategory") String parameterCategory,
			@RequestParam("parameterLevel") String parameterLevel, @RequestParam("zhName") String zhName)
```
`说明：`
```
@RequestParam用于将请求参数区数据映射到功能处理方法的参数上。
在url中输入:localhost:8080/**/?userName=zhangsan

请求中包含username参数（如/requestparam1?userName=zhang），则自动传入。

接下来我们看一下@RequestParam注解主要有哪些参数：

value：参数名字，即入参的请求参数名字，如username表示请求的参数区中的名字为username的参数的值将传入；

required：是否必须，默认是true，表示请求中一定要有相应的参数，否则将报404错误码；

defaultValue：默认值，表示如果请求中没有同名参数时的默认值，默认值可以是SpEL表达式，如“#{systemProperties['java.vm.version']}”。
如果要@RequestParam为一个int型的数据传值，假如前端并未输入，那么将会为int型的数据赋值为null。显然，这是不允许的，直接报错。 
可以通过required=false或者true来要求@RequestParam配置的前端参数是否一定要传
```

####  1.2、Post Http请求后端处理(仅是其中一种实现方式)

##### 1.2.1、使用@RequestBody注解  
示例：
```
    /**
	 * 前端执行参数查询指令
	 * 
	 * @param parameterQueryWebEntities
	 * @return
	 */
	@RequestMapping(value = "executeParameterQueryCommands")
	public ResponseMessage executeParameterQueryCommands(
			@RequestBody List<ParameterQueryWebEntity> parameterQueryWebEntities) 
```
`说明:`
```
前端HTTP请求Content-Type需设置为application/json
@RequestBody接收的是一个Json对象的字符串,所以只能自动注入到一个JavaBean实体类中
@requestBody注解常用来处理content-type不是默认的application/x-www-form-urlcoded编码的内容，比如说：application/json或者是application/xml等。一般情况下来说常用其来处理application/json类型。
```
前端post数据示例：
```
```
[
		{
			"cgi":"460-00-100268-43",
			"parameterName":"定时测量模式",
			"parameterGroup":"上行时间对齐定时器",
			"enodebName":"龙门宾馆HL1W",
			"localCellId":"2",
			"queryCommandText":"LST TATIMER:LOCALCELLID=2;",
			"neVendorId":8,
			"parameterVendorId":8
		},
		{
			"cgi":"460-00-100268-43",
			"parameterName":"TA增强",
			"parameterGroup":"上行时间对齐定时器",
			"enodebName":"龙门宾馆HL1W",
			"localCellId":"2",
			"queryCommandText":"LST TATIMER:LOCALCELLID=2;",
			"neVendorId":8,
			"parameterVendorId":8
		},
		{
			"cgi":"460-00-100268-43",
			"parameterName":"异频频点小区重选优先级",
			"parameterGroup":"EUTRAN异频相邻频点",
			"enodebName":"龙门宾馆HL1W",
			"localCellId":"2",
			"queryCommandText":"LST EUTRANINTERNFREQ: LOCALCELLID=XX, DlEarfcn=37900;",
			"firstVariable":"DlEarfcn=37900",
			"neVendorId":8,
			"parameterVendorId":8
		},
				{
			"cgi":"460-00-100268-43",
			"parameterName":"移动性目标频点指示",
			"parameterGroup":"EUTRAN异频相邻频点",
			"enodebName":"龙门宾馆HL1W",
			"localCellId":"2",
			"queryCommandText":"LST EUTRANINTERNFREQ: LOCALCELLID=XX, DlEarfcn=XXX;",
			"firstVariable":"DlEarfcn=37900",
			"neVendorId":8,
			"parameterVendorId":8
		}
		]

```

PostMan测试方式

![20170705161223367.jpg-120.4kB][1]


##### 1.2.2、使用@RequestParam注解 
示例：
```
@RequestMapping(value = "/selectAll")
	public ResponseMessage selectAll(@RequestParam(name = "pageNum", required = false) Integer pageNum,
			@RequestParam(name = "pageSize", required = false) Integer pageSize) 
```
`说明：`
```
前端HTTP请求Content-Type设置application/x-www-form-urlencoded、multipart/form-data
适合传递简单类型数据
```
#### 1.3、其他注解
##### 1.1、@RequestHeader
```
@RequestHeader(name = "myHeader") String myHeader
```
##### 1.2、@CookieValue
```
@CookieValue(name = "myCookie") String myCookie
```
#####   1.3、@PathVariable
```
获取路径参数。即url/{id}这种形式。

@GetMapping("/demo/{id}")
public void demo(@PathVariable(name = "id") String id, @RequestParam(name = "name") String name) {
    System.out.println("id="+id);
    System.out.println("name="+name);
}
```











  [1]: http://static.zybuluo.com/zhanghuanliang/25188iiiuwwjr8kzsccvfzak/20170705161223367.jpg