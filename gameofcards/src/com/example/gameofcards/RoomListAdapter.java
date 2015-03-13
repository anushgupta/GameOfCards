package com.example.gameofcards;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;

public class RoomListAdapter extends BaseAdapter {

        
        private ArrayList<String> roomIdList = new ArrayList<String>();
        private ArrayList<String> roomIdName = new ArrayList<String>();
        private Context context;
        private JoinRoom roomlistActivity;
        
        RoomListAdapter(Context c){
                this.context = c;
                roomlistActivity = (JoinRoom)context;
        }
        
        @Override
        public int getCount() {        
                return roomIdList.size();
        }
        
        public void setData(RoomData[] roomData){
                roomIdList.clear();
                roomIdName.clear();
                for(int i=0;i<roomData.length;i++){
                        roomIdList.add(roomData[i].getId());
                        roomIdName.add(roomData[i].getRoomOwner());
                        
                }
                notifyDataSetChanged();
        }
        public void clear(){
                roomIdList.clear();
                notifyDataSetChanged();
        }
        
        @Override
        public Object getItem(int number) {
                return roomIdList.get(number);
        }

        @Override
        public long getItemId(int arg0) {
                return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.roomlistlayout, null);
        }
        if (roomIdList != null) {
                TextView roomId = (TextView) convertView.findViewById(R.id.tvroomId);
                Button joinButton = (Button) convertView.findViewById(R.id.bjoinButton);
                roomId.setText(roomIdName.get(position));
                joinButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                        roomlistActivity.joinRoom(roomIdList.get(position));
                                }
                        });
        }
        return convertView;        
        }

}
