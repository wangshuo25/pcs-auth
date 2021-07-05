package cn.edu.bupt.pcsauth.service.impl;

import cn.edu.bupt.pcsauth.repository.CounselorRepository;
import cn.edu.bupt.pcsauth.service.UserService;
import cn.edu.bupt.pcsmavenjpa.entity.CounselorEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author arron
 * @date 19-6-11
 * @description
 */
@Component("counselor-user")
public class CounselorUserServiceImpl implements UserService {

    @Resource
    private CounselorRepository counselorRepository;

    @Override
    public void insert(Object userEntity) {

        CounselorEntity user = (CounselorEntity) userEntity;

        String username = user.getUsername();
        if (exist(username)){
            throw new RuntimeException("用户名已存在！");
        }
        //密码加密
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        counselorRepository.save(user);

    }

    @Override
    public boolean exist(String username) {

        boolean res = true;
        String stat = counselorRepository.getStatusByTel(username);
        if(stat!=null&&stat.equals("废除")){
            res = false;
        }

        return counselorRepository.findByUsername(username) != null&&res;
    }

    @Override
    public UserDetails findByUsername(String username) {
        return counselorRepository.findByUsername(username);
    }
}
