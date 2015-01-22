package com.devin.messagecenter.fragment;

import com.devin.messagecenter.R;
import com.devin.messagecenter.core.Invoked;
import com.devin.messagecenter.core.MessageCenter;
import com.devin.messagecenter.core.MessageMethod;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment4 extends Fragment {
	
	boolean mHasParams;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MessageCenter.register(this, MessageMethod.NOTIFYFROMFRAGMENT2);
		MessageCenter.register(this, MessageMethod.NOTIFYFROMFRAGMENT3);
	}
	

	@Override
	public void onDestroy() {
		super.onDestroy();
		MessageCenter.unregister(this, MessageMethod.NOTIFYFROMFRAGMENT2);
		MessageCenter.unregister(this, MessageMethod.NOTIFYFROMFRAGMENT3);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment, container, false);
		TextView tv = (TextView) view.findViewById(R.id.name);
		tv.setText("This is Fragment 4");
		tv.setTextColor(Color.BLUE);
		final Button button = (Button) view.findViewById(R.id.button);
		button.setText("override");
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String p = "";
				if (mHasParams) {
					p = "";
					MessageCenter.notify(Fragment1.class, "p1", "p2");
				} else {
					p = ":p1,p2";
					MessageCenter.notify(Fragment1.class);
				}
				button.setText("override" + p);
				mHasParams = !mHasParams;
			}
		});
		return view;
	}

	
	
	@Invoked
	public void notifyFromFragment2() {
		TextView tv = (TextView) getView().findViewById(R.id.panel);
		tv.setText("receive from fragment2");
	}
	
	@Invoked
	public void notifyFromFragment3(String str, int number) {
		TextView tv = (TextView) getView().findViewById(R.id.panel);
		tv.setText(str + ",magic:" + number);
	}
	
}
