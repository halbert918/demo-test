package com.test.ddd.domain.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 资金账户信息
 * 领域对象
 */
public class Account {

	private int id;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 账户ID
	 */
	private String accountId;
	/**
	 * 账户余额
	 */
	private BigDecimal amount = BigDecimal.ZERO;
	/**
	 * 账户状态
	 */
	private StatusEnum status = StatusEnum.NORMAL;
	
	public Account() {}

	public Account(String accountId, BigDecimal amount) {
		this.accountId = accountId;
		this.amount = amount;
	}

	/**
	 * 提现逻辑
	 * @param withdrawAmount 提现金额
     */
	public void withdraw(BigDecimal withdrawAmount) {
		//判断提现金额是否大于0
		if (withdrawAmount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("提现金额不能小于0");
		}
		//判断余额是否足够
		if(amount.compareTo(withdrawAmount) <= 0) {
			throw new IllegalArgumentException("提现金额大于账户余额");
		}
		if (status == StatusEnum.FREEZE) {
			throw new IllegalArgumentException("该账户已被冻结，无法提现");
		}

		//TODO 处理手续费逻辑

		//减少账户金额
		amount = amount.subtract(withdrawAmount);
	}

	/**
	 * 充值
	 * @param rechargeAmount 充值金额
     */
	public void recharge(BigDecimal rechargeAmount) {
		if (rechargeAmount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("充值金额不能小于0");
		}
		if (status != StatusEnum.NORMAL) {
			throw new IllegalArgumentException("该账户已被冻结，无法充值");
		}
		//增加账户金额
		amount = amount.add(rechargeAmount);
	}

	public int getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
