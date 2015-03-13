package com.example.gameofcards;

public class Players {

	String nickname;
	int id;
	int myCards[];
	
	public int[] getMyCards() {
		return myCards;
	}
	public void setMyCards(int[] myCards) {
		this.myCards = myCards;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
