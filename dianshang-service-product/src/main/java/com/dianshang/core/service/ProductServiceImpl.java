package com.dianshang.core.service;

import com.dianshang.core.dao.ColorDAO;
import com.dianshang.core.dao.ProductDAO;
import com.dianshang.core.dao.SkuDAO;
import com.dianshang.core.pojo.Color;
import com.dianshang.core.pojo.Product;
import com.dianshang.core.pojo.Sku;
import com.dianshang.core.tools.PageHelper;
import com.github.abel533.entity.Example;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired HttpSolrServer solrServer;
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

    @Override
    public void update(Product product, String ids)
            throws Exception{
        System.err.println("product.getIsShow():"+product.getIsShow());
        Example example = new Example(Product.class);
        System.err.println("商品上架:"+ids);
        // 将ids的字符串转成list集合
        List arrayList = new ArrayList();
        String[] split = ids.split(",");
        for (String string : split) {
            arrayList.add(string);
        }

        // 设置批量修改的id条件
        example.createCriteria().andIn("id", arrayList);

        // 进行批量，选择性属性修改
        productDAO.updateByExampleSelective(product, example);

        // 如果是商品上架，将商品信息添加到solr服务器中
        // 需要保存的信息有：商品id、商品名称、图片地址、售价、品牌id、上架时间（可选）
        System.err.println("product.getIsShow()="+product.getIsShow());
        if (product.getIsShow() == 1) {

            System.err.println("进入添加solr库");
            // 将数组转为集合给下面用
            List<Object> al = new ArrayList<Object>();
            for (String id : split) {
                al.add(id);
            }
            // 根据ids查询这些商品的信息
            System.err.println("al"+al);
            Example example1 = new Example(Product.class);
            example1.createCriteria().andIn("id", al);
            List<Product> products = productDAO.selectByExample(example1);
            // 遍历查询出来的商品集合
            System.err.println("开始添加到solr中数量"+products.size());
            for (Product product2 : products) {
                System.err.println("开始添加到solr中"+product2);
                // 将商品的各个信息，添加到文档对象中

                SolrInputDocument document = new SolrInputDocument();

                // id
                document.addField("id", String.valueOf(product2.getId()));
                // name
                document.addField("name_ik", product2.getName());
                // brandId
                document.addField("brandId", product2.getBrandId());

                // 商品首张图片
                String url = product2.getImgUrl().split(",")[0];
                document.addField("url", url);

                // 该商品旗下的所有库存的最低价格
                // SELECT * FROM bbs_sku WHERE product_id = 438 ORDER BY price
                // ASC LIMIT 0,1;
                Example example2 = new Example(Sku.class);

                // =
                example2.createCriteria().andEqualTo("productId",
                        product2.getId());
                System.err.println("product2.getId()"+product2.getId());
                // 排序
                example2.setOrderByClause("price asc");

                // limit
                PageHelper.startPage(1, 1);
                skuDAO.selectByExample(example2);
                PageHelper.Page<Sku> endPage = PageHelper.endPage();
                System.err.println("endPage"+endPage.getResult().size());
                if(endPage.getResult().size()>0){
                    Sku sku = endPage.getResult().get(0);
                    document.addField("price", sku.getPrice());
                }


                solrServer.add(document);
                System.err.println("开始添加到solr中结束"+9999);
            }
            solrServer.commit();
        }
    }

    }

