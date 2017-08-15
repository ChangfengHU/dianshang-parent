package com.dianshang.action;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 核心控制器
 *
 * @author by ChangFeng
 * @Created by Administrator on 2017/8/13 00139:05.
 */
@Controller
public class CenterAction {
    /**
     * 将用户输入的url路径，取出关键部分，直接转发到指定jsp页面
     *
     * @param pageName
     * @return
     */
    // 总
    @RequestMapping(value = "console/{pageName}.do")
    public String consoleShow(@PathVariable(value = "pageName") String pageName) {
        return pageName;
    }

    // 框架页面
    @RequestMapping(value = "console/frame/{pageName}.do")
    public String consoleFrameShow(@PathVariable(value = "pageName") String pageName) {
        return "/frame/" + pageName;
    }

    // 商品
    @RequestMapping(value = "console/product/{pageName}.do")
    public String consoleProductShow(@PathVariable(value = "pageName") String pageName) {
        return "/product/" + pageName;
    }

}
