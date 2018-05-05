## MyBlog

> 抛弃了实验楼源码的那版，想看那版的小伙伴切换到实验楼分支

> 1. 前端用的[别人的工程](https://github.com/jameszbl/fs-blogPo)修改(自己前端太渣了)
> 2. 别人的前端基础上，做成前后端分离，nginx动静分离，全站https
> 3. 后端maven构建，依赖lombok插件，ssm的基本框架，没用boot+cloud+gradle这套新玩意
> 4. 服务引入activemq，dubbo，redis
> 5. 权限框架使用shiro，重写使用到的filter，避免返回跳转页面，实现rest服务。使用redis存储session，避免单点
> 6. 整体结构是伪高可用，全部都是单机多实例，1nginx+2tomcat+3activemq+6redis+3zookeeper+2mysql(未实现)
> 7. 准备使用mycat，完成mysql的热备，不准备学习使用keepalived(lvs)，太偏运维了
> 8. 引入持续集成，github+sonar+jenkins+nexus，pom中有关于nexus的配置
> 9. 最终效果[https://www.laifuzhi.cn](https://www.laifuzhi.cn)

#### 欢迎关注我的[简书](http://www.jianshu.com/u/4c0c1fda9313)，[chinaunix博客](http://blogPo.chinaunix.net/uid/30592332.html)(2015-2016)


![肥肥小浣熊](http://upload-images.jianshu.io/upload_images/7504966-312110be9245b60c.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
