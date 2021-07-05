package cn.edu.bupt.pcsauth.service.impl;

import cn.edu.bupt.pcsauth.service.UserService;
import cn.edu.bupt.pcsauth.util.ClientID;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author arron
 * @date 19-5-8
 * @description
 */


@Service
@Primary
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {


    @Resource(name = "sys-user")
    UserService userService;

    @Resource(name = "test-user")
    UserService testService;

    @Resource(name = "org-user")
    UserService orgService;

    @Resource(name = "counselor-user")
    UserService counselorService;


    /**
     * 通过用户名获取用户信息
     * 通过SecurityContextHolder得到客户端ID:clientId
     * 通过clientId 选择 不同的用户表
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        {
//            "name": "13356734599",
//                "dep_id": "123"
//        }


        UserService us = null;

        UserDetails clientId = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (clientId.getUsername().equals(ClientID.SYS)) {
            us = userService;
        }

        if (clientId.getUsername().equals(ClientID.TEST)) {
            us = testService;
        }

        if (clientId.getUsername().equals(ClientID.ORG)) {
            us = orgService;
        }

        if (clientId.getUsername().equals(ClientID.COUNSELOR)) {
            us = counselorService;
        }

        assert us != null;
        if (!us.exist(username)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return us.findByUsername(username);
    }
}