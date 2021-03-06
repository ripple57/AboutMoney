package com.ripple.lendmoney.model;

import java.util.ArrayList;

public class IndexBean {

    /**
     * data : {"day":1,"notify":["恭喜潘**成功向云**借款2000","恭喜袁**成功向湛**借款22000","恭喜伍**成功向罗**借款10000","恭喜杜**成功向潘**借款25000","恭喜萧**成功向宋**借款30000","恭喜董**成功向严**借款7000","恭喜计**成功向邹**借款9000","恭喜蓝**成功向殷**借款10000","恭喜汪**成功向卞**借款17000","恭喜秦**成功向廉**借款6000","恭喜陈**成功向齐**借款28000","恭喜禹**成功向于**借款10000","恭喜花**成功向纪**借款11000","恭喜吕**成功向禹**借款6000","恭喜纪**成功向祁**借款21000","恭喜柳**成功向席**借款17000","恭喜韩**成功向凤**借款24000","恭喜孟**成功向吕**借款6000","恭喜祝**成功向褚**借款0000","恭喜湛**成功向范**借款25000","恭喜孔**成功向计**借款11000","恭喜俞**成功向乐**借款16000","恭喜曹**成功向祝**借款9000","恭喜祁**成功向项**借款12000","恭喜齐**成功向曹**借款26000","恭喜金**成功向平**借款11000","恭喜陈**成功向周**借款8000","恭喜倪**成功向周**借款5000","恭喜姚**成功向伏**借款13000","恭喜齐**成功向毛**借款28000"]}
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
         * day : 1
         * notify : ["恭喜潘**成功向云**借款2000","恭喜袁**成功向湛**借款22000","恭喜伍**成功向罗**借款10000","恭喜杜**成功向潘**借款25000","恭喜萧**成功向宋**借款30000","恭喜董**成功向严**借款7000","恭喜计**成功向邹**借款9000","恭喜蓝**成功向殷**借款10000","恭喜汪**成功向卞**借款17000","恭喜秦**成功向廉**借款6000","恭喜陈**成功向齐**借款28000","恭喜禹**成功向于**借款10000","恭喜花**成功向纪**借款11000","恭喜吕**成功向禹**借款6000","恭喜纪**成功向祁**借款21000","恭喜柳**成功向席**借款17000","恭喜韩**成功向凤**借款24000","恭喜孟**成功向吕**借款6000","恭喜祝**成功向褚**借款0000","恭喜湛**成功向范**借款25000","恭喜孔**成功向计**借款11000","恭喜俞**成功向乐**借款16000","恭喜曹**成功向祝**借款9000","恭喜祁**成功向项**借款12000","恭喜齐**成功向曹**借款26000","恭喜金**成功向平**借款11000","恭喜陈**成功向周**借款8000","恭喜倪**成功向周**借款5000","恭喜姚**成功向伏**借款13000","恭喜齐**成功向毛**借款28000"]
         */

        private int day;
        private float price;
        private ArrayList<String> notify;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public ArrayList<String> getNotify() {
            return notify;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public void setNotify(ArrayList<String> notify) {
            this.notify = notify;
        }
    }
}
