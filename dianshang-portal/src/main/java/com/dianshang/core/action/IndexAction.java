package com.dianshang.core.action;

import com.dianshang.core.pojo.SuperPojo;
import com.dianshang.core.service.SolrService;
import com.dianshang.core.tools.MyPage;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String indexSearch(Model model, String keyword,String sort,Integer pageNum,Integer pageSize)
			throws SolrServerException {
		System.err.println("进入搜索首页");
		System.err.println("keyword:"+keyword);
		System.err.println("sort:"+sort);
		System.err.println("pageNum:"+pageNum);
		System.err.println("pageSize:"+pageSize);

		/*keyword = Encoding.encodeGetRequest(keyword);
		System.err.println("keyword:"+keyword);*/

		MyPage<SuperPojo> pageSuperPojos = solrService.findProductByKeyWord(keyword, sort, pageNum, pageSize);
		System.err.println("含有分页的数据:"+pageSuperPojos.getResult());
		model.addAttribute("pageSuperPojos",pageSuperPojos);
		System.err.println("进入排序:"+sort);
		// 将反转前的sort加入到sort2
		model.addAttribute("sort2", sort);

		// 对sort进行反转
		if (sort != null && sort.equals("price asc")) {
			sort = "price desc";
		} else {
			sort = "price asc";
		}

		model.addAttribute("sort", sort);

		System.err.println("走出排序:"+sort);
		model.addAttribute("keyword",keyword);
		return "search";
	}

}