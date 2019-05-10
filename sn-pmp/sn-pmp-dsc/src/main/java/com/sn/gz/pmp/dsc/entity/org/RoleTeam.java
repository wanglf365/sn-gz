package com.sn.gz.pmp.dsc.entity.org;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.sn.gz.jdbc.starter.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 角色组
 *
 * @author xiaoxueliang
 * @since 2018-03-21
 */
@Data
@ToString(callSuper = true)
@TableName("t_org_role_team")
public class RoleTeam extends BaseEntity {

    public RoleTeam() {
    }

    public RoleTeam(Long groupId, String name, String type, Date date) {
        this.name = name;
        this.type = type;
        super.setCreateDatetime(date);
        super.setModifiedDatetime(date);
    }

    /**
     * 角色组名称
     */
    @TableField("name")
    private String name;

    /**
     * 角色组类型
     */
    @TableField("type")
    private String type;
}
