package com.tiwi.pengiriman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.tiwi.pengiriman.utils.Helper;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Helper.initialize(getBaseContext());
			
		if(Helper.read("isLogin", "0").equals("0"))
	    {
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
			return;
	    }
		
		findViewById(R.id.btnScan).setOnClickListener(this);
		findViewById(R.id.btnProfile).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btnScan:
			intent = new Intent(MainActivity.this, ScanActivity.class);
			break;
		case R.id.btnProfile:
			intent = new Intent(MainActivity.this, ScanActivity.class);
			break;
		default:
			break;
		}
		
		if(intent != null)
			startActivity(intent);
	}
}
