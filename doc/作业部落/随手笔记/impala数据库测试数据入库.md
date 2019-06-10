# impala数据库测试数据入库

标签（空格分隔）： 随手笔记

---

登录120.131.10.162 远程机器；zhaodefa/Boco123，
root 账号密码Boco123，
boco 账号密码Boco123，

1、将要导入数据传到服务器上 （bd_h_g20_e_d.csv） 

2、将数据传到集群中用以下命令 （用boco 账号） 
 hdfs dfs -put  /home1/boco/test/bd_h_g20_e_d.csv  /dinglicom/ltemr/ltemr/test.db/

3、登录集群输入hive 进入hive中创建表；
use test1; 要把数据导入的库
drop table  bd_h_g20_e_d;
create table bd_h_g20_e_d
(
  scan_start_time string, 
  
   insert_time string, 
  
   region_id bigint
)
PARTITIONED BY ( 
  mon int, 
  day int)
 row format delimited fields terminated by '\t'; (这里指数据是用什么进行分割的，csv 一般是逗号)

4、将数据load 到新建的表中用以下命令在 hive下执行
load data inpath '/dinglicom/ltemr/ltemr/test.db/bd_h_g20_e_d.csv' into table bd_h_g20_e_d partition(day=20181024,mon=201810);


5、将数据刷新到impala 首先登录到非主节点机器，
输入impala-shell 进入impalashell执行以下命

invalidate metadata  test1.bd_h_g20_e_d;

其他：
切换用户指令：
su -boco ; 
删除集群中的文件：
 hadoop fs –rmr /home1/boco/test/bd_h_g20_e_d.csv
查看集群文件加下的内容：
 hadoop fs -ls  /dinglicom/ltemr/ltemr/test.db/





