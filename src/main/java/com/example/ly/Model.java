package com.example.ly;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @description: model
 * @author: ly
 * @create: 2024-07-20 15:17
 **/
@TableName("ACT_RE_MODEL")
public class Model {

    @TableField("ID_")
    private String ID_;
    private String key_;

    public String getID_() {
        return ID_;
    }

    public void setID_(String ID_) {
        this.ID_ = ID_;
    }

    public String getKey_() {
        return key_;
    }

    public void setKey_(String key_) {
        this.key_ = key_;
    }
}
