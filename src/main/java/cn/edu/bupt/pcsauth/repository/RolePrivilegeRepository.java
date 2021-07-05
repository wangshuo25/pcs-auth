package cn.edu.bupt.pcsauth.repository;

import cn.edu.bupt.pcsmavenjpa.entity.RolePrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RolePrivilegeRepository extends JpaRepository<RolePrivilegeEntity, Integer> {

    @Query(value = "select rp.level from role_privilege rp where rp.role_id = ?1 and rp.privilege_id = ?2",nativeQuery = true)
    String findLevel(int roleId, int privilegeId);
}
