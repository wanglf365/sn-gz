/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: GroupUpdateInDTO
 * Author:   lufeiwang
 * Date:   2019/4/26
 */
package com.sn.gz.pmp.api.dto.org;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 企业信息更新注册
 *
 * @author lufeiwang
 * 2019/4/26
 */
@Data
public class GroupUpdateInDTO implements Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 集团名称
     */
    private String name;
    /**
     * 集团类型
     */
    private String type;
    /**
     * 经营类型（CONSTRUCTION_SIDE：施工方，MATERIAL_MERCHANT：材料商，LABOR_SERVICE：劳务商）
     **/
    private String managementTypes;
    /**
     * 经营期限
     */
    private Date operatingPeriod;
    /**
     * 经营范围
     */
    private String businessScope;
    /**
     * 开户银行
     */
    private String depositBank;
    /**
     * 银行账户
     */
    private String bankAccount;
    /**
     * 注册资金
     */
    private BigDecimal registeredCapital;
    /**
     * 成立日期
     */
    private Date registerDate;
    /**
     * 统一社会信息代码
     */
    private String businessLicenseCode;
    /**
     * 集团简介
     */
    private String introduction;
    /**
     * 办公电话
     */
    private String officePhone;
    /**
     * 集团所在省
     */
    private Long province;
    /**
     * 集团所在市
     */
    private Long city;
    /**
     * 集团所在区/县
     */
    private Long county;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 法人姓名
     */
    private String legalPersonName;
    /**
     * 法人电话
     */
    private String legalPersonPhone;
    /**
     * 法人身份证号码
     */
    private String legalPersonIdNumber;
    /**
     * 法人身份证号码有效期
     */
    private Date legalPersonIdCardExpiryDate;
    /**
     * 集团联系人姓名
     */
    private String contactName;
    /**
     * 集团联系人号码
     */
    private String contactPhoneNumber;
    /**
     * 集团联系人邮箱
     */
    private String contactEmail;
    /**
     * 集团审核状态：0:信息缺失 1:审核中 2：审核失败 3:审核成功
     */
    private String status;

    /**
     * 审核意见
     */
    private String auditMessage;
}
