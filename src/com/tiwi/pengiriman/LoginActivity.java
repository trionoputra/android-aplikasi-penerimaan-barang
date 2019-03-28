package com.tiwi.pengiriman;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tiwi.pengiriman.app.AppController;
import com.tiwi.pengiriman.utils.Helper;

public class LoginActivity extends Activity {
	private Button login;
	private EditText username;
	private EditText password;
	private ProgressDialog loader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Helper.initialize(this);
		setContentView(R.layout.activity_login);
		
		login = (Button)findViewById(R.id.btnLogin);
		username = (EditText)findViewById(R.id.txtuserid);
		password = (EditText)findViewById(R.id.txtpassword);
	
		loader = new ProgressDialog(this);
		login.setOnClickListener(loginOnclick);
	}
	
private OnClickListener loginOnclick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			final String user = username.getText().toString();
			String pass = password.getText().toString();
			
			if(user.equals("") || pass.equals(""))
			{
				Toast.makeText(getApplicationContext(), "field tidak boleh kosong", Toast.LENGTH_LONG).show();
				return;
			}
			
			loader.show();
			String data = Base64.encodeToString((user+"|"+pass).getBytes(), Base64.DEFAULT);
			String Url = Helper.SERVER_URL+"authenticate?auth="+data;
			
			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,Url, null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						loader.dismiss();
						try {
							Object json = new JSONTokener(response.getString("result")).nextValue();
							if (json instanceof JSONObject)
							{
								JSONObject data = response.getJSONObject("result");
								Helper.write("sid",data.getString("id_kurir"));
								Helper.write("isLogin","1");
								Helper.write("uname", data.getString("nama"));									
								Intent intent = new Intent(LoginActivity.this, MainActivity.class);
								startActivity(intent);
								finish();
							}
							else
							{
								Toast.makeText(LoginActivity.this,response.getString("result"), Toast.LENGTH_SHORT).show();
							}

							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							Toast.makeText(LoginActivity.this,"Unable to parse data", Toast.LENGTH_SHORT).show();
						}
					}
				}, new Response.ErrorListener() {
		
					@Override
					public void onErrorResponse(VolleyError error) {
						loader.dismiss();
						Toast.makeText(LoginActivity.this,"Unable to login", Toast.LENGTH_SHORT).show();	
					}
				});
			
			jsonReq.setShouldCache(false);
			AppController.getInstance().addToRequestQueue(jsonReq);
			
			
		}
	};
}
