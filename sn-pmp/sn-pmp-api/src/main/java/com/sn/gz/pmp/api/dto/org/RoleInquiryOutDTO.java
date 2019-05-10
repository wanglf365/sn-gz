/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: RoleInquiryOutDTO
 * Author:   lufeiwang
 * Date:   2019/5/5
 */
package com.sn.gz.pmp.api.dto.org;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色查询
 *
 * @author lufeiwang
 * 2019/5/5
 */
@Data
public class RoleInquiryOutDTO implements Serializable {

    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;

}
