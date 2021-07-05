package cn.edu.bupt.pcsauth.dto;

import cn.edu.bupt.pcsauth.util.TreeNode;
import lombok.Data;

import java.util.ArrayList;

/**
 * @author arron
 * @date 19-6-2
 * @description 路由表
 */
@Data
public class RouterDTO extends TreeNode{
    private String path;
    private String componentName;
    private Meta meta;

}
