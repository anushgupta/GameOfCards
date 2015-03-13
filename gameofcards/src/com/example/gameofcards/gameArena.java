package com.example.gameofcards;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyData;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.UpdateRequestListener;

public class gameArena extends Activity implements RoomRequestListener, NotifyListener,UpdateRequestListener,View.OnClickListener,AnimationListener{
	
	ImageButton ib1,ib2,ib3,ib4,ib5,ib6,ib7,ib8;
	TextView p1,p2,p3,p4;
	int numberOfPlayers;
	WarpClient myGame;
	String roomId;
	Animation animMove;
	int bidder;
	int maxbid;
	int myHandCards[]=new int[8];
	int trump;
	int[] myCards=new int[40];
	int trumpbidder=0;
	Context mContext;
	int turn;
	Players player=new Players();
	getCardResource cardResource=new getCardResource();
	int roundHigh=0;
	int roundWinner=0;
	int roundPoint=0;
   	int suit;
   	int roundchance=0;
   	boolean firstChance=true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gamearena);
		mContext=this;
		try {
			myGame = WarpClient.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		Intent i=getIntent();
		roomId=i.getStringExtra("roomId");
		maxbid=i.getIntExtra("maxbid", maxbid);
		myHandCards=i.getIntArrayExtra("myCards");
		player.setId(i.getIntExtra("playerid", 0));
		int flag=i.getIntExtra("flag", 0);
		trumpbidder=i.getIntExtra("player", 1);
		if(flag==1){
			trump=i.getIntExtra("trump", 0);
		}
		turn=trumpbidder;
		//turn=1;
		//player.setId(1);
		
		setupVariables();
		/* if(myGame!=null){
             myGame.addRoomRequestListener(this);
             myGame.addNotificationListener(this);
             myGame.joinRoom(roomId);
		 }*/
	}

	public int nextTurn(int curTurn){
		roundchance++;
		int turny=curTurn;
		if(curTurn==4)
			turny=1;
		else
			turny++;
		if(roundchance==4){
			firstChance=true;
			runOnUiThread(new Runnable() { 
				public void run() {
					String whoWon="Round Winner is "+Integer.toString((player.getId()));
	            	Toast.makeText(mContext, whoWon, Toast.LENGTH_LONG).show();
	            }
			});
		}
		return turny;
	}
	
	public void setupVariables(){
		ib1=(ImageButton) findViewById(R.id.ib1);
		ib2=(ImageButton) findViewById(R.id.ib2);
		ib3=(ImageButton) findViewById(R.id.ib3);
		ib4=(ImageButton) findViewById(R.id.ib4);
		ib5=(ImageButton) findViewById(R.id.ib5);
		ib6=(ImageButton) findViewById(R.id.ib6);
		ib7=(ImageButton) findViewById(R.id.ib7);
		ib8=(ImageButton) findViewById(R.id.ib8);
		p1=(TextView) findViewById(R.id.tvgame1);
		p2=(TextView) findViewById(R.id.tvgame2);
		p3=(TextView) findViewById(R.id.tvgame3);
		p4=(TextView) findViewById(R.id.tvgame4);
		ib1.setOnClickListener(this);
		ib2.setOnClickListener(this);
		ib3.setOnClickListener(this);
		ib4.setOnClickListener(this);
		ib5.setOnClickListener(this);
		ib6.setOnClickListener(this);
		ib7.setOnClickListener(this);
		ib8.setOnClickListener(this);
		
		for(int i=0;i<4;i++){
			myCards[myHandCards[i]]=1;
		}
		for(int i=4;i<8;i++){
			int x= (int)(Math.random()*32);
			while(myCards[x]==1){
				x=(int)Math.random()*32;
			}
			myHandCards[i]=x;
			myCards[x]=1;
		}
		
		ib1.setImageResource(cardResource.cardToResource(myHandCards[0]));
		ib2.setImageResource(cardResource.cardToResource(myHandCards[1]));
		ib3.setImageResource(cardResource.cardToResource(myHandCards[2]));
		ib4.setImageResource(cardResource.cardToResource(myHandCards[3]));
		ib5.setImageResource(cardResource.cardToResource(myHandCards[4]));
		ib6.setImageResource(cardResource.cardToResource(myHandCards[5]));
		ib7.setImageResource(cardResource.cardToResource(myHandCards[6]));
		ib8.setImageResource(cardResource.cardToResource(myHandCards[7]));
		p1.setText(Integer.toString(player.getId()));
		/*
		 * Load with myHandCards
		 * Distribute the other four cards
		 */
	}
	
	@Override
	public void onClick(View v) {
		String moveData=null;
		// TODO Auto-generated method stub
		if(turn==player.getId()){
			switch(v.getId()){
				case R.id.ib1:
					//moveData=Integer.toString(player.getId())+" "+Integer.toString(myHandCards[0]);
					//myGame.sendUpdatePeers(moveData.getBytes());
					animMove = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.move1);
					animMove.setAnimationListener(this);
					ib1.startAnimation(animMove);
					break;
				case R.id.ib2:
					//moveData=Integer.toString(player.getId())+" "+Integer.toString(myHandCards[1]);
					//myGame.sendUpdatePeers(moveData.getBytes());
					animMove = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.move2);
					animMove.setAnimationListener(this);
					ib2.startAnimation(animMove);
					break;
				case R.id.ib3:
					//moveData=Integer.toString(player.getId())+" "+Integer.toString(myHandCards[2]);
					//myGame.sendUpdatePeers(moveData.getBytes());
					animMove = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.move3);
					animMove.setAnimationListener(this);
					ib3.startAnimation(animMove);
					break;
				case R.id.ib4:
					//moveData=Integer.toString(player.getId())+" "+Integer.toString(myHandCards[3]);
					//myGame.sendUpdatePeers(moveData.getBytes());
					animMove = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.move4);
					animMove.setAnimationListener(this);
					ib4.startAnimation(animMove);
					break;
				case R.id.ib5:
					//moveData=Integer.toString(player.getId())+" "+Integer.toString(myHandCards[4]);
					//myGame.sendUpdatePeers(moveData.getBytes());
					animMove = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.move5);
					animMove.setAnimationListener(this);
					ib5.startAnimation(animMove);
					break;
				case R.id.ib6:
					//moveData=Integer.toString(player.getId())+" "+Integer.toString(myHandCards[5]);
					//myGame.sendUpdatePeers(moveData.getBytes());
					animMove = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.move6);
					animMove.setAnimationListener(this);
					ib6.startAnimation(animMove);
					break;
				case R.id.ib7:
					//moveData=Integer.toString(player.getId())+" "+Integer.toString(myHandCards[6]);
					//myGame.sendUpdatePeers(moveData.getBytes());
					animMove = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.move7);
					animMove.setAnimationListener(this);
					ib7.startAnimation(animMove);
					break;
				case R.id.ib8:
					//moveData=Integer.toString(player.getId())+" "+Integer.toString(myHandCards[7]);
					//myGame.sendUpdatePeers(moveData.getBytes());
					animMove = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.move8);
					animMove.setAnimationListener(this);
					ib8.startAnimation(animMove);
					break;
			}	
		}
	}

	@Override
	public void onChatReceived(ChatEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameStarted(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameStopped(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMoveCompleted(MoveEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrivateChatReceived(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomCreated(RoomData arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomDestroyed(RoomData arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdatePeersReceived(UpdateEvent event) {
		// TODO Auto-generated method stub
		final String movedata=new String(event.getUpdate());
		/*runOnUiThread(new Runnable() { 
			public void run() {
				//Toast.makeText(mContext, movedata, Toast.LENGTH_SHORT).show();
			}
		});*/
		runOnUiThread(new Runnable() {
	           @Override
	           public void run() {
	           	String move=movedata.substring(1);
	           	int prevturn=Integer.parseInt(move.substring(0,1));
	           	int moveInt=Integer.parseInt(move);
	           	int localSuit = 0,localMove = 0,localPoint=0;
	           	if(moveInt%4==0)
	           		localSuit=1;
	           	else if(moveInt%4==1)
	           		localSuit=2;
	           	else if(moveInt%4==2)
	           		localSuit=3;
	           	else if(moveInt%4==3)
	           		localSuit=4;
	           	if(moveInt<4){
	           		localMove=7;
	           		localPoint=0;
	           	}
	           	else if(moveInt>=4 && moveInt<=7){
	           		localMove=8;
	           		localPoint=0;
	           	}
	           	else if(moveInt>=8 && moveInt<=11){
	           		localMove=9;
	           		localPoint=2;
	           	}
	           	else if(moveInt>=12 && moveInt<=15){
	           		localMove=10;
	           		localPoint=1;
	           	}
	           	else if(moveInt>=16 && moveInt<=19){
	           		localMove=11;
	           		localPoint=3;
	           	}
	           	else if(moveInt>=19 && moveInt<=23){
	           		localMove=12;
	           		localPoint=0;
           		}
	           	else if(moveInt>=24 && moveInt<=27){
	           		localMove=13;
	           		localPoint=0;
	           	}
	           	else if(moveInt>=28 && moveInt<=31){
	           		localMove=1;
	           		localPoint=1;
	           	}
	           	if(firstChance==true){
	           		suit=localSuit;
	           		roundWinner=turn;
	           		roundHigh=localMove;
	           		roundPoint=localPoint;
	           		turn=nextTurn(prevturn);
	           	}
	           	else{
	           		if(suit==localSuit){
	           			if(localPoint>roundPoint){
	           				roundWinner=turn;
	           				roundHigh=localMove;
	           				roundPoint=localPoint;
	           			}
	           		}
	           		else{
	           		}
	           		turn=nextTurn(prevturn);			//Trump parts come here
	           	}
	           }
			 });
	}

	@Override
	public void onUserChangeRoomProperty(RoomData arg0, String arg1,
			HashMap<String, Object> arg2, HashMap<String, String> arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserJoinedLobby(LobbyData arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserJoinedRoom(RoomData arg0, String username) {
		// TODO Auto-generated method stub
		addNewPlayer(username);
	}

	@Override
	public void onUserLeftLobby(LobbyData arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserLeftRoom(RoomData arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserPaused(String arg0, boolean arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUserResumed(String arg0, boolean arg1, String arg2) {
		// TODO Auto-generated method stub
		
	}

	public void addMorePlayer(final String[] name){
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	
            	if(name.length==4){
            		p1.setText(name[0]);
            		p2.setText(name[1]);
            		p3.setText(name[2]);
            		p4.setText(name[3]);
            		player.setId(4);
            	}
            	else if(name.length==3){
            		p1.setText(name[0]);
            		p2.setText(name[1]);
            		p3.setText(name[2]);
            		player.setId(3);
            	}
            	else if(name.length==2){
            		p1.setText(name[0]);
            		p2.setText(name[1]);
            		player.setId(2);
            	}
            	else if(name.length==1){
            		p1.setText(name[0]);
            		player.setId(1);
            	}
            }
		 });
	}
	
	public void addNewPlayer(final String name){
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	/*Check whether same cards are not repeated*/
            	numberOfPlayers++;
            	if(numberOfPlayers==4){
            		p4.setText(name);
            		}
            	else if(numberOfPlayers==3){
            		p3.setText(name);
            	}
            	else if(numberOfPlayers==2){
            		p2.setText(name);
            	}	
            }
           });
	}
	
	@Override
	public void onGetLiveRoomInfoDone(LiveRoomInfoEvent event) {
		// TODO Auto-generated method stub
		 if(event.getResult()==WarpResponseResultCode.SUCCESS){
             String[] joinedUser = event.getJoinedUsers();
             if(joinedUser!=null){
            	 numberOfPlayers=joinedUser.length;
            	 addMorePlayer(joinedUser);
             }
		 }
         
		 else{
           //  Utils.showToastOnUIThread(this, "onGetLiveRoomInfoDone: Failed "+event.getResult());
		 }
	}

	@Override
	public void onJoinRoomDone(RoomEvent event) {
		// TODO Auto-generated method stub
		 if(event.getResult()==WarpResponseResultCode.SUCCESS){
             myGame.subscribeRoom(roomId);
     }else{
             //Utils.showToastOnUIThread(this, "onJoinRoomDone: Failed "+event.getResult());
     }
		
	}

	@Override
	public void onLeaveRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLockPropertiesDone(byte arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetCustomRoomDataDone(LiveRoomInfoEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSubscribeRoomDone(RoomEvent event) {
		// TODO Auto-generated method stub
		if(event.getResult()==WarpResponseResultCode.SUCCESS){
            myGame.getLiveRoomInfo(roomId);
		 }else{
            //Utils.showToastOnUIThread(this, "onSubscribeRoomDone: Failed "+event.getResult());
		 }
	}

	@Override
	public void onUnSubscribeRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnlockPropertiesDone(byte arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdatePropertyDone(LiveRoomInfoEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSendUpdateDone(byte arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationEnd(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
