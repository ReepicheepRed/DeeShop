package com.deeshop.bean;

import android.databinding.ObservableField;

import java.io.Serializable;

/**
 * Created by zhiPeng.S on 2017/3/23.
 */

public class User implements Serializable {

    /**
     * returncode : success
     * memberinfo : {"id":1,"userID":"10000","mobile":"18858260343","nickName":"vane","headImgUrl":"192.168.126.63:8101/upload/user05.png"}
     */

    public ObservableField<String> returncode = new ObservableField<>();

    public ObservableField<String> msg = new ObservableField<>();

    public Info memberinfo;

    public static class Info implements Serializable{
        /**
         * id : 1
         * userID : 10000
         * mobile : 18858260343
         * nickName : vane
         * headImgUrl : 192.168.126.63:8101/upload/user05.png
         */

        public ObservableField<Integer> id = new ObservableField<>();
        public ObservableField<String> userid = new ObservableField<>();
        public ObservableField<String> mobile = new ObservableField<>();
        public ObservableField<String> nickname = new ObservableField<>("");
        public ObservableField<String> headimgurl = new ObservableField<>("");
        public ObservableField<String> shop = new ObservableField<>();
        public ObservableField<String> tipscount = new ObservableField<>();

    }
}
