# hexagon-boot 六边形架构风格 demo 项目

```    
                    /----------\
                  /              \
                /                  \
              /                      \
            /         /-----\          \
           |         /       \          |  
    User --+-Adapter/ 应用服务 \  Adapter+-- DB
           |        |    ^    |         |
           |        |    |    |         |
           |        \ 领域模型 / Adapter-+-- Document
            \        \       /         /
              \       \-----/        /
                \                  /
                  \              /
                    \----------/

```

* `libs` 项目自身所需要的依赖的公共库，比如工具类
* `core` 包含领域模型 和 应用服务器，实现业务逻辑。core 自己不提供独立运行能力
* `adapter` 和 `endpoints` 属于适配端点，这里区别是在于 `adapter` 可以独立运行，`endpoints` 不能独立运行

# 运行 demo

1. 启动依赖服务器 mysql
```bash
docker-compose up -d
```

2. 启动 `adapter-rest` 适配端点
```bash
./gradlew bootRun -p hexagon-boot-adapter-rest
```

3. 测试服务

```bash
# post 提交数据
curl -XPOST -H "Content-type: application/json" http://127.0.0.1:8080/v1/report/error \
-d'{ "appType":"1111111111", "pageUrl":"222222" }'


# 通过 get 请求查看数据提交是否成功
curl -XGET http://127.0.0.1:8080/v1/report/error 

```