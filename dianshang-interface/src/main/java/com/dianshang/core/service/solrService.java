package com.dianshang.core.service;

import com.dianshang.core.pojo.SuperPojo;
import org.apache.solr.client.solrj.SolrServerException;

import java.util.List;
/**
 * solr服务接口
 * Created by Administrator on 2017/8/26 0026.
 */
public interface SolrService {
    /**
     * 根据关键字搜索商品
     *
     * @param keyword
     * @return
     * @throws SolrServerException
     */
    List<SuperPojo> findProductByKeyWord(String keyword) throws SolrServerException;

}
