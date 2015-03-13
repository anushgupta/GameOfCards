package com.example.gameofcards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainPage extends Activity implements View.OnClickListener{
	Button play,rules;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
		play=(Button) findViewById(R.id.bPlay);
		rules=(Button) findViewById(R.id.bRules);
		play.setOnClickListener(this);
		rules.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.bPlay:
				Intent forJoin = new Intent(this,JoinRoom.class);
				startActivity(forJoin);
				break;
			case R.id.bRules:
				Intent forRule=new Intent(mainPage.this,aboutRules.class);
				startActivity(forRule);
				break;
		}
	}
}
