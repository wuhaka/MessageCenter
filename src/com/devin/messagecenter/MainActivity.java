package com.devin.messagecenter;

import com.devin.messagecenter.fragment.Fragment1;
import com.devin.messagecenter.fragment.Fragment2;
import com.devin.messagecenter.fragment.Fragment3;
import com.devin.messagecenter.fragment.Fragment4;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.fragment1, new Fragment1());
		ft.add(R.id.fragment2, new Fragment2());
		ft.add(R.id.fragment3, new Fragment3());
		ft.add(R.id.fragment4, new Fragment4());
		ft.commit();
	}

}
