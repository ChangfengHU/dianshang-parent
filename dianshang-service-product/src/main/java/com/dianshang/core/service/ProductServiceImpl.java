package com.dianshang.core.service;

import com.dianshang.core.dao.ProductDAO;
import com.dianshang.core.pojo.Product;
import com.dianshang.core.tools.PageHelper;
import com.github.abel533.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务类
 *
 * @author Administrator
 *
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Override
    public PageHelper.Page<Product> findByExample(Product product, Integer pageNum,
                                                  Integer pageSize) {
        System.err.println("进入远程商品列表查询service");
        // TODO Auto-generated method stub
        if(product.getName()==null)
        {
            product.setName("");
        }
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Product.class);
        example.createCriteria().andLike("name", "%" + product.getName() + "%");
        List<Product> products = productDAO.selectByExample(example);

        // 结束分页
        PageHelper.Page<Product> endPage = PageHelper.endPage();
        return endPage;
    }

}