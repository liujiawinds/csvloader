# 使用说明


1. 程序入口在com.betalpha.csvloader.App

2. 各个模块可以根据主机情况在application.conf调整线程数目

3. 日志文件位置需要自定义

4. 为了避免代码压缩包过大，把测试数据删除了，如果需要的话，可以在test里面使用CSVGenerator生成



## 测试情况

我是在本机部署了一个mysql，因为磁盘不怎么行，测试结果是120tps（进程启动后，可以通过jmx查看）
