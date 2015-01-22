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

public class Fragment3 extends Fragment {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MessageCenter.register(this, MessageMethod.NOTIFYFROMFRAGMENT2);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		MessageCenter.unregister(this, MessageMethod.NOTIFYFROMFRAGMENT2);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment, container, false);
		TextView tv = (TextView) view.findViewById(R.id.name);
		tv.setText("This is Fragment 3");
		tv.setTextColor(Color.BLUE);
		Button button = (Button) view.findViewById(R.id.button);
		button.setText("3->1,2,4 magic: 9");
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MessageCenter.notify(MessageMethod.NOTIFYFROMFRAGMENT3, "3->1,2,4", 9);
			}
		});
		return view;
	}

	
	@Invoked
	public void notifyFromFragment2() {
		TextView tv = (TextView) getView().findViewById(R.id.panel);
		tv.setText("receive from fragment2");
	}
	
}
