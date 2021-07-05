package cn.edu.bupt.pcsauth.service.impl;

import cn.edu.bupt.pcsauth.PcsAuthApplicationTest;
import cn.edu.bupt.pcsauth.service.UserService;
import cn.edu.bupt.pcsmavenjpa.entity.OrgUserEntity;
import cn.edu.bupt.pcsmavenjpa.utils.Status;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.Timestamp;


public class OrgSysUserServiceImplTest extends PcsAuthApplicationTest {

    @Resource(name = "org-user")
    UserService userService;

    @Test
    public void insert() {
        OrgUserEntity orgUserEntity = new OrgUserEntity();
        orgUserEntity.setUsername("orgUser");
        orgUserEntity.setPassword("123456");
        orgUserEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        orgUserEntity.setStatus(Status.normal);
        orgUserEntity.setTrueName("hzg");
        orgUserEntity.setDptId(1001);
        orgUserEntity.setIsAdmin(true);
        orgUserEntity.setPhone("123345567");
        orgUserEntity.setOrgId(1);
        userService.insert(orgUserEntity);
    }

    @Test
    public void exist() {
    }

    @Test
    public void findByUsername() {
        OrgUserEntity orgUserEntity =  (OrgUserEntity)userService.findByUsername("orgUser");
        System.out.println(orgUserEntity);
    }
}