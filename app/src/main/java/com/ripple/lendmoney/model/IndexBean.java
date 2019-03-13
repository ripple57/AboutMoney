package com.ripple.lendmoney.model;

import java.util.ArrayList;

public class IndexBean {

    /**
     * day : 1
     * notify : ["恭喜齐**成功向贺**借款4000","恭喜余**成功向苏**借款23000","恭喜水**成功向米**借款12000","恭喜唐**成功向皮**借款13000","恭喜和**成功向贝**借款0000","恭喜喻**成功向狄**借款19000","恭喜元**成功向袁**借款25000","恭喜戚**成功向柏**借款0000","恭喜鲍**成功向雷**借款13000","恭喜皮**成功向湛**借款9000","恭喜张**成功向郝**借款17000","恭喜吕**成功向云**借款15000","恭喜席**成功向乐**借款11000","恭喜和**成功向华**借款30000","恭喜顾**成功向谈**借款11000","恭喜曹**成功向计**借款14000","恭喜谈**成功向唐**借款5000","恭喜杨**成功向袁**借款7000","恭喜祝**成功向金**借款12000","恭喜曹**成功向云**借款6000","恭喜褚**成功向卞**借款18000","恭喜廉**成功向常**借款5000","恭喜席**成功向于**借款23000","恭喜纪**成功向殷**借款24000","恭喜贺**成功向常**借款14000","恭喜屈**成功向元**借款19000","恭喜章**成功向潘**借款3000","恭喜范**成功向廉**借款15000","恭喜乐**成功向赵**借款14000","恭喜戴**成功向杜**借款9000"]
     */

    private int day;
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

    public void setNotify(ArrayList<String> notify) {
        this.notify = notify;
    }
    public ArrayList<CharSequence> getNotifyData() {
        ArrayList<CharSequence> list = new ArrayList<>();
        for (String s : notify) {
            list.add(s);
        }
        return list;
    }
}
