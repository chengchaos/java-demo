## MQTT 发布订阅模式简述

MQTT 是基于 发布(Publish)/订阅(Subscribe) 模式来进行通信及数据交换的，与 HTTP 的 请求(Request)/应答(Response) 的模式有本质的不同。

订阅者(Subscriber) 会向 消息服务器(Broker) 订阅一个 主题(Topic) 。成功订阅后，消息服务器会将该主题下的消息转发给所有的订阅者。

主题(Topic)以 ‘/’ 为分隔符区分不同的层级。包含通配符 ‘+’ 或 ‘#’ 的主题又称为 主题过滤器(Topic Filters); 不含通配符的称为 主题名(Topic Names) 例如:


```
sensor/1/temperature

chat/room/subject

presence/user/feng

sensor/1/#

sensor/+/temperature

uber/drivers/joe/inbox

```


> 注: `+` 通配一个层级，`#` 通配多个层级(必须在末尾)。

> 注: 发布者(Publisher) 只能向 ‘主题名’ 发布消息，订阅者(Subscriber) 则可以通过订阅 ‘主题过滤器’ 来通配多个主题名称。 

## 安装 

### Windows

下载, 然后:

```
D:\mqtt\emqx-windows10-v3.1.2>bin\emqx start

D:\mqtt\emqx-windows10-v3.1.2>bin\emqx_ctl status
Node 'emqx@127.0.0.1' is started
emqx 3.0.0 is running
```

### Ubuntu

- disco 19
- Bionic 18.04 (LTS)
- Xenial 16.04 (LTS)
- Trusty 14.04 (LTS)
- Precise 12.04 (LTS)

1) 安装所需的依赖包

```bash
$ sudo apt update && sudo apt install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common

```

2) 添加 EMQ X 的官方 GPG 密钥

```bash
$ curl -fsSL https://repos.emqx.io/gpg.pub | sudo apt-key add -

```


验证秘钥

```bash
$ sudo apt-key fingerprint 3E640D53

pub   rsa2048 2019-04-10 [SC]
    FC84 1BA6 3775 5CA8 487B  1E3C C0B4 0946 3E64 0D53
uid           [ unknown] emqx team <support@emqx.io>
```

3) 使用以下命令设置 stable 存储库。 如果要添加 unstable 存储库，请在以下命令中的单词 stable 之后添加单词 unstable。

```bash
$ sudo add-apt-repository \
    "deb [arch=amd64] https://repos.emqx.io/emqx-ce/deb/ubuntu/ \
    $(lsb_release -cs) \
    stable"
$ sudo add-apt-repository \
    "deb [arch=amd64] https://repos.emqx.io/emqx-ce/deb/ubuntu/ \
    bionic \
    stable"
```


> lsb_release -cs 子命令返回 Ubuntu 发行版的名称，例如 xenial。 有时，在像 Linux Mint 这样的发行版中，您可能需要将 $(lsb_release -cs) 更改为您的父 Ubuntu 发行版。 例如，如果您使用的是 Linux Mint Tessa，则可以使用 bionic。 EMQ X 不对未经测试和不受支持的 Ubuntu 发行版提供任何保证。

后来不行,因为我的 ubuntu 19 没有对应的包, 我改成使用 deb 了:

1) 查找: 

[emqx.io](https://www.emqx.io/downloads/broker?osType=Linux)

2) 下载:

```
$ wget https://www.emqx.io/downloads/broker/v3.1.2/emqx-ubuntu18.04-v3.1.2_amd64.deb
```

3) 安装:

```
$ sudo dpkg -i emqx-ubuntu18.04-v3.1.2_amd64.deb
```

4) 启动 EMQ X

4.1) 直接启动

```
    $ emqx start
    emqx 3.1.0 is started successfully!

    $ emqx_ctl status
    Node 'emqx@127.0.0.1' is started
    emqx v3.1.0 is running
```

4.2) systemctl 启动

```
    $ sudo systemctl start emqx
```

4.3) service 启动

```
    $ sudo service emqx start
```


EMQ X 消息服务器默认占用的 TCP 端口包括:

| 端口 | 说明 |
| ---- | ---- |
| 1883  | 	MQTT 协议端口 |
| 8883  | 	MQTT/SSL 端口 |
| 8083  | 	MQTT/WebSocket 端口 |
| 8080  | 	HTTP API 端口 |
| 18083  | 	Dashboard 管理控制台端口 |

## MQTT 发布订阅

EMQ X 启动后，任何设备或终端可通过 MQTT 协议连接到服务器，通过 发布(Publish)/订阅(Subscribe) 进行交换消息。

MQTT 客户端库: https://github.com/mqtt/mqtt.github.io/wiki/libraries

例如，mosquitto_sub/pub 命令行发布订阅消息:

```
mosquitto_sub -h 127.0.0.1 -p 1883 -t topic -q 2
mosquitto_sub -h 192.168.88.156 -p 1883 -t topic -q 2
mosquitto_pub -h 127.0.0.1 -p 1883 -t topic -q 1 -m "Hello, MQTT!"
```

## 认证/访问控制

消息服务器 **连接认证** 和 **访问控制** 由一系列的认证插件(Plugins)提供，他们的命名都符合 emqx_auth_<name> 的规则。

在 EMQ X 中，这两个功能分别是指：

- 连接认证: EMQ X 校验每个连接上的客户端是否具有接入系统的权限，若没有则会断开该连接
- 访问控制: EMQ X 校验客户端每个 发布(Publish)/订阅(Subscribe) 的权限，以 允许/拒绝 相应操作


## 认证(Authentication)

EMQ X 消息服务器认证由一系列认证插件(Plugins)提供，系统支持按用户名密码、ClientID 或匿名认证。

系统默认开启匿名认证(Anonymous)，通过加载认证插件可开启的多个认证模块组成认证链:

```
           ----------------           ----------------           ------------
Client --> | Username认证 | -ignore-> | ClientID认证 | -ignore-> | 匿名认证 |
           ----------------           ----------------           ------------
                  |                         |                         |
                 \|/                       \|/                       \|/
            allow | deny              allow | deny              allow | deny
```


### 开启匿名认证

`etc/emqx.conf` 配置启用匿名认证:

```
## 允许匿名访问
## Value: true | false
allow_anonymous = true
```


### 访问控制(ACL)

EMQ X 消息服务器通过 ACL(Access Control List) 实现 MQTT 客户端访问控制。

ACL 访问控制规则定义:

```
允许(Allow)|拒绝(Deny), 谁(Who), 订阅(Subscribe)|发布(Publish), 主题列表(Topics)
```

MQTT 客户端发起订阅/发布请求时，EMQ X 消息服务器的访问控制模块会逐条匹配 ACL 规则，直到匹配成功为止:

```
          ---------              ---------              ---------
Client -> | Rule1 | --nomatch--> | Rule2 | --nomatch--> | Rule3 | --> Default
          ---------              ---------              ---------
              |                      |                      |
            match                  match                  match
             \|/                    \|/                    \|/
        allow | deny           allow | deny           allow | deny
```


### 默认访问控制设置

EMQ X 消息服务器默认访问控制，在 `etc/emqx.conf` 中设置:


```bash
## 设置所有 ACL 规则都不能匹配时是否允许访问
## Value: allow | deny
acl_nomatch = allow

## 设置存储 ACL 规则的默认文件
## Value: File Name
acl_file = etc/acl.conf
```

ACL 规则定义在 etc/acl.conf，EMQ X 启动时加载到内存:

```erlang
%% 允许 'dashboard' 用户订阅 '$SYS/#'
{allow, {user, "dashboard"}, subscribe, ["$SYS/#"]}.

%% 允许本机用户发布订阅全部主题
{allow, {ipaddr, "127.0.0.1"}, pubsub, ["$SYS/#", "#"]}.

%% 拒绝除本机用户以外的其他用户订阅 '$SYS/#' 与 '#' 主题
{deny, all, subscribe, ["$SYS/#", {eq, "#"}]}.

%% 允许上述规则以外的任何情形
{allow, all}.
```

EMQ X 提供的认证插件包括:

... 
https://developer.emqx.io/docs/broker/v3/cn/guide.html
... 


> auth 插件可以同时启动多个。每次检查的时候，按照优先级从高到低依次检查，同一优先级的，先启动的插件先检查。




