package com.sequarius.lightplayer.object;
/**
 * 歌曲类 用于封装歌曲信息
 * @author Sequarius
 *
 */
public class Song {
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUri() {
		return Uri;
	}
	public void setUri(String uri) {
		Uri = uri;
	}
	public Song() {
	}
	public Song(int id, String name, String uri) {
		super();
		this.id = id;
		this.name = name;
		Uri = uri;
	}
	private String Uri;
	
}
