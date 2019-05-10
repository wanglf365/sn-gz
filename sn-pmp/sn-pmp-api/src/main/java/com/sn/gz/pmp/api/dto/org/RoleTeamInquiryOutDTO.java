/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: RoleTeamInquiryOutDTO
 * Author:   lufeiwang
 * Date:   2019/5/5
 */
package com.sn.gz.pmp.api.dto.org;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色组
 *
 * @author lufeiwang
 * 2019/5/5
 */
@Data
public class RoleTeamInquiryOutDTO implements Serializable {

    /**
     * 角色组id
     */
    private Long roleTeamId;
    /**
     * 角色组名称
     */
    private String roleTeamName;

    /**
     * 角色列表
     */
    private List<RoleInquiryOutDTO> roleList;
}
