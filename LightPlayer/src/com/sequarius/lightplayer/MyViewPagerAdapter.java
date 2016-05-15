package com.sequarius.lightplayer;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyViewPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> mFragmentList;
	private List<String> mTittleList;
	public MyViewPagerAdapter(FragmentManager fm,List<Fragment> fragemts,List<String> titlelists) {
		super(fm);
		mFragmentList=fragemts;
		mTittleList=titlelists;
		// TODO Auto-generated constructor stub
	}


	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return mFragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFragmentList.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return mTittleList.get(position);
	}

}
