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
	/**
	 * 根据id查询 单个品牌对象信息
	 * @param id
	 * @return
	 */
	public Brand findById(Long id);


	/**
	 * 去修改单个品牌
	 * @param brand
	 */
	public void updateById(Brand brand);


	/**
	 * 删除品牌根据id删除
	 * @param ids
	 */
   public void deleteByIds(String ids);
}