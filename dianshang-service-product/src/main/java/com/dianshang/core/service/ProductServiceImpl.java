package com.dianshang.core.service;

import com.dianshang.core.dao.ColorDAO;
import com.dianshang.core.dao.ProductDAO;
import com.dianshang.core.dao.SkuDAO;
import com.dianshang.core.pojo.Color;
import com.dianshang.core.pojo.Product;
import com.dianshang.core.pojo.Sku;
import com.dianshang.core.tools.PageHelper;
import com.github.abel533.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    private ColorDAO colorDAO;
    @Autowired
    private SkuDAO skuDAO;

    @Override
    public PageHelper.Page<Product> findByExample(Product product, Integer pageNum,
                                                  Integer pageSize) {
        System.err.println("进入远程商品列表查询service");
        if(product.getName()==null)
        {
            product.setName("");
        }
        // 开始分页
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Product.class);
        example.setOrderByClause("createTime desc");
        example.createCriteria().andLike("name", "%" + product.getName() + "%");
        List<Product> products = productDAO.selectByExample(example);

        // 结束分页
        PageHelper.Page<Product> endPage = PageHelper.endPage();
        return endPage;
    }


    @Override
    public List<Color> findEnableColors() {
        System.err.println("远程服务颜色列表显示");
        Example example = new Example(Color.class);
        example.createCriteria().andNotEqualTo("parentId", 0 + "");
        List<Color> colors = colorDAO.selectByExample(example);
        return colors;
    }

    @Override
    public void add(Product product) {
        System.err.println("远程服务添加商品方法:"+ product.getId());

            // 设置默认值
            if (product.getIsShow() == null) {
                product.setIsShow(0);
            }
            if (product.getCreateTime() == null) {
                product.setCreateTime(new Date());
            }
            if (product.getIsHot() == null) {
                product.setIsHot(0);
            }
            if (product.getIsShow() == null) {
                product.setIsShow(0);
            }
            if (product.getIsCommend() == null) {
                product.setIsCommend(0);
            }
            if (product.getIsNew() == null) {
                product.setIsNew(0);
            }

            // 先添加商品到数据库中
            productDAO.insert(product);
            System.out.println("获得回显id" + product.getId());

            // 将商品信息添加到库存表中
            // 遍历不同的颜色和尺码
            // 每一个不同颜色，或者不同尺码，都应该插入库存表中，成为一条数据
          // TODO: 2017/8/20 0020 此处可能出现空指针异常
            String[] colors = product.getColors().split(",");
            String[] sizes = product.getSizes().split(",");

            for (String color : colors) {
                for (String size : sizes) {
                    Sku sku = new Sku();
                    sku.setProductId(product.getId());
                    sku.setColorId(Long.parseLong(color));
                    sku.setSize(size);
                    sku.setMarketPrice(1000.00f);
                    sku.setPrice(800.00f);
                    sku.setDeliveFee(20f);
                    sku.setStock(0);
                    sku.setUpperLimit(100);
                    sku.setCreateTime(new Date());

                    skuDAO.insert(sku);
                }
            }
        }

}