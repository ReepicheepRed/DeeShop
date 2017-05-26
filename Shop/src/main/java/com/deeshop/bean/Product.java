package com.deeshop.bean;

import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhiPeng.S on 2017/3/27.
 */

public class Product implements Serializable {

    /**
     * returncode : success
     * goodlist : [{"id":57,"goodid":"34433222","goodname":"测试数据","type":3,"shopid":1,"state":3,"isdistribution":1,"createtime":"2017-03-16t10:31:34.067","userid":1,"price":"33333","profit":"333","goodimgurl":"/images//20170316-3.png","isreview":1,"isdelete":0},{"id":56,"goodid":"467896","goodname":"中国红","type":3,"shopid":1,"state":3,"isdistribution":1,"createtime":"2017-03-16t10:24:23.34","userid":1,"price":"333","profit":"333","goodimgurl":"system.web.httppostedfilewrapper","isreview":1,"isdelete":0},{"id":55,"goodid":"32132145666","goodname":"65656","type":2,"shopid":1,"state":3,"isdistribution":1,"createtime":"2017-03-16t10:21:16.12","userid":1,"price":"6565","profit":"6565","goodimgurl":"system.web.httppostedfilewrapper","isreview":1,"isdelete":0},{"id":54,"goodid":"896754","goodname":"654654","type":2,"shopid":1,"state":3,"isdistribution":0,"createtime":"2017-03-16t10:20:34.29","userid":1,"price":"65","profit":"1","isreview":1,"isdelete":0},{"id":53,"goodid":"0101010101","goodname":"你不知道我为什么离开你","type":2,"shopid":1,"state":3,"isdistribution":0,"createtime":"2017-03-16t10:13:45.723","userid":1,"price":"3445","profit":"555","goodimgurl":"system.web.httppostedfilewrapper","isreview":1,"isdelete":0}]
     */

    public final ObservableField<String> returncode = new ObservableField<>();
    public final ObservableField<String> msg = new ObservableField<>();
    public ObservableArrayList<GoodlistBean> goodlist = new ObservableArrayList<>();

    public static class GoodlistBean implements Serializable{


        /**
         * id : 57
         * goodid : 34433222
         * goodname : 测试数据
         * type : 3
         * shopid : 1
         * state : 3
         * isdistribution : 1
         * createtime : 2017-03-16t10:31:34.067
         * userid : 1
         * price : 33333
         * profit : 333
         * goodimgurl : /images//20170316-3.png
         * isreview : 1
         * isdelete : 0
         */



        public final ObservableField<Integer> id = new ObservableField<>();
        public final ObservableField<String> goodid = new ObservableField<>();
        public final ObservableField<String> goodname = new ObservableField<>();
        public final ObservableField<Integer> goodtype = new ObservableField<>();
        public final ObservableField<String> typename = new ObservableField<>();
        public final ObservableField<Integer> shopid = new ObservableField<>();
        public final ObservableField<Integer> state = new ObservableField<>();
        public final ObservableField<Integer> isdistribution = new ObservableField<>();
        public final ObservableField<String> createtime = new ObservableField<>();
        public final ObservableField<Integer> userid = new ObservableField<>();
        public final ObservableField<String> price = new ObservableField<>();
        public final ObservableField<String> profit = new ObservableField<>();
        public final ObservableField<String> goodimgurl = new ObservableField<>();
        public final ObservableField<Integer> isreview = new ObservableField<>();
        public final ObservableField<Integer> isdelete = new ObservableField<>();
        //4-11
        public final ObservableField<String> offsale = new ObservableField<>();
        public final ObservableField<String> line = new ObservableField<>();
        public final ObservableField<String> link = new ObservableField<>();
        public final ObservableField<String> imgcount = new ObservableField<>();
        public final ObservableField<String> introduction = new ObservableField<>("");

        public final ObservableArrayList<Images> goodimgs = new ObservableArrayList<>();

        public static class Images implements Serializable{
            /**
             * path : http://192.168.126.63:8101/images/201703/29/0938405249.png
             */
            public final ObservableField<String> path = new ObservableField<>();
        }
    }
}
