

参考： https://developer.emqx.io/docs/emq/v3/cn/getstarted.html

## 下载地址

下载地址: http://emqtt.com/downloads

```bash
$ curl  -L http://www.emqtt.com/downloads/3104/centos7 -o emqx-centos7-v3.1-rc.1.zip
$ unzip emqx-centos7-v3.1-rc.1.zip
$ mv emqx /works/apps/
$ cd /works/apps/emqx
$ bin//emqx start
emqx 3.1 is started successfully!
$ bin/emqx_ctl status
Node 'emqx@127.0.0.1' is started
emqx v3.1-rc.1 is running
$ bin/emqx stop
ok
```

## Web 管理控制台(Dashboard)

EMQ X 消息服务器启动后，会默认加载 Dashboard 插件，启动 Web 管理控制台。用户可通过 Web 控制台，查看服务器运行状态、统计数据、客户端(Client)、会话(Session)、主题(Topic)、订阅(Subscription)、插件(Plugin)。

控制台地址: http://127.0.0.1:18083，默认用户: admin，密码：public

