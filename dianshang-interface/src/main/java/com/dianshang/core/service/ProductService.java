package com.dianshang.core.service;

import com.dianshang.core.pojo.Product;
import com.dianshang.core.tools.PageHelper;

/**
 * 商品服务接口
 * 
 * @author Administrator
 *
 */
public interface ProductService {
	/**
	 * 根据条件查询 带分页
	 * 
	 * @param product
	 *            查询条件的模版对象
	 * @param pageNum
	 *            当前页码
	 * @param pageSize
	 *            每页显示行数
	 * @return
	 */
	public PageHelper.Page<Product> findByExample(Product product, Integer pageNum,
												  Integer pageSize);
}