/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: DepartmentMemberOutModel
 * Author:   lufeiwang
 * Date:   2019/5/5
 */
package com.sn.gz.pmp.api.dto.org;

import lombok.Data;

import java.io.Serializable;

/**
 * 部门下的成员
 *
 * @author lufeiwang
 * 2019/5/5
 */
@Data
public class MemberDepartmentOutDTO implements Serializable {

    public MemberDepartmentOutDTO(String departmentName, String mainDepartment, Long departmentId) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.mainDepartment = mainDepartment;
    }

    private String departmentName;
    /**
     * 1为主部门
     */
    private String mainDepartment;
    private Long departmentId;
}
