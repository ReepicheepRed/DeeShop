package com.deeshop.bean;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhiPeng.S on 2017/4/1.
 */

public class Category implements Serializable {

    /**
     * returncode : success
     * goodtypes : [{"tyid":1,"name":"未分类"},{"tyid":2,"name":"男人"},{"tyid":3,"name":"女人"},{"tyid":4,"name":"儿童"},{"tyid":5,"name":"化妆品"},{"tyid":6,"name":"其它"}]
     */

    public final ObservableField<String> returncode = new ObservableField<>();
    public final ObservableArrayList<GoodtypesBean> goodtypes = new ObservableArrayList<>();

    public static class GoodtypesBean implements Serializable{
        /**
         * tyid : 1
         * name : 未分类
         */

        public final ObservableField<Integer> tyid = new ObservableField<>();
        public final ObservableField<String> name = new ObservableField<>();
        public final ObservableField<Boolean> isSelect = new ObservableField<>();
    }
}
