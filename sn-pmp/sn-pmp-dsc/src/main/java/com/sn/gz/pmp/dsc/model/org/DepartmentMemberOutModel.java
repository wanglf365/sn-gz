/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: DepartmentMemberOutModel
 * Author:   lufeiwang
 * Date:   2019/5/5
 */
package com.sn.gz.pmp.dsc.model.org;

import lombok.Data;

/**
 * 部门下的成员
 *
 * @author lufeiwang
 * 2019/5/5
 */
@Data
public class DepartmentMemberOutModel {
    private Long memberId;
    private String memberName;
    private String phone;
    private String departmentName;
    /**
     * 1为主部门
     */
    private String mainDepartment;
    private Long departmentId;
}
