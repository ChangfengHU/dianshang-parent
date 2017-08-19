package com.dianshang.core.service;

import com.dianshang.core.dao.BrandDAO1;
import com.dianshang.core.pojo.Brand;
import com.dianshang.core.tools.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 品牌服务类
 *
 * @author Administrator
 *
 */
@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandDAO1 brandDAO;

	@Override
	public PageHelper.Page<Brand> findByExample(Brand brand, Integer pageNum, Integer pageSize) {
		// 开始分页
		PageHelper.startPage(pageNum, pageSize);
		brandDAO.findByExample(brand);
		PageHelper.Page endPage = PageHelper.endPage();// 结束分页
		return endPage;
	}

	@Override
	public Brand findById(Long id) {
		System.out.println("服务者 查询修改id"+id);
		return brandDAO.findById(id);
	}


	@Override
	public void updateById(Brand brand) {
		System.err.println("调用品牌数据是否过来了:"+brand);
		brandDAO.updateById(brand);
	}

	/**
	 * 删除批量的拼盘
	 * @param ids
	 */
	@Override
	public void deleteByIds(String ids) {
		System.err.println("看看远程过来了ids:"+ids);
            brandDAO.deleteByIds(ids);
	}

}