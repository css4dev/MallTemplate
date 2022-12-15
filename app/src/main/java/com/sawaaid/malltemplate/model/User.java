package com.sawaaid.malltemplate.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "users")
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id1;

    public int id, accountTypeId;
    public String firstName, lastName, password, loginName, phoneNumber, hashToken;

}
