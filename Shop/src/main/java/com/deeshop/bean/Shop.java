package com.deeshop.bean;

import android.databinding.ObservableField;

import java.io.Serializable;

/**
 * Created by zhiPeng.S on 2017/3/27.
 */

public class Shop implements Serializable {
    public static class Info implements Serializable{
        public final ObservableField<String> title = new ObservableField<>();
        public final ObservableField<String> content = new ObservableField<>();
        public final ObservableField<String> avatar = new ObservableField<>();
    }

    /**
     * returncode : success
     * shopdetial : {"id":1,"shopid":"ym-1000","shoplogo":"/images/201703/10/1216444335.png","storename":"测试","introduction":"welcome","authentication":1,"phone":"18858260343"}
     */

    public final ObservableField<String> returncode = new ObservableField<>();
    public ShopdetialBean shopdetial;

    public static class ShopdetialBean implements Serializable{
        /**
         * id : 1
         * shopid : ym-1000
         * shoplogo : /images/201703/10/1216444335.png
         * storename : 测试
         * introduction : welcome
         * authentication : 1
         * phone : 18858260343
         */

        public final ObservableField<Integer> id = new ObservableField<>();
        public final ObservableField<String> shopid = new ObservableField<>();
        public final ObservableField<String> shoplogo = new ObservableField<>();
        public final ObservableField<String> storename = new ObservableField<>();
        public final ObservableField<String> introduction = new ObservableField<>();
        public final ObservableField<Integer> authentication = new ObservableField<>();
        public final ObservableField<Integer> authtype = new ObservableField<>();
        public final ObservableField<String> authimg = new ObservableField<>();
        public final ObservableField<String> phone = new ObservableField<>();
        public final ObservableField<String> facebook = new ObservableField<>();
        public final ObservableField<String> line = new ObservableField<>();
    }
}
