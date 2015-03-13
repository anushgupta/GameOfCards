package com.example.gameofcards;

public class getCardResource {
	
	public int cardToResource(int n){
		int result=0;
		if(n==0)
			result=R.drawable.sevenheart;
		else if(n==1)
			result=R.drawable.sevenspade;
		else if(n==2)
			result=R.drawable.sevendia;
		else if(n==3)
			result=R.drawable.sevenclub;
		else if(n==4)
			result=R.drawable.eightheart;
		else if(n==5)
			result=R.drawable.eightspade;
		else if(n==6)
			result=R.drawable.eightdia;
		else if(n==7)
			result=R.drawable.eightclub;
		else if(n==8)
			result=R.drawable.nineheart;
		else if(n==9)
			result=R.drawable.ninespade;
		else if(n==10)
			result=R.drawable.ninedia;
		else if(n==11)
			result=R.drawable.nineclub;
		else if(n==12)
			result=R.drawable.tenheart;
		else if(n==13)
			result=R.drawable.tenspade;
		else if(n==14)
			result=R.drawable.tendia;
		else if(n==15)
			result=R.drawable.tenclub;
		else if(n==16)
			result=R.drawable.jheart;
		else if(n==17)
			result=R.drawable.jspade;
		else if(n==18)
			result=R.drawable.jdia;
		else if(n==19)
			result=R.drawable.jclub;
		else if(n==20)
			result=R.drawable.qheart;
		else if(n==21)
			result=R.drawable.qspade;
		else if(n==22)
			result=R.drawable.qdia;
		else if(n==23)
			result=R.drawable.qclub;
		else if(n==24)
			result=R.drawable.kheart;
		else if(n==25)
			result=R.drawable.kspade;
		else if(n==26)
			result=R.drawable.kdia;
		else if(n==27)
			result=R.drawable.kclub;
		else if(n==28)
			result=R.drawable.aceheart;
		else if(n==29)
			result=R.drawable.acespade;
		else if(n==30)
			result=R.drawable.acedia;
		else if(n==31)
			result=R.drawable.aceclub;
			return result;
	}
}
