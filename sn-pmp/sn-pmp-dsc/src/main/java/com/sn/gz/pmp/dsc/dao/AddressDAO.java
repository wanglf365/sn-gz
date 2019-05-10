/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: AddressDAO
 * Author:   Enma.ai
 * Date:     2019-01-23
 */
package com.sn.gz.pmp.dsc.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sn.gz.jdbc.starter.dao.BaseDAO;
import com.sn.gz.pmp.dsc.entity.Address;
import com.sn.gz.pmp.dsc.mapper.org.AddressMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 地址
 *
 * @author lufeiwang
 * 2019/4/24
 */
@Component
public class AddressDAO extends BaseDAO<Address, Long> {

    @Resource
    private AddressMapper addressMapper;

    @Override
    public BaseMapper<Address> getMapper() {
        return addressMapper;
    }
}
