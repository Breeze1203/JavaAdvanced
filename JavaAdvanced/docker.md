docker使用mysql和redis

拉取镜像

docker pull mysql 

 docker pull redis

因为我之前的数据库是放在linux虚拟机上，先导出back.sql，挂载到容器中

docker run --name my-mysql -e MYSQL_ROOT_PASSWORD=3548297839e -p 3306:3306 -v /Users/pt/Downloads/back.sql:/mysql -d mysql

进入容器交互  docker exec -it my-mysql bash

mysql -uroot -p 输入密码

创建数据库adminflow ;

create database adminflow;

use  adminflow;

编译sql文件

Source mysql



