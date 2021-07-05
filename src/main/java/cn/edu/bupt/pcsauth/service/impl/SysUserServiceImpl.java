package cn.edu.bupt.pcsauth.service.impl;

import cn.edu.bupt.pcsauth.repository.RolePrivilegeRepository;
import cn.edu.bupt.pcsauth.repository.UserRepository;
import cn.edu.bupt.pcsauth.service.UserService;
import cn.edu.bupt.pcsmavenjpa.entity.PrivilegeEntity;
import cn.edu.bupt.pcsmavenjpa.entity.RoleEntity;
import cn.edu.bupt.pcsmavenjpa.entity.RolePrivilegeEntity;
import cn.edu.bupt.pcsmavenjpa.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author arron
 * @date 2019/5/28
 * @description
 */
@Component("sys-user")
public class SysUserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private RolePrivilegeRepository rolePrivilegeRepository;


    @Override
    public void insert(Object userEntity) {
        UserEntity user = (UserEntity) userEntity;

        String username = user.getUsername();
        if (exist(username)){
            throw new RuntimeException("用户名已存在！");
        }
        //密码加密
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean exist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public UserDetails findByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        // 找到角色
        // 把角色和权限的级别取出来
        // 把级别赋值给权限
        List<RoleEntity> roles = user.getRoles();
        for(RoleEntity role : roles){
            List<PrivilegeEntity> privileges = role.getPrivileges();
            privileges.stream().forEach(privilege -> {
                String privielegLevel = rolePrivilegeRepository.findLevel(role.getId(),privilege.getId());
                if(privielegLevel != null)
                    privilege.setLevel(Integer.valueOf(privielegLevel));
            });
            role.setPrivileges(privileges);
        }
        return user;
    }
}
