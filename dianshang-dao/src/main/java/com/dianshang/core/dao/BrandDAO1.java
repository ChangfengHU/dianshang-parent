package com.dianshang.core.dao;

import com.dianshang.core.pojo.Brand;

import java.util.List;

/**
 * 品牌管理DAO
 * 
 * @author Administrator
 *
 */
public interface BrandDAO1 {

	/**
	 * 根据条件查询
	 * @return
	 */
	public List<Brand> findByExample(Brand brand);

}