# 基于ROS的安卓程序结构说明文档

标签（空格分隔）： 结构说明

---

### 参考资料
[CSDN博客链接](http://blog.csdn.net/f_season/article/details/9177931)
[GitHub——android_gingerbread_mr1链接](https://github.com/ardrobot/android_gingerbread_mr1)
[GitHub——android_honeycomb_mr2链接](https://github.com/ardrobot/android_honeycomb_mr2)
### 程序说明：
由于各种原因，本程序将许多多余的代码通过隐藏界面的方式暂时屏蔽，
### 程序结构：
com.example包下程序为自己编写
org.ros包下程序为基于ROS的基本组件

#### com.example包
- com.example.android.VoiceInteractionAdapter.java 语音识别适配器（因中文通过网络传输出现乱码功能未实现，界面已隐藏）

---

- com.example.application.NetBroadcastReceiver.java 
网络变化广播接受者，用于监听网络变化（由于基于局域网通信，所以网络必须为无线wifi）
- com.example.application.NetworkType.java 
定义静态方法GetNetworkType（），获取当前网络类型

---

- com.example.bank_robot.Main.java
银行机器人主界面
- com.example.bank_robot.MoveControl.java
银行机器人运动控制界面
- com.example.bank_robot.RobotControl.java
银行机器人控制界面
- com.example.bank_robot.VirtualJoystickView.java
银行机器人运动控制圆形自定义控件（需要设置最大速度）
- com.example.bank_robot.VoiceInteraction.java
银行机器人语音交互界面（已隐藏）
- com.example.bank_robot.WorkMode.java
银行机器人工作模式界面（由于银行机器人自身模式切换已修改接口，此功能已失效）


---


- com.example.meal.Main.java
送餐机器人主界面.（现已隐藏屏蔽）
- com.example.meal.MapNavigation.java
送餐机器人地图导航界面（仅测试，需要与机器人接口对接）（现已隐藏屏蔽）
- com.example.meal.MoveControl.java
送餐机器人运动控制界面（仅测试，需要与机器人接口对接）（现已隐藏屏蔽）

---

- com.example.ros.CharPublisher.java
字符串发布者（仅做测试）
- com.example.ros.TalkParam.java
控制运动消息发布者，发送运动消息控制机器人运动

---

- com.example.tccorobot_ros.MainActivity.java
APP主页面（现已隐藏屏蔽）

---

- com.example.trackedcar.ArmControl.java
坦克机器人机械臂控制界面（现已隐藏屏蔽）
- com.example.trackedcar.Main.java
坦克机器人主界面（现已隐藏屏蔽）
- com.example.trackedcar.MoveControl.java
坦克机器人运动控制界面（现已隐藏屏蔽）

### 基于ROS的安卓程序开发流程


1. 在AndroidManifest.xml配置启动NodeMainExecutorService
2. 继承 RosActivity类：此类继承Android原生类Activity
    开始一个 MasterChooser activity来提示用户填写master URI，并且显示正在进行的通告信息通知用户ROS节点正在后台运行。
3. 实现 NodeMain接口或继承AbstractNodeMain抽象类:定义一个基于ROS的节点






