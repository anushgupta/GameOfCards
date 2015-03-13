package com.example.gameofcards;

/*

 * 2. Cards are not unique, add property for that
	3. going to main arena giving load
 */

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.shephertz.app42.gaming.multiplayer.client.listener.TurnBasedRoomListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.UpdateRequestListener;

public class bidding extends Activity implements View.OnClickListener, RoomRequestListener, NotifyListener, TurnBasedRoomListener, UpdateRequestListener {

	EditText a, b, c, d;
	TextView p1,p2,p3,p4;
	Players player=new Players();
	getCardResource cardResource=new getCardResource();
	WarpClient myGame;
	String roomId;			//stores roomid
	Button passA,passB,passC,passD,bidA,bidB,bidC,bidD;
	int maxbid = 16;		//maxbid for the game
	int bidder=0;			//Stores who is bidding
	int turn=1;
	int bidNumber;			//individual bids
	int[] peoplePassed=new int[4];	//who all have passed
	int numberOfPass=0;		//how many passes are done
	boolean passed=false;
	ImageView iv1,iv2,iv3,iv4;
	int numberOfPlayers=0;	//players joined till now	
	int[] myCards=new int[40];
	int []myHandCards=new int[8];
	Context mContext;
	//make myCards equal with players cards
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_);
		
		for(int i=0;i<40;i++){
			myCards[i]=0;
		}
		for(int i=0;i<4;i++){
			int x= (int)(Math.random()*32);
			while(myCards[x]==1){
				x=(int)Math.random()*32;
			}
			myHandCards[i]=x;
			myCards[x]=1;
		}
		player.setId(1);
		mContext=this;
		setupVariables();
	}

	private void setupVariables() {
		
		try {
			myGame = WarpClient.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		Intent i=getIntent();
		roomId=i.getStringExtra("roomId");
		 if(myGame!=null){
             myGame.addRoomRequestListener(this);
             myGame.addNotificationListener(this);
             myGame.joinRoom(roomId);
		 }
		 p1=(TextView) findViewById(R.id.tvPlayer1);
		 p2=(TextView) findViewById(R.id.tvPlayer2);
		 p3=(TextView) findViewById(R.id.tvPlayer3);
		 p4=(TextView) findViewById(R.id.tvPlayer4);
		 for(int j=0;j<4;j++)
			 peoplePassed[j]=0;
	}
	
	
	public void loadingVariables(){
		iv1=(ImageView) findViewById(R.id.imageView1);
		iv2=(ImageView) findViewById(R.id.imageView2);
		iv3=(ImageView) findViewById(R.id.imageView3);
		iv4=(ImageView) findViewById(R.id.imageView4);
		
		a = (EditText) findViewById(R.id.etPlayer1);
		bidA = (Button) findViewById(R.id.bPlayer1);
		passA = (Button) findViewById(R.id.bPass1);
		b = (EditText) findViewById(R.id.etPlayer2);
		bidB = (Button) findViewById(R.id.bPlayer2);
		passB = (Button) findViewById(R.id.bPass2);
		c = (EditText) findViewById(R.id.etPlayer3);
		bidC = (Button) findViewById(R.id.bPlayer3);
		passC = (Button) findViewById(R.id.bPass3);
		d = (EditText) findViewById(R.id.etPlayer4);
		bidD = (Button) findViewById(R.id.bPlayer4);
		passD = (Button) findViewById(R.id.bPass4);
		
		if(player.id==1){			//Should be player.id==1
			bidA.setVisibility(View.VISIBLE);
			passA.setVisibility(View.VISIBLE);
			bidA.setOnClickListener(this);
			passA.setOnClickListener(this);
			iv1.setImageResource(cardResource.cardToResource(myHandCards[0]));
			iv2.setImageResource(cardResource.cardToResource(myHandCards[1]));
			iv3.setImageResource(cardResource.cardToResource(myHandCards[2]));
			iv4.setImageResource(cardResource.cardToResource(myHandCards[3]));
			freeze(b,bidB,passB);
			freeze(c,bidC,passC);
			freeze(d,bidD,passD);
		}	
		else if(player.id==2){		//Should be player.id==2
			bidB.setVisibility(View.VISIBLE);
			passB.setVisibility(View.VISIBLE);
			bidB.setOnClickListener(this);
			passB.setOnClickListener(this);
			iv1.setImageResource(cardResource.cardToResource(myHandCards[0]));
			iv2.setImageResource(cardResource.cardToResource(myHandCards[1]));
			iv3.setImageResource(cardResource.cardToResource(myHandCards[2]));
			iv4.setImageResource(cardResource.cardToResource(myHandCards[3]));
			freeze(a,bidA,passA);
			freeze(b,bidB,passB);
			freeze(c,bidC,passC);
			freeze(d,bidD,passD);
		}
		else if(player.id==3){	//Should be player.id==3
			passC.setOnClickListener(this);
			bidC.setOnClickListener(this);
			bidC.setVisibility(View.VISIBLE);
			passC.setVisibility(View.VISIBLE);
			iv1.setImageResource(cardResource.cardToResource(myHandCards[0]));
			iv2.setImageResource(cardResource.cardToResource(myHandCards[1]));
			iv3.setImageResource(cardResource.cardToResource(myHandCards[2]));
			iv4.setImageResource(cardResource.cardToResource(myHandCards[3]));
			freeze(a,bidA,passA);
			freeze(b,bidB,passB);
			freeze(c,bidC,passC);
			freeze(d,bidD,passD);
		}
		else{
			passD.setOnClickListener(this);
			bidD.setOnClickListener(this);		
			bidD.setVisibility(View.VISIBLE);
			passD.setVisibility(View.VISIBLE);
			iv1.setImageResource(cardResource.cardToResource(myHandCards[0]));
			iv2.setImageResource(cardResource.cardToResource(myHandCards[1]));
			iv3.setImageResource(cardResource.cardToResource(myHandCards[2]));
			iv4.setImageResource(cardResource.cardToResource(myHandCards[3]));
			freeze(a,bidA,passA);
			freeze(b,bidB,passB);
			freeze(c,bidC,passC);
			freeze(d,bidD,passD);
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String moveSent;
		switch (v.getId()) {
		case R.id.bPass1:
			numberOfPass++;
			a.setText("PASS");
			freeze(a,bidA,passA);
			passed=true;
			moveSent="P1";
			myGame.sendUpdatePeers(moveSent.getBytes());
			break;
		
		case R.id.bPass2:
			numberOfPass++;
			b.setText("PASS");
			freeze(b,bidB,passB);
			passed=true;
			moveSent="P2";
			myGame.sendUpdatePeers(moveSent.getBytes());
			break;
		
		case R.id.bPass3:
			numberOfPass++;
			c.setText("PASS");
			freeze(c,bidC,passC);
			passed=true;
			moveSent="P3";
			myGame.sendUpdatePeers(moveSent.getBytes());
			break;
		
		case R.id.bPass4:
			numberOfPass++;
			d.setText("PASS");
			freeze(d,bidD,passD);
			passed=true;
			moveSent="P4";
			myGame.sendUpdatePeers(moveSent.getBytes());
			break;
			
		case R.id.bPlayer1:
			try{
				bidNumber=Integer.parseInt(a.getText().toString());	
			}catch (NumberFormatException e) {
				Toast t = Toast.makeText(bidding.this,
						"You must enter numeric value ",
						Toast.LENGTH_LONG);
				t.show();
			}finally{
				if(checkValidity(bidNumber)==1){
					freeze(a,bidA,passA);
					moveSent="B1"+Integer.toString(bidNumber);
					myGame.sendUpdatePeers(moveSent.getBytes());
					//Communicate here by taking this bidNumber value
					
				}
			}
			break;
		case R.id.bPlayer2:
			try{
				bidNumber=Integer.parseInt(b.getText().toString());	
			}catch (NumberFormatException e) {
				Toast t = Toast.makeText(bidding.this,
						"You must enter numeric value ",
						Toast.LENGTH_LONG);
				t.show();
			}finally{
				if(checkValidity(bidNumber)==1){
					//Communicate here by taking this bidNumber value
					freeze(b,bidB,passB);					
					moveSent="B2"+Integer.toString(bidNumber);
					myGame.sendUpdatePeers(moveSent.getBytes());
				}
			}
			break;
		case R.id.bPlayer3:try{
			bidNumber=Integer.parseInt(c.getText().toString());	
		}catch (NumberFormatException e) {
			Toast t = Toast.makeText(bidding.this,
					"You must enter numeric value ",
					Toast.LENGTH_LONG);
			t.show();
		}finally{
			if(checkValidity(bidNumber)==1){
				//Communicate here by taking this bidNumber value
				freeze(c,bidC,passC);
				moveSent="B3"+Integer.toString(bidNumber);
				myGame.sendUpdatePeers(moveSent.getBytes());
			}
		}
			break;
		case R.id.bPlayer4:
			try{
				bidNumber=Integer.parseInt(d.getText().toString());	
			}catch (NumberFormatException e) {
				Toast t = Toast.makeText(bidding.this,
						"You must enter numeric value ",
						Toast.LENGTH_LONG);
				t.show();
			}finally{
				if(checkValidity(bidNumber)==1){
					//Communicate here by taking this bidNumber value
					freeze(d,bidD,passD);
					moveSent="B4"+Integer.toString(bidNumber);
					myGame.sendUpdatePeers(moveSent.getBytes());
				}
			}
			break;
		}
	}
	
	public int checkValidity(int bid){
		int flag=0;
		if (bid < 16 || bid > 28) {
			Toast t = Toast.makeText(bidding.this,
					"You must enter values in between 16-28",
					Toast.LENGTH_LONG);
			t.show();
		} else {
			if (bid < maxbid) {
				Toast t = Toast.makeText(bidding.this,
						"You must enter value greater than or equal to "+ maxbid + " or pass",
						Toast.LENGTH_LONG);
				t.show();
			} else {
				maxbid = bid;			//Have to make this universal
				flag=1;
				//prepareForNextTurn();		//Pass this turn now
			}
		}
		return flag;
	}
	
	public void goToTrump() {
		// TODO Auto-generated method stub
		String max = String.valueOf(maxbid);
		Intent i = new Intent("com.example.gameofcards.CHOOSETRUMP");
		Bundle bundle = new Bundle();
		bundle.putInt("maxbid", maxbid);
		bundle.putInt("player", bidder);
		bundle.putString("roomId",roomId);
		bundle.putInt("playerid", player.getId());
		bundle.putIntArray("myCards", myHandCards);
		i.putExtras(bundle);
		/*try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		startActivity(i);
	}

	public void goToArena(){
		Intent i = new Intent(this,gameArena.class);
		Bundle bundle = new Bundle();
		bundle.putInt("maxbid", maxbid);
		bundle.putString("roomId",roomId);
		bundle.putInt("player", bidder);
		bundle.putIntArray("myCards", myHandCards);
		bundle.putInt("flag", 0);
		bundle.putInt("playerid", player.getId());
		i.putExtras(bundle);
		/*try {
			
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		startActivity(i);
	}
	
	private void unfreeze(int number) {
		EditText et=null;
		Button butbid=null,butpass=null;
		if(number==1){
			et=(EditText)a;
			butbid=(Button)bidA;
			butpass=(Button)passA;
		}
		else if(number==2){
			et=(EditText)b;
			butbid=(Button)bidB;
			butpass=(Button)passB;
		}
			
		else if(number==3){
			et=(EditText)c;
			butbid=(Button)bidC;
			butpass=(Button)passC;
		}
		else if(number==4){
			et=(EditText)d;
			butbid=(Button)bidD;
			butpass=(Button)passD;
		}
		if(turn==player.getId()){
			et.setEnabled(true);
			et.setFocusable(true);
			et.setClickable(true);
			et.setInputType(1);
			et.setFocusableInTouchMode(true);
			butbid.setVisibility(View.VISIBLE);
			butpass.setVisibility(View.VISIBLE);
		}
	}

	public void freeze(EditText et,Button butbid,Button butpass) {
		et.setEnabled(false);
		et.setFocusable(false);
		et.setClickable(false);
		et.setInputType(0);
		et.setFocusableInTouchMode(false);
		butbid.setVisibility(View.INVISIBLE);
		butpass.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	public void addNewPlayer(final String name){
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	/*Check whether same cards are not repeated*/
            	numberOfPlayers++;
            	if(numberOfPlayers==4){
            		p4.setText(name);
            		loadingVariables();
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
	
	public void addMorePlayer(final String[] name){
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	
            	/*Check whether same cards are not repeated*/
            	
            	if(name.length==4){
            		p1.setText(name[0]);
            		p2.setText(name[1]);
            		p3.setText(name[2]);
            		p4.setText(name[3]);
            		player.setId(4);
            		loadingVariables();
            	}
            	else if(name.length==3){
            		p1.setText(name[0]);
            		p2.setText(name[1]);
            		p3.setText(name[2]);
            		player.setId(3);
            		loadingVariables();
            	}
            	else if(name.length==2){
            		p1.setText(name[0]);
            		p2.setText(name[1]);
            		player.setId(2);
            		loadingVariables();
            	}
            	else if(name.length==1){
            		p1.setText(name[0]);
            		player.setId(1);
            		loadingVariables();
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
             /*properties = event.getProperties();
             for (Map.Entry<String, Object> entry : properties.entrySet()) { 
            	 if(entry.getValue().toString().length()>0){
                             int fruitId = Integer.parseInt(entry.getValue().toString());
                             placeObject(fruitId, entry.getKey(), null, false);
                     }
             }*/
		 
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
	public void onMoveCompleted(MoveEvent event) {
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
	
	public int nextTurnPlayer(int nextTurn){
		if(numberOfPass==4){
			if(nextTurn==player.getId()){
				maxbid=16;
				bidder=1;
				goToTrump();
				return 0;
			}
			else{
				goToArena();
			}
		}
		if(peoplePassed[nextTurn-1]==1){
			while(peoplePassed[nextTurn-1]==1){
				nextTurn++;
				if(nextTurn==4)
					nextTurn=1;
			}
		}
		if(numberOfPass==3 &&  bidder!=0 && bidder==nextTurn)
		{
			if(nextTurn==player.getId()){
				bidder=nextTurn;
				goToTrump();
				return 0;
			}
			else{
				goToArena();
			}
		}
		return nextTurn;
	}

	@Override
	public void onUpdatePeersReceived(UpdateEvent event) {
		// TODO Auto-generated method stub
		final String movedata=new String(event.getUpdate());
		runOnUiThread(new Runnable() { 
			public void run() {
				//Toast.makeText(mContext, movedata, Toast.LENGTH_SHORT).show();
			}
		});
		if(movedata.charAt(0)=='B'){
			runOnUiThread(new Runnable() {
	            @Override
	            public void run() {
	            	char pturn=movedata.charAt(1);
	            	String actualbid=movedata.substring(2);
	            	/*Check whether same cards are not repeated*/
	            		if(pturn=='1'){
	            			a.setText(actualbid);
	            			maxbid=Integer.parseInt(actualbid);
	            			bidder=1;
	            			turn=nextTurnPlayer(2);
	            			if(turn!=0)
	            				unfreeze(turn);
	            		}
	            		else if(pturn=='2'){
	            			b.setText(actualbid);
	            			maxbid=Integer.parseInt(actualbid);
	            			bidder=2;
	            			turn=nextTurnPlayer(3);
	            			if(turn!=0)
	            				unfreeze(turn);
	            		}
	            		else if(pturn=='3'){
	            			c.setText(actualbid);
	            			maxbid=Integer.parseInt(actualbid);
	            			bidder=3;
	            			turn=nextTurnPlayer(4);
	            			if(turn!=0)
	            				unfreeze(turn);
	            		}
	            		else if(pturn=='4'){
	            			d.setText(actualbid);
	            			maxbid=Integer.parseInt(actualbid);
	            			bidder=4;
	            			turn=nextTurnPlayer(1);
	            			if(turn!=0)
	            				unfreeze(turn);
	            		}
	            }
			 });
		}
		if(movedata.charAt(0)=='P'){
			runOnUiThread(new Runnable() {
	            public void run() {
	            	char pturn=movedata.charAt(1);
	            	String actualbid="PASS";
	            	/*Check whether same cards are not repeated*/
	            		if(pturn=='1'){
	            			a.setText(actualbid);
	            			numberOfPass++;
	            			peoplePassed[0]=1;
	            			turn=nextTurnPlayer(2);
	            			if(turn!=0)
	            				unfreeze(turn);
	            		}
	            		else if(pturn=='2'){
	            			b.setText(actualbid);
	            			numberOfPass++;
	            			peoplePassed[1]=1;
	            			turn=nextTurnPlayer(3);
	            			if(turn!=0)
	            				unfreeze(turn);
	            		}
	            		else if(pturn=='3'){
	            			c.setText(actualbid);
	            			numberOfPass++;
	            			peoplePassed[2]=1;
	            			turn=nextTurnPlayer(4);
	            			if(turn!=0)
	            				unfreeze(turn);
	            		}
	            		else if(pturn=='4'){
	            			d.setText(actualbid);
	            			numberOfPass++;
	            			peoplePassed[3]=1;
	            			turn=nextTurnPlayer(1);
	            			if(turn!=0)
	            				unfreeze(turn);
	            		}
	            	}
			 });
		}
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
	public void onUserJoinedRoom(RoomData room, String username) {
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

	@Override
	public void onGetMoveHistoryDone(byte arg0, MoveEvent[] arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSendMoveDone(byte byteevent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartGameDone(byte arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopGameDone(byte arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSendUpdateDone(byte arg0) {
		// TODO Auto-generated method stub
		
	}
}
