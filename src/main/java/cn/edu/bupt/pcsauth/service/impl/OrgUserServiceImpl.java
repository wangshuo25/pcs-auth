package cn.edu.bupt.pcsauth.service.impl;

import cn.edu.bupt.pcsauth.repository.OrgUserRepository;
import cn.edu.bupt.pcsauth.service.UserService;
import cn.edu.bupt.pcsmavenjpa.entity.OrgUserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author arron
 * @date 19-6-11
 * @description
 */
@Component("org-user")
public class OrgUserServiceImpl implements UserService {

    @Resource
    OrgUserRepository orgUserRepository;

    @Override
    public void insert(Object userEntity) {
        OrgUserEntity user = (OrgUserEntity) userEntity;

        String username = user.getUsername();
        if (exist(username)){
            throw new RuntimeException("用户名已存在！");
        }
        //密码加密
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        orgUserRepository.save(user);

    }

    @Override
    public boolean exist(String username) {
        return orgUserRepository.findByUsername(username) != null;
    }

    @Override
    public UserDetails findByUsername(String username) {
        return orgUserRepository.findByUsername(username);

    }
}
