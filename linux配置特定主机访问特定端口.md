如果您想配置特定主机访问您的 CentOS 电脑上的特定端口，您可以在防火墙的区域配置文件中添加相应的规则。以下是一个示例，演示如何在 `public.xml` 文件中添加允许特定主机访问特定端口的规则：

1. 打开 `public.xml` 文件进行编辑：
   ```shell
   sudo vi /etc/firewalld/zones/public.xml
   ```

2. 在 `<zone>` 标签内，找到 `<source>` 标签，并在其内部添加一个 `<port>` 标签，指定要允许访问的端口号。例如，如果要允许主机 `192.168.1.100` 访问端口 `8080`，可以添加以下规则：
   ```xml
   <source>
     <address>192.168.1.100</address>
   </source>
   <port protocol="tcp" port="8080"/>
   ```

   您可以根据需要添加多个 `<port>` 标签，每个标签对应一个要允许访问的端口。

3. 保存并关闭文件。

4. 重新加载防火墙规则，使其生效：
   ```shell
   sudo firewall-cmd --reload
   ```

这样，特定主机就可以访问您的 CentOS 电脑上指定的端口了。请确保将 `<address>` 标签中的 IP 地址和 `<port>` 标签中的端口号替换为您实际要允许访问的主机和端口。