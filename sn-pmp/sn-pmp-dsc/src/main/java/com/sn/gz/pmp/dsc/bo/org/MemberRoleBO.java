/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: MemberRoleBO
 * Author:   lufeiwang
 * Date:   2019/5/5
 */
package com.sn.gz.pmp.dsc.bo.org;

import lombok.Data;

/**
 * 成员
 *
 * @author lufeiwang
 * 2019/5/5
 */
@Data
public class MemberRoleBO {

    public MemberRoleBO(Long memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }

    /**
     * 成员id
     */
    private Long memberId;
    /**
     * 成员名称
     */
    private String memberName;
}
