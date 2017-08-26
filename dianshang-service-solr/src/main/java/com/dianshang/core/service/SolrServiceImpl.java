package com.dianshang.core.service;

import com.dianshang.core.pojo.SuperPojo;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author by ChangFeng
 * @Created by Administrator on 2017/8/26 0026 23:51.
 */
@Service(value = "solrService")
public class SolrServiceImpl implements SolrService {
    /**
     * 根据关键字搜索商品
     *
     * @param keyword
     * @return
     * @throws SolrServerException
     */

    @Autowired
    private SolrServer solrServer;
    @Override
    public List<SuperPojo> findProductByKeyWord(String keyword) throws SolrServerException {
        System.err.println("进入查询索引库服务类:"+keyword);
// 设置查询条件
        SolrQuery solrQuery = new SolrQuery("name_ik:" + keyword);
        // 设置提取行数
         solrQuery.setRows(12);
        // 设置高亮
        // 设置高亮的格式
        solrQuery.setHighlight(true);// 开启高亮
        solrQuery.setHighlightSimplePre("<span style='color:red;'>");// 前缀
        solrQuery.setHighlightSimplePost("</span>");// 后缀
        solrQuery.addHighlightField("name_ik");// 添加高亮的字段

        // 开始查询
        QueryResponse response = solrServer.query(solrQuery);

        // 获得高亮数据集合
        Map<String, Map<String, List<String>>> highlighting = response
                .getHighlighting();

        // 获得结果集
        SolrDocumentList results = response.getResults();

        // 获得总数量
        long numFound = results.getNumFound();

        // 将结果集中的信息封装到商品对象中
        // 注意：由于原商品对象中并没有价格属性，而价格属性本应该是在商品对象的子对象库存对象中，
        // 而本次设计并不打算使用类似于hibernate的在pojo中做对象的相应关联，所以这里，我们可以使用万能对象来装载数据
        // 万能对象就可以等同于从数据库查询（包括连接查询）出的结果表中的一条数据

        // 创建商品对象（万能对象）集合
        List<SuperPojo> superProducts = new ArrayList<SuperPojo>();

        for (SolrDocument solrDocument : results) {
            // 创建商品对象
            SuperPojo superProduct = new SuperPojo();
            // 商品id
            String id = (String) solrDocument.get("id");
            superProduct.setProperty("id", id);

             //商品名称
            /* String name = (String) solrDocument.get("name_ik");
             superProduct.setProperty("name", name);
*/
            // 取得高亮数据集合中的商品名称
            Map<String, List<String>> map = highlighting.get(id);
            String string = map.get("name_ik").get(0);
            superProduct.setProperty("name", string);

            // 图片地址
            String imgUrl = (String) solrDocument.get("url");
            superProduct.setProperty("imgUrl", imgUrl);

            // 商品最低价格
            Float price = (Float) solrDocument.get("price");
            superProduct.setProperty("price", price);

            // 品牌id
            String brandId = (String) solrDocument.get("brandId");
            superProduct.setProperty("brandId", brandId);

            // 将万能商品对象添加到集合中
            superProducts.add(superProduct);
        }
        System.err.println("查询索引库服务类结束:"+superProducts);
        return superProducts;
    }
}
