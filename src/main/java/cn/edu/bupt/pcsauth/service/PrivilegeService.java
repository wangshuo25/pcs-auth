package cn.edu.bupt.pcsauth.service;

import cn.edu.bupt.pcsauth.dto.InfoDTO;
import cn.edu.bupt.pcsauth.dto.RouterDTO;

import java.util.List;

/**
 * @author arron
 * @date 19-6-4
 * @description 权限树生成 serve
 */
public interface PrivilegeService {

    public InfoDTO getInfo();

    public List<RouterDTO> getPrivilegeTree();
}
