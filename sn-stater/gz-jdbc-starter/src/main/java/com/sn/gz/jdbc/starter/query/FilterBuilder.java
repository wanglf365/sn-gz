/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: FilterBuilder
 * Author:   xiaoxueliang
 * Date:     2018/7/7 10:08
 * @since 1.0.0
 */
package com.sn.gz.jdbc.starter.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 过滤组装类
 * @author 肖学良<br>
 * Version: 1.0<br>
 * Date: 2015年12月24日
 */
public class FilterBuilder implements Serializable {


	private List<Filter> filters = new ArrayList<Filter>();
	
	/**
	 * 添加过滤条件
	 * @author 肖学良<br>
	 * Date: 2015年12月24日
	 */
	public FilterBuilder add(Filter filter){
		filters.add(filter);
		return this;
	}
	/**
	 * 返回过滤集
	 * @author 肖学良<br>
	 * Date: 2015年12月24日
	 */
	public List<Filter> build(){
		return filters;
	}
}
