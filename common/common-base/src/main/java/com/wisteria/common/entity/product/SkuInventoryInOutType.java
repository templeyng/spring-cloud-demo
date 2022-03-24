package com.wisteria.common.entity.product;

public class SkuInventoryInOutType {
    //10 purchaseIn 11 refundOutOccupied 12 refundOut
    //采购入库
    public static final int PURCHASE_IN = 10;
    //采购退货占用
    public static final int PURCHASE_REFUND_OUT_OCCUPIED = 11;
    //采购退货
    public static final int PURCHASE_REFUND_OUT = 12;

    //销售出库
    public static final int SALE_OUT = 20;
    //销售出库占用
    public static final int SALE_OUT_OCCUPIED = 21;
    //销售退货入库
    public static final int SALE_REFUND_IN = 22;

}
