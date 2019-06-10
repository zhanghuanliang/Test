# 上海参数操作PostMan测试接口说明文档

标签（空格分隔）： 说明文档

---

PostMan下载地址：
> https://www.getpostman.com/apps


- HTTP GET 方式
![1529462700(1).jpg-89.6kB][1]


- HTTP POST方式

![1529462834(1).jpg-94.3kB][2]

示例：
460-00-111720-11   安居金祁新城HL2W 基站网元
切换信令优化开关 参数

参数查询
> 10.221.33.175:8550/commandOperationOpen/getParameterValueByInfo

```
{
	"cellKey": "460-00-111720-11",
	"paramEnName": "HoSignalingOptSwitch",
	"vendorEnName": "HUAWEI",
	"paramType": "ENODEBALGOSWITCH"
}
```
参数修改
> http://10.221.33.175:8550/commandOperationOpen/executeParameterAlter
```
{
	"cellKey": "460-00-111720-11",
	"paramEnName": "HoSignalingOptSwitch",
	"vendorEnName": "HUAWEI",
	"paramType": "ENODEBALGOSWITCH",
	"variables":[
		{"key":"HOSIGNALINGOPTSWITCH",
		"value":"HOSuccRateBoostOptSwitch-0"}
		]
}
```

  [1]: http://static.zybuluo.com/zhanghuanliang/az7dy2vxtlwvqnl81siv56co/1529462700%281%29.jpg
  [2]: http://static.zybuluo.com/zhanghuanliang/9fzhy4i13wrtjz56yjl1ra99/1529462834%281%29.jpg