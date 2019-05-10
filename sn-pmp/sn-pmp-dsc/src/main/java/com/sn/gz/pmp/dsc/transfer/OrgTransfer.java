/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: OrgTransfer
 * Author:   lufeiwang
 * Date:   2019/4/25
 */
package com.sn.gz.pmp.dsc.transfer;

import com.sn.gz.core.jwt.LoginUser;
import com.sn.gz.pmp.api.dto.auth.MenuOutDTO;
import com.sn.gz.pmp.api.dto.org.*;
import com.sn.gz.pmp.dsc.bo.org.DepartmentBO;
import com.sn.gz.pmp.dsc.bo.org.MemberRoleBO;
import com.sn.gz.pmp.dsc.entity.auth.Menu;
import com.sn.gz.pmp.dsc.entity.auth.UnionUser;
import com.sn.gz.pmp.dsc.entity.org.*;
import com.sn.gz.pmp.dsc.model.org.MemberOutModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 数值转化
 *
 * @author lufeiwang
 * 2019/4/25
 */
@Mapper
public interface OrgTransfer {

    OrgTransfer INSTANCE = Mappers.getMapper(OrgTransfer.class);

    DepartmentBO map(Department department);

    Role map(RoleUpdateInDTO roleUpdateInDTO);

    Group map(GroupUpdateInDTO groupUpdateInDTO);

    UnionUser map(UnionUserUpdateInDTO unionUserUpdateInDTO);

    RoleTeamOutDTO map(RoleTeam roleTeam);

    List<RoleTeamOutDTO> mapRoleTeam(List<RoleTeam> list);

    MemberOutDTO map(MemberOutModel memberOutModel);

    List<MemberOutDTO> mapMemberOut(List<MemberOutModel> list);

    DepartmentOutDTO map(DepartmentBO departmentBO);

    List<DepartmentOutDTO> mapDepartmentOut(List<DepartmentBO> departmentBOList);

    DepartmentRoleOutDTO mapDepartmentRole(DepartmentBO departmentBO);

    MemberRoleOutDTO map(MemberRoleBO memberRoleBO);

    Authority map(BaseAuthority baseAuthority);

    @Mapping(target = "unionId", source = "id")
    LoginUser mapLoginUser(UnionUser user);

    MenuOutDTO mapMenuInfoOutDTO(Menu menu);

    List<MenuOutDTO> mapMenuOutDTOList(List<Menu> list);

}
