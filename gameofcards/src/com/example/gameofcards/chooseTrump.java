package com.example.gameofcards;

//Load them with cards

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class chooseTrump extends Activity implements View.OnClickListener{
	
	ImageButton heart,spade,club,dia;
	ImageView iv1,iv2,iv3,iv4;
	String roomId;
	int bidder;
	int maxbid;
	int trump=0;
	int[] myCards=new int[8];
	getCardResource res=new getCardResource();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trump);
		Intent i=getIntent();
		bidder=i.getIntExtra("player",0);
		maxbid=i.getIntExtra("maxbid",16);
		roomId=i.getStringExtra("roomId");
		myCards=i.getIntArrayExtra("myCards");
		setupVar();
	}
	
	private void setupVar(){
		heart = (ImageButton) findViewById(R.id.ibhearts);
		dia = (ImageButton) findViewById(R.id.ibdiamonds);
		club = (ImageButton) findViewById(R.id.ibclubs);
		spade = (ImageButton) findViewById(R.id.ibspades);
		
		iv1=(ImageView) findViewById(R.id.imageView1);
		iv2=(ImageView) findViewById(R.id.imageView2);
		iv3=(ImageView) findViewById(R.id.imageView3);
		iv4=(ImageView) findViewById(R.id.imageView4);
		
		iv1.setImageResource(res.cardToResource(myCards[0]));
		iv2.setImageResource(res.cardToResource(myCards[1]));
		iv3.setImageResource(res.cardToResource(myCards[2]));
		iv4.setImageResource(res.cardToResource(myCards[3]));

		heart.setOnClickListener(this);
		club.setOnClickListener(this);
		dia.setOnClickListener(this);
		spade.setOnClickListener(this);
	}

	public void goToNext(){
		Intent i = new Intent(this,gameArena.class);
		Bundle bundle = new Bundle();
		bundle.putInt("maxbid", maxbid);
		bundle.putInt("player", bidder);
		bundle.putString("roomId",roomId);
		bundle.putInt("trump", trump);
		bundle.putInt("flag", 1);
		bundle.putIntArray("myCards", myCards);
		bundle.putInt("playerid",bidder );
		i.putExtras(bundle);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startActivity(i);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.ibclubs:
			trump=4;
			goToNext();
			break;
			
		case R.id.ibdiamonds:
			trump=3;
			goToNext();
			break;
			
		case R.id.ibhearts:
			trump=1;
			goToNext();
			break;
			
		case R.id.ibspades:
			trump=2;
			goToNext();
			break;
		}	
	}
}
