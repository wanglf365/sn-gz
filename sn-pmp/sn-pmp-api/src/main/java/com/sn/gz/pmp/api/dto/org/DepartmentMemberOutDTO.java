/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: DepartmentMemberOutModel
 * Author:   lufeiwang
 * Date:   2019/5/5
 */
package com.sn.gz.pmp.api.dto.org;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 部门下的成员
 *
 * @author lufeiwang
 * 2019/5/5
 */
@Data
public class DepartmentMemberOutDTO implements Serializable {

    public DepartmentMemberOutDTO(Long memberId, String memberName, String phone) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.phone = phone;
    }

    private Long memberId;
    private String memberName;
    private String phone;

    List<MemberDepartmentOutDTO> departmentList;
}
