package com.sawaaid.malltemplate.room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.sawaaid.malltemplate.R;
import com.sawaaid.malltemplate.model.Ads;
import com.sawaaid.malltemplate.model.Product;
import com.sawaaid.malltemplate.model.Section;
import com.sawaaid.malltemplate.model.SubSection;
import com.sawaaid.malltemplate.model.User;


@Database(entities = {Ads.class, Product.class, Section.class, SubSection.class}, version = 1, exportSchema = false)
@TypeConverters({})
public abstract class AppDatabase extends RoomDatabase {

    public abstract DAO get();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDb(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, context.getString(R.string.app_name) + "_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}