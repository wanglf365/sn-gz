/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: RoleOutModel
 * Author:   lufeiwang
 * Date:   2019/5/5
 */
package com.sn.gz.pmp.dsc.model.org;

import lombok.Data;

/**
 * 角色列表
 *
 * @author lufeiwang
 * 2019/5/5
 */
@Data
public class RoleOutModel {

    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色组id
     */
    private Long roleTeamId;
    /**
     * 角色组名称
     */
    private String roleTeamName;
}
