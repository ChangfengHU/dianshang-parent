package com.dianshang.core.service;

import com.dianshang.core.pojo.Brand;
import com.dianshang.core.tools.PageHelper;

/**
 * 品牌服务类接口
 * 
 * @author Administrator
 *
 */
public interface BrandService {

	/**
	 * 根据条件查询
	 * 
	 * @return
	 */
	public PageHelper.Page<Brand> findByExample(Brand brand, Integer pageNum, Integer pageSize);
	/**
	 * 根据id查询
	 *
	 * @return
	 */
	public Brand findById(Long id);

	/**
	 * 根据id修改品牌
	 *
	 * @return
	 */
	public void updateById(Brand brand);

	/**
	 *  根據品牌id刪除品牌
	 *  @param ids
	 */
	void deleteByIds(String ids);
}