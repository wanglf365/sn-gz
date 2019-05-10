
package com.sn.gz.pmp.dsc.dao.org;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sn.gz.core.utils.ListUtils;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.jdbc.starter.query.EntityClassWrapper;
import com.sn.gz.jdbc.starter.query.PageWrapper;
import com.sn.gz.pmp.dsc.entity.org.Member;
import com.sn.gz.pmp.dsc.mapper.org.MemberMapper;
import com.sn.gz.pmp.dsc.model.org.DepartmentMemberOutModel;
import com.sn.gz.pmp.dsc.model.org.MemberOutModel;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 成员
 *
 * @author lufeiwang
 * 2019/4/25
 */
@Component
public class MemberDAO extends BaseDAO<Member, Long> {

    @Resource
    private MemberMapper memberMapper;

    @Override
    public BaseMapper<Member> getMapper() {
        return memberMapper;
    }

    /**
     * 批量插入
     *
     * @param memberList 成员
     * @author lufeiwang
     * 2019/4/25
     */
    public void batchInsert(List<Member> memberList) {
        if (ListUtils.isNotNull(memberList)) {
            memberMapper.batchInsert(memberList);
        }
    }

    /**
     * 根据集团id删除所有部门
     *
     * @param groupId 集团id
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.Department>
     * @author lufeiwang
     * 2019/4/25
     */
    public Integer delete(Long groupId) {
        EntityClassWrapper<Member> ew = new EntityClassWrapper(Member.class);
        ew.eq("groupId", groupId);
        return memberMapper.delete(ew);
    }

    /**
     * 按条件查询用户信息
     *
     * @param page 分页
     * @param ew   条件
     * @return java.util.List
     * @author Enma.ai
     * 2018/6/2
     */
    public List<Member> selectPage(Page<Member> page, EntityWrapper<Member> ew) {
        return memberMapper.selectPage(page, ew);
    }

    /**
     * 分页查询集团已启用成员列表（通过姓名、手机号检索）
     *
     * @param pageWrapper 分页
     * @param groupId     集团id
     * @param keyword     姓名|手机号
     * @return java.util.List
     * @author Enma.ai
     * 2018/9/14
     */
    public List<Member> listMembers(PageWrapper<Member> pageWrapper, Long groupId, String keyword) {
        Page<Member> page = getMyBatisPlusPage(pageWrapper);
        List<Member> customerList = memberMapper.listMembers(page, groupId, keyword);
        pageWrapper.setTotal(page.getTotal());
        return customerList;
    }

    /**
     * 根据集团id查询所有成员
     *
     * @param groupId 集团id
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.Member>
     * @author lufeiwang
     * 2019/4/26
     */
    public List<Member> listByGroupId(Long groupId) {
        EntityClassWrapper<Member> ew = new EntityClassWrapper(Member.class);
        ew.eq("groupId", groupId);
        return memberMapper.selectList(ew);
    }

    /**
     * 全局账户id所有成员
     *
     * @param unionId 全局账户id
     * @return java.util.List<com.sn.gz.pmp.dsc.entity.org.Member>
     * @author lufeiwang
     * 2019/4/26
     */
    public List<Member> listByUnionId(Long unionId) {
        EntityClassWrapper<Member> ew = new EntityClassWrapper(Member.class);
        ew.eq("unionId", unionId);
        return memberMapper.selectList(ew);
    }

    /**
     * 根据角色id查询成员
     *
     * @param groupId 集团id
     * @param roleId  角色id
     * @return java.util.List<com.sn.gz.pmp.dsc.model.org.MemberOutModel>
     * @author lufeiwang
     * 2019/5/5
     */
    public List<MemberOutModel> listByRoleId(Long groupId, Long roleId) {
        return memberMapper.listByRoleId(groupId, roleId);
    }

    /**
     * 查询部门成员关系
     *
     * @param groupId 集团id
     * @return java.util.List<com.sn.gz.pmp.dsc.model.org.DepartmentMemberOutModel>
     * @author lufeiwang
     * 2019/5/5
     */
    public List<DepartmentMemberOutModel> listDepartmentMember(Long groupId) {
        return memberMapper.listDepartmentMember(groupId);
    }

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
    public List<DepartmentMemberOutModel> listMemberByExcludeRole(Long groupId, Long roleId, String keyword) {
        return memberMapper.listMemberByExcludeRole(groupId, roleId, keyword);
    }

}
