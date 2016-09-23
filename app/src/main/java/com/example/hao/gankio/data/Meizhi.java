package com.example.hao.gankio.data;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Table;

import java.util.Date;

/**
 * Created by hao on 2016-09-22.
 */

@Table("meizhi")
public class Meizhi extends Add{
    @Column("url") public String url;
    @Column("type") public String type;
    @Column("type") public String desc;
    @Column("publishedAt") public Date publishedAt;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /*"_id": "57e1c76c421aa95bd05015f2",
            "createdAt": "2016-09-21T07:34:04.60Z",
            "desc": "9-21",
            "publishedAt": "2016-09-21T11:37:24.210Z",
            "source": "chrome",
            "type": "福利",
            "url": "http://ww3.sinaimg.cn/large/610dc034jw1f80uxtwgxrj20u011hdhq.jpg",
            "used": true,
            "who": "daimajia"*/
}
