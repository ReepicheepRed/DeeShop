package com.deeshop.bean;


import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import java.io.Serializable;

/**
 * Created by zhiPeng.S on 2017/3/23.
 */

public class Banner implements Serializable {


    /**
     * returncode : success
     * bannerlist : [{"bannerid":1,"type":0,"img":"https://img20.360buyimg.com/da/jfs/t4696/22/593963225/44887/622d08a7/58d1efecnaa908700.jpg","link":"www.baidu.com"},{"bannerid":2,"type":0,"img":"https://img20.360buyimg.com/da/jfs/t4249/102/1331521120/224199/40dcb547/58c0b221n5acfd3c6.jpg","link":"www.jd.com"},{"bannerid":3,"type":1,"img":"https://img14.360buyimg.com/da/jfs/t4672/333/449064956/118239/10c89d34/58cf9223nf5292b8c.jpg","goodid":13},{"bannerid":4,"type":1,"img":"https://img20.360buyimg.com/da/jfs/t4696/22/593963225/44887/622d08a7/58d1efecnaa908700.jpg","goodid":31}]
     */

    public final ObservableField<String> returncode = new ObservableField<>();
    public final ObservableArrayList<BannerlistBean> bannerlist = new ObservableArrayList<>();

    public static class BannerlistBean implements Serializable{
        /**
         * bannerid : 1
         * type : 0
         * img : https://img20.360buyimg.com/da/jfs/t4696/22/593963225/44887/622d08a7/58d1efecnaa908700.jpg
         * link : www.baidu.com
         * goodid : 13
         */

        public final ObservableField<Integer> bannerid = new ObservableField<>();
        public final ObservableField<Integer> type = new ObservableField<>();
        public final ObservableField<String> img = new ObservableField<>();
        public final ObservableField<String> link = new ObservableField<>();
        public final ObservableField<Integer> goodid = new ObservableField<>();
    }
}
