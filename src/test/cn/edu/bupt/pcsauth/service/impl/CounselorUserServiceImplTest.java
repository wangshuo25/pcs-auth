package cn.edu.bupt.pcsauth.service.impl;

import cn.edu.bupt.pcsauth.PcsAuthApplicationTest;
import cn.edu.bupt.pcsauth.service.UserService;
import cn.edu.bupt.pcsmavenjpa.entity.CounselorEntity;
import org.junit.Test;

import javax.annotation.Resource;

public class CounselorUserServiceImplTest extends PcsAuthApplicationTest {

    @Resource(name = "counselor-user")
    UserService userService;

    @Test
    public void insert() {

        CounselorEntity userEntity = new CounselorEntity();
        userEntity.setUsername("counselor");
        userEntity.setPassword("123456");
        userService.insert(userEntity);
    }

    @Test
    public void exist() {
    }

    @Test
    public void findByUsername() {
        System.out.println(userService.findByUsername("counselor"));
    }
}