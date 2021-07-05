package cn.edu.bupt.pcsauth.repository;

import cn.edu.bupt.pcsmavenjpa.entity.CounselorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author arron
 * @date 19-6-11
 * @description 咨询师查询
 */
public interface CounselorRepository extends JpaRepository<CounselorEntity, Integer> {
    /**
     * 通过用户名查找咨询师
     * @author arron
     * @date 19-6-11
     * @param username
     * @return cn.edu.bupt.pcsauth.repository.CounselorRepository
     */

    CounselorEntity findByUsername(String username);

    @Query(value="select status from counselor c where phone_number=:tel limit 1",nativeQuery = true)
    String getStatusByTel(String tel);
}
