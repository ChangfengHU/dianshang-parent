package com.dianshang.action;

import com.dianshang.core.pojo.Brand;
import com.dianshang.core.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 品牌管理控制器
 * 
 * @author Administrator
 *
 */
@Controller
public class BrandAction {

	@Autowired
	private BrandService brandService;

	// 品牌
	@RequestMapping(value = "console/brand/{pageName}.do")
	public String consoleBrandShow(@PathVariable(value = "pageName") String pageName, Model model, String name,
								   Integer isDisplay) {

		// 品牌查询
		if (pageName.equals("list")) {
			System.out.println(name);//品牌名称
			System.out.println(isDisplay);//是否可用
			System.out.println(222222);
			//设置查询条件
			Brand brand = new Brand();
			brand.setName(name);
			brand.setIsDisplay(isDisplay);

			List<Brand> brands = brandService.findByExample(brand);
			// 将查询出来的品牌集合传递给页面
			System.out.println(brands.size());

			model.addAttribute("brands", brands);
			model.addAttribute("name", name);
			model.addAttribute("isDisplay", isDisplay);
		}

		return "/brand/" + pageName;
	}

}