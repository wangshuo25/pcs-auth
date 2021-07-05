package cn.edu.bupt.pcsauth.repository;

import cn.edu.bupt.pcsmavenjpa.entity.OrgUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author arron
 * @date 19-6-11
 * @description
 */
public interface OrgUserRepository  extends JpaRepository<OrgUserEntity, Integer> {
    /**
     * 通过用户名
     * @author arron
     * @date 19-6-11
     * @param username
     * @return cn.edu.bupt.pcsauth.entity.UserEntity
     */
    OrgUserEntity findByUsername(String username);

}
