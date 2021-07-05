package cn.edu.bupt.pcsauth.repository;

import cn.edu.bupt.pcsmavenjpa.entity.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author arron
 * @date 19-6-2
 * @description
 */
public interface PrivilegeRepository extends JpaRepository<PrivilegeEntity, Integer> {
}
