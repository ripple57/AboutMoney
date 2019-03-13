package com.ripple.lendmoney.model;

import java.util.List;

/*****************************************************
 * 作者: HuangShaobo on 2019/3/5 22:04.
 * 微信: ripple57  e-mail: 247421018@qq.com
 * 项目: AboutMoney
 * 作用: 
 *****************************************************/
public class OrderListBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * payState : -1
         * createTime : 2019-03-13 17:21:42
         * loanAmount : 10000
         * userId : 402881ef6975e089016975f3bf270000
         * borrower : 张某某
         * userName : 18000000000
         * repaymentMethod : 到期还本付息
         * loanRate : 15
         * loanDate : 30
         * appIOUInfoid : 402881ef6975e0890169765b80fa000a
         * orderId :
         * lender : 张某某
         */

        private int payState;
        private String createTime;
        private int loanAmount;
        private String userId;
        private String borrower;
        private String userName;
        private String repaymentMethod;
        private int loanRate;
        private int loanDate;
        private String appIOUInfoid;
        private String orderId;
        private String lender;

        public int getPayState() {
            return payState;
        }

        public void setPayState(int payState) {
            this.payState = payState;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getLoanAmount() {
            return loanAmount;
        }

        public void setLoanAmount(int loanAmount) {
            this.loanAmount = loanAmount;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getBorrower() {
            return borrower;
        }

        public void setBorrower(String borrower) {
            this.borrower = borrower;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRepaymentMethod() {
            return repaymentMethod;
        }

        public void setRepaymentMethod(String repaymentMethod) {
            this.repaymentMethod = repaymentMethod;
        }

        public int getLoanRate() {
            return loanRate;
        }

        public void setLoanRate(int loanRate) {
            this.loanRate = loanRate;
        }

        public int getLoanDate() {
            return loanDate;
        }

        public void setLoanDate(int loanDate) {
            this.loanDate = loanDate;
        }

        public String getAppIOUInfoid() {
            return appIOUInfoid;
        }

        public void setAppIOUInfoid(String appIOUInfoid) {
            this.appIOUInfoid = appIOUInfoid;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getLender() {
            return lender;
        }

        public void setLender(String lender) {
            this.lender = lender;
        }
    }
}
