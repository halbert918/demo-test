package com.test.batch.simple.domian;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by admin on 2016/8/17.
 */
public class PayBillItem implements Serializable {
    /**
     * 商户id
     */
    private String mchId;
    /**
     * 子商户id
     */
    private String subMchId;
    /**
     * 业务订单号
     */
    private String orderNo;
    /**
     * 交易时间
     */
    private Date tradingTime;
    /**
     * 商户订单号，pay_recharge表主键id
     */
    private String outTradeNo;
    /**
     * 第三方支付订单号
     */
    private String transactionId;
    /**
     * 交易状态
     */
    private Integer tradeState;
    /**
     * 订单总金额
     */
    private BigDecimal totalFee;
    /**
     * 代金券或立减优惠金额
     */
    private BigDecimal couponFee;
    /**
     * 交易描述
     */
    private String tradeDesc;
    /**
     * 商户退款单号
     */
    private String outRefundNo;
    /**
     * 第三方退款单号
     */
    private String refundId;
    /**
     * 退款申请时间
     */
    private Date refundTime;
    /**
     * 退款完成时间
     */
    private Date refundFinishTime;
    /**
     * 退款金额
     */
    private BigDecimal refundFee;
    /**
     * 代金券或立减优惠退款金额
     */
    private BigDecimal refundCouponFee;
    /**
     * 退款状态
     */
    private Integer refundState;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getTradingTime() {
        return tradingTime;
    }

    public void setTradingTime(Date tradingTime) {
        this.tradingTime = tradingTime;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getTradeState() {
        return tradeState;
    }

    public void setTradeState(Integer tradeState) {
        this.tradeState = tradeState;
    }

    public void setRefundState(Integer refundState) {
        this.refundState = refundState;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(BigDecimal couponFee) {
        this.couponFee = couponFee;
    }

    public String getTradeDesc() {
        return tradeDesc;
    }

    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public Date getRefundFinishTime() {
        return refundFinishTime;
    }

    public void setRefundFinishTime(Date refundFinishTime) {
        this.refundFinishTime = refundFinishTime;
    }

    public BigDecimal getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(BigDecimal refundFee) {
        this.refundFee = refundFee;
    }

    public BigDecimal getRefundCouponFee() {
        return refundCouponFee;
    }

    public void setRefundCouponFee(BigDecimal refundCouponFee) {
        this.refundCouponFee = refundCouponFee;
    }

    public Integer getRefundState() {
        return refundState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
