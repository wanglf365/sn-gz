/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: RoleMemberOutModel
 * Author:   lufeiwang
 * Date:   2019/4/25
 */
package com.sn.gz.pmp.dsc.model.org;

import lombok.Data;

/**
 * 角色成员
 *
 * @author lufeiwang
 * 2019/4/25
 */
@Data
public class RoleMemberOutModel {
    /**
     * 用户id
     */
    private Long memberId;
    /**
     * 姓名
     */
    private String memberName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 所属部门（使用“,”分隔）
     */
    private String departmentName;
}
