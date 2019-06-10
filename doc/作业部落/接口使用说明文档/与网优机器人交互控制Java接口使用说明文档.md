# 与网优机器人交互控制Java接口使用说明文档

标签： 接口使用说明文档

---
[TOC]
### 1. 全部接口

 - 启动半自动建图（在已知地图中可指定导航目标点控制机器人移动）相关程序
 - 启动人工建图（需要人工移动机器人逐步建图）相关程序
 - 关闭半自动建图相关程序
 - 关闭人工建图相关程序
 - 保存已建立好2D地图
 - 启动导航相关程序
 - 关闭导航相关程序
 - 开启连续导航
 - 终止连续导航（到达一个导航目标点后终止下次导航任务）
 - 重新载入2D地图
 - 启动获取导航目标点相关程序
 - 关闭获取导航目标点相关程序

### 2. 接口说明
#### 1. 类结构
![1.png-12.5kB][1]
#### 2. 类 MappingController 结构
![2.png-39.9kB][2]
![3.png-63.5kB][3]
#### 3. 类 NavigationControllerNode 结构
![5.png-14.9kB][4]
![4.png-63.3kB][5]
![11.png-12.3kB][6]
![6.png-71.9kB][7]
#### 4. 类 NavigationTargetPointSubscriberNode 结构
![7.png-23.2kB][8]
![8.png-34.2kB][9]
![9.png-22.8kB][10]
![10.png-34.7kB][11]

### 2. 接口使用实例

```
		//1.启动人工建图（需要人工移动机器人逐步建图）相关程序
		MappingController.startupHectorMapping();
		
		
		//2.启动自动建图（可通过机器人运动接口控制机器人移动）相关程序
		MappingController.startupGmapping();
		
		// 3.启动导航相关程序
		NavigationControllerNode.startUpNavigation(" /home/robot/catkin_ws/src/wpb_home/wpb_home_tutorials/maps/map.yaml");
		
		
		//4.关闭人工建图（需要人工移动机器人逐步建图）相关程序
		MappingController.shutdownHectorMappong();
		
		
		//5.关闭自自动建图（可通过机器人运动接口控制机器人移动）相关程序
		MappingController.shutdownGmapping();
		
		
		//6. 关闭导航相关程序
		NavigationControllerNode.shutdownNavigation();
		
		
		//7. 保存已建立好2D地图
		MappingController.saveMap("/home/robot/map/map");
		
		
		
		
		
		//8. 开启连续导航 （需要提前加载节点）
		NodeConfiguration configuration=NodeConfiguration.newPrivate();
		NodeMainExecutor executor=new NodeMainExecutorService();
		NavigationControllerNode node=new NavigationControllerNode();
		executor.execute(node, configuration);
//由于上述代码会开辟另一个线程对对象进行初始化及其他操作且未找到初始化完成的标志，所以让线程挂起一秒钟。（实际使用时可在加载页面过程中执行上述代码，然后等待用户点击开启连续导航及中断连续导航。）
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		//组装数据
		PoseArray poseArray=node.topicMessageFactory.newFromType(PoseArray._TYPE);;
		List<Pose> poses=new ArrayList<Pose>();
		
		Pose pose1=node.topicMessageFactory.newFromType(Pose._TYPE);
		Pose pose2=node.topicMessageFactory.newFromType(Pose._TYPE);
		Pose pose3=node.topicMessageFactory.newFromType(Pose._TYPE);
		Pose pose4=node.topicMessageFactory.newFromType(Pose._TYPE);
		pose1.getPosition().setX(1.0708719492D);
		pose1.getPosition().setY(1.19932293892D);
		pose1.getOrientation().setW(1.0D);
		
		pose2.getPosition().setX(1.19524562359D);
		pose2.getPosition().setY(2.21338844299D);//
		pose2.getOrientation().setZ(0.720842671953D);
		pose2.getOrientation().setW(0.69309872478D);
		
		pose3.getPosition().setX(0.793729424477D);
		pose3.getPosition().setY(3.38977050781);
		pose3.getOrientation().setZ(0.891295273462D);
		pose3.getOrientation().setW(0.453423351301D);
		
		pose4.getPosition().setX(-0.00832581520081D);
		pose4.getPosition().setY(4.51576137543D);
		pose4.getOrientation().setZ(0.998092280034D);
		pose4.getOrientation().setW(0.0617397808248D);
		
		poses.add(pose1);
		poses.add(pose2);
		poses.add(pose3);
		poses.add(pose4);
		
		poseArray.setPoses(poses);
		//调用对象函数
		node.startupSuccessiveNavigation(poseArray);
		
		
		//9.终止连续导航（到达一个导航目标点后终止下次导航任务）（需要提前加载节点）
		node.shutdownSuccessiveNavigation();
		
		
		//10.重新载入2D地图
		NavigationControllerNode.afreshLoadingMap("/home/robot/map/map.yaml");
				//11 开启获取导航目标点相关程序
		NavigationTargetPointSubscriberNode.startUpGetNavigationTargetPoint("/home/robot/catkin_ws/src/wpb_home/wpb_home_tutorials/maps/map.yaml");
		
		//12 关闭获取导航目标点相关程序
		NavigationTargetPointSubscriberNode.shutdownGetNavigationTargetPoint();
```
 
 


  [1]: http://static.zybuluo.com/zhanghuanliang/gltg69p1ncu9lu55ym9i3679/1.png
  [2]: http://static.zybuluo.com/zhanghuanliang/mxan6elu45wabi9738yas7g7/2.png
  [3]: http://static.zybuluo.com/zhanghuanliang/f6j1q3qubp3ym9sgaszokf6b/3.png
  [4]: http://static.zybuluo.com/zhanghuanliang/fxdmw4kskpnlxephwwz7au19/5.png
  [5]: http://static.zybuluo.com/zhanghuanliang/le97dmjwb6vlb2xrc9s7reao/4.png
  [6]: http://static.zybuluo.com/zhanghuanliang/505424v1djz0afb9fkfiun8g/11.png
  [7]: http://static.zybuluo.com/zhanghuanliang/w0qn8b8oojxvt5c64kft139z/6.png
  [8]: http://static.zybuluo.com/zhanghuanliang/3zsld7lfqoqlbz4fdeals00f/7.png
  [9]: http://static.zybuluo.com/zhanghuanliang/iafnnrmhhse2yjft1r9e0i4o/8.png
  [10]: http://static.zybuluo.com/zhanghuanliang/6fnkjl27u86rbt82gysitdgj/9.png
  [11]: http://static.zybuluo.com/zhanghuanliang/su2zqfepm75757mo7w7hfqut/10.png