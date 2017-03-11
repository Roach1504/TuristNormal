package com.example.roach12.turistnormal.dateBase;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreatDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "BaikalPolice";

    public static final String TABLE_TREAKS = "treaks";
    public static final String TABLE_paint = "paint";
    public static final String TABLE_message = "message";
    public static final String TABLE_CATEG = "categorii";
    public static final String TABLE_USER = "user";
    public static final String TABLE_USER_info = "user_info";

    public static final String trackFildTrackID = "track_id";
    public static final String trackFildPoint_X = "x";
    public static final String trackFildPoint_Y = "y";
    public static final String trackFildDate = "date";
    public static final String trackFildMarker = "marker";

    public static final String paintFildX = "x";
    public static final String paintFildY = "y";

    public static final String messageFildX = "x";
    public static final String messageFildY = "y";
    public static final String messageFildText = "text";
    public static final String messageFildCateg = "categ_id";
    public static final String messageFildUserId = "user_id";
    public static final String messageFildImage = "image_in_base64";

    public static final String categFildId = "categ_id";
    public static final String categFildName = "categ_name";

    public static final String userFildName = "fio";
    public static final String userFildId = "user_id";

    public static final String userInfoFildPhone = "phone";
    public static final String userInfoFildSocial = "social";
    public static final String userInfoFildemail = "email";

    public CreatDB(Context context, String name, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_TREAKS + "("//таблица трека
                + trackFildTrackID + " text,"
                + trackFildPoint_X + " text,"
                + trackFildPoint_Y + " text,"
                + trackFildDate + " text,"
                + trackFildMarker + " text" +");");

        db.execSQL("create table " + TABLE_paint + "(" //таблица отрисовки маршрута
                + paintFildX + " text,"
                + paintFildY + " text" +");");

        db.execSQL("create table " + TABLE_message + "("//таблица отправки сообщения
                + messageFildX + " text,"
                + messageFildY + " text,"
                + messageFildText + " text,"
                + messageFildCateg + " text,"
                + messageFildUserId + " text,"
                + messageFildImage + " text" +");");

        db.execSQL("create table " + TABLE_CATEG + "(" //таблица категорий, для отправки сообщений
                + categFildId + " text,"
                + categFildName + " text" +");");

        db.execSQL("create table " + TABLE_USER + "(" //таблица информации о профиле
                + userFildName + " text,"
                + userFildId + " text" +");");

        db.execSQL("create table " + TABLE_USER_info + "(" //таблица контактной информации о профиле
                + userInfoFildPhone + " text,"
                + userInfoFildSocial + " text,"
                + userInfoFildemail + " text" +");");
    }
    String[] dat;
    String s = "select sysdate from dual";
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_TREAKS);
        db.execSQL("drop table if exists " + TABLE_paint);
        db.execSQL("drop table if exists " + TABLE_message);
        db.execSQL("drop table if exists " + TABLE_CATEG);
        db.execSQL("drop table if exists " + TABLE_USER);
        db.execSQL("drop table if exists " + TABLE_USER_info);
        onCreate(db);
        db.rawQuery(s, dat);
    }
}
