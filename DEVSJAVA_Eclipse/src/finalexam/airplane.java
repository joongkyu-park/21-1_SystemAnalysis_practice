package finalexam;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class airplane extends ViewableAtomic
{
	protected user user;
	protected double processing_time;
	protected ArrayList<Integer> booked_arr = new ArrayList<Integer>(); // 예약된 좌석 정보  
	protected ArrayList<Integer> vacant_arr = new ArrayList<Integer>(); // 빈 좌석 정보
	protected int[] airlineFood_arr = new int[41]; // 예약된 좌석의 기내식의 필요유무 정보 - 0:기내식 필요X, 1:기내식 필요     
	protected int[] age_arr = new int[41]; // 예약된 좌석의 나이에 대한 정보
	
	// 매 loop에 state_msg에 기내식, 나이, 좌석번호를 넘겨줄 임시 변수들   
	protected int airlineFood;
	protected int ageOfUser;
	protected int numberOfSeat;
	
	public airplane()
	{
		this("airplane", 5);
	}

	public airplane(String name, double Processing_time)
	{
		super(name);
		
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
	
	public void initialize()
	{
		user = new user("none", 0, 0, 0, 0);
		
		// 초기화 : 1~40번의 빈 좌석 생성  
		for(int i = 1; i <= 40; i++) {
			vacant_arr.add(i);
		}
		
		airlineFood = 0;
		
		holdIn("passive", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.size(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					user = (user)x.getValOnPort("in", i);
					
					holdIn("busy", processing_time);
				}
			}
		}
	}
	
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			user = new user("none", 0, 0, 0, 0);
			
			airlineFood = 0;
			
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		
		if (phaseIs("busy"))
		{
			// 예약된 좌석, 빈 좌석들을 랜덤하게 섞어주기  
			Collections.shuffle(vacant_arr);
			Collections.shuffle(booked_arr);
			
			// 예매인 경우  
			if(user._action >=1 && user._action <= 5) {

				// 랜덤하게 섞인 빈 좌석의 번호 중 하나를 예약  
				int booking_num = vacant_arr.get(vacant_arr.size()-1);
				vacant_arr.remove(vacant_arr.size()-1);
				
				booked_arr.add(booking_num);
				
				// 나이 정보 저장
				ageOfUser = user._age;
				age_arr[booking_num] = user._age;
				numberOfSeat = booking_num;
				
				// 기내식이 필요하지 않은 경우
				if(user._age > 8 && user._age < 65) {
					airlineFood = 0;
				}		 
				// 기내식이 필요한 경우  
				else {
					airlineFood_arr[booking_num] = 1;
					airlineFood = 1;
				}				
			}
			// 확인만 하는 경우  
			else if(user._action >=6 && user._action <= 9) {
				
				// 랜덤하게 섞인 예약된 자리 정보 중 하나 가져오기   
				numberOfSeat = booked_arr.get(booked_arr.size()-1);
				ageOfUser = age_arr[numberOfSeat];
			}
			// 취소하는 경우  
			else {
				
				// 랜덤하게 섞인 예약된 좌석의 번호 중 하나를 취소  
				int cancel_num = booked_arr.get(booked_arr.size()-1);
				booked_arr.remove(booked_arr.size()-1);
				
				vacant_arr.add(cancel_num);
				
				// 나이 정보를 가져오고 초기화  
				ageOfUser = age_arr[cancel_num];
				numberOfSeat = cancel_num;
				age_arr[cancel_num] = 0;
				
				// 기내식이 필요했던 경우 기내식 취소  
				if(airlineFood_arr[cancel_num] == 1) {
					airlineFood_arr[cancel_num] = 0;
					airlineFood = -1;
				}
			}
			
			int count = user._count;
			int typeOfPlane = user._airplaneFlag;
			int typeOfAction;
			if(user._action >= 1 && user._action <= 5) {
				typeOfAction = 1;
			}
			else if(user._action >= 6 && user._action <= 9) {
				typeOfAction = 2;
			}
			else {
				typeOfAction = 3;
			}
			m.add(makeContent("out", new state_msg("state msg", count, typeOfPlane, typeOfAction, airlineFood, ageOfUser, numberOfSeat)));
		}
		return m;
	}	
	
	public String getTooltipText()
	{
		return
        super.getTooltipText();
	}

}



