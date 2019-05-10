package com.sn.gz.pmp.dsc.entity.org;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.sn.gz.jdbc.starter.entity.BaseEntity;
import lombok.Data;

/**
 * 云之家配置项entity
 *
 * @author luffy
 * <p>
 * Date 2018-05-07
 * @since 1.0.0
 */
@Data
@TableName("t_org_platform_conf")
public class GroupPlatformConf extends BaseEntity {

    /**
     * 配置项json字符串
     */
    @TableField("conf")
    private String conf;

    /**
     * 审批渠道企业第三方平台标识
     * 钉钉(corpId)
     */
    @TableField("corp_id")
    private String corpId;

    /**
     * 审批渠道名称
     */
    @TableField("channel")
    private String channel;

}
