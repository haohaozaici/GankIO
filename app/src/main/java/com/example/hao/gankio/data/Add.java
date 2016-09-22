package com.example.hao.gankio.data;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Unique;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by hao on 2016-09-22.
 */

public class Add implements Serializable {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    public long id;

    @NotNull
    @Unique
    @Column("objectId")
    public String objectId;

}
