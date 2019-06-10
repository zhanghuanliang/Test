# NB-IoT指标评估体系报表

标签（空格分隔）： 设计文档

---

### 汇总表

#### NB-IoT指标评估体系汇总表（tap_nbiot_IndexAssess_sum）

| 字段名        | 类型   |  说明  |
| --------   | -----  | ----  |
| int_id        |   INTEGER   |   唯一ID(小区ID/地市ID)   |
| zh_name        |   VARCHAR2(128)   |   nb小区中文名   |
| cgi        |   VARCHAR2(128)   |   nb小区cgi   |
| region_id        |   INTEGER   |   地市编号  |
| region_name        |   VARCHAR2(64)   |   地市名称   |
| scan_start_time        |   Date   |   汇总时间   |
| ne_type        |   INTEGER   |   网元类型  |
| sum_level        |   INTEGER  |   汇总粒度（1天，2周，3月）   |
| insert_time        |   Date   |   插入时间   |
| Score        |   float   |   得分   |
| up_low_repeat_rate        |   float   |   上行低重复次数占比   |
| down_low_repeat_rate       |   float   |   下行低重复次数占比   |
| cover_lev2_user_rate        |   float   |   NB-IoT覆盖等级2的用户数占比   |
| cover_lev2_rrc_rate        |   float   |   NB-IoT覆盖等级2的RRC连接建立次数占比   |
| up_avg_noise        |   float   |   每子载波接收的上行平均干扰噪声   |
| max_rrc_user_num        |   integer   |   最大RRC连接用户数   |
|avg _rrc_user_num         |   integer   |   平均RRC连接用户数   |
| rrc_refuse_rate        |   float   |   rrc连接建立拒绝占比   |
| up_carrier_occupy_rate        |   float   |   NB-IoT上行子载波占用率   |
| down_carrier_occupy_rate        |   float   |   NB-IoT下行子载波占用率   |
| Oct_Ul        |   float   |   SRB1bis接收上行总吞吐量   |
| Oct_Dl        |   float   |   SRB1bis发送下行总吞吐量   |
| Pag_Received        |   float   |   寻呼负荷   |
| up_mcs_sum        |   float   |   上行MCS统计*   |
| down_mcs_sum        |   float   |   下行MCS统计*   |
| NBMAC_ul_bler_rate        |   float   |   MAC层上行平均BLER   |
| NBMAC_dl_bler_rate        |   float   |   MAC层下行平均BLER   |
| 0rrc_Succ_Conn_rate        |   float   |   NB-IoT覆盖等级0下RRC连接建立成功率   |
| 1rrc_Succ_Conn_rate        |   float   |   NB-IoT覆盖等级1下RRC连接建立成功率   |
| 2rrc_Succ_Conn_rate        |   float   |   NB-IoT覆盖等级2下RRC连接建立成功率   |
| Setup_Time_Mean        |   float   |   RRC连接建立平均时延   |
| NBS1SIG_ConnEstabSucc_rate        |   float   |   S1建立成功率   |
| NBPAG_succ_rate        |   float   |   寻呼成功率   |
| NBPAG_plugging_rate        |   float   |   寻呼丢弃率   |
| NB_wireless_dropcall_rate        |   float   |   无线掉线率   |



 


 











 










