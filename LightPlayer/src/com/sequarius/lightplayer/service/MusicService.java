package com.sequarius.lightplayer.service;

import java.io.IOException;

import com.sequarius.lightplayer.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;

/**
 * 后台音乐播放服务，确保activity在非活动状态可以正常播放音乐
 * 
 * @author Sequarius
 *
 */
public class MusicService extends Service {
	// 进度条最大值
	public static int MAX_PROGRESS = 0;
	// 当前进度值
	private int progress = 0;
	// 进度的回调接口
	private OnProgressListener mOnProgressListener;

	/**
	 * 注册回调方法，用于外部调用
	 * 
	 * @param onProgressListener
	 */
	public void setOnProgressListner(OnProgressListener onProgressListener) {
		mOnProgressListener = onProgressListener;
	}

	/**
	 * 用于给Activity调用的获取进度的方法
	 * 
	 * @return 当前播放进度
	 */
	public int getProgress() {
		return progress;
	}

	/**
	 * 继续播放service中的音乐
	 */
	public void continuePlay() {
		if (mMediaPlayer != null && (!mMediaPlayer.isPlaying())) {
			mMediaPlayer.start();
		}
	}

	/**
	 * 暂停播放service的音乐
	 */
	public void pausePlay() {
		if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
			mMediaPlayer.pause();
			Log.i("mylog", currentTime() + "---------" + totalTime());
		}
	}

	public void Playing() {
		mMediaPlayer.start();
	}

	public boolean isNull() {
		return mMediaPlayer == null ? true : false;
	}

	/**
	 * 切换服务中播放的歌曲
	 * 
	 * @param uri
	 *            歌曲的绝对路径
	 */
	public void changePlaying(String uri) {
		try {
			if (mMediaPlayer == null) {
				mMediaPlayer = new MediaPlayer();
			}
			mMediaPlayer.pause();
			mMediaPlayer.reset();// 释放之前资源
			mMediaPlayer.setDataSource(uri);
			mMediaPlayer.prepare();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mMediaPlayer.start();
	}

	private MediaPlayer mMediaPlayer;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		seekBarCallBack();
		super.onCreate();
		// mMediaPlayer = MediaPlayer.create(this, R.raw.test);
	}

	public boolean isPlaying() {
		return mMediaPlayer.isPlaying();
	}

	public class MyBinder extends Binder {
		/**
		 * 获取当前Service的实例
		 * 
		 * @return
		 */
		public MusicService getService() {
			return MusicService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new MyBinder();
	}

	private String totalTime() {
		int time = mMediaPlayer.getDuration();
		return timeToString(time);
	}

	private String currentTime() {
		int time = mMediaPlayer.getCurrentPosition();
		return timeToString(time);
	}

	private String timeToString(int time) {
		time /= 1000;
		int minutes = time / 60;
		int seconds = time % 60;
		return seconds < 10 ? (minutes + ":0" + seconds)
				: (minutes + ":" + seconds);

	}

	public void setProgress(int positon) {
		mMediaPlayer.seekTo(positon);
		progress = positon;
	}

	/**
	 * playing界面seekbar更新
	 */
	private void seekBarCallBack() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {

					// 进度发生变化通知调用方
					if (mOnProgressListener != null && mMediaPlayer != null) {
						MAX_PROGRESS = mMediaPlayer.getDuration();
						progress = mMediaPlayer.getCurrentPosition();
						mOnProgressListener.onProgress(progress, MAX_PROGRESS);
					}

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
	}

}
