# jar包
```
sudo mvn clean install -Dmaven.test.skip=true
```
# 打包文件
## 修改 application.yml 配置文件

服务器IP  
39.105.25.16 (公网)
172.17.127.72 (内网)

## 需要Dockerfile 文件
```
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```
## 需要pom.xml 插件
```
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>dockerfile-maven-plugin</artifactId>
                <version>1.3.6</version>
                <configuration>
                    <repository>${project.artifactId}</repository>
                    <buildArgs>
                        <JAR_FILE>target/${project.build.finalName}.jar</JAR_FILE>
                    </buildArgs>
                </configuration>
            </plugin>
```

## 打包
```
sudo mvn package dockerfile:build -e -DskipTests
```

# 登录
```
 sudo docker login --username=pwalan registry.cn-hangzhou.aliyuncs.com
```

# 打tag
```
sudo docker tag pcs-auth:latest registry.cn-hangzhou.aliyuncs.com/xnt-pcs/pcs:[镜像版本号]
```
例如 ：
sudo docker tag pcs-auth:latest registry.cn-hangzhou.aliyuncs.com/xnt-pcs/pcs:auth

ImageId为镜像id 通过 sudo docker images 查看

# push 到私有云
```
sudo docker push registry.cn-hangzhou.aliyuncs.com/xnt-pcs/pcs:[镜像版本号]
```

例如 ： 
sudo docker push registry.cn-hangzhou.aliyuncs.com/xnt-pcs/pcs:auth

# 打包合并命令
```
sudo bash docker.sh
```

# 阿里云 启动
```
ssh root@39.105.25.16
Yang1290
# cd pcs
# ll
docker start mysql
docker start myredis
./pcs/eureka.sh
./pcs/gateway.sh
./pcs/auth.sh
./pcs/consult.sh
./pcs/dict.sh
./pcs/evaluation.sh
./pcs/exam.sh
./pcs/homepage.sh
./pcs/org.sh
./pcs/orgui.sh
./pcs/pcsui.sh
./pcs/platform.sh
./pcs/portal.sh
./pcs/scale.sh
./pcs/simsun.ttc
./pcs/statistic.sh
./pcs/system.sh
./pcs/sysui.sh
```
# redis 清空緩存
```
redis-cli -h 127.0.0.1 -p 6379
flushall
```
#实时查看docker容器日志
```
sudo docker logs -f -t --tail 行数 容器名
```

# 进入docker
```
sudo docker exec -it name /bin/bash
```

# too many connection
```
show variables like '%max_connections%';
show global status like 'Max_used_connections';
set GLOBAL max_connections=10000; 
show global variables like 'wait_timeout';
set global wait_timeout=3600;
set global interactive_timeout=3600;
show status like 'Threads%';
set global Threads_running=2;

```

# docker 启动
```
systemctl start docker
```


