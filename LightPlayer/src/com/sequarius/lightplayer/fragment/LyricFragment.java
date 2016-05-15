package com.sequarius.lightplayer.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sequarius.lightplayer.R;
import com.sequarius.lightplayer.object.LrcParse;

public class LyricFragment extends Fragment {
	private TreeMap<Integer, String> mInfoTree;
	private List<String> mLrcs;
	private ListView mListView;
	private int[] mTime = new int[200];
	private MyAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.layout_lyric, null);
		mListView = (ListView) view.findViewById(R.id.ListView_lyric);
		mLrcs = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			mLrcs.add("");
		}
		mAdapter = new MyAdapter();
		mListView.setAdapter(mAdapter);

		return view;
	}
	private class MyAdapter extends BaseAdapter {
		public int currentPosition = 0;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mLrcs.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mLrcs.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			TextView textView = new TextView(getActivity());
			textView.setTextSize(20);
			if (currentPosition == position) {
				textView.setTextColor(Color.WHITE);
				textView.setTextSize(23);
			} else {
				textView.setTextColor(Color.GRAY);
			}
			textView.setGravity(Gravity.CENTER);
			String strLrc = mLrcs.get(position);
			textView.setText(strLrc);
			return textView;
		}
	}
	/**
	 * 改变lrc的显示位置	
	 * @param position 高亮的位置
	 */
	public void changeLrcPostion(int position) {
		mListView.smoothScrollToPosition(position + 18);
		mAdapter.currentPosition = position + 9;
		mAdapter.notifyDataSetChanged();

	}
	public void setLrc(String strFilePath){
		String lrcsPath="/sdcard/lrc/"+strFilePath;
		Log.i("myLog", lrcsPath);
		File file = new File(lrcsPath);
		LrcParse lrc = null;
		mLrcs.clear();
		try {
			lrc = new LrcParse(file);
			mInfoTree = lrc.getLrc();
			for (int i = 0; i < 10; i++) {
				mLrcs.add("");
			}
			int index = 0;
			for (Map.Entry<Integer, String> me : mInfoTree.entrySet()) {
				int time = me.getKey();
				String strLrc = me.getValue();
				mTime[index++] = time;
				mLrcs.add(strLrc);
				if (index == mInfoTree.size()) {
					for (int i = 200 - 1; i >= mInfoTree.size(); i--) {
						mTime[i] = time;
					}
				}
			}
			for (int i = 0; i < 10; i++) {
				mLrcs.add("");
			}
			mAdapter.notifyDataSetChanged();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			for (int i = 0; i < 8; i++) {
				mLrcs.add("");
			}
			mLrcs.add("未找到歌词");
			for (int i = 0; i < mTime.length; i++) {
				
				mTime[i]=0;
			}
			mAdapter.notifyDataSetChanged();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 获取歌词的时间轴
	 * @return 歌词的毫秒时间轴
	 */
	public int[] getTimerShaft() {
		return mTime;
	}
}
