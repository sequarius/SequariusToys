package com.sequarius.lightplayer.fragment;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sequarius.lightplayer.MainActivity;
import com.sequarius.lightplayer.R;
import com.sequarius.lightplayer.database.PlayListDAO;
import com.sequarius.lightplayer.object.Song;
import com.sequarius.lightplayer.service.MusicService;

public class PlayListFragment extends Fragment {
	private ListView mListView;
	private List<Song> songs;
	private MyAdapter adapter;
	private MusicService mMusicService;
	private int mId=1;
	private int songCount=0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.layout_playlist, null);
		mListView = (ListView) view.findViewById(R.id.ListView_playlist);
		PlayListDAO listDAO = new PlayListDAO(getActivity());
		songs = listDAO.selectAll();
		songCount=songs.size();
		adapter = new MyAdapter();
		mListView.setAdapter(adapter);
		Intent intent = new Intent("com.sequarius.action.musicservice");
		mMainActivity = (MainActivity) getActivity();
		getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mId=position;
				mMusicService.changePlaying(songs.get(position).getUri());
				mMainActivity.setLrc(songs.get(position).getName());
				// Toast.makeText(getActivity(),songs.get(position).getName(),
				// Toast.LENGTH_SHORT).show();
				changePlayingTittle(position);
			}


		});
		return view;
	}
	/**
	 * 改变正在播放界面的正在播放歌曲的信息
	 * @param position 播放歌曲的列表位置
	 */
	private void changePlayingTittle(int position) {
		String strTemp = songs.get(position).getName();
		// 分割歌名字符串
		String[] info = strTemp.split("-");
		String strPlayer = info[0];
		String strName = "未知歌手";
		if (info.length > 1 && info[1].endsWith("mp3")) {
			
			strName = info[1].replace(".mp3", "");
			
		}else{
			strName=strTemp;
			strPlayer="";
		}
		mMainActivity.changeInfo(strName, strPlayer);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		PlayListDAO listDAO = new PlayListDAO(getActivity());
		songs = listDAO.selectAll();
		// Log.i("mylog", "调用！");
		adapter.notifyDataSetChanged();
		super.onResume();
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return songs.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return songs.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Song song = songs.get(position);
			View view = View.inflate(getActivity(), R.layout.layout_item, null);
			TextView tvName = (TextView) view.findViewById(R.id.textview_name);
			TextView tvID = (TextView) view.findViewById(R.id.textview_id);
			TextView tvPlayer = (TextView) view
					.findViewById(R.id.textview_player);
			String strTemp = song.getName();
			// 分割歌名字符串
			String[] info = strTemp.split("-");
			if (info.length > 1) {
				tvPlayer.setText(info[0]);
				if (info[1].endsWith("mp3")) {
					info[1] = info[1].replace(".mp3", "");
				}
				tvName.setText(info[1]);
			}else{
				tvName.setText(info[0]);
			}
			tvID.setText(song.getId() + "");
			return view;
		}

	}

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			getActivity().unbindService(connection);
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			// 返回一个service对象
			mMusicService = ((MusicService.MyBinder) service).getService();

		}
	};
	private MainActivity mMainActivity;
	/**
	 * 播放列表顺序的下一曲，如果播放到列表末尾，则跳转到第一曲
	 */
	public void playNextSong(){
		if(mId<songCount-1){
		mId++;
		}else{
			mId=1;
		}
		changePlayingTittle(mId);
		mMusicService.changePlaying(songs.get(mId).getUri());
		mMainActivity.setLrc(songs.get(mId).getName());
	}
	/**
	 * 播放列表顺序的上一曲，如果列表到达开头，则跳至最后一曲
	 */
	public void playPreSong(){
		if(mId>1){
			mId--;
		}else{
			mId=songCount-1;
		}
		changePlayingTittle(mId);
		mMusicService.changePlaying(songs.get(mId).getUri());
		
		mMainActivity.setLrc(songs.get(mId).getName());
	}
	/**
	 * 开启应用后初始化Service资源。
	 */
	public void initService(){
		changePlayingTittle(0);
		mMusicService.changePlaying(songs.get(0).getUri());
		mMainActivity.setLrc(songs.get(0).getName());
	}
}
