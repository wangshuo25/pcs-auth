mvn clean install -DskipTests

docker build -t registry.cn-beijing.aliyuncs.com/bsd-hub/auth:1.0 . #最后的.不可以省略 #例如 docker build -t registry.cn-beijing.aliyuncs.com/bsd-hub/data-analysis:0.1 .

docker push registry.cn-beijing.aliyuncs.com/bsd-hub/auth