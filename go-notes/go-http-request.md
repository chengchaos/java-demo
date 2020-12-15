# Go HTTP Request 结构

Request 结构主要由以下部分组成：

- URL 字段
- Header 字段
- Body 字段
- Form/PostForm/MultipartForm 字段

## 请求 URL

```go
type URL struct {
    Scheme string
    Opaque string
    User *Userinfo
    Host string
    Path string
    RawQuery string
    Fragment string
}
```

## Header

一个 Header 类型的实例就是一个映射。key 为字符串， value 为任意多个字符串切片。
