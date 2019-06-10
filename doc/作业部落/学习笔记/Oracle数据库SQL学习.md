# Oracle数据库SQL学习

标签： 随手笔记 学习笔记

---
[toc]

### Oracle中查询某字段不为空或者为空
```
 is null
  is not null
```
### Oracle 中 decode 函数用法
含义解释： 
decode(条件,值1,返回值1,值2,返回值2,...值n,返回值n,缺省值)

该函数的含义如下：
IF 条件=值1 THEN
　　　　RETURN(翻译值1)
ELSIF 条件=值2 THEN
　　　　RETURN(翻译值2)
　　　　......
ELSIF 条件=值n THEN
　　　　RETURN(翻译值n)
ELSE
　　　　RETURN(缺省值)
END IF

decode(字段或字段的运算，值1，值2，值3）

       这个函数运行的结果是，当字段或字段的运算的值等于值1时，该函数返回值2，否则返回值3
 当然值1，值2，值3也可以是表达式，这个函数使得某些sql语句简单了许多

### oracle在使用sum函数计算式会遇到这样的情况。 
如果sum的值为null，则什么都不显示。想要如果为null，则显示为0，怎么办？ 
方法1： 
select when sum(t.money) is null then    0    else    sum(t.money) from Money t 
方法2： 
NVL(Expr1,Expr2)如果Expr1为NULL，返回Expr2的值，否则返回Expr1的值
例如：select NVL(SUM(MONEY) ,0) from tb全都在NVL这儿起作用
select    NVL(SUM(t.money) ,0)  from Money t 
 
###  `NVL(Expr1,Expr2)如果Expr1为NULL，返回Expr2的值，否则返回Expr1的值`

### mybatis + oracle insert clob，出现ORA-01461:仅能绑定要插入LONG列的LONG值
在网上查了很久，有可能问题是出现在当从dual中取数据时，会将clob对象的字段转为Long型

最后的解决方法用到了Begin和end语法:

1.用到begin 和end

2.用到insert into value()语法

不能用insert into select from dual (union all)语法

3.参数，指定 jdbcType=CLOB 类型

复制代码
 <insert id="batchInsert" parameterType="java.util.List">
    begin
    <foreach collection="list" item="item" index="index" separator=";">
       insert into tableName(ID, content) 
       values( #{item.id},#{item.content,jdbcType=CLOB})
    </foreach> 
    ;end;
  </insert>
  
  
  
### Oracle中Merge into用法总结

```
MERGE INTO table_name alias1 
USING (table|view|sub_query) alias2
ON (join condition) 
WHEN MATCHED THEN 
    UPDATE table_name 
    SET col1 = col_val1, 
           col2 = col_val2 
WHEN NOT MATCHED THEN 
    INSERT (column_list) VALUES (column_values); 
```
### oracle执行update时卡死问题

```
SELECT s.sid, s.serial# FROM v$locked_object lo, dba_objects ao, v$session s WHERE ao.object_id = lo.object_id AND lo.session_id = s.sid;


ALTER system KILL session 'SID,serial#';  




SELECT 'alter system kill session '''||  a.sid||','||a.serial#||''';',c.object_name, b.*
  FROM v$process p, v$session a, v$locked_object b, all_objects c
 WHERE p.addr = a.paddr
   AND a.process = b.process
   AND c.object_id = b.object_id

```
 








