package com.sequarius.lightplayer;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sequarius.lightplayer.fragment.LyricFragment;
import com.sequarius.lightplayer.fragment.PlayListFragment;
import com.sequarius.lightplayer.fragment.PlayingFragment;

public class MainActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private LyricFragment mLyricFragment;
	private PlayingFragment mPlayingFragment;
	private PlayListFragment mPlayListFragment;
	// 标签列表
	private List<Fragment> mfragmentList;
	// 标题列表
	private List<String> mTitleList;
	// 标题属性的设置对象
	private PagerTabStrip mPagerTabStrip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initActivityLayout();

	}

	private void initActivityLayout() {
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mViewPager.setOffscreenPageLimit(5);//viewpager的最大缓存数
		mPagerTabStrip = (PagerTabStrip) findViewById(R.id.pagetittle);
		// 初始化Fragment
		mPagerTabStrip.setBackgroundColor(0xFF333333);
		mPagerTabStrip.setTabIndicatorColor(Color.WHITE);
		mPagerTabStrip.setTextColor(Color.WHITE);
		mLyricFragment = new LyricFragment();
		mPlayingFragment = new PlayingFragment();
		mPlayListFragment = new PlayListFragment();

		// 初始化List
		mfragmentList = new ArrayList<Fragment>();
		mTitleList = new ArrayList<String>();

		mfragmentList.add(mPlayingFragment);
		mfragmentList.add(mPlayListFragment);
		mfragmentList.add(mLyricFragment);

		mTitleList.add("正在播放");
		mTitleList.add("播放列表");
		mTitleList.add("歌词");
		FragmentManager manager = getSupportFragmentManager();
		mViewPager.setAdapter(new MyViewPagerAdapter(manager, mfragmentList,
				mTitleList));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.meue_action_add:
			Intent intent = new Intent(MainActivity.this,
					SearchMusicActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 改变正在播放界面的歌曲摘要，供其他fragment通过activity调用
	 * 
	 * @param name
	 *            歌曲名
	 * @param player
	 *            歌手名
	 */
	public void changeInfo(String name, String player) {
		mPlayingFragment.changeInfo(name, player);
	}

	/*
	 * 播放下一曲，供其他fragment通过activity调用
	 */
	public void playNext() {
		mPlayListFragment.playNextSong();
	}

	/*
	 * 播放上一曲，供其他fragment通过activity调用
	 */
	public void playPre() {
		mPlayListFragment.playPreSong();
	}
	/**
	 * 初始化播放服务,供其他fragement调用
	 */
	public void InitServiceRes() {
		mPlayListFragment.initService();
	}
	/**
	 * 滚动歌词位置
	 * @param position 当前句所在的position
	 */
	public void lrcScroll(int position){
		mLyricFragment.changeLrcPostion(position);
	}
	public int[] getTimeShaft(){
		return mLyricFragment.getTimerShaft();
	}
	public void setLrc(String songName){
		String strLrcPath=songName.replace("mp3", "lrc");
		mLyricFragment.setLrc(strLrcPath);
	}
}
