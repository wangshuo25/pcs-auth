docker stop auth
docker rm auth
docker rmi registry.cn-hangzhou.aliyuncs.com/xnt-pcs/pcs:auth
docker pull registry.cn-hangzhou.aliyuncs.com/xnt-pcs/pcs:auth
docker run -d -p 8003:8003 --name auth -v /etc/localtime:/etc/localtime:ro registry.cn-hangzhou.aliyuncs.com/xnt-pcs/pcs:auth
