package com.dianshang;

import com.dianshang.core.dao.ProductDAO;
import com.dianshang.core.pojo.Product;
import com.github.abel533.entity.Example;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试solr
 *
 * @author by ChangFeng
 * @Created by Administrator on 2017/8/24 0024 19:35.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SolrTest {
   @Autowired
   private HttpSolrServer solrServer;
    @Autowired
    private ProductDAO productDAO;
    /**
     * 创建索引（传统方式）
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void createIndex1() throws SolrServerException, IOException {
        // 使用HttpSolr服务端（HttpSolrServer） 创建solr服务器端对象
        HttpSolrServer solrServer = new HttpSolrServer(
                "http://172.93.44.150:8080/solr/collection1");
        // 使用solr输入文档（SolrInputDocument） 创建文档对象
        SolrInputDocument document = new SolrInputDocument();
        // 添加字段到文档对象
        document.addField("id", "3");
        document.addField("name_ik", "白富美范冰冰3");
        //添加文档到solr服务器对象
        solrServer.add(document);
        // 提交
        solrServer.commit();
    }


    /**
     * 创建索引（使用Spring方式）
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void createIndex2() throws SolrServerException, IOException {
        // 使用solr输入文档（SolrInputDocument） 创建文档对象
        SolrInputDocument document = new SolrInputDocument();
        // 添加字段到文档对象
        document.addField("id", "7");
        document.addField("name_ik", "白富美范冰冰7");
        // 添加文档到solr服务器对象
        solrServer.add(document);
        // 提交
        solrServer.commit();
    }
    @Test
    public void test2() {
        List<Object> ids = new ArrayList<>();
        ids.add(450);
        ids.add(449);
        Example example = new Example(Product.class);
        example.createCriteria().andIn("id",ids);
        List<Product> products=productDAO.selectByExample(example);
        System.out.println(products.size());

    }
}
