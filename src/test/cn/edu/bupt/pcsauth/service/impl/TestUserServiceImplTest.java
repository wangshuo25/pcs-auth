package cn.edu.bupt.pcsauth.service.impl;

import cn.edu.bupt.pcsauth.PcsAuthApplicationTest;
import cn.edu.bupt.pcsauth.service.UserService;
import cn.edu.bupt.pcsmavenjpa.entity.TesteeEntity;
import org.junit.Test;

import javax.annotation.Resource;

public class TestUserServiceImplTest extends PcsAuthApplicationTest {

    @Resource(name = "test-user")
    UserService testService;

    @Test
    public void insert() {
        TesteeEntity userEntity = new TesteeEntity();
        userEntity.setUsername("testee");
        userEntity.setPassword("123456");
        testService.insert(userEntity);
    }

    @Test
    public void exist() {
    }

    @Test
    public void findByUsername() {
    }
}