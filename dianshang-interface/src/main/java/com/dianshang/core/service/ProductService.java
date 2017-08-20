package com.dianshang.core.service;

import com.dianshang.core.pojo.Color;
import com.dianshang.core.pojo.Product;
import com.dianshang.core.tools.PageHelper;

import java.util.List;

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

	/**
	 *  显示商品列表的时候把颜色显示出来
	 *  @return
	 */
 	public List<Color> findEnableColors();


	/**
	 *  添加商品
 	 *  @param product
	 */
 public void add(Product product);
}