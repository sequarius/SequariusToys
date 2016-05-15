package com.sequarius.lightplayer.database;

import java.util.ArrayList;
import java.util.List;

import com.sequarius.lightplayer.object.Song;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PlayListDAO {
	private PlayListSQLiteOpenHelper helper;
	public PlayListDAO(Context context){
		helper =new PlayListSQLiteOpenHelper(context);
	}
	/**
	 * 添加一条记录
	 * @param name 文件名
	 * @param uri  文件地址
	 */
	public void add(String name,String uri){
		SQLiteDatabase db=helper.getWritableDatabase();
		db.execSQL("insert into playlist (name,uri) values(?,?)",new Object[]{name,uri});
		db.close();
	}
	/**
	 * 查询所有歌曲列表
	 * @return 所有在库歌曲
	 */
	public List<Song> selectAll(){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor cursor=db.rawQuery("select * from playlist", null);
		List<Song> songs=new ArrayList<Song>();
		while(cursor.moveToNext()){
			int id=cursor.getInt(cursor.getColumnIndex("id"));
			String name=cursor.getString(cursor.getColumnIndex("name"));
			String uri=cursor.getString(cursor.getColumnIndex("uri"));
			Song song=new Song(id, name,uri );
			songs.add(song);
		}
		cursor.close();
		db.close();
		return songs;
	}
	public void clear(){
		SQLiteDatabase db=helper.getWritableDatabase();
		db.execSQL("delete from playlist");//清空表
		db.execSQL("update sqlite_sequence set seq=0 where name='playlist'");//重置id自增段
		db.close();
	}
}
