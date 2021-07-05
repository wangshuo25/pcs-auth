package cn.edu.bupt.pcsauth.service.impl;

import cn.edu.bupt.pcsauth.PcsAuthApplicationTest;
import cn.edu.bupt.pcsauth.service.UserService;
import cn.edu.bupt.pcsmavenjpa.entity.UserEntity;
import org.junit.Test;

import javax.annotation.Resource;

public class SysUserServiceImplTest extends PcsAuthApplicationTest {


    @Resource(name = "sys-user")
    UserService userService;

    @Test
    public void insert() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test2");
        userEntity.setPassword("123456");
        userEntity.setStatus("启用");
        userService.insert(userEntity);
    }

    @Test
    public void exist() {
    }

    @Test
    public void findByUsername() {
    }
}