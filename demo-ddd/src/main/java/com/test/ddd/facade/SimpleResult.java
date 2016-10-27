package com.test.ddd.facade;

import com.test.ddd.domain.model.Account;
import com.test.ddd.facade.enums.ResultEnum;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Created by admin on 2016/9/14.
 */
public class SimpleResult {

    private ResultEnum resultEnum = ResultEnum.EXECUTE_SUCCESS;
    /**
     *
     */
    private Account account;

    public ResultEnum getResultEnum() {
        return resultEnum;
    }

    public void setResultEnum(ResultEnum resultEnum) {
        this.resultEnum = resultEnum;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
