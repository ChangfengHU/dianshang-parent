package com.dianshang.core.service;

import com.dianshang.core.dao.BrandDAO1;
import com.dianshang.core.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
	public List<Brand> findByExample(Brand brand) {
		System.out.println("提供者:"+brand);
		return brandDAO.findByExample(brand);
	}

}