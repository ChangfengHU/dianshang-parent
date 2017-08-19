package com.dianshang.core.tools;

/**
 * 分页工具条
 *
 * @author by ChangFeng
 * @Created by Administrator on 2017/8/16 0016 21:26.
 */
public class Beginend {
    // 工具条使用的结果
    private static  int begin;// 起始页码数
    private static  int end;// 结束的页码数

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    /**
     * 计算出按钮的开始和结束
     * @param pageNum
     * @param pages
     */
    public static void setBeginend(int pageNum,int pages) {
        // 计算显示的起始页码（根据当前页码计算）：当前页码-5
        begin = pageNum - 5;
        if (begin < 1) {// 页码修复
            begin = 1;
        }
        // 计算显示的结束页码（根据开始页码计算）：开始页码+9
        end = begin + 9;
        if (end > pages) {// 页码修复
            end = pages;
        }
    }
}
