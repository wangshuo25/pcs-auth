package cn.edu.bupt.pcsauth.service.impl;

import cn.edu.bupt.pcsauth.dto.InfoDTO;
import cn.edu.bupt.pcsauth.dto.Meta;
import cn.edu.bupt.pcsauth.dto.RouterDTO;
import cn.edu.bupt.pcsauth.service.PrivilegeService;
import cn.edu.bupt.pcsauth.util.TreeUtil;
import cn.edu.bupt.pcsmavenjpa.entity.*;
import cn.edu.bupt.pcsmavenoss.OSSUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author arron
 * @date 19-6-4
 * @description
 */
@Service
public class SysPrivilegeServiceImpl implements PrivilegeService {


    @Override
    public InfoDTO getInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserEntity) {
            Collection<? extends GrantedAuthority> authorities = ((UserEntity) principal).getAuthorities();
            ArrayList<String> roles = new ArrayList<>();
            for (GrantedAuthority authority : authorities) {
                roles.add(authority.getAuthority());
            }
            InfoDTO infoDTO = new InfoDTO();
            infoDTO.setRoles(roles);
            infoDTO.setTrueName(((UserEntity) principal).getTrueName());
            infoDTO.setUserId(((UserEntity) principal).getId());
            return infoDTO;
        }

        if (principal instanceof OrgUserEntity) {
            Collection<? extends GrantedAuthority> authorities = ((OrgUserEntity) principal).getAuthorities();
            ArrayList<String> roles = new ArrayList<>();
            for (GrantedAuthority authority : authorities) {
                roles.add(authority.getAuthority());
            }
            InfoDTO infoDTO = new InfoDTO();
            infoDTO.setRoles(roles);
            infoDTO.setTrueName(((OrgUserEntity) principal).getTrueName());
            infoDTO.setOrgName(((OrgUserEntity) principal).getOrganization().getName());
            infoDTO.setLogoImage(OSSUtils.getUrl(((OrgUserEntity) principal).getOrganization().getLogo(),24));
            return infoDTO;
        }
        return null;
    }


    @Override
    public List<RouterDTO> getPrivilegeTree() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserEntity){
            UserEntity user = (UserEntity) principal;
            List<RoleEntity> roleEntities = user.getRoles();
            Set<Integer> ids = new TreeSet<>();
            List<RouterDTO> routerDTOS = new ArrayList<>();
            for (RoleEntity roleEntity : roleEntities) {
                for (PrivilegeEntity privilege : roleEntity.getPrivileges()) {
                    if (!ids.contains(privilege.getId())) {
                        ids.add(privilege.getId());
                        RouterDTO routerDTO = new RouterDTO();
                        routerDTO.setId(privilege.getId());
                        routerDTO.setParentId(privilege.getParentId());
                        routerDTO.setPath(privilege.getUrl());
                        routerDTO.setComponentName(privilege.getComponent());
                        routerDTO.setMeta(new Meta(privilege.getName(),privilege.getIcon()));
                        routerDTO.setSort(privilege.getSort());
                        routerDTOS.add(routerDTO);
                    }
                }
            }
            return TreeUtil.build(routerDTOS, 0);
        }
        else if (principal instanceof OrgUserEntity) {
            OrgUserEntity user = (OrgUserEntity) principal;
            List<OrgRoleEntity> roleEntities = user.getRoles();
            Set<Integer> ids = new TreeSet<>();
            List<RouterDTO> routerDTOS = new ArrayList<>();
            for (OrgRoleEntity roleEntity : roleEntities) {
                for (OrgPrivilegeEntity privilege : roleEntity.getPrivileges()) {
                    if (!ids.contains(privilege.getId())) {
                        ids.add(privilege.getId());
                        RouterDTO routerDTO = new RouterDTO();
                        routerDTO.setMeta(new Meta(privilege.getName(),privilege.getIcon()));
                        routerDTO.setId(privilege.getId());
                        routerDTO.setParentId(privilege.getParentId());
                        routerDTO.setPath(privilege.getUrl());
                        routerDTO.setComponentName(privilege.getComponent());
                        routerDTO.setSort(privilege.getSort());
                        routerDTOS.add(routerDTO);
                    }
                }
            }
            return TreeUtil.build(routerDTOS, 0);
        }
        return null;






    }
}
