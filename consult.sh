docker stop consult
docker rm consult
docker rmi registry.cn-hangzhou.aliyuncs.com/xnt-pcs/pcs:consult
docker pull registry.cn-hangzhou.aliyuncs.com/xnt-pcs/pcs:consult
docker run -d -p 8011:8011 --name consult -v /etc/localtime:/etc/localtime:ro registry.cn-hangzhou.aliyuncs.com/xnt-pcs/pcs:consult
