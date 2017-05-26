package com.deeshop.bean;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import java.io.Serializable;

/**
 * Created by zhiPeng.S on 2017/3/29.
 */

public class Detail implements Serializable {

    /**
     * returncode : success
     * gooddetial : {"id":3,"goodid":3,"goodimg":[{"imgid":10,"sort":1,"path":"http://192.168.126.63:8101/images/3/1.png"},{"imgid":11,"sort":2,"path":"http://192.168.126.63:8101/images/3/2.png"},{"imgid":12,"sort":3,"path":"http://192.168.126.63:8101/images/3/3.png"},{"imgid":13,"sort":4,"path":"http://192.168.126.63:8101/images/3/4.png"}],"goodname":"zara 男装 混纺运动裤 07248304802","introduction":"上市年份季节\t 2016年冬季","price":"199.0","profit":"9.9","shopid":1,"shopname":"myshop","shopimg":"http://192.168.126.63:8101/images/201703/24/1401483739.png","isauth":1,"item":26,"discount":0}
     */

    public final ObservableField<String> returncode = new ObservableField<>();
    public GooddetialBean gooddetial;

    public static class GooddetialBean implements Serializable{
        /**
         * id : 3
         * goodid : 3
         * goodimg : [{"imgid":10,"sort":1,"path":"http://192.168.126.63:8101/images/3/1.png"},{"imgid":11,"sort":2,"path":"http://192.168.126.63:8101/images/3/2.png"},{"imgid":12,"sort":3,"path":"http://192.168.126.63:8101/images/3/3.png"},{"imgid":13,"sort":4,"path":"http://192.168.126.63:8101/images/3/4.png"}]
         * goodname : zara 男装 混纺运动裤 07248304802
         * introduction : 上市年份季节2016年冬季
         * price : 199.0
         * profit : 9.9
         * shopid : 1
         * shopname : myshop
         * shopimg : http://192.168.126.63:8101/images/201703/24/1401483739.png
         * isauth : 1
         * item : 26
         * discount : 0
         */
        public final ObservableField<CharSequence> introHtml = new ObservableField<>();

        public final ObservableField<Integer> id = new ObservableField<>();
        public final ObservableField<Integer> goodid = new ObservableField<>();
        public final ObservableField<String> goodname = new ObservableField<>();
        public final ObservableField<String> introduction = new ObservableField<>();
        public final ObservableField<String> price = new ObservableField<>();
        public final ObservableField<String> profit = new ObservableField<>();
        public final ObservableField<Integer> shopid = new ObservableField<>();
        public final ObservableField<String> shopname = new ObservableField<>();
        public final ObservableField<String> shopimg = new ObservableField<>();
        public final ObservableField<Integer> isauth = new ObservableField<>();
        public final ObservableField<Integer> item = new ObservableField<>();
        public final ObservableField<Integer> discount = new ObservableField<>();
        public final ObservableField<Integer> gooddiscount = new ObservableField<>();
        public final ObservableField<String> shopphone = new ObservableField<>();
        public final ObservableField<String> facebook = new ObservableField<>();
        public final ObservableField<String> line = new ObservableField<>();
        public final ObservableField<Integer> exist = new ObservableField<>();
        public ObservableArrayList<GoodimgBean> goodimg = new ObservableArrayList<>();

        public static class GoodimgBean implements Serializable{
            /**
             * imgid : 10
             * sort : 1
             * path : http://192.168.126.63:8101/images/3/1.png
             */

            public final ObservableField<Integer> imgid = new ObservableField<>();
            public final ObservableField<Integer> sort = new ObservableField<>();
            public final ObservableField<String> path = new ObservableField<>();
        }
    }
}
