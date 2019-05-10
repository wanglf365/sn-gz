/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: SynchronizeOrgBO
 * Author:   lufeiwang
 * Date:   2019/4/28
 */
package com.sn.gz.pmp.dsc.bo.org;

import com.sn.gz.pmp.dsc.entity.auth.UnionUser;
import com.sn.gz.pmp.dsc.entity.org.Department;
import com.sn.gz.pmp.dsc.entity.org.DepartmentMemberRelationship;
import com.sn.gz.pmp.dsc.entity.org.Member;
import lombok.Data;

import java.util.List;

/**
 * 组织架构同步
 *
 * @author lufeiwang
 * 2019/4/28
 */
@Data
public class SynchronizeOrgBO {

    private List<Department> departments;

    private List<Member> members;

    private List<DepartmentMemberRelationship> departmentMemberRelationships;

    /**
     * 删除的成员
     */
    private List<Long> deleteMembers;

    /**
     * 注册的账户
     */
    private List<UnionUser> unionUsers;
}
