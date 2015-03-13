package com.example.gameofcards;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;


public class JoinRoom extends Activity implements View.OnClickListener, ConnectionRequestListener, ZoneRequestListener{
	EditText username;
	Button join,newRoom;
	WarpClient myGame;
	ListView listRoom; 
	private RoomListAdapter roomlistAdapter;
	
	String apikey="5b615402c4173534193cadb56c20372e0e995fb619790e89f9eea033a1bae841";
	String pvtkey="f141ff514efe516a1b68255d7665b49b8e47b6a19fb5e5132adb4b35af60ddb5";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join_room);
		username=(EditText) findViewById(R.id.etusername);
		join=(Button) findViewById(R.id.busername);
		newRoom=(Button) findViewById(R.id.bnewroom);
		join.setOnClickListener(this);
		newRoom.setOnClickListener(this);
		init();
		roomlistAdapter=new RoomListAdapter(this);
		listRoom=(ListView) findViewById(R.id.lvRoom);
	}
	
	public void onBackPressed() {
        super.onBackPressed();
        myGame.removeConnectionRequestListener(this);
        if(myGame!=null){
                myGame.disconnect();
        }
        
	}
	
	 public void onStop(){
         super.onStop();
         myGame.removeZoneRequestListener(this);
	 }
	
	public void init(){
		WarpClient.initialize(apikey,pvtkey);
		try {  
            myGame = WarpClient.getInstance();  
        } catch (Exception ex) {  
            //Utils.showToastAlert(this, "Exception in Initilization");  
        }
		myGame.addConnectionRequestListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		String name=username.getText().toString();
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.busername:
			myGame.connectWithUserName(name);
			break;
		case R.id.bnewroom:
			myGame.createTurnRoom(""+System.currentTimeMillis(), name , 4, null, 300);
			break;
		}
	}
	
	@Override
	public void onConnectDone(ConnectEvent event) {
		// TODO Auto-generated method stub
		 if(event.getResult() == WarpResponseResultCode.SUCCESS){// go to room  list 
             myGame.addZoneRequestListener(this);
             myGame.getRoomInRange(0, 3);

			 /*Intent intent = new Intent(JoinRoom.this, bidding.class);
             startActivity(intent);*/
		 }else if(event.getResult() == WarpResponseResultCode.CONNECTION_ERROR){
			 Intent intent = new Intent(JoinRoom.this, mainPage.class);
			 startActivity(intent);
		 }
	}

	@Override
	public void onDisconnectDone(ConnectEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInitUDPDone(byte arg0) {
		// TODO Auto-generated method stub
		
	}

	public void joinRoom(String roomId){
		 if(roomId!=null && roomId.length()>0){
    		 Intent intent = new Intent(this, bidding.class);
    		 intent.putExtra("roomId", roomId);
    		 startActivity(intent);
    	 }
	}
	
	@Override
	public void onCreateRoomDone(final RoomEvent event) {
		// TODO Auto-generated method stub
		 runOnUiThread(new Runnable() {
             @Override
             public void run() {
                    
                     if(event.getResult()==WarpResponseResultCode.SUCCESS){// if room created successfully
                    	 String roomId = event.getData().getId();
                    	 joinRoom(roomId);                   	
                     }else{
                    	 Intent intent = new Intent(JoinRoom.this, mainPage.class);
                    	 startActivity(intent);
                     }
             }
		 });
	}

	@Override
	public void onDeleteRoomDone(RoomEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetAllRoomsDone(AllRoomsEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetLiveUserInfoDone(LiveUserInfoEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetMatchedRoomsDone(final MatchedRoomsEvent event) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable(){
            @Override
            public void run() {
                    RoomData[] roomDataList = event.getRoomsData();
                    if(roomDataList!=null && roomDataList.length>0){
                            roomlistAdapter.setData(roomDataList);
                            listRoom.setAdapter(roomlistAdapter);
                    }else{
                            roomlistAdapter.clear();
                    }
            }
		});
		
	}

	@Override
	public void onGetOnlineUsersDone(AllUsersEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSetCustomUserDataDone(LiveUserInfoEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
