package com.dianshang.core.action;

import com.dianshang.core.pojo.SuperPojo;
import com.dianshang.core.service.SolrService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 前台首页控制中心
 * 
 * @author Administrator
 *
 */
@Controller
public  class IndexAction {
	@Autowired
	private SolrService solrService;
	//显示前台首页
	@RequestMapping(value="/")
	public String showIndex()
	{
		return "index";
	}


	// 前台首页搜索功能
	@RequestMapping(value = "/search")
	public String indexSearch(Model model, String keyword)
			throws SolrServerException {
		System.err.println("进入搜索首页");
        System.err.println("keyword:"+keyword);
		/*keyword = Encoding.encodeGetRequest(keyword);
		System.err.println("keyword:"+keyword);*/
        List<SuperPojo> SuperPojos = solrService.findProductByKeyWord(keyword);
        System.err.println("action成功获取数据:"+SuperPojos);
        model.addAttribute("SuperPojos",SuperPojos);
        return "search";
	}

}