/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: Group
 * Author:   Enma.ai
 * Date:     2018/3/27
 */
package com.sn.gz.pmp.dsc.entity.org;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 集团
 *
 * @author lufeiwang
 * 2019/4/24
 */
@Data
@TableName("t_org_group")
public class Group {
    /**
     * ID
     */
    @TableField(value = "id", fill = FieldFill.INSERT)
    private Long id;
    /**
     * 集团名称
     */
    @TableField("name")
    private String name;
    /**
     * 集团类型
     */
    @TableField("type")
    private String type;
    /**
     * 经营类型（CONSTRUCTION_SIDE：施工方，MATERIAL_MERCHANT：材料商，LABOR_SERVICE：劳务商）
     **/
    @TableField("management_types")
    private String managementTypes;
    /**
     * 经营期限
     */
    @TableField("operating_period")
    private Date operatingPeriod;
    /**
     * 经营范围
     */
    @TableField("business_scope")
    private String businessScope;
    /**
     * 开户银行
     */
    @TableField("deposit_bank")
    private String depositBank;
    /**
     * 银行账户
     */
    @TableField("bank_account")
    private String bankAccount;
    /**
     * 注册资金
     */
    @TableField("registered_capital")
    private BigDecimal registeredCapital;
    /**
     * 成立日期
     */
    @TableField("register_date")
    private Date registerDate;
    /**
     * 统一社会信息代码
     */
    @TableField("business_license_code")
    private String businessLicenseCode;
    /**
     * 集团简介
     */
    @TableField("introduction")
    private String introduction;
    /**
     * 办公电话
     */
    @TableField("office_phone")
    private String officePhone;
    /**
     * 集团所在省
     */
    @TableField("province")
    private Long province;
    /**
     * 集团所在市
     */
    @TableField("city")
    private Long city;
    /**
     * 集团所在区/县
     */
    @TableField("county")
    private Long county;
    /**
     * 详细地址
     */
    @TableField("address")
    private String address;
    /**
     * 法人姓名
     */
    @TableField("legal_person_name")
    private String legalPersonName;
    /**
     * 法人电话
     */
    @TableField("legal_person_phone")
    private String legalPersonPhone;
    /**
     * 法人身份证号码
     */
    @TableField("legal_person_idnumber")
    private String legalPersonIdNumber;
    /**
     * 法人身份证号码有效期
     */
    @TableField("legal_person_idcard_expiry_date")
    private Date legalPersonIdCardExpiryDate;
    /**
     * 集团联系人姓名
     */
    @TableField("contact_name")
    private String contactName;
    /**
     * 集团联系人号码
     */
    @TableField("contact_phone_number")
    private String contactPhoneNumber;
    /**
     * 集团联系人邮箱
     */
    @TableField("contact_email")
    private String contactEmail;
    /**
     * 集团审核状态：0:信息缺失 1:审核中 2：审核失败 3:审核成功
     */
    @TableField("status")
    private String status;

    /**
     * 审核意见
     */
    @TableField("audit_message")
    private String auditMessage;

    /**
     * 创建时间
     */
    @TableField(value = "create_datetime", fill = FieldFill.INSERT)
    private Date createDatetime;
    /**
     * 修改时间
     */
    @TableField(value = "modified_datetime", fill = FieldFill.INSERT_UPDATE)
    private Date modifiedDatetime;
}
