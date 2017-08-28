package com.dianshang.core.service;

import com.dianshang.core.pojo.SuperPojo;
import com.dianshang.core.tools.MyPage;
import org.apache.solr.client.solrj.SolrServerException;
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

    MyPage<SuperPojo> findProductByKeyWord(String keyword, String sor, Integer pageNum, Integer pageSize) throws SolrServerException;

}
