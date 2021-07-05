package cn.edu.bupt.pcsauth.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author arron
 * @date 19-5-13
 * @description
 */
public interface UserService {

    public void insert(Object userEntity);

    public boolean exist(String username);

    public UserDetails findByUsername(String username);

}
