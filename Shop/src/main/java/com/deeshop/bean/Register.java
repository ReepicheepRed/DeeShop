package com.deeshop.bean;

import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import java.io.Serializable;

/**
 * Created by zhiPeng.S on 2017/3/23.
 */

public class Register implements Serializable {
    public final ObservableField<String> phoneNum = new ObservableField<>();
    public ObservableBoolean enabled = new ObservableBoolean(false);

    public Register() {
        phoneNum.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if(observable == phoneNum){
                    enabled.set(phoneNum.get().length() >=10);
                }
            }
        });
    }


    
    /**
     * returncode : success
     * memberinfo : {"id":13,"userid":"10009","userguid":"bb284b4d-0e3a-4fbb-b254-75272fa49dc3","mobile":"18550350709","password":"e10adc3949ba59abbe56e057f20f883e","isactive":1,"lastlogintime":"2017-04-06t13:51:07.6857651+08:00","lastloginip":"192.168.126.104","createtime":"2017-04-06t13:51:07.6857651+08:00","createip":"192.168.126.104","isbanned":0,"isreview":1,"isdelete":0}
     */

    public final ObservableField<String> returncode = new ObservableField<>();
    public final ObservableField<String> msg = new ObservableField<>();

    public MemberinfoBean memberinfo;

    public static class MemberinfoBean {
        /**
         * id : 13
         * userid : 10009
         * userguid : bb284b4d-0e3a-4fbb-b254-75272fa49dc3
         * mobile : 18550350709
         * password : e10adc3949ba59abbe56e057f20f883e
         * isactive : 1
         * lastlogintime : 2017-04-06t13:51:07.6857651+08:00
         * lastloginip : 192.168.126.104
         * createtime : 2017-04-06t13:51:07.6857651+08:00
         * createip : 192.168.126.104
         * isbanned : 0
         * isreview : 1
         * isdelete : 0
         */

        public final ObservableField<Integer> id = new ObservableField<>();
        public final ObservableField<String> userid = new ObservableField<>();
        public final ObservableField<String> userguid = new ObservableField<>();
        public final ObservableField<String> mobile = new ObservableField<>();
        public final ObservableField<String> password = new ObservableField<>();
        public final ObservableField<Integer> isactive = new ObservableField<>();
        public final ObservableField<String> lastlogintime = new ObservableField<>();
        public final ObservableField<String> lastloginip = new ObservableField<>();
        public final ObservableField<String> createtime = new ObservableField<>();
        public final ObservableField<String> createip = new ObservableField<>();
        public final ObservableField<Integer> isbanned = new ObservableField<>();
        public final ObservableField<Integer> isreview = new ObservableField<>();
        public final ObservableField<Integer> isdelete = new ObservableField<>();
    }
}
