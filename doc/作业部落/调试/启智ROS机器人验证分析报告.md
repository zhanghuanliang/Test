# 启智ROS机器人验证分析报告

标签： 启智ROS机器人 调试 功能验证 用户手册


----------
[TOC]
### 1. 需验证的功能

 - 手柄控制机器人运动
 - SLAM建图
 - Navigation导航
 - 人脸检测
 - 桌面物体检测
 - 语音指令
 - 语音播报
 
 ### 2. 参考资料及ROS基本介绍
`启智ROS机器人用户手册`

*ROS基本介绍可参考[博客链接](http://blog.csdn.net/pinbodexiaozhu/article/details/35642977)*
    ROS(Robot OperatingSystem）是一个机器人软件平台，它能为异质计算机集群提供类似操作系统的功能。ROS的前身是斯坦福人工智能实验室为了支持斯坦福智能机器人STAIR而建立的交换庭(switchyard）项目。到2008年，主要由威楼加拉吉继续该项目的研发。
    ROS提供一些标准操作系统服务，例如硬件抽象，底层设备控制，常用功能实现，进程间消息以及数据包管理。ROS是基于一种图状架构，从而不同节点的进程能接受，发布，聚合各种信息（例如传感，控制，状态，规划等等）。目前ROS主要支持Ubuntu。
    ROS可以分成两层，低层是上面描述的操作系统层，高层则是广大用户群贡献的实现不同功能的各种软件包，例如定位绘图，行动规划，感知，模拟等等。
    ROS（低层）使用BSD许可证，所有是开源软件，并能免费用于研究和商业用途。而高层的用户提供的包则可以使用很多种不同的许可证。
* ROS wiki的解释：
    ROS（Robot Operating System，下文简称“ROS”）是一个适用于机器人的开源的元操作系统。它提供了操作系统应有的服务，包括硬件抽象，底层设备控制，常用函数的实现，进程间消息传递，以及包管理。它也提供用于获取、编译、编写、和跨计算机运行代码所需的工具和库函数。
    ROS 的主要目标是为机器人研究和开发提供代码复用的支持。ROS是一个分布式的进程（也就是“节点”）框架，这些进程被封装在易于被分享和发布的程序包和功能包中。ROS也支持一种类似于代码储存库的联合系统，这个系统也可以实现工程的协作及发布。这个设计可以使一个工程的开发和实现从文件系统到用户接口完全独立决策（不受ROS限制）。同时，所有的工程都可以被ROS的基础工具整合在一起。
* Brian Gerkey的网上留言：
我通常这样解释ROS：
1. 通道：ROS提供了一种发布-订阅式的通信框架用以简单、快速地构建分布式计算系。
2. 工具：ROS提供了大量的工具组合用以配置、启动、自检、调试、可视化、登录、测试、终止分布式计算系统。
3. 强大的库：ROS提供了广泛的库文件实现以机动性、操作控制、感知为主的机器人功能。
4. 生态系统：ROS的支持与发展依托着一个强大的社区。ros.org尤其关注兼容性和支持文档，提供了一套“一站式”的方案使得用户得以搜索并学习来自全球开发者数以千计的ROS程序包。
* 摘自《ROS by Example》的解释：
    ROS的首要目标是提供一套统一的开源程序框架，用以在多样化的现实世界与仿真环境中实现对机器人的控制。
* 关于ROS的“通道（plumbing）”（摘自《ROS by Example》
    ROS的核心是节点（node）。节点是一小段用Python或C++写成的程序，用来执行某个相对简单的任务或进程。多个节点之间互相传递信息（message），并可以独立控制启动或终止。某一节点可以面向其它节点针对特定标题（topic）发布信息或提供服务（service）。
    例如：现有以节点将传感器读数传递至机器人控制器，在“/head_sonar标题“下存在一条信息包含有变量值“0.5“，即意味着传感器检测到的当前物体距离为0.5米。任何一个想要知道该传感器读数的节点都只要订阅（subscribe）/head_sonar标题即可。为了便于使用该读数，针对该订阅者的节点会定义一个回调函数，每当有新的信息传递到订阅者标题时，即执行该函数。上述流程的运行频率取决于发布者节点（publisher node）更新信息的频率。
    此外，节点还可以用来定义一个或多个服务（service）。ROS中服务的作用是在接收到来自其它节点的请求时回复该节点或执行某项任务。例如：控制LED灯的开关是一个服务；移动机器人在给定起始和目标位置的条件下返回导航路线规划也是一个服务。

### 3. 各功能验证过程及问题
#### **手柄控制机器人运动**
    通过有线的游戏手柄控制机器人的前后左右平移以及左右转运动。
问题：

 - *由于使用有线的手柄导致使用操作不便，实用性差。*
 - *松开手柄后，机器人还继续运动，停止不灵敏。*

代码模块实现分析：

 1.运行命令
```
$ roslaunch wpb_home_bringup js_ctrl.launch
``` 
launch文件内容：
```
<launch>

  <!-- wpb_home core-->
  <node pkg="wpb_home_bringup" type="wpb_home_core" name="wpb_home_core" output="screen">
    <param name="serial_port" type="string" value="/dev/ftdi"/> 
  </node>

  <!-- js node -->
  <node respawn="true" pkg="joy" type="joy_node" name="wpb_home_joy" >
    <param name="dev" type="string" value="/dev/input/js0" />
    <param name="deadzone" value="0.12" />
  </node>

  <!-- Axes Velcmd -->
  <param name="axis_linear" value="1" type="int"/>
  <param name="axis_angular" value="0" type="int"/>
  <param name="scale_linear" value="1" type="double"/>
  <param name="scale_angular" value="1" type="double"/>
  <node pkg="wpb_home_bringup" type="wpb_home_js_vel" name="teleop"/>

</launch>
``` 
2.数据关系图

  ![rosgraph.png-37.6kB][1]
  3.各节点结构信息
```
Node [/wpb_home_core]
Publications: 
 * /rosout [rosgraph_msgs/Log]
 * /imu/data_raw [sensor_msgs/Imu]
 * /tf [tf2_msgs/TFMessage]
 * /odom [nav_msgs/Odometry]

Subscriptions: 
 * /cmd_vel [geometry_msgs/Twist]

Services: 
 * /wpb_home_core/get_loggers
 * /wpb_home_core/set_logger_level
``` 
该节点说明：
  读写串口"`/dev/ftdi`"数据，发布`imu`数据、`tf`坐标转换数据，接收运动方向数据。
```
  Node [/teleop]
Publications: 
 * /rosout [rosgraph_msgs/Log]
 * /cmd_vel [geometry_msgs/Twist]

Subscriptions: 
 * /joy [sensor_msgs/Joy]

Services: 
 * /teleop/get_loggers
 * /teleop/set_logger_level
```
该节点说明：（[（joy功能包wiki链接）](http://wiki.ros.org/joy) ）
订阅游戏手柄操作数据，发布机器人运动方向数据。
```
Node [/wpb_home_joy]
Publications: 
 * /rosout [rosgraph_msgs/Log]
 * /joy [sensor_msgs/Joy]
 * /diagnostics [diagnostic_msgs/DiagnosticArray]

Subscriptions: None

Services: 
 * /wpb_home_joy/set_logger_level
 * /wpb_home_joy/get_loggers

```
该节点说明：
读取`/dev/input/js0`该输入设备，发布游戏手柄操作数据。
  
####  **SLAM建图**
 
    运行已提供的程序命令，人工缓慢移动机器人，逐步构建办公室地图并保存。
问题：

 - *需要人工移动机器人，不方便*



代码模块实现分析：

##### **Hector SLAM**

1.运行命令
```
$ roslaunch wpb_home_tutorials hector_mapping.launch 
```
launch文件内容：
```
<launch>

  <arg name="model" default="$(find wpb_home_bringup)/urdf/wpb_home.urdf"/>
  <arg name="gui" default="true" />
  <arg name="rvizconfig" default="$(find wpb_home_tutorials)/rviz/slam.rviz" />

  <param name="robot_description" command="$(find xacro)/xacro.py $(arg model)" />
  <param name="use_gui" value="$(arg gui)"/>

  <node name="joint_state_publisher" pkg="joint_state_publisher" type="joint_state_publisher">
    <rosparam file="$(find wpb_home_bringup)/config/wpb_home.yaml" command="load" />
  </node>
  <node name="robot_state_publisher" pkg="robot_state_publisher" type="state_publisher"/>
  <node name="rviz" pkg="rviz" type="rviz" args="-d $(arg rvizconfig)" required="true" />

  <node pkg="hector_mapping" type="hector_mapping" name="hector_mapping" output="screen">
  <!-- Frame names -->
  <param name="pub_map_odom_transform" value="true"/>
  <param name="map_frame" value="map" />
  <param name="base_frame" value="base_footprint" />
  <param name="odom_frame" value="base_footprint" />

  <!-- Tf use -->
  <param name="use_tf_scan_transformation" value="true"/>
  <param name="use_tf_pose_start_estimate" value="false"/>

  <!-- Map size / start point -->
  <param name="map_resolution" value="0.05"/>
  <param name="map_size" value="1024"/>
  <param name="map_start_x" value="0.5"/>
  <param name="map_start_y" value="0.5" />
  <param name="laser_z_min_value" value = "-1.0" />
  <param name="laser_z_max_value" value = "1.0" />
  <param name="map_multi_res_levels" value="2" />

  <param name="map_pub_period" value="2" />
  <param name="laser_min_dist" value="0.4" />
  <param name="laser_max_dist" value="5.5" />
  <param name="output_timing" value="false" />
  <param name="pub_map_scanmatch_transform" value="true" />
  <param name="tf_map_scanmatch_transform_frame_name" value="base_footprint" />

  <!-- Map update parameters -->
  <param name="update_factor_free" value="0.4"/>
  <param name="update_factor_occupied" value="0.7" />    
  <param name="map_update_distance_thresh" value="0.2"/>
  <param name="map_update_angle_thresh" value="0.06" />

  <!-- Advertising config --> 
  <param name="advertise_map_service" value="true"/>
  <param name="scan_subscriber_queue_size" value="5"/>
  <param name="scan_topic" value="scan"/>

  <remap from="/scanmatcher_frame" to="/base_footprint" />
  </node>

  <!--- Run Rplidar -->
  <node pkg="rplidar_ros" type="rplidarNode" name="rplidar" output="screen">
  <param name="serial_port"         type="string" value="/dev/rplidar"/>  
  <param name="serial_baudrate"     type="int"    value="115200"/>
  <param name="frame_id"            type="string" value="laser"/>
  <param name="inverted"            type="bool"   value="false"/>
  <param name="angle_compensate"    type="bool"   value="true"/>
  <remap from="scan" to="scan_raw"/>
  </node>
  
  <!-- Run lidar filter -->
  <node pkg="wpb_home_bringup" type="wpb_home_lidar_filter" name="wpb_home_lidar_filter">
    <param name="pub_topic" value="/scan"/>
  </node>

</launch>
```
2.数据关系图

![rosgraph.png-105.2kB][2]
3.各节点结构信息
``` {#1}
Node [/joint_state_publisher]
Publications: 
 * /joint_states [sensor_msgs/JointState]
 * /rosout [rosgraph_msgs/Log]

Subscriptions: None

Services: 
 * /joint_state_publisher/get_loggers
 * /joint_state_publisher/set_logger_level
```
该节点说明：[（joint_state_publisher功能包wiki链接）](http://wiki.ros.org/joint_state_publisher)
载入机器人模型的yaml文件，并将其解析发布出去。
``` {#2}
Node [/robot_state_publisher]
Publications: 
 * /rosout [rosgraph_msgs/Log]
 * /tf [tf2_msgs/TFMessage]
 * /tf_static [tf2_msgs/TFMessage]

Subscriptions: 
 * /joint_states [sensor_msgs/JointState]

Services: 
 * /robot_state_publisher/get_loggers
 * /robot_state_publisher/set_logger_level
```
该节点说明：[（robot_state_publisher功能包wiki链接）](http://wiki.ros.org/robot_state_publisher)
发布机器人的状态。
```
Node [/hector_mapping]
Publications: 
 * /map_metadata [nav_msgs/MapMetaData]
 * /slam_cloud [sensor_msgs/PointCloud]
 * /rosout [rosgraph_msgs/Log]
 * /tf [tf2_msgs/TFMessage]
 * /poseupdate [geometry_msgs/PoseWithCovarianceStamped]
 * /map [nav_msgs/OccupancyGrid]
 * /slam_out_pose [geometry_msgs/PoseStamped]

Subscriptions: 
 * /syscommand [unknown type]
 * /tf [tf2_msgs/TFMessage]
 * /tf_static [tf2_msgs/TFMessage]
 * /initialpose [geometry_msgs/PoseWithCovarianceStamped]
 * /scan [sensor_msgs/LaserScan]

Services: 
 * /hector_mapping/set_logger_level
 * /hector_mapping/get_loggers
 * /dynamic_map
```
该节点说明：[（hector_mapping功能包wiki链接）](http://wiki.ros.org/hector_mapping)
订阅激光雷达数据、坐标转换数据，发布绘制出地图数据。
```
Node [/rplidar]
Publications: 
 * /scan_raw [sensor_msgs/LaserScan]
 * /rosout [rosgraph_msgs/Log]

Subscriptions: None

Services: 
 * /rplidar/set_logger_level
 * /start_motor
 * /stop_motor
 * /rplidar/get_loggers

```
该节点说明：
思岚激光雷达驱动节点，读取/dev/rplidar改设备，发布激光雷达数据。
```
Node [/wpb_home_lidar_filter]
Publications: 
 * /rosout [rosgraph_msgs/Log]
 * /scan [sensor_msgs/LaserScan]

Subscriptions: 
 * /scan_raw [sensor_msgs/LaserScan]

Services: 
 * /wpb_home_lidar_filter/set_logger_level
 * /wpb_home_lidar_filter/get_loggers

```
该节点说明:
订阅思岚激光雷达的原始数据，发布经过处理过滤的激光雷达数据。

 
``` {#0}
Node [/rviz] 
Publications: 
 * /move_base_simple/goal [geometry_msgs/PoseStamped]
 * /rosout [rosgraph_msgs/Log]
 * /initialpose [geometry_msgs/PoseWithCovarianceStamped]
 * /clicked_point [geometry_msgs/PointStamped]

Subscriptions: 
 * /tf [tf2_msgs/TFMessage]
 * /tf_static [tf2_msgs/TFMessage]
 * /map_updates [unknown type]
 * /scan [sensor_msgs/LaserScan]
 * /map [nav_msgs/OccupancyGrid]

Services: 
 * /rviz/get_loggers
 * /rviz/set_logger_level
 * /rviz/reload_shaders

```
该节点说明： 
订阅各种数据并将这些数据可视化。

##### **Gmapping**

1.运行命令
```
$ roslaunch wpb_home_tutorials gmapping.launch
```
launch文件内容
```
<launch>
  
  <arg name="model" default="$(find wpb_home_bringup)/urdf/wpb_home.urdf"/>
  <arg name="gui" default="true" />

  <param name="robot_description" command="$(find xacro)/xacro.py $(arg model)" />
  <param name="use_gui" value="$(arg gui)"/>
  <arg name="rvizconfig" default="$(find wpb_home_tutorials)/rviz/slam.rviz" />
  <node name="rviz" pkg="rviz" type="rviz" args="-d $(arg rvizconfig)" required="true" />
  <node name="joint_state_publisher" pkg="joint_state_publisher" type="joint_state_publisher" />
  <node name="robot_state_publisher" pkg="robot_state_publisher" type="state_publisher" />

  <node pkg="gmapping" type="slam_gmapping" name="slam_gmapping">
    <param name="base_frame" value="base_footprint"/>
  </node>

  <!-- Run wpb_home core -->
  <node pkg="wpb_home_bringup" type="wpb_home_core" name="wpb_home_core" output="screen">
    <param name="serial_port" type="string" value="/dev/ftdi"/> 
  </node>

  <!--- Run Rplidar -->
  <node name="rplidarNode"          pkg="rplidar_ros"  type="rplidarNode" output="screen">
    <param name="serial_port"         type="string" value="/dev/rplidar"/>  
    <param name="serial_baudrate"     type="int"    value="115200"/>
    <param name="frame_id"            type="string" value="laser"/>
    <param name="inverted"            type="bool"   value="false"/>
    <param name="angle_compensate"    type="bool"   value="true"/>
    <remap from="scan" to="scan_raw"/>
  </node>
    
  <!-- Run lidar filter -->
  <node pkg="wpb_home_bringup" type="wpb_home_lidar_filter" name="wpb_home_lidar_filter">
    <param name="pub_topic" value="/scan"/>
  </node>

  <!-- joy node -->
  <node respawn="true" pkg="joy" type="joy_node" name="wpb_home_joy" >
    <param name="dev" type="string" value="/dev/input/js0" />
    <param name="deadzone" value="0.12" />
  </node>

  <!-- Axes Velcmd -->
  <param name="axis_linear" value="1" type="int"/>
  <param name="axis_angular" value="0" type="int"/>
  <param name="scale_linear" value="1" type="double"/>
  <param name="scale_angular" value="1" type="double"/>
  <node pkg="wpb_home_bringup" type="wpb_home_js_vel" name="teleop"/>

</launch>
```
2.数据关系图
![rosgraph.png-146.9kB][3]
3.各节点结构信息

```
Node [/slam_gmapping]
Publications: 
 * /map_metadata [nav_msgs/MapMetaData]
 * /slam_gmapping/entropy [std_msgs/Float64]
 * /rosout [rosgraph_msgs/Log]
 * /tf [tf2_msgs/TFMessage]
 * /map [nav_msgs/OccupancyGrid]

Subscriptions: 
 * /tf [tf2_msgs/TFMessage]
 * /tf_static [tf2_msgs/TFMessage]
 * /scan [sensor_msgs/LaserScan]

Services: 
 * /slam_gmapping/get_loggers
 * /slam_gmapping/set_logger_level
 * /dynamic_map

```
该节点说明：[（gmapping功能包wiki链接）](http://wiki.ros.org/gmapping)
订阅激光雷达数据、坐标转换数据，发布绘制出地图数据。


##### **保存地图**
1.运行命令
```
$ rosrun map_server map_saver -f map
```
说明：
将绘制的地图保存成文件，方便下次载入使用。



#### **Navigation导航**
    运行已提供的程序命令，加载之前绘制保存的地图，矫正机器人的初始位置，然后指定目标及机器人位姿，进行自主导航。
问题：

 - *由于加载地图时，机器人的默认初始位置为绘制地图的出发点，机器人实际初始位置与地图上的机器人初始位置不一致，导致进行自主导航之前，需要人工调整机器人的位置使之一致。*
 - *激光雷达只扫描一个2D平面，对支架以及较低的底盘类物品扫描不到，导致绘制的地图上无法标识，导航过程中也无法识别成障碍进行躲避。



 代码模块实现分析：
 1.运行命令
```
$ roslaunch wpb_home_tutorials nav.launch 
```
launch文件内容：
```
<launch>
  <master auto="start"/>

  <!-- Run wpb_home core -->
  <node pkg="wpb_home_bringup" type="wpb_home_core" name="wpb_home_core" output="screen">
    <param name="serial_port" type="string" value="/dev/ftdi"/> 
  </node>

  <!--- Run Rplidar -->
  <node name="rplidarNode"          pkg="rplidar_ros"  type="rplidarNode" output="screen">
    <param name="serial_port"         type="string" value="/dev/rplidar"/>  
    <param name="serial_baudrate"     type="int"    value="115200"/>
    <param name="frame_id"            type="string" value="laser"/>
    <param name="inverted"            type="bool"   value="false"/>
    <param name="angle_compensate"    type="bool"   value="true"/>
    <remap from="scan" to="scan_raw"/>
  </node>
    
  <!-- Run lidar filter -->
  <node pkg="wpb_home_bringup" type="wpb_home_lidar_filter" name="wpb_home_lidar_filter">
    <param name="pub_topic" value="/scan"/>
  </node>

  <!-- Run the map server -->
  <node name="map_server" pkg="map_server" type="map_server" args="$(find wpb_home_tutorials)/maps/map.yaml"/>

  <!--- Run AMCL -->
  <include file="$(find wpb_home_tutorials)/nav_lidar/amcl_omni.launch" />

  <!--- Run move base -->
  <node pkg="move_base" type="move_base" respawn="false" name="move_base"  output="screen">
    <rosparam file="$(find wpb_home_tutorials)/nav_lidar/costmap_common_params.yaml" command="load" ns="global_costmap" />
    <rosparam file="$(find wpb_home_tutorials)/nav_lidar/costmap_common_params.yaml" command="load" ns="local_costmap" />
    <rosparam file="$(find wpb_home_tutorials)/nav_lidar/local_costmap_params.yaml" command="load" />
    <rosparam file="$(find wpb_home_tutorials)/nav_lidar/global_costmap_params.yaml" command="load" />
    <param name="base_global_planner" value="global_planner/GlobalPlanner" /> 
    <param name="use_dijkstra" value="true"/>
    <param name="base_local_planner" value="wpbh_local_planner/WpbhLocalPlanner" />

    <param name= "controller_frequency" value="10" type="double"/>
  </node>

  <!-- RViz and TF tree -->
  <arg name="model" default="$(find wpb_home_bringup)/urdf/wpb_home.urdf"/>
  <arg name="gui" default="true" />
  <arg name="rvizconfig" default="$(find wpb_home_tutorials)/rviz/nav.rviz" />

  <param name="robot_description" command="$(find xacro)/xacro.py $(arg model)" />
  <param name="use_gui" value="$(arg gui)"/>

  <node name="joint_state_publisher" pkg="joint_state_publisher" type="joint_state_publisher">
    <rosparam file="$(find wpb_home_bringup)/config/wpb_home.yaml" command="load" />
  </node>
  <node name="robot_state_publisher" pkg="robot_state_publisher" type="state_publisher"/>
  <node name="rviz" pkg="rviz" type="rviz" args="-d $(arg rvizconfig)" required="true" />

</launch>
```
内含amcl_omni.launch文件
```
<launch>
<node pkg="amcl" type="amcl" name="amcl" output="screen">
  <!-- Publish scans from best pose at a max of 10 Hz -->
  <param name="odom_model_type" value="omni"/>
  <param name="odom_alpha5" value="0.1"/>
  <param name="transform_tolerance" value="0.2" />
  <param name="gui_publish_rate" value="10.0"/>
  <param name="laser_max_beams" value="30"/>
  <param name="min_particles" value="100"/>
  <param name="max_particles" value="1000"/>
  <param name="kld_err" value="0.05"/>
  <param name="kld_z" value="0.99"/>
  <param name="odom_alpha1" value="0.2"/>
  <param name="odom_alpha2" value="0.2"/>
  <!-- translation std dev, m -->
  <param name="odom_alpha3" value="0.8"/>
  <param name="odom_alpha4" value="0.2"/>
  <param name="laser_z_hit" value="0.5"/>
  <param name="laser_z_short" value="0.05"/>
  <param name="laser_z_max" value="0.05"/>
  <param name="laser_z_rand" value="0.5"/>
  <param name="laser_sigma_hit" value="0.2"/>
  <param name="laser_lambda_short" value="0.1"/>
  <param name="laser_lambda_short" value="0.1"/>
  <param name="laser_model_type" value="likelihood_field"/>
  <!-- <param name="laser_model_type" value="beam"/> -->
  <param name="laser_likelihood_max_dist" value="2.0"/>
  <param name="update_min_d" value="0.2"/>
  <param name="update_min_a" value="0.5"/>
  <param name="odom_frame_id" value="odom"/>
  <param name="resample_interval" value="1"/>
  <param name="transform_tolerance" value="0.1"/>
  <param name="recovery_alpha_slow" value="0.0"/>
  <param name="recovery_alpha_fast" value="0.0"/>
</node>
</launch>
```
2.数据关系图：
![rosgraph.png-357kB][4]
3.各节点结构信息
```
Node [/amcl]
Publications: 
 * /amcl_pose [geometry_msgs/PoseWithCovarianceStamped]
 * /tf [tf2_msgs/TFMessage]
 * /particlecloud [geometry_msgs/PoseArray]
 * /rosout [rosgraph_msgs/Log]
 * /amcl/parameter_descriptions [dynamic_reconfigure/ConfigDescription]
 * /amcl/parameter_updates [dynamic_reconfigure/Config]

Subscriptions: 
 * /tf [tf2_msgs/TFMessage]
 * /scan [sensor_msgs/LaserScan]
 * /tf_static [tf2_msgs/TFMessage]
 * /initialpose [geometry_msgs/PoseWithCovarianceStamped]

Services: 
 * /global_localization
 * /set_map
 * /amcl/set_parameters
 * /amcl/get_loggers
 * /request_nomotion_update
 * /amcl/set_logger_level
```
该节点说明[（amcl功能包wiki链接）](http://wiki.ros.org/amcl)
 AMCL 主动蒙特卡罗粒子滤波定位算法，订阅激光雷达数据、坐标转换数据 ，发布坐标转换数据，进行定位。
```
Node [/map_server]
Publications: 
 * /map_metadata [nav_msgs/MapMetaData]
 * /map [nav_msgs/OccupancyGrid]
 * /rosout [rosgraph_msgs/Log]

Subscriptions: None

Services: 
 * /static_map
 * /map_server/get_loggers
 * /map_server/set_logger_level

```
该节点说明：[（map_server功能包wiki链接）](http://wiki.ros.org/map_server)
将地图文件载入，并将地图数据发布出去。

```
Node [/move_base]
Publications: 
 * /move_base/global_costmap/costmap_updates [map_msgs/OccupancyGridUpdate]
 * /move_base/current_goal [geometry_msgs/PoseStamped]
 * /move_base/parameter_descriptions [dynamic_reconfigure/ConfigDescription]
 * /move_base/local_costmap/inflation_layer/parameter_descriptions [dynamic_reconfigure/ConfigDescription]
 * /move_base/feedback [move_base_msgs/MoveBaseActionFeedback]
 * /move_base/global_costmap/parameter_updates [dynamic_reconfigure/Config]
 * /move_base/local_costmap/costmap [nav_msgs/OccupancyGrid]
 * /move_base/global_costmap/static_layer/parameter_updates [dynamic_reconfigure/Config]
 * /move_base/WpbhLocalPlanner/local_planner_target [geometry_msgs/PoseStamped]
 * /move_base/status [actionlib_msgs/GoalStatusArray]
 * /move_base/local_costmap/inflation_layer/parameter_updates [dynamic_reconfigure/Config]
 * /move_base/global_costmap/static_layer/parameter_descriptions [dynamic_reconfigure/ConfigDescription]
 * /move_base/local_costmap/costmap_updates [map_msgs/OccupancyGridUpdate]
 * /move_base/local_costmap/parameter_descriptions [dynamic_reconfigure/ConfigDescription]
 * /move_base/parameter_updates [dynamic_reconfigure/Config]
 * /move_base/global_costmap/inflation_layer/parameter_updates [dynamic_reconfigure/Config]
 * /move_base/global_costmap/costmap [nav_msgs/OccupancyGrid]
 * /cmd_vel [geometry_msgs/Twist]
 * /move_base/local_costmap/parameter_updates [dynamic_reconfigure/Config]
 * /move_base/local_costmap/obstacle_layer/parameter_updates [dynamic_reconfigure/Config]
 * /move_base/global_costmap/footprint [geometry_msgs/PolygonStamped]
 * /rosout [rosgraph_msgs/Log]
 * /move_base/GlobalPlanner/parameter_descriptions [dynamic_reconfigure/ConfigDescription]
 * /move_base/local_costmap/obstacle_layer/parameter_descriptions [dynamic_reconfigure/ConfigDescription]
 * /move_base/goal [move_base_msgs/MoveBaseActionGoal]
 * /move_base/local_costmap/footprint [geometry_msgs/PolygonStamped]
 * /move_base/GlobalPlanner/plan [nav_msgs/Path]
 * /move_base/global_costmap/inflation_layer/parameter_descriptions [dynamic_reconfigure/ConfigDescription]
 * /move_base/global_costmap/obstacle_layer/parameter_descriptions [dynamic_reconfigure/ConfigDescription]
 * /move_base/GlobalPlanner/potential [nav_msgs/OccupancyGrid]
 * /move_base/GlobalPlanner/parameter_updates [dynamic_reconfigure/Config]
 * /move_base/result [move_base_msgs/MoveBaseActionResult]
 * /move_base/global_costmap/obstacle_layer/parameter_updates [dynamic_reconfigure/Config]
 * /move_base/global_costmap/parameter_descriptions [dynamic_reconfigure/ConfigDescription]

Subscriptions: 
 * /move_base/local_costmap/footprint [geometry_msgs/PolygonStamped]
 * /tf [tf2_msgs/TFMessage]
 * /scan [sensor_msgs/LaserScan]
 * /move_base_simple/goal [geometry_msgs/PoseStamped]
 * /tf_static [tf2_msgs/TFMessage]
 * /map [nav_msgs/OccupancyGrid]
 * /move_base/global_costmap/footprint [geometry_msgs/PolygonStamped]
 * /move_base/goal [move_base_msgs/MoveBaseActionGoal]
 * /scan_filtered [unknown type]
 * /move_base/cancel [unknown type]

Services: 
 * /move_base/global_costmap/static_layer/set_parameters
 * /move_base/get_loggers
 * /move_base/local_costmap/obstacle_layer/set_parameters
 * /move_base/local_costmap/set_parameters
 * /move_base/set_logger_level
 * /move_base/global_costmap/obstacle_layer/set_parameters
 * /move_base/GlobalPlanner/make_plan
 * /move_base/GlobalPlanner/set_parameters
 * /move_base/global_costmap/inflation_layer/set_parameters
 * /move_base/global_costmap/set_parameters
 * /move_base/set_parameters
 * /move_base/make_plan
 * /move_base/local_costmap/inflation_layer/set_parameters
 * /move_base/clear_costmaps
```
该节点说明[（move_base功能包wiki链接）](http://wiki.ros.org/move_base)
 订阅导航目标点，激光雷达数据，坐标转换数据等，发布机器人运动数据，最终让机器人到达目标点。



#### **人脸检测&&桌面物体检测**
    人脸检测：运行已提供的程序命令，当人脸出现在Kinect2摄像头图像中时，会输出人脸的空间位置。
    
1.运行命令
```
$ roslaunch wpb_home_tutorials face_detect.launch
```
launch文件内容
```
<launch>
  <arg name="model" default="$(find wpb_home_bringup)/urdf/wpb_home.urdf"/>
  <arg name="gui" default="true" />
  <arg name="rvizconfig" default="$(find wpb_home_tutorials)/rviz/face_detect.rviz" />

  <param name="robot_description" command="$(find xacro)/xacro.py $(arg model)" />
  <param name="use_gui" value="$(arg gui)"/>
  <param name="/use_sim_time" value="false"/>

  <node name="joint_state_publisher" pkg="joint_state_publisher" type="joint_state_publisher">
  <rosparam command="load" file="$(find wpb_home_bringup)/config/wpb_home.yaml" />
  </node>
  <node name="robot_state_publisher" pkg="robot_state_publisher" type="state_publisher" />
  <node name="rviz" pkg="rviz" type="rviz" args="-d $(arg rvizconfig)" required="true" />

<!--************************ kinect ************************-->
  <arg name="base_name"         default="kinect2"/>
  <arg name="sensor"            default=""/>
  <arg name="publish_tf"        default="false"/>
  <arg name="base_name_tf"      default="$(arg base_name)"/>
  <arg name="fps_limit"         default="-1.0"/>
  <arg name="calib_path"        default="$(find kinect2_bridge)/data/"/>
  <arg name="use_png"           default="false"/>
  <arg name="jpeg_quality"      default="90"/>
  <arg name="png_level"         default="1"/>
  <arg name="depth_method"      default="default"/>
  <arg name="depth_device"      default="-1"/>
  <arg name="reg_method"        default="default"/>
  <arg name="reg_device"        default="-1"/>
  <arg name="max_depth"         default="12.0"/>
  <arg name="min_depth"         default="0.1"/>
  <arg name="queue_size"        default="5"/>
  <arg name="bilateral_filter"  default="true"/>
  <arg name="edge_aware_filter" default="true"/>
  <arg name="worker_threads"    default="4"/>
  <arg name="machine"           default="localhost"/>
  <arg name="nodelet_manager"   default="$(arg base_name)"/>
  <arg name="start_manager"     default="true"/>
  <arg name="use_machine"       default="true"/>
  <arg name="respawn"           default="true"/>
  <arg name="use_nodelet"       default="true"/>
  <arg name="output"            default="screen"/>

  <machine name="localhost" address="localhost" if="$(arg use_machine)"/>

  <node pkg="nodelet" type="nodelet" name="$(arg nodelet_manager)" args="manager"
        if="$(arg start_manager)" machine="$(arg machine)" />

  <!-- Nodelet version of kinect2_bridge -->
  <node pkg="nodelet" type="nodelet" name="$(arg base_name)_bridge" machine="$(arg machine)"
        args="load kinect2_bridge/kinect2_bridge_nodelet $(arg nodelet_manager)"
        respawn="$(arg respawn)" output="$(arg output)" if="$(arg use_nodelet)">
    <param name="base_name"         type="str"    value="$(arg base_name)"/>
    <param name="sensor"            type="str"    value="$(arg sensor)"/>
    <param name="publish_tf"        type="bool"   value="$(arg publish_tf)"/>
    <param name="base_name_tf"      type="str"    value="$(arg base_name_tf)"/>
    <param name="fps_limit"         type="double" value="$(arg fps_limit)"/>
    <param name="calib_path"        type="str"    value="$(arg calib_path)"/>
    <param name="use_png"           type="bool"   value="$(arg use_png)"/>
    <param name="jpeg_quality"      type="int"    value="$(arg jpeg_quality)"/>
    <param name="png_level"         type="int"    value="$(arg png_level)"/>
    <param name="depth_method"      type="str"    value="$(arg depth_method)"/>
    <param name="depth_device"      type="int"    value="$(arg depth_device)"/>
    <param name="reg_method"        type="str"    value="$(arg reg_method)"/>
    <param name="reg_device"        type="int"    value="$(arg reg_device)"/>
    <param name="max_depth"         type="double" value="$(arg max_depth)"/>
    <param name="min_depth"         type="double" value="$(arg min_depth)"/>
    <param name="queue_size"        type="int"    value="$(arg queue_size)"/>
    <param name="bilateral_filter"  type="bool"   value="$(arg bilateral_filter)"/>
    <param name="edge_aware_filter" type="bool"   value="$(arg edge_aware_filter)"/>
    <param name="worker_threads"    type="int"    value="$(arg worker_threads)"/>
  </node>

  <!-- Node version of kinect2_bridge -->
  <node pkg="kinect2_bridge" type="kinect2_bridge" name="$(arg base_name)_bridge" machine="$(arg machine)"
        respawn="$(arg respawn)" output="$(arg output)" unless="$(arg use_nodelet)">
    <param name="base_name"         type="str"    value="$(arg base_name)"/>
    <param name="sensor"            type="str"    value="$(arg sensor)"/>
    <param name="publish_tf"        type="bool"   value="$(arg publish_tf)"/>
    <param name="base_name_tf"      type="str"    value="$(arg base_name_tf)"/>
    <param name="fps_limit"         type="double" value="$(arg fps_limit)"/>
    <param name="calib_path"        type="str"    value="$(arg calib_path)"/>
    <param name="use_png"           type="bool"   value="$(arg use_png)"/>
    <param name="jpeg_quality"      type="int"    value="$(arg jpeg_quality)"/>
    <param name="png_level"         type="int"    value="$(arg png_level)"/>
    <param name="depth_method"      type="str"    value="$(arg depth_method)"/>
    <param name="depth_device"      type="int"    value="$(arg depth_device)"/>
    <param name="reg_method"        type="str"    value="$(arg reg_method)"/>
    <param name="reg_device"        type="int"    value="$(arg reg_device)"/>
    <param name="max_depth"         type="double" value="$(arg max_depth)"/>
    <param name="min_depth"         type="double" value="$(arg min_depth)"/>
    <param name="queue_size"        type="int"    value="$(arg queue_size)"/>
    <param name="bilateral_filter"  type="bool"   value="$(arg bilateral_filter)"/>
    <param name="edge_aware_filter" type="bool"   value="$(arg edge_aware_filter)"/>
    <param name="worker_threads"    type="int"    value="$(arg worker_threads)"/>
  </node>

   <!-- hd point cloud (1920 x 1080) -->
  <node pkg="nodelet" type="nodelet" name="$(arg base_name)_points_xyzrgb_hd" machine="$(arg machine)"
        args="load depth_image_proc/point_cloud_xyzrgb $(arg nodelet_manager)" respawn="$(arg respawn)">
    <remap from="rgb/camera_info"             to="$(arg base_name)/hd/camera_info"/>
    <remap from="rgb/image_rect_color"        to="$(arg base_name)/hd/image_color_rect"/>
    <remap from="depth_registered/image_rect" to="$(arg base_name)/hd/image_depth_rect"/>
    <remap from="depth_registered/points"     to="$(arg base_name)/hd/points"/>
    <param name="queue_size" type="int" value="$(arg queue_size)"/>
  </node>
  <!--************************ end ************************-->

  <arg name="face_cascade_file" default="$(find wpb_home_tutorials)/config/haarcascade_frontalface_alt.xml" />

  <node name="face" pkg="wpb_home_tutorials" type="wpb_home_face_detect" output="screen">
    <param name="face_cascade_name" value="$(arg face_cascade_file)"/>
  </node>

</launch>
```
2.数据关系图
![rosgraph.png-223.6kB][5]
3.各节点信息
```
Node [/face]
Publications: 
 * /face_pointcloud [sensor_msgs/PointCloud2]
 * /face/image [sensor_msgs/Image]
 * /face_marker [visualization_msgs/Marker]
 * /rosout [rosgraph_msgs/Log]

Subscriptions: 
 * /kinect2/hd/image_color [sensor_msgs/Image]
 * /tf [tf2_msgs/TFMessage]
 * /tf_static [tf2_msgs/TFMessage]
 * /kinect2/hd/points [sensor_msgs/PointCloud2]

Services: 
 * /face/get_loggers
 * /face/set_logger_level

```

```
Node [/kinect2]
Publications: 
 * /kinect2/qhd/image_depth_rect/compressed [sensor_msgs/CompressedImage]
 * /kinect2/hd/image_color/compressed [sensor_msgs/CompressedImage]
 * /kinect2/hd/image_color_rect/compressed [sensor_msgs/CompressedImage]
 * /kinect2/hd/image_mono/compressed [sensor_msgs/CompressedImage]
 * /kinect2/qhd/image_depth_rect [sensor_msgs/Image]
 * /kinect2/hd/points [sensor_msgs/PointCloud2]
 * /kinect2/hd/image_depth_rect [sensor_msgs/Image]
 * /kinect2/sd/image_depth_rect [sensor_msgs/Image]
 * /kinect2/hd/image_mono [sensor_msgs/Image]
 * /kinect2/sd/image_color_rect [sensor_msgs/Image]
 * /kinect2/hd/image_color_rect [sensor_msgs/Image]
 * /kinect2/sd/image_ir/compressed [sensor_msgs/CompressedImage]
 * /kinect2/qhd/image_mono [sensor_msgs/Image]
 * /kinect2/bond [bond/Status]
 * /kinect2/sd/image_depth [sensor_msgs/Image]
 * /kinect2/qhd/image_color [sensor_msgs/Image]
 * /kinect2/qhd/image_color_rect [sensor_msgs/Image]
 * /kinect2/sd/image_ir [sensor_msgs/Image]
 * /kinect2/hd/image_color [sensor_msgs/Image]
 * /kinect2/sd/image_depth_rect/compressed [sensor_msgs/CompressedImage]
 * /rosout [rosgraph_msgs/Log]
 * /kinect2/hd/image_depth_rect/compressed [sensor_msgs/CompressedImage]
 * /kinect2/sd/camera_info [sensor_msgs/CameraInfo]
 * /kinect2/qhd/image_mono_rect [sensor_msgs/Image]
 * /kinect2/hd/image_mono_rect [sensor_msgs/Image]
 * /kinect2/sd/image_ir_rect/compressed [sensor_msgs/CompressedImage]
 * /kinect2/qhd/camera_info [sensor_msgs/CameraInfo]
 * /kinect2/hd/image_mono_rect/compressed [sensor_msgs/CompressedImage]
 * /kinect2/qhd/image_mono_rect/compressed [sensor_msgs/CompressedImage]
 * /kinect2/sd/image_color_rect/compressed [sensor_msgs/CompressedImage]
 * /kinect2/qhd/image_color_rect/compressed [sensor_msgs/CompressedImage]
 * /kinect2/sd/image_ir_rect [sensor_msgs/Image]
 * /kinect2/qhd/image_mono/compressed [sensor_msgs/CompressedImage]
 * /kinect2/hd/camera_info [sensor_msgs/CameraInfo]
 * /kinect2/qhd/image_color/compressed [sensor_msgs/CompressedImage]
 * /kinect2/sd/image_depth/compressed [sensor_msgs/CompressedImage]

Subscriptions: 
 * /kinect2/bond [bond/Status]
 * /kinect2/hd/camera_info [sensor_msgs/CameraInfo]
 * /kinect2/hd/image_color_rect [sensor_msgs/Image]
 * /kinect2/hd/image_depth_rect [sensor_msgs/Image]

Services: 
 * /kinect2/unload_nodelet
 * /kinect2/load_nodelet
 * /kinect2/get_loggers
 * /kinect2/set_logger_level
 * /kinect2/list

```


```
Node [/kinect2_points_xyzrgb_hd]
Publications: 
 * /kinect2/bond [bond/Status]
 * /rosout [rosgraph_msgs/Log]

Subscriptions: 
 * /kinect2/bond [bond/Status]

Services: 
 * /kinect2_points_xyzrgb_hd/set_logger_level
 * /kinect2_points_xyzrgb_hd/get_loggers

```

```
Node [/kinect2_bridge]
Publications: 
 * /kinect2/bond [bond/Status]
 * /rosout [rosgraph_msgs/Log]

Subscriptions: 
 * /kinect2/bond [bond/Status]

Services: 
 * /kinect2_bridge/set_logger_level
 * /kinect2_bridge/get_loggers

```



    桌面物体检测：运行已提供的程序命令，当物品当人脸出现在Kinect2摄像头图像中时，会输出物品的空间位置。
    
代码模块实现分析：

1.运行命令
```
$ roslaunch wpb_home_tutorials obj_detect.launch
```
launch文件
```
<launch>
  <arg name="model" default="$(find wpb_home_bringup)/urdf/wpb_home.urdf"/>
  <arg name="gui" default="true" />
  <arg name="rvizconfig" default="$(find wpb_home_tutorials)/rviz/face_detect.rviz" />

  <param name="robot_description" command="$(find xacro)/xacro.py $(arg model)" />
  <param name="use_gui" value="$(arg gui)"/>
  <param name="/use_sim_time" value="false"/>

  <node name="joint_state_publisher" pkg="joint_state_publisher" type="joint_state_publisher">
  <rosparam command="load" file="$(find wpb_home_bringup)/config/wpb_home.yaml" />
  </node>
  <node name="robot_state_publisher" pkg="robot_state_publisher" type="state_publisher" />
  <node name="rviz" pkg="rviz" type="rviz" args="-d $(arg rvizconfig)" required="true" />

<!--************************ kinect ************************-->
  <arg name="base_name"         default="kinect2"/>
  <arg name="sensor"            default=""/>
  <arg name="publish_tf"        default="false"/>
  <arg name="base_name_tf"      default="$(arg base_name)"/>
  <arg name="fps_limit"         default="-1.0"/>
  <arg name="calib_path"        default="$(find kinect2_bridge)/data/"/>
  <arg name="use_png"           default="false"/>
  <arg name="jpeg_quality"      default="90"/>
  <arg name="png_level"         default="1"/>
  <arg name="depth_method"      default="default"/>
  <arg name="depth_device"      default="-1"/>
  <arg name="reg_method"        default="default"/>
  <arg name="reg_device"        default="-1"/>
  <arg name="max_depth"         default="12.0"/>
  <arg name="min_depth"         default="0.1"/>
  <arg name="queue_size"        default="5"/>
  <arg name="bilateral_filter"  default="true"/>
  <arg name="edge_aware_filter" default="true"/>
  <arg name="worker_threads"    default="4"/>
  <arg name="machine"           default="localhost"/>
  <arg name="nodelet_manager"   default="$(arg base_name)"/>
  <arg name="start_manager"     default="true"/>
  <arg name="use_machine"       default="true"/>
  <arg name="respawn"           default="true"/>
  <arg name="use_nodelet"       default="true"/>
  <arg name="output"            default="screen"/>

  <machine name="localhost" address="localhost" if="$(arg use_machine)"/>

  <node pkg="nodelet" type="nodelet" name="$(arg nodelet_manager)" args="manager"
        if="$(arg start_manager)" machine="$(arg machine)" />

  <!-- Nodelet version of kinect2_bridge -->
  <node pkg="nodelet" type="nodelet" name="$(arg base_name)_bridge" machine="$(arg machine)"
        args="load kinect2_bridge/kinect2_bridge_nodelet $(arg nodelet_manager)"
        respawn="$(arg respawn)" output="$(arg output)" if="$(arg use_nodelet)">
    <param name="base_name"         type="str"    value="$(arg base_name)"/>
    <param name="sensor"            type="str"    value="$(arg sensor)"/>
    <param name="publish_tf"        type="bool"   value="$(arg publish_tf)"/>
    <param name="base_name_tf"      type="str"    value="$(arg base_name_tf)"/>
    <param name="fps_limit"         type="double" value="$(arg fps_limit)"/>
    <param name="calib_path"        type="str"    value="$(arg calib_path)"/>
    <param name="use_png"           type="bool"   value="$(arg use_png)"/>
    <param name="jpeg_quality"      type="int"    value="$(arg jpeg_quality)"/>
    <param name="png_level"         type="int"    value="$(arg png_level)"/>
    <param name="depth_method"      type="str"    value="$(arg depth_method)"/>
    <param name="depth_device"      type="int"    value="$(arg depth_device)"/>
    <param name="reg_method"        type="str"    value="$(arg reg_method)"/>
    <param name="reg_device"        type="int"    value="$(arg reg_device)"/>
    <param name="max_depth"         type="double" value="$(arg max_depth)"/>
    <param name="min_depth"         type="double" value="$(arg min_depth)"/>
    <param name="queue_size"        type="int"    value="$(arg queue_size)"/>
    <param name="bilateral_filter"  type="bool"   value="$(arg bilateral_filter)"/>
    <param name="edge_aware_filter" type="bool"   value="$(arg edge_aware_filter)"/>
    <param name="worker_threads"    type="int"    value="$(arg worker_threads)"/>
  </node>

  <!-- Node version of kinect2_bridge -->
  <node pkg="kinect2_bridge" type="kinect2_bridge" name="$(arg base_name)_bridge" machine="$(arg machine)"
        respawn="$(arg respawn)" output="$(arg output)" unless="$(arg use_nodelet)">
    <param name="base_name"         type="str"    value="$(arg base_name)"/>
    <param name="sensor"            type="str"    value="$(arg sensor)"/>
    <param name="publish_tf"        type="bool"   value="$(arg publish_tf)"/>
    <param name="base_name_tf"      type="str"    value="$(arg base_name_tf)"/>
    <param name="fps_limit"         type="double" value="$(arg fps_limit)"/>
    <param name="calib_path"        type="str"    value="$(arg calib_path)"/>
    <param name="use_png"           type="bool"   value="$(arg use_png)"/>
    <param name="jpeg_quality"      type="int"    value="$(arg jpeg_quality)"/>
    <param name="png_level"         type="int"    value="$(arg png_level)"/>
    <param name="depth_method"      type="str"    value="$(arg depth_method)"/>
    <param name="depth_device"      type="int"    value="$(arg depth_device)"/>
    <param name="reg_method"        type="str"    value="$(arg reg_method)"/>
    <param name="reg_device"        type="int"    value="$(arg reg_device)"/>
    <param name="max_depth"         type="double" value="$(arg max_depth)"/>
    <param name="min_depth"         type="double" value="$(arg min_depth)"/>
    <param name="queue_size"        type="int"    value="$(arg queue_size)"/>
    <param name="bilateral_filter"  type="bool"   value="$(arg bilateral_filter)"/>
    <param name="edge_aware_filter" type="bool"   value="$(arg edge_aware_filter)"/>
    <param name="worker_threads"    type="int"    value="$(arg worker_threads)"/>
  </node>

   <!-- hd point cloud (1920 x 1080) -->
  <node pkg="nodelet" type="nodelet" name="$(arg base_name)_points_xyzrgb_hd" machine="$(arg machine)"
        args="load depth_image_proc/point_cloud_xyzrgb $(arg nodelet_manager)" respawn="$(arg respawn)">
    <remap from="rgb/camera_info"             to="$(arg base_name)/hd/camera_info"/>
    <remap from="rgb/image_rect_color"        to="$(arg base_name)/hd/image_color_rect"/>
    <remap from="depth_registered/image_rect" to="$(arg base_name)/hd/image_depth_rect"/>
    <remap from="depth_registered/points"     to="$(arg base_name)/hd/points"/>
    <param name="queue_size" type="int" value="$(arg queue_size)"/>
  </node>
  <!--************************ end ************************-->

  <arg name="face_cascade_file" default="$(find wpb_home_tutorials)/config/haarcascade_frontalface_alt.xml" />

  <node name="face" pkg="wpb_home_tutorials" type="wpb_home_face_detect" output="screen">
    <param name="face_cascade_name" value="$(arg face_cascade_file)"/>
  </node>

</launch>
```
2.数据关系图
![rosgraph.png-188.1kB][6]
3.各节点信息
```
Node [/wpb_home_obj_detect]
Publications: 
 * /segmented_plane [sensor_msgs/PointCloud2]
 * /obj_pointcloud [sensor_msgs/PointCloud2]
 * /rosout [rosgraph_msgs/Log]
 * /segmented_objects [sensor_msgs/PointCloud2]
 * /obj_marker [visualization_msgs/Marker]

Subscriptions: 
 * /kinect2/qhd/points [sensor_msgs/PointCloud2]
 * /tf_static [tf2_msgs/TFMessage]
 * /tf [tf2_msgs/TFMessage]

Services: 
 * /wpb_home_obj_detect/set_logger_level
 * /wpb_home_obj_detect/get_loggers

```

问题：

 - *使用了OpenCV的一些开源的算法，没有进行修改优化，识别的准确率低，占用的运行资源过大。*
 
#### **语音指令**
    运行已提供的程序命令，使用开源的英文语音引擎，当人说出特定的关键词，机器人可识别出，进而执行相应的命令（forward-前进，backward-后退等等）。
问题：

 - *仅识别英文。*

 代码模块实现分析：
 
 1.运行命令
```
$ roslaunch wpb_home_tutorials voice_cmd.launch 
```
launch文件
```
<launch>

  <node name="recognizer" pkg="pocketsphinx" type="recognizer.py">
    <param name="lm" value="$(find wpb_home_tutorials)/sr/voice_cmd.lm"/>
    <param name="dict" value="$(find wpb_home_tutorials)/sr/voice_cmd.dic"/>
  </node>

  <!-- Run wpb_home_voice_cmd -->
  <node pkg="wpb_home_tutorials" type="wpb_home_voice_cmd" name="wpb_home_voice_cmd" output="screen"/>
  
  <!-- Run wpb_home core -->
  <node pkg="wpb_home_bringup" type="wpb_home_core" name="wpb_home_core" output="screen">
    <param name="serial_port" type="string" value="/dev/ftdi"/> 
  </node>

</launch>
```
2.数据关系图
![rosgraph.png-47.5kB][7]

3.各节点信息
```
Node [/recognizer]
Publications: 
 * /rosout [rosgraph_msgs/Log]
 * /recognizer/output [std_msgs/String]

Subscriptions: None

Services: 
 * /recognizer/set_logger_level
 * /recognizer/stop
 * /recognizer/get_loggers
 * /recognizer/start

```


```
Node [/wpb_home_voice_cmd]
Publications: 
 * /rosout [rosgraph_msgs/Log]
 * /cmd_vel [geometry_msgs/Twist]

Subscriptions: 
 * /recognizer/output [std_msgs/String]

Services: 
 * /wpb_home_voice_cmd/get_loggers
 * /wpb_home_voice_cmd/set_logger_level

```

```
Node [/wpb_home_core]
Publications: 
 * /rosout [rosgraph_msgs/Log]
 * /tf [tf2_msgs/TFMessage]
 * /imu/data_raw [sensor_msgs/Imu]
 * /odom [nav_msgs/Odometry]

Subscriptions: 
 * /cmd_vel [geometry_msgs/Twist]

Services: 
 * /wpb_home_core/get_loggers
 * /wpb_home_core/set_logger_level

```

 #### **语音播报**
    运行已提供的程序命令，可对英文文本进行合成并播放。
1.运行命令
```
$ roslaunch wpb_home_tutorials speak.launch
```
launch文件
```
<launch>
  
  <!-- Run sound_play -->
  <node name="soundplay_node" pkg="sound_play" type="soundplay_node.py"/>

  <!-- Run wpb_home_speak -->
  <node pkg="wpb_home_tutorials" type="wpb_home_speak" name="wpb_home_speak" output="screen"/>

</launch>
```
2.数据关系图
![rosgraph.png-31.9kB][8]
3.各节点信息
```
Node [/soundplay_node]
Publications: 
 * /sound_play/result [sound_play/SoundRequestActionResult]
 * /rosout [rosgraph_msgs/Log]
 * /sound_play/status [actionlib_msgs/GoalStatusArray]
 * /sound_play/feedback [sound_play/SoundRequestActionFeedback]
 * /diagnostics [diagnostic_msgs/DiagnosticArray]

Subscriptions: 
 * /sound_play/cancel [unknown type]
 * /robotsound [sound_play/SoundRequest]
 * /sound_play/goal [unknown type]

Services: 
 * /soundplay_node/set_logger_level
 * /soundplay_node/get_loggers

```

```
Node [/wpb_home_speak]
Publications: 
 * /rosout [rosgraph_msgs/Log]
 * /robotsound [sound_play/SoundRequest]

Subscriptions: None

Services: 
 * /wpb_home_speak/get_loggers
 * /wpb_home_speak/set_logger_level
```

问题：

 - *仅合成英文。*


  [1]: http://static.zybuluo.com/zhanghuanliang/80ox087ez63ezlx8jtiuabw0/rosgraph.png
  [2]: http://static.zybuluo.com/zhanghuanliang/fuwn8uxxe26nokq8f9fn27ot/rosgraph.png
  [3]: http://static.zybuluo.com/zhanghuanliang/zhlzai2cwnel3i1xdzei3q17/rosgraph.png
  [4]: http://static.zybuluo.com/zhanghuanliang/76kla9gjtkb2c7iv6k9h89dc/rosgraph.png
  [5]: http://static.zybuluo.com/zhanghuanliang/rtreeh7ocbyxn9agdvdxf74k/rosgraph.png
  [6]: http://static.zybuluo.com/zhanghuanliang/qc1jo1tub7wd5u1inpbsvocj/rosgraph.png
  [7]: http://static.zybuluo.com/zhanghuanliang/yh2r5bzbq3s1p2op5lxl61qd/rosgraph.png
  [8]: http://static.zybuluo.com/zhanghuanliang/cp7z1hbf8om1xld5h5n3z2nz/rosgraph.png