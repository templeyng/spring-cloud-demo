package com.wisteria.common.entity.base;

import lombok.Data;

@Data
public class Page {
    private int pageNum;
    private int pageTotal;
    private int totalCount;

    public Page() {
    }

    public Page(int pageNum, int pageTotal) {
        this.pageNum = pageNum;
        this.pageTotal = pageTotal;
    }

    public Page(int pageNum, int pageTotal, int totalCount) {
        this.pageNum = pageNum;
        this.pageTotal = pageTotal;
        this.totalCount = totalCount;
    }

    public static Page firstPage(int totalCount) {
        return new Page(1, 1, totalCount);
    }

    public static Page firstPage() {
        return firstPage(0);
    }
}