package com.sykj.uusmart.utils;

import java.util.List;

public class PageUtil<T> {
 
    private List<T> list; //对象记录结果集
    private Long total = 0L; // 总记录数
    private int limit = 8; // 每页显示记录数
    private Long pages = 1L; // 总页数
    private Long pageNumber = 1L; // 当前页
     
    private boolean isFirstPage=false;        //是否为第一页
    private boolean isLastPage=false;         //是否为最后一页
    private boolean hasPreviousPage=false;   //是否有前一页
    private boolean hasNextPage=false;       //是否有下一页
     

     
    public PageUtil(long total, long pageNumber) {
        init(total, pageNumber, limit);
    }
     
    public PageUtil(long total, long pageNumber, int limit) {
        init(total, pageNumber, limit);
    }
     
    private void init(long total, long pageNumber, int limit){
        //设置基本参数
        this.total=total;
        this.limit=limit;
        this.pages=(this.total-1)/this.limit+1;
         
        //根据输入可能错误的当前号码进行自动纠正
        if(pageNumber<1){
            this.pageNumber=1L;
        }else if(pageNumber>this.pages){
            this.pageNumber=this.pages;
        }else{
            this.pageNumber=pageNumber;
        }

         
        //以及页面边界的判定
        judgePageBoudary();
    }
     

 
    /**
     * 判定页面边界
     */
    private void judgePageBoudary(){
        isFirstPage = pageNumber == 1;
        isLastPage = pageNumber == pages;
        hasPreviousPage = pageNumber!=1;
        hasNextPage = pageNumber!=pages;
    }
     
     
    public void setList(List<T> list) {
        this.list = list;
    }
 
    /**
     * 得到当前页的内容
     * @return {List}
     */
    public List<T> getList() {
        return list;
    }
 
    /**
     * 得到记录总数
     * @return {int}
     */
    public Long getTotal() {
        return total;
    }
 
    /**
     * 得到每页显示多少条记录
     * @return {int}
     */
    public int getLimit() {
        return limit;
    }
 
    /**
     * 得到页面总数
     * @return {int}
     */
    public Long getPages() {
        return pages;
    }

    /**
     * 得到当前页号
     * @return {int}
     */
    public Long getPageNumber() {
        return pageNumber;
    }
 
    public boolean isFirstPage() {
        return isFirstPage;
    }
 
    public boolean isLastPage() {
        return isLastPage;
    }
 
    public boolean hasPreviousPage() {
        return hasPreviousPage;
    }
 
    public boolean hasNextPage() {
        return hasNextPage;
    }
 

}