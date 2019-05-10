package com.sn.gz.pmp.dsc.definition.object;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;
/**
 * 对象定义类
 *
 * @author lufeiwang
 * 2019/4/17
 */
@Data
@ToString
public class SnObjectDefinition {
    private String apiName;
    private Long createdBy;
    private Date createdTime;
    private String displayName;
    private String description;
    private Long lastModifiedBy;
    private Date lastModifiedTime;
    private int version;
    private List<String> triggers;
    private List<String> validateRules;
    private String objectDescribe;
}
