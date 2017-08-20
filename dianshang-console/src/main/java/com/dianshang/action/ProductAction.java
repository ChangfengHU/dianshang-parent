package com.dianshang.action;

import com.dianshang.core.pojo.Product;
import com.dianshang.core.service.ProductService;
import com.dianshang.core.tools.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品增删改查的action
 *
 * @author by ChangFeng
 * @Created by Administrator on 2017/8/20 0020 1:40.
 */
@Controller
public class ProductAction {

    @Autowired(required=true)
    private ProductService productService;

    // 显示商品列表（具体映射）
    @RequestMapping(value = "console/product/list.do")
    public String consoleProductShowList(Model model, String name, Long brandId,
                                         Integer isShow, Integer pageNum, Integer pageSize) {

        // 设置查询条件
        // 只演示查name，其它条件省略掉了，如果是get方式，注意处理乱码
        Product product = new Product();
        System.out.println(name);
        product.setName(name);
        System.err.println("进入商品列表查询action");

        PageHelper.Page<Product> pageProduct = productService.findByExample(product, pageNum,
                pageSize);
        System.err.println("查看商品集合件数"+pageProduct.getResult().size());

        int begin;
        int end;

        // 计算显示的起始页码（根据当前页码计算）：当前页码-5
        begin = pageProduct.getPageNum() - 5;
        if (begin < 1) {// 页码修复
            begin = 1;
        }
        // 计算显示的结束页码（根据开始页码计算）：开始页码+9
        end = begin + 9;
        if (end > pageProduct.getPages()) {// 页码修复
            end = pageProduct.getPages();
        }
        // 将查询出来的品牌集合传递给页面
        model.addAttribute("pageProducts", pageProduct);
        // 设置查询数据回显之将查询数据传回给页面
        model.addAttribute("name",name);
        model.addAttribute("isShow", isShow);
        // model.addAttribute("brandId", brandId);
        model.addAttribute("begin", begin);
        model.addAttribute("end", end);
        return "/product/list";
    }
    // 显示商品添加（具体映射）
    @RequestMapping(value = "console/product/showAdd.do")
    public String consoleBrandShowAdd(Model model) {
        // 加载所有可用颜色
        System.err.println("进入商品添加action");
        Model colors = model.addAttribute("colors", productService.findEnableColors());
        return "/product/add";
    }


    // 添加商品
    @RequestMapping(value = "console/product/doAdd.do")
    public String consoleProductDoAdd(Model model,Product product) {
        System.err.println("进行商品添加操作action:"+product);
        productService.add(product);
        return "redirect:/console/product/list.do";
    }
}
