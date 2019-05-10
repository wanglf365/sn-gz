/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: CustomerMapper
 * Author:   Enma.ai
 * Date:     2018/3/28
 */
package com.sn.gz.pmp.dsc.mapper.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sn.gz.pmp.dsc.entity.org.Member;
import com.sn.gz.pmp.dsc.model.org.DepartmentMemberOutModel;
import com.sn.gz.pmp.dsc.model.org.MemberOutModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成员mapper
 *
 * @author lufeiwang
 * 2019/4/25
 */
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 批量保存用户
     *
     * @param memberList 用户信息
     * @author xiaoxueliang
     * 2018/5/4
     */
    void batchInsert(@Param("memberList") List<Member> memberList);

    /**
     * 批量保存用户
     *
     * @param memberList 用户信息
     * @author xiaoxueliang
     * 2018/5/4
     */
    void batchUpdate(@Param("memberList") List<Member> memberList);

    /**
     * 分页查询集团已启用成员列表（通过姓名、手机号检索）
     *
     * @param page    分页
     * @param groupId 集团id
     * @param keyword 姓名|手机号
     * @return java.util.List
     * @author Enma.ai
     * 2018/9/14
     */
    List<Member> listMembers(Page<Member> page, @Param("groupId") Long groupId, @Param("keyword") String keyword);


    /**
     * 根据角色id查询成员
     *
     * @param groupId 集团id
     * @param roleId  角色id
     * @return java.util.List<com.sn.gz.pmp.dsc.model.org.MemberOutModel>
     * @author lufeiwang
     * 2019/5/5
     */
    List<MemberOutModel> listByRoleId(@Param("groupId") Long groupId, @Param("roleId") Long roleId);

    /**
     * 查询部门成员关系
     *
     * @param groupId 集团id
     * @return java.util.List<com.sn.gz.pmp.dsc.model.org.DepartmentMemberOutModel>
     * @author lufeiwang
     * 2019/5/5
     */
    List<DepartmentMemberOutModel> listDepartmentMember(@Param("groupId") Long groupId);

    /**
     * 查询部门成员，排除在已知角色内的
     *
     * @param groupId 集团id
     * @param roleId  角色id
     * @param keyword 关键字
     * @return java.util.List<com.sn.gz.pmp.dsc.model.org.DepartmentMemberOutModel>
     * @author lufeiwang
     * 2019/5/5
     */
    List<DepartmentMemberOutModel> listMemberByExcludeRole(@Param("groupId") Long groupId, @Param("roleId") Long roleId, @Param("keyword") String keyword);


}
