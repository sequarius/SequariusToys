package com.sequarius.lightplayer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class PlayListSQLiteOpenHelper extends SQLiteOpenHelper {
	
	/*
	 * 数据库构造
	 */
	public PlayListSQLiteOpenHelper(Context context) {
		super(context, "playlist.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table playlist (id integer primary key autoincrement,name varchar(20),uri varchar(20))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
