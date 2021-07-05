package cn.edu.bupt.pcsauth.service.impl;

import cn.edu.bupt.pcsauth.repository.TesteeRepository;
import cn.edu.bupt.pcsauth.service.UserService;
import cn.edu.bupt.pcsmavenjpa.entity.TesteeEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author arron
 * @date 2019/5/28
 * @description
 */

@Component("test-user")
public class TestUserServiceImpl implements UserService {

    @Resource
    TesteeRepository testeeRepository;

    @Override
    public void insert(Object userEntity) {
//        TesteeEntity user = (TesteeEntity) userEntity;
//
//        String username = user.getUsername();
//        if (exist(username)){
//            throw new RuntimeException("用户名已存在！");
//        }
//        //密码加密
//        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//        testeeRepository.save(user);
    }

    @Override
    public boolean exist(String username) {
        JSONObject jsonObject = JSON.parseObject(username);
        String name = jsonObject.getString("name");
        String orgId = jsonObject.getString("orgId");
        TesteeEntity testeeEntity = testeeRepository.findByUsernameAndOrgId(name,Integer.valueOf(orgId));
        return testeeEntity != null;

    }

    @Override
    public UserDetails findByUsername(String username) {
        JSONObject jsonObject = JSON.parseObject(username);
        String name = jsonObject.getString("name");
        String orgId = jsonObject.getString("orgId");
         TesteeEntity testeeEntity = testeeRepository.findByUsernameAndOrgId(name,Integer.valueOf(orgId));
        return testeeEntity;
    }
}
