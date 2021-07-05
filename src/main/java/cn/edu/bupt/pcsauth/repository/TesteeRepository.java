package cn.edu.bupt.pcsauth.repository;

import cn.edu.bupt.pcsmavenjpa.entity.TesteeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author arron
 * @date 2019/5/28
 * @description 被试者
 */
public interface TesteeRepository  extends JpaRepository<TesteeEntity, Integer> {

    /**
     * 通过用户名找到对象
     * @param username
     * @return
     */
    TesteeEntity findByUsername(String username);
    TesteeEntity findByUsernameAndOrgId(String username,Integer orgId);

}
