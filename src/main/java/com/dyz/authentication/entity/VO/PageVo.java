package com.dyz.authentication.entity.VO;

/**
 * @ClassName PageVo
 * @Author
 * @Date 2020/9/9
 * @description
 */
public class PageVo {

    private String condition;

    private String type;

    private Integer page;

    private Integer pageSize;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageVo{" +
                "condition='" + condition + '\'' +
                ", type='" + type + '\'' +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
