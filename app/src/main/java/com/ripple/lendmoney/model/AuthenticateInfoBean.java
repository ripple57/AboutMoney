package com.ripple.lendmoney.model;

import java.io.Serializable;

public class AuthenticateInfoBean implements Serializable {


    /**
     * data : {"openingBank":"北京银行","contactPhone":"18688566866","backIDCard":"/IOU/customerinfo/18000000000/b95292d1f12b41be905443faf470488d_18000000000.jpg","telephoneList":"true","alipayCreditImg2":"/IOU/customerinfo/18000000000/26d4035200b247008328b9d7ef05c8da_18000000000.jpg","reservePhone":"18000000000","kinshipName":"安全","alipayCreditImg1":"/IOU/customerinfo/18000000000/0700a054363b4f2bb32ff9fdf0326ef7_18000000000.jpg","directKinship":"父母","urgentContact":"父母","id":"402881ef6975e089016975f3bf5e0001","frontIDCard":"/IOU/customerinfo/18000000000/bed3f9361d1c4ea693210d0e18292192_18000000000.jpg","contactName":"哦可以了","userId":"402881ef6975e089016975f3bf270000","userName":"18000000000","realName":"é\u0098¿å¦\u0088","kinshipPhone":"18000889888","bankCardNumber":"66666666666","idNumber":"142354198901010000"}
     */

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * openingBank : 北京银行
         * contactPhone : 18688566866
         * backIDCard : /IOU/customerinfo/18000000000/b95292d1f12b41be905443faf470488d_18000000000.jpg
         * telephoneList : true
         * alipayCreditImg2 : /IOU/customerinfo/18000000000/26d4035200b247008328b9d7ef05c8da_18000000000.jpg
         * reservePhone : 18000000000
         * kinshipName : 安全
         * alipayCreditImg1 : /IOU/customerinfo/18000000000/0700a054363b4f2bb32ff9fdf0326ef7_18000000000.jpg
         * directKinship : 父母
         * urgentContact : 父母
         * id : 402881ef6975e089016975f3bf5e0001
         * frontIDCard : /IOU/customerinfo/18000000000/bed3f9361d1c4ea693210d0e18292192_18000000000.jpg
         * contactName : 哦可以了
         * userId : 402881ef6975e089016975f3bf270000
         * userName : 18000000000
         * realName : é¿å¦
         * kinshipPhone : 18000889888
         * bankCardNumber : 66666666666
         * idNumber : 142354198901010000
         */

        private String openingBank;
        private String contactPhone;
        private String backIDCard;
        private String telephoneList;
        private String alipayCreditImg2;
        private String reservePhone;
        private String kinshipName;
        private String alipayCreditImg1;
        private String directKinship;
        private String urgentContact;
        private String id;
        private String frontIDCard;
        private String contactName;
        private String userId;
        private String userName;
        private String realName;
        private String kinshipPhone;
        private String bankCardNumber;
        private String idNumber;

        public boolean getIdCardState() {
            return idNumber != null;
        }

        public boolean getFamilyState() {
            return directKinship != null;
        }

        public boolean getBankState() {
            return backIDCard != null;
        }

        public boolean getCreditState() {
            return alipayCreditImg1 != null;
        }

        public boolean getContactsState() {
            return "true".equals(telephoneList);
        }

        public String getOpeningBank() {
            return openingBank;
        }

        public void setOpeningBank(String openingBank) {
            this.openingBank = openingBank;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getBackIDCard() {
            return backIDCard;
        }

        public void setBackIDCard(String backIDCard) {
            this.backIDCard = backIDCard;
        }

        public String getTelephoneList() {
            return telephoneList;
        }

        public void setTelephoneList(String telephoneList) {
            this.telephoneList = telephoneList;
        }

        public String getAlipayCreditImg2() {
            return alipayCreditImg2;
        }

        public void setAlipayCreditImg2(String alipayCreditImg2) {
            this.alipayCreditImg2 = alipayCreditImg2;
        }

        public String getReservePhone() {
            return reservePhone;
        }

        public void setReservePhone(String reservePhone) {
            this.reservePhone = reservePhone;
        }

        public String getKinshipName() {
            return kinshipName;
        }

        public void setKinshipName(String kinshipName) {
            this.kinshipName = kinshipName;
        }

        public String getAlipayCreditImg1() {
            return alipayCreditImg1;
        }

        public void setAlipayCreditImg1(String alipayCreditImg1) {
            this.alipayCreditImg1 = alipayCreditImg1;
        }

        public String getDirectKinship() {
            return directKinship;
        }

        public void setDirectKinship(String directKinship) {
            this.directKinship = directKinship;
        }

        public String getUrgentContact() {
            return urgentContact;
        }

        public void setUrgentContact(String urgentContact) {
            this.urgentContact = urgentContact;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFrontIDCard() {
            return frontIDCard;
        }

        public void setFrontIDCard(String frontIDCard) {
            this.frontIDCard = frontIDCard;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getKinshipPhone() {
            return kinshipPhone;
        }

        public void setKinshipPhone(String kinshipPhone) {
            this.kinshipPhone = kinshipPhone;
        }

        public String getBankCardNumber() {
            return bankCardNumber;
        }

        public void setBankCardNumber(String bankCardNumber) {
            this.bankCardNumber = bankCardNumber;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }
    }
}
