package com.sequarius.lightplayer;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.sequarius.lightplayer.database.PlayListDAO;
import com.sequarius.lightplayer.tools.FileFilterBySuffix;
import com.sequarius.lightplayer.tools.Queue;

public class SearchMusicActivity extends Activity {
	PlayListDAO mPlayListDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mPlayListDAO = new PlayListDAO(this);
		setContentView(R.layout.layout_search_music);
		Button button = (Button) findViewById(R.id.button_search_music);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int count = searchMusicFile();
				Toast.makeText(SearchMusicActivity.this,
						"扫描完毕,共找到" + count + "个音乐文件。", Toast.LENGTH_SHORT)
						.show();
				finish();
			}
		});
	}

	/**
	 * 遍历SD卡所有MP3文件
	 * 
	 * @return 找到的MP3数量
	 */

	public int searchMusicFile() {

		// 扫描目录
		File dir = new File("sdcard/");
		// 过滤器
		FileFilter filter = new FileFilterBySuffix("mp3");
		// 储存符合要求的文件
		List<File> list = new ArrayList<File>();
		// getFileList(dir, filter, list);

		Queue<File> queue = new Queue<File>();
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				queue.myAdd(file);
			} else {
				if (filter.accept(file)) {
					list.add(file);
				}
			}

		}
		while (!queue.isNull()) {
			File subDir = queue.myGet();
			File[] subFiles = subDir.listFiles();
			if (subFiles != null) {
				for (File file : subFiles) {
					if (file.isDirectory()) {
						queue.myAdd(file);
					} else {
						if (filter.accept(file)) {
							list.add(file);
						}
					}

				}
			}
		}
		mPlayListDAO.clear();
		for (File file : list) {
			mPlayListDAO.add(file.getName(), file.getAbsolutePath());
		}
		return list.size();
	}

	// 以队列方式递归
	/*
	 * public void getFileList(File dir, FileFilter filter, List<File> list) {
	 * // TODO Auto-generated method stub File[] files = dir.listFiles(); for
	 * (File file : files) { if (file.isDirectory()) { getFileList(file, filter,
	 * list); } else { if (filter.accept(file)) { list.add(file); } } } }
	 */

}
