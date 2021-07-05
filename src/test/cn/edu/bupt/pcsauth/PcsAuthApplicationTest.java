package cn.edu.bupt.pcsauth;

import cn.edu.bupt.pcsauth.service.UserService;
import cn.edu.bupt.pcsauth.service.impl.SysUserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PcsAuthApplicationTest {

    @Resource
    SysUserServiceImpl userService;
    @Test
    public void contextLoads() {
        System.out.println(userService.findByUsername("BUPT"));
    }

}
