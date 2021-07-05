package cn.edu.bupt.pcsauth.repository;

import cn.edu.bupt.pcsmavenjpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
}
