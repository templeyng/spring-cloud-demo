package com.wisteria.common.entity.base;

public final class DicConstant {
    /////////////接口返回////////////
    public static final String RESULT_SUCCESS = "success";
    public static final String RESULT_ERROR = "error";

    /////////////分页参数////////////
    //分页起始条
    public static final String PAGE_START = "pageStart";
    //分页结束条
    public static final String PAGE_END = "pageEnd";
    //处理时间
    public static final String SEARCH_TIME_BEGIN = "searchTimeBegin";
    public static final String SEARCH_TIME_END = "searchTimeEnd";
    //最大分页大小
    public static final int PAGE_SIZE_MAX = 1000;
    //默认分页大小
    public static final int PAGE_SIZE_NOR = 200;
    //分页起始点
    public static final int PAGE_BEGIN = 0;

    /////////////流程状态////////////
    //流程开始
    public static final String STATUS_BEGIN = "begin";
    //流程结束
    public static final String STATUS_END = "end";
    //流程异常结束
    public static final String STATUS_ERROR = "error";

    /////////////系统参数////////////
    public static final String DATA_FORMAT_TYPE = "yyyy-MM-dd HH:mm:ss";
    public static final String DATA_NORMAL_BEGIN = "1970-01-01 00:00:00";
    public static final String RELATION_NO = "relationNo";

    /////////////Exception返回信息////////////
    public static final String EXP_EXCEPTION_ERROR_MSG = "Unknown Error,message is : {}";
    public static final String EXP_LOAD_DATA_EXCEPTION_ERROR_MSG = "Load Data Error,message is : {}";
    public static final String EXP_ERROR_MSG_LOG = "error is : {}";

    /////////////服务注册////////////
    public static final String SERVICE_EMP ="emp-service";

    /////////////ResourceID////////////
    public static final String NACOS_RESOURCE_ID = "nacos-resource";

    /////////////Redis Key////////////
    public static final String PRODUCT_SKU_ALL_LIST = "product_sku_all_list";

    /////////////Message////////////
    /////////////QUEUE////////////
    public static final String PURCHASE_ORDER_STOCK_IN_QUEUE = "com.wisteria.purchase.order.stock.in.queue";
    /////////////EXCHANGE////////////
    public static final String PURCHASE_ORDER_EXCHANGE = "com.wisteria.purchase.order.exchange";
    /////////////ROUTING_KEY////////////
    public static final String PURCHASE_ORDER_ROUTING_KEY = "com.wisteria.purchase.order.routing.key";
}
