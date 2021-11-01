package finalexam;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user_genr extends ViewableAtomic
{
	
	protected double int_arr_time;
	
	protected int count; // 유저카운트 - 1부터 시작하여 1씩 증가  
	protected int airplaneA_booked; // A비행기의 예약된 좌석 수  
	protected int airplaneB_booked; // B비행기의 예약된 좌석 수  
	
	// 유저정보  
	protected int age; // 나이 - 2~80세 사이  
	protected int action; // 행동 - 1~5:예매, 6~9:확인, 10:취소
	protected int airplaneFlag; // 비행기 선택 - 1:A비행기, 2:B비행기  

	public user_genr() 
	{
		this("user_genr", 10);
	}
  
	public user_genr(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		count = 1;
		airplaneA_booked = 0;
		airplaneB_booked = 0;
		
		holdIn("active", int_arr_time);
	}
   
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("active"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					holdIn("stop", INFINITY);
				}
			}
		}
	}

	public void deltint()
	{
		if (phaseIs("active"))
		{
			if(airplaneA_booked == 40 && airplaneB_booked == 40) {
				holdIn("stop", INFINITY);
			}
			else 
			{
			count = count + 1;
			
			holdIn("active", int_arr_time);
			}
		}
	}

	public message out()
	{
		message m = new message();
		
		// 비행기 선택 - 1:A비행기, 2:B비행기  
		// 1~2 사이 난수생성  
		int randNum_airplaneFlag = ((int)(Math.random() * 2) + 1);
		// A비행기의 예약이 꽉 찬 경우  
		if(airplaneA_booked == 40) {
			airplaneFlag = 2;
		}
		// B비행기의 예약이 꽉 찬 경우  
		else if(airplaneB_booked == 40) {
			airplaneFlag = 1;
		}
		// 둘 다 아닌 경우  
		else {
			airplaneFlag = randNum_airplaneFlag;
		}
		
		// 행동 - 1~5:예매, 6~9:확인, 10:취소  
		// 1~10 사이 난수생성  
		int randNum_action = ((int)(Math.random() * 10) + 1);
		
		// A비행기일 경우   
		if(airplaneFlag == 1) {
			// 비행기에 예매된 좌석이 0개일 때 반드시 예매   
			if(airplaneA_booked == 0) {
				action = 1;
			}
			else {
				action = randNum_action;
			}
		}
		// B비행기일 경우  
		else {
			// 비행기에 예매된 좌석이 0개일 때 반드시 예매   
			if(airplaneB_booked == 0) {
				action = 1;
			}
			else {
				action = randNum_action;
			}
		}
		
		
		// 예매 시  
		if(action >=1 && action <= 5) {
			
			// 나이 - 2~80세 사이  
			// 2~80 사이 난수생성  
			int randNum_age = ((int)(Math.random() * 79) + 2); 
			age = randNum_age;
			
			// A비행기 예매 시  
			if(airplaneFlag == 1) {
				airplaneA_booked++;
			}
			// B비행기 예매 시  
			else {
				airplaneB_booked++;
			}
		}
		// 확인 시 
		else if(action >=6 && action <= 9) {
			
			age = -1; // 임시값 대입  
			
		}
		// 취소 시  
		else {
			// A비행기 취소 시  
			if(airplaneFlag == 1) {
				airplaneA_booked--;
			}
			// B비행기 취소 시  
			else {
				airplaneB_booked--;
			}	
			
			age = -1; // 임시값 대입  
		}

		
		m.add(makeContent("out", new user("User count : " + count, count, age, action, airplaneFlag)));
		return m;
	}
  
	public String getTooltipText()
	{
		return
        super.getTooltipText();
	}

}
