package com.sn.gz.pmp.dsc.entity.definition;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.sn.gz.jdbc.starter.entity.BaseEntity;
import lombok.Data;

/**
 *
 *apiName递增
 * @author lufeiwang
 * 2019/4/18
 */
@Data
@TableName("t_meta_field_api_name_sequence")
public class ObjectApiNameSequence extends BaseEntity {

	@TableField("sequence")
	private Integer sequence;
    /**
     * 业务对象名称:CUSTOMER,PRODUCT,ORDER
     */
	@TableField("object_type")
	private String objectType;

}
