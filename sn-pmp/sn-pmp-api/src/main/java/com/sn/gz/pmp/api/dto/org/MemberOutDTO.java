/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: MemberOutModel
 * Author:   lufeiwang
 * Date:   2019/5/5
 */
package com.sn.gz.pmp.api.dto.org;

import lombok.Data;

import java.io.Serializable;

/**
 * 成员查询
 *
 * @author lufeiwang
 * 2019/5/5
 */
@Data
public class MemberOutDTO implements Serializable {
    /**
     * 成员id
     */
    private Long memberId;

    /**
     * 成员名称
     */
    private String memberName;

    /**
     * 成员手机
     */
    private String phone;

    /**
     * 成员主部门
     */
    private String departmentName;

}
