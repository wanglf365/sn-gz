/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: OrgUtils
 * Author:   lufeiwang
 * Date:   2019/4/25
 */
package com.sn.gz.pmp.dsc.utils;

import com.sn.gz.component.org.constants.OrgConstants;
import com.sn.gz.component.tpsbiz.dingtalk.bo.DingtalkDepartmentBO;
import com.sn.gz.component.tpsbiz.dingtalk.bo.DingtalkUserBO;
import com.sn.gz.core.utils.IdGenerator;
import com.sn.gz.core.utils.ListUtils;
import com.sn.gz.core.utils.NumberUtils;
import com.sn.gz.pmp.dsc.bo.org.DepartmentBO;
import com.sn.gz.pmp.dsc.bo.org.MemberRoleBO;
import com.sn.gz.pmp.dsc.bo.org.SynchronizeOrgBO;
import com.sn.gz.pmp.dsc.entity.auth.UnionUser;
import com.sn.gz.pmp.dsc.entity.org.Department;
import com.sn.gz.pmp.dsc.entity.org.DepartmentMemberRelationship;
import com.sn.gz.pmp.dsc.entity.org.Member;
import com.sn.gz.pmp.dsc.model.org.DepartmentMemberOutModel;
import com.sn.gz.pmp.dsc.transfer.OrgTransfer;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 组织架构工具类
 *
 * @author lufeiwang
 * 2019/4/25
 */
public class OrgUtils {
    /**
     * 转换成树形
     *
     * @param list 部门列表
     * @return com.sn.gz.pmp.dsc.bo.org.DepartmentBO
     * @author lufeiwang
     * 2019/4/25
     */
    public static DepartmentBO listToTree(List<Department> list) {
        DepartmentBO departmentBO = null;
        for (Department department : list) {
            if (department.getParentId() == null) {
                departmentBO = OrgTransfer.INSTANCE.map(department);
                //用递归找子
                departmentBO.setChildren(findChildren(departmentBO, list));
            }
        }
        return departmentBO;
    }

    /**
     * 查找自部门
     *
     * @param parentDepartmentBO 部门节点
     * @param list               部门列表
     * @return list
     */
    private static List<DepartmentBO> findChildren(DepartmentBO parentDepartmentBO, List<Department> list) {
        List<DepartmentBO> children = new ArrayList<>();
        for (Department department : list) {
            if (null != department.getParentId() && department.getParentId().longValue() == parentDepartmentBO.getId().longValue()) {
                DepartmentBO departmentBO = OrgTransfer.INSTANCE.map(department);
                departmentBO.setChildren(findChildren(departmentBO, list));
                children.add(departmentBO);
            }
        }
        return children;
    }

    /**
     * 合并结果
     *
     * @param all    所有
     * @param select 刷选
     * @return java.util.List<com.sn.gz.pmp.dsc.bo.org.DepartmentBO>
     * @author lufeiwang
     * 2019/5/6
     */
    public static DepartmentBO mergeDepartment(List<Department> all, List<Department> select) {
        List<Department> list = merge(all, select);
        return listToTree(list);
    }

    /**
     * 合并结果，根绝select从all合并它的父节点
     * 因为sql不支持这样的查询
     *
     * @param all    所有
     * @param select 刷选
     * @return java.util.List<com.sn.gz.pmp.dsc.bo.org.DepartmentBO>
     * @author lufeiwang
     * 2019/5/6
     */
    private static List<Department> merge(List<Department> all, List<Department> select) {
        if (ListUtils.isNull(all)) {
            return new ArrayList<>();
        }
        if (ListUtils.isNull(select)) {
            return all;
        }

        Map<Long, Department> allMap = ListUtils.list2Map(all, "getId", Department.class);
        Map<Long, Department> map = new HashMap<>();
        for (Department department : select) {
            if (!map.containsKey(department.getId())) {
                map.put(department.getId(), department);
                findParent(department, allMap, map);
                findChild(department, all, map);
            }
        }
        return ListUtils.map2List(map);
    }

    /**
     * @param department 当前部门
     * @param allMap     所有
     * @param result     结果
     * @author lufeiwang
     * 2019/5/6
     */
    private static void findParent(Department department, Map<Long, Department> allMap, Map<Long, Department> result) {
        if (null == department.getParentId()) {
            return;
        }

        Department temp = allMap.get(department.getParentId());
        if (null != temp && !result.containsKey(department.getParentId())) {
            result.put(temp.getId(), temp);
            findParent(temp, allMap, result);
        }
    }

    /**
     * @param department 当前部门
     * @param all        所有
     * @param result     结果
     * @author lufeiwang
     * 2019/5/6
     */
    private static void findChild(Department department, List<Department> all, Map<Long, Department> result) {
        if (ListUtils.isNull(all)) {
            return;
        }
        for (Department item : all) {
            if (null != item.getParentId() && item.getParentId().longValue() == department.getId().longValue()
                    && !result.containsKey(item.getId())) {
                result.put(item.getId(), item);
                findChild(item, all, result);
            }
        }
    }

    /**
     * 转换成树形
     *
     * @param list                         部门列表
     * @param departmentMemberOutModelList 成员部门列表
     * @return com.sn.gz.pmp.dsc.bo.org.DepartmentBO
     * @author lufeiwang
     * 2019/4/25
     */
    public static DepartmentBO listToTreeAndMember(List<Department> list, List<DepartmentMemberOutModel> departmentMemberOutModelList) {
        DepartmentBO departmentBO = null;
        for (Department department : list) {
            if (department.getParentId() == null) {
                departmentBO = OrgTransfer.INSTANCE.map(department);
                //用递归找子
                departmentBO.setChildren(findChildrenAndMember(departmentBO, list, departmentMemberOutModelList));
                departmentBO.setMemberList(getMemberList(departmentBO.getId(), departmentMemberOutModelList));
            }
        }
        return departmentBO;
    }

    /**
     * 获取部门下的成员
     *
     * @param departmentId                 部门id
     * @param departmentMemberOutModelList 成员部门
     * @return java.util.List<com.sn.gz.pmp.dsc.bo.org.MemberRoleBO>
     * @author lufeiwang
     * 2019/5/5
     */
    private static List<MemberRoleBO> getMemberList(Long departmentId, List<DepartmentMemberOutModel> departmentMemberOutModelList) {
        List<MemberRoleBO> memberRoleList = new ArrayList<>();
        if (ListUtils.isNull(departmentMemberOutModelList)) {
            return memberRoleList;
        }
        for (DepartmentMemberOutModel item : departmentMemberOutModelList) {
            if (departmentId.longValue() == item.getDepartmentId().longValue()) {
                MemberRoleBO memberRoleBO = new MemberRoleBO(item.getMemberId(), item.getMemberName());
                memberRoleList.add(memberRoleBO);
            }
        }
        return memberRoleList;
    }

    /**
     * 查找自部门
     *
     * @param parentDepartmentBO           部门节点
     * @param list                         部门列表
     * @param departmentMemberOutModelList 成员部门列表
     * @return list
     */
    private static List<DepartmentBO> findChildrenAndMember(DepartmentBO parentDepartmentBO, List<Department> list, List<DepartmentMemberOutModel> departmentMemberOutModelList) {
        List<DepartmentBO> children = new ArrayList<>();
        for (Department department : list) {
            if (null != department.getParentId() && department.getParentId().longValue() == parentDepartmentBO.getId().longValue()) {
                DepartmentBO departmentBO = OrgTransfer.INSTANCE.map(department);
                departmentBO.setChildren(findChildrenAndMember(departmentBO, list, departmentMemberOutModelList));
                departmentBO.setMemberList(getMemberList(departmentBO.getId(), departmentMemberOutModelList));
                children.add(departmentBO);
            }
        }
        return children;
    }

    /**
     * 要删除的部门
     *
     * @param memberList        原来成员
     * @param dingtalkMemberMap 钉钉成员列表
     * @return java.util.List<java.lang.Long>
     * @author lufeiwang
     * 2019/4/28
     */
    private static List<Long> getDeleteMember(List<Member> memberList, Map<String, DingtalkUserBO> dingtalkMemberMap) {
        if (ListUtils.isNull(memberList)) {
            return null;
        }
        List<Long> deleteMemberList = new ArrayList<>();
        for (Member member : memberList) {
            if (dingtalkMemberMap == null || !dingtalkMemberMap.containsKey(member.getDingtalkPK())) {
                deleteMemberList.add(member.getId());
            }
        }
        return deleteMemberList;
    }


    /**
     * 获取钉钉同步组织架构信息
     *
     * @param groupId                          集团id
     * @param dingtalkDepartmentList           钉钉部门
     * @param departmentList                   原来部门
     * @param memberList                       原来成员
     * @param dingtalkUserList                 钉钉成员
     * @param departmentMemberRelationshipList 成员部门关系
     * @return com.sn.gz.pmp.dsc.bo.org.SynchronizeOrgBO
     * @author lufeiwang
     * 2019/4/28
     */
    public static SynchronizeOrgBO getSynchronizeOrgBO(Long groupId, List<DingtalkDepartmentBO> dingtalkDepartmentList, List<Department> departmentList,
                                                       List<Member> memberList, List<DingtalkUserBO> dingtalkUserList, List<DepartmentMemberRelationship> departmentMemberRelationshipList) {
        SynchronizeOrgBO synchronizeOrgBO = new SynchronizeOrgBO();
        Map<Long, DingtalkDepartmentBO> dingtalkDepartmentMap = ListUtils.list2Map(dingtalkDepartmentList, "getId", DingtalkDepartmentBO.class);
        Map<String, Department> departmentMap = ListUtils.list2Map(departmentList, "getDingtalkPK", Department.class);
        Department rootDepartment = getRootDepartment(departmentList);

        //新的部门以及结构
        List<Department> newDepartmentList = getDepartment(groupId, dingtalkDepartmentMap, departmentMap, rootDepartment);

        Map<String, DingtalkUserBO> dingtalkMemberMap = ListUtils.list2Map(dingtalkUserList, "getUserid", DingtalkUserBO.class);
        Map<String, Member> memberMap = ListUtils.list2Map(memberList, "getDingtalkPK", Member.class);

        //新的成员信息
        List<Member> newMemberList = getMember(groupId, memberMap, dingtalkMemberMap);

        Map<String, Department> newDepartmentMap = ListUtils.list2Map(newDepartmentList, "getDingtalkPK", Department.class);

        Map<String, Member> newMemberMap = ListUtils.list2Map(newMemberList, "getDingtalkPK", Member.class);

        //新的部门和成员关系
        List<DepartmentMemberRelationship> newDepartmentMemberRelationships = getDepartmentMemberRelationship
                (groupId, departmentMemberRelationshipList, newDepartmentMap, dingtalkMemberMap, newMemberMap, rootDepartment);

        List<Long> deleteMembers = getDeleteMember(memberList, dingtalkMemberMap);

        List<UnionUser> unionUserList =getUnionUser(newMemberList);
        synchronizeOrgBO.setDepartmentMemberRelationships(newDepartmentMemberRelationships);
        synchronizeOrgBO.setDepartments(newDepartmentList);
        synchronizeOrgBO.setMembers(newMemberList);
        synchronizeOrgBO.setDeleteMembers(deleteMembers);
        synchronizeOrgBO.setUnionUsers(unionUserList);

        return synchronizeOrgBO;

    }

    /**
     * 处理部门
     *
     * @param groupId               集团id
     * @param departmentMap         原来部门
     * @param dingtalkDepartmentMap 钉钉部门
     * @param rootDepartment        根部门
     * @return list
     */
    private static List<Department> getDepartment(Long groupId, Map<Long, DingtalkDepartmentBO> dingtalkDepartmentMap, Map<String, Department> departmentMap, Department rootDepartment) {
        List<Department> list = getDepartment(groupId, departmentMap, dingtalkDepartmentMap);
        Map<String, Department> newDepartmentMap = ListUtils.list2Map(list, "getDingtalkPK", Department.class);

        setDepartment(list, dingtalkDepartmentMap, newDepartmentMap, rootDepartment);
        return list;
    }


    /**
     * 处理成员
     *
     * @param groupId           集团id
     * @param memberMap         原来成员
     * @param dingtalkMemberMap 钉钉成员
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.Member>
     * @author lufeiwang
     * 2019/4/28
     */
    private static List<Member> getMember(Long groupId, Map<String, Member> memberMap, Map<String, DingtalkUserBO> dingtalkMemberMap) {
        if (MapUtils.isEmpty(dingtalkMemberMap)) {
            return null;
        }
        List<Member> memberList = new ArrayList<>();
        Date date = new Date();

        for (Map.Entry<String, DingtalkUserBO> entry : dingtalkMemberMap.entrySet()) {
            //处理更新
            if (memberMap.containsKey(entry.getKey())) {
                Member member = memberMap.get(entry.getKey());
                member.setDingtalkPK(entry.getValue().getUserid());
                member.setName(entry.getValue().getName());
                member.setModifiedDatetime(date);
                member.setEmail(entry.getValue().getEmail());
                member.setDingtalkUnionId(entry.getValue().getUnionid());
                member.setPhone(entry.getValue().getMobile());
                memberList.add(member);
            }
            //处理新增
            else {
                Member member = new Member.Builder().id(IdGenerator.getInstance().nextId()).name(entry.getValue().getName()).groupId(groupId)
                        .dingtalkPK(entry.getValue().getUserid()).email(entry.getValue().getEmail()).dingtalkUnionId(entry.getValue().getUnionid())
                        .phone(entry.getValue().getMobile()).enable(OrgConstants.MemberIsEnable.ENABLE)
                        .createDatetime(date).modifiedDatetime(date).build();
                memberList.add(member);
            }
        }
        return memberList;
    }


    /**
     * 生成账户
     *
     * @param memberList 成员
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.auth.UnionUser>
     * @author lufeiwang
     * 2019/5/8
     */
    private static List<UnionUser> getUnionUser(List<Member> memberList) {
        if (ListUtils.isNull(memberList)) {
            return null;
        }
        Date date = new Date();

        List<UnionUser> unionUserList = new ArrayList<>();

        for (Member member : memberList) {
            if (NumberUtils.isNullOrZero(member.getUnionId())) {
                UnionUser unionUser = new UnionUser.Builder().email(member.getEmail()).phone(member.getPhone())
                        .createDatetime(date).modifiedDatetime(date).enable(OrgConstants.UnionUserIsEnable.ENABLE)
                        .id(IdGenerator.getInstance().nextId()).build();

                member.setUnionId(unionUser.getId());

                unionUserList.add(unionUser);
            }
        }

        return unionUserList;
    }

    /**
     * 处理部门成员
     *
     * @param groupId                          集团id
     * @param newDepartmentMap                 新部门
     * @param dingtalkMemberMap                钉钉
     * @param newMemberMap                     成员
     * @param departmentMemberRelationshipList 成员
     * @param rootDepartment                   根部门
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.DepartmentMemberRelationship>
     * @author lufeiwang
     * 2019/4/28
     */
    private static List<DepartmentMemberRelationship> getDepartmentMemberRelationship(Long groupId, List<DepartmentMemberRelationship> departmentMemberRelationshipList, Map<String, Department> newDepartmentMap,
                                                                                      Map<String, DingtalkUserBO> dingtalkMemberMap, Map<String, Member> newMemberMap, Department rootDepartment) {
        Map<String, DepartmentMemberRelationship> srcMap = getDepartmentMemberRelationship(departmentMemberRelationshipList);
        Map<String, DepartmentMemberRelationship> destMap = generateDepartmentMemberRelationship(newDepartmentMap, dingtalkMemberMap, newMemberMap, rootDepartment);

        return getDepartmentMemberRelationship(groupId, srcMap, destMap);
    }


    /**
     * 处理部门成员
     *
     * @param groupId 集团id
     * @param srcMap  源部门成员关系
     * @param destMap 钉钉部门成员关系
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.DepartmentMemberRelationship>
     * @author lufeiwang
     * 2019/4/28
     */
    private static List<DepartmentMemberRelationship> getDepartmentMemberRelationship(Long groupId, Map<String, DepartmentMemberRelationship> srcMap, Map<String, DepartmentMemberRelationship> destMap) {
        if (MapUtils.isEmpty(destMap)) {
            return null;
        }

        List<DepartmentMemberRelationship> departmentMemberRelationshipList = new ArrayList<>();
        Date date = new Date();
        for (Map.Entry<String, DepartmentMemberRelationship> entry : destMap.entrySet()) {
            //处理更新
            if (MapUtils.isNotEmpty(srcMap) && srcMap.containsKey(entry.getKey())) {
                DepartmentMemberRelationship departmentMemberRelationship = srcMap.get(entry.getKey());
                departmentMemberRelationship.setLeader(entry.getValue().getLeader());
                departmentMemberRelationship.setModifiedDatetime(date);
                departmentMemberRelationshipList.add(departmentMemberRelationship);
            }
            //处理新增
            else {
                DepartmentMemberRelationship departmentMemberRelationship = entry.getValue();
                departmentMemberRelationship.setId(IdGenerator.getInstance().nextId());
                departmentMemberRelationship.setCreateDatetime(date);
                departmentMemberRelationship.setModifiedDatetime(date);
                departmentMemberRelationship.setGroupId(groupId);
                departmentMemberRelationship.setMainDepartment(OrgConstants.DepartmentIsMain.NOT_MAIN);
                departmentMemberRelationshipList.add(departmentMemberRelationship);
            }
        }

        //设置默认主部门
        setMainDepartment(departmentMemberRelationshipList);

        return departmentMemberRelationshipList;
    }

    /**
     * 给成员设置默认主部门
     *
     * @param list 关系
     * @author lufeiwang
     * 2019/5/5
     */
    private static void setMainDepartment(List<DepartmentMemberRelationship> list) {
        if (ListUtils.isNull(list)) {
            return;
        }
        Map<Long, List<DepartmentMemberRelationship>> map = new HashMap<>();
        for (DepartmentMemberRelationship item : list) {
            List<DepartmentMemberRelationship> departmentMemberRelationships = map.get(item.getMemberId());
            if (null == departmentMemberRelationships) {
                departmentMemberRelationships = new ArrayList<>();
                departmentMemberRelationships.add(item);
                map.put(item.getMemberId(), departmentMemberRelationships);
            } else {
                departmentMemberRelationships.add(item);
            }
        }
        boolean flag;
        for (Map.Entry<Long, List<DepartmentMemberRelationship>> entry : map.entrySet()) {
            flag = false;
            List<DepartmentMemberRelationship> departmentMemberRelationships = entry.getValue();
            if (ListUtils.isNotNull(departmentMemberRelationships)) {
                for (DepartmentMemberRelationship item : departmentMemberRelationships) {
                    if (OrgConstants.DepartmentIsMain.NOT_MAIN.equals(item.getMainDepartment())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    departmentMemberRelationships.get(0).setMainDepartment(OrgConstants.DepartmentIsMain.IS_MAIN);
                }
            }
        }
    }


    /**
     * 部门成员关系转map
     *
     * @param departmentMemberRelationshipList 部门成员关系map
     * @author lufeiwang
     * 2019/4/28
     */
    private static Map<String, DepartmentMemberRelationship> getDepartmentMemberRelationship(List<DepartmentMemberRelationship> departmentMemberRelationshipList) {
        if (ListUtils.isNull(departmentMemberRelationshipList)) {
            return null;
        }
        Map<String, DepartmentMemberRelationship> map = new HashMap<>();

        for (DepartmentMemberRelationship item : departmentMemberRelationshipList) {
            map.put(item.getMemberId().toString() + "," + item.getDepartmentId(), item);
        }
        return map;
    }

    /**
     * 新的部门和成员关系
     *
     * @param newDepartmentMap  新部门
     * @param dingtalkMemberMap 钉钉
     * @param newMemberMap      成员
     * @author lufeiwang
     * 2019/4/28
     */
    private static Map<String, DepartmentMemberRelationship> generateDepartmentMemberRelationship(Map<String, Department> newDepartmentMap,
                                                                                                  Map<String, DingtalkUserBO> dingtalkMemberMap, Map<String, Member> newMemberMap, Department rootDepartment) {
        if (MapUtils.isEmpty(dingtalkMemberMap)) {
            return null;
        }

        Map<String, DepartmentMemberRelationship> map = new HashMap<>();
        for (Map.Entry<String, DingtalkUserBO> entry : dingtalkMemberMap.entrySet()) {
            List<Long> departmentIds = entry.getValue().getDepartment();
            for (Long id : departmentIds) {
                //根部门成员
                if (id == 1L) {
                    DepartmentMemberRelationship relationship = new DepartmentMemberRelationship.Builder().memberId(newMemberMap.get(entry.getValue().getUserid()).getId())
                            .departmentId(rootDepartment.getId()).build();
                    map.put(relationship.getMemberId().toString() + "," + relationship.getDepartmentId().toString(), relationship);
                } else {
                    DepartmentMemberRelationship relationship = new DepartmentMemberRelationship.Builder().memberId(newMemberMap.get(entry.getValue().getUserid()).getId())
                            .departmentId(newDepartmentMap.get(id.toString()).getId())
                            .leader(entry.getValue().getIsLeader() ? OrgConstants.MemberIsLeader.LEADER : OrgConstants.MemberIsLeader.NOT_LEADER).build();
                    map.put(relationship.getMemberId().toString() + "," + relationship.getDepartmentId().toString(), relationship);
                }
            }
        }
        return map;
    }

    /**
     * 处理部门
     *
     * @param groupId               集团id
     * @param departmentMap         原来部门
     * @param dingtalkDepartmentMap 钉钉部门
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.Department>
     * @author lufeiwang
     * 2019/4/28
     */
    private static List<Department> getDepartment(Long groupId, Map<String, Department> departmentMap, Map<Long, DingtalkDepartmentBO> dingtalkDepartmentMap) {
        if (MapUtils.isEmpty(dingtalkDepartmentMap)) {
            return null;
        }
        List<Department> departmentList = new ArrayList<>();
        Date date = new Date();

        for (Map.Entry<Long, DingtalkDepartmentBO> entry : dingtalkDepartmentMap.entrySet()) {
            //处理更新
            if (departmentMap.containsKey(entry.getKey().toString())) {
                Department department = departmentMap.get(entry.getKey().toString());
                department.setDingtalkPK(entry.getValue().getId().toString());
                department.setName(entry.getValue().getName());
                department.setParentId(entry.getValue().getParentid());
                department.setModifiedDatetime(date);
                departmentList.add(department);
            }
            //处理新增
            else {
                Department department = new Department.Builder().id(IdGenerator.getInstance().nextId()).name(entry.getValue().getName()).groupId(groupId)
                        .dingtalkPK(entry.getValue().getId().toString()).createDatetime(date).modifiedDatetime(date).build();
                departmentList.add(department);
            }
        }
        return departmentList;
    }

    /**
     * 获取根部门
     *
     * @param departmentList 部门
     * @return com.sn.gz.pmp.dsc.entity.org.Department
     * @author lufeiwang
     * 2019/4/28
     */
    private static Department getRootDepartment(List<Department> departmentList) {
        for (Department department : departmentList) {
            if (NumberUtils.isNullOrZero(department.getParentId())) {
                return department;
            }
        }
        return null;
    }


    /**
     * @param dingtalkParentId      当前节点钉钉父id
     * @param newDepartmentMap      全部部门
     * @param dingtalkDepartmentMap 钉钉
     * @param rootDepartment        根部门
     * @return java.lang.String
     * @author lufeiwang
     * 2019/4/28
     */
    private static String getDepartmentPath(Long dingtalkParentId, Map<String, Department> newDepartmentMap, Map<Long, DingtalkDepartmentBO> dingtalkDepartmentMap, Department rootDepartment) {
        String path = "";
        if (dingtalkParentId != 1L) {
            DingtalkDepartmentBO dingtalkDepartmentBO = dingtalkDepartmentMap.get(dingtalkParentId);
            Long parentIdTemp = dingtalkDepartmentBO.getParentid();
            path = newDepartmentMap.get(dingtalkParentId.toString()).getId().toString();
            path = path + "," + getDepartmentPath(parentIdTemp, newDepartmentMap, dingtalkDepartmentMap, rootDepartment);
        } else {
            path = path + rootDepartment.getId().toString();
        }

        return path;
    }

    /**
     * 设置部门父部门,path,level
     *
     * @param list                  部门
     * @param dingtalkDepartmentMap 钉钉部门
     * @author lufeiwang
     * 2019/4/28
     */
    private static void setDepartment(List<Department> list, Map<Long, DingtalkDepartmentBO> dingtalkDepartmentMap, Map<String, Department> newDepartmentMap, Department rootDepartment) {
        if (ListUtils.isNull(list)) {
            return;
        }
        for (Department department : list) {
            DingtalkDepartmentBO dingtalkDepartmentBO = dingtalkDepartmentMap.get(new Long(department.getDingtalkPK()));
            Long dingtalkParentId = dingtalkDepartmentBO.getParentid();
            if (dingtalkParentId == 1L) {
                department.setParentId(rootDepartment.getId());
            } else {
                department.setParentId(newDepartmentMap.get(dingtalkParentId.toString()).getId());
            }
            String path = getDepartmentPath(dingtalkParentId, newDepartmentMap, dingtalkDepartmentMap, rootDepartment);
            department.setPath(getPath(path));
            department.setLevel(getLevel(path));
        }
    }

    /**
     * 获取部门层级
     *
     * @param path 路径
     * @return int
     * @author lufeiwang
     * 2019/4/28
     */
    private static int getLevel(String path) {
        if (StringUtils.isEmpty(path)) {
            return 0;
        }
        String[] str = path.split(",");
        return str.length;
    }

    /**
     * path 倒序，root在前
     *
     * @param path 路径
     * @return java.lang.String
     * @author lufeiwang
     * 2019/4/28
     */
    private static String getPath(String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        String[] str = path.split(",");
        StringBuffer bf = new StringBuffer();
        for (int i = str.length - 1; i >= 0; i--) {
            bf.append(str[i]).append(",");
        }

        return bf.substring(0, bf.length() - 1);
    }
}
