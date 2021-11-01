package finalexam;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class checking extends  ViewableAtomic
{
	
	protected Function arrived;
	protected int airplanes; // 비행기 대수(2대)  
	protected double clock, total_ta, observation_time;
	protected state_msg state_msg;
	
	// 각 비행기들의 정보를 저장할 변수(예약좌석, 빈 좌석, 기내식 수요)    
	protected int airplaneA_booked;
	protected int airplaneA_vacant;
	protected int airplaneA_airlineFood;
	protected int airplaneB_booked;
	protected int airplaneB_vacant;
	protected int airplaneB_airlineFood;
	
	// 종료 clock을 저장할 변수  
	protected double terminated_time;

	public checking(String name, double Observation_time, int _airplanes)
	{
		super(name);
    
		addOutport("out");
		addInport("arrived");
    
		arrived = new Function();
    
		airplanes = _airplanes;
		observation_time = Observation_time;
	}
  
	public checking()
	{
		this("checking", 50000, 2);
	}

	public void initialize()
	{	
		clock = 0;
		total_ta = 0;
		
		state_msg = new state_msg("none", 0, 0, 0, 0, 0, 0);
		
		// 최초 예약좌석은 0, 빈 좌석은 40, 기내식 수요는 0으로 초기화  
		airplaneA_booked = 0;
		airplaneA_vacant = 40;
		airplaneA_airlineFood = 0;
		
		airplaneB_booked = 0;
		airplaneB_vacant = 40;
		airplaneB_airlineFood = 0;
    
		arrived = new Function();
		
		holdIn("on", observation_time);
	}

	public void deltext(double e, message x)
	{
		clock = clock + e;
		Continue(e);
		
		if(phaseIs("on"))
		{
			for (int i = 0; i < x.size(); i++)
			{
				if (messageOnPort(x, "arrived", i))
				{
					state_msg = (state_msg)x.getValOnPort("arrived", i);
					arrived.put(state_msg.getName(), new doubleEnt(clock));
				}
			}

			// A비행기인 경우  
			if(state_msg._typeOfPlane == 1) {
				// 예약인 경우  
				if(state_msg._typeOfAction == 1) {
					// 예약좌석 증가, 빈좌석 감소  
					airplaneA_booked++;
					airplaneA_vacant--;
					// 기내식이 필요한 경우 기내식 증가  
					if(state_msg._airlineFood == 1) {
						airplaneA_airlineFood++;
					}
				}
				// 확인인 경우  
				else if(state_msg._typeOfAction == 2) {
			
				}
				// 취소인 경우  
				else {
					// 예약좌석 감소, 빈좌석 증가  
					airplaneA_booked--;
					airplaneA_vacant++;
					// 기내식이 필요했던 경우 기내식 감소  
					if(state_msg._airlineFood == -1) {
						airplaneA_airlineFood--;
					}
				}
			}			
			// B비행기인 경우  
			else {
				// 예약인 경우  
				if(state_msg._typeOfAction == 1) {
					// 예약좌석 증가, 빈좌석 감소  
					airplaneB_booked++;
					airplaneB_vacant--;
					// 기내식이 필요한 경우 기내식 증가  
					if(state_msg._airlineFood == 1) {
						airplaneB_airlineFood++;
					}
				}
				// 확인인 경우  
				else if(state_msg._typeOfAction == 2) {
			
				}
				// 취소인 경우  
				else {
					// 예약좌석 감소, 빈좌석 증가  
					airplaneB_booked--;
					airplaneB_vacant++;
					// 기내식이 필요했던 경우 기내식 감소  
					if(state_msg._airlineFood == -1) {
						airplaneB_airlineFood--;
					}
				}
			}
			show_state();
			
			// 2대의 비행기가 모두 예약이 다 되었을 때의 시간을 저장  
			if(airplaneA_booked == 40 && airplaneB_booked == 40) {
				terminated_time = clock;
			}
		}
	}

	public void deltint()
	{
		if (phaseIs("on"))
		{
			clock = clock + sigma;
			
			System.out.println(terminated_time + "clock에 2대의 비행기 예약이 모두 마감되었습니다. ");
	   		
	   		holdIn("off", 0);
		}
	}
  
	public message out()
	{
		message m = new message();
		
		if (phaseIs("off"))
		{
				m.add(makeContent("out", new stop_msg("stop", true)));
		}
		return m;
	}

	public void show_state()
	{
		System.out.println("---------------------- 비행기 좌석 예약 현황 ----------------------");
		System.out.println("phase : " + "\"" + phase + "\"");
		System.out.println("sigma : " + sigma);
		
		if (arrived != null)
		{
			String actionName;
			if(state_msg._typeOfAction == 1) {
				actionName = "예매";
			}
			else if(state_msg._typeOfAction == 2) {
				actionName = "확인";
			}
			else {
				actionName = "취소";
			}
			String planeName;
			if(state_msg._typeOfPlane == 1) {
				planeName = "A";
			}
			else {
				planeName = "B";
			}
			System.out.println("clock : " + clock);
			System.out.println("***** 유저 정보 *****");
			System.out.println("행동 : " + actionName);
			System.out.println("비행기 종류 : " + planeName);
			System.out.println("나이 : " + state_msg._ageOfUser);
			System.out.println("좌석번호 : " + state_msg._numberOfSeat);
			System.out.println("***** A비행기 좌석 현황 *****");
			System.out.println("예약석 : " + airplaneA_booked);
			System.out.println("빈 좌석 : " + airplaneA_vacant);
			System.out.println("***** B비행기 좌석 현황 *****");
			System.out.println("예약석 : " + airplaneB_booked);
			System.out.println("빈 좌석 : " + airplaneB_vacant);
			System.out.println("***** 기내식 수요 *****");
			System.out.println("A비행기 기내식 수요 : " + airplaneA_airlineFood);
			System.out.println("B비행기 기내식 수요 : " + airplaneB_airlineFood);
			System.out.println("총 기내식 수요 : " + (airplaneA_airlineFood+airplaneB_airlineFood));
		}
		System.out.println("--------------------------------------------------------------");
	}	
  
	public String getTooltipText()
	{
		return
		super.getTooltipText();
	}

}
