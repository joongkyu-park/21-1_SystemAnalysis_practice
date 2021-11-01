package finalexam;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class airplane_scheduler extends ViewableAtomic
{
	protected user user;
	protected double scheduling_time;
	protected int airplanes; // 비행기 대수(2대)  
	protected int outport_num; // outport_num이 1번이면 A비행기에 연결, 2번이면 B비행기에 연결  	

	public airplane_scheduler()
	{
		this("airplane_scheduler", 0, 2);
	}

	public airplane_scheduler(String name, double Scheduling_time, int _airplanes)
	{
		super(name);
		airplanes = _airplanes;
		
		addInport("in");
		
		for(int i=1; i<=airplanes; i++) {
			addOutport("out"+i);
		}
		
		scheduling_time = Scheduling_time;
	}
  
	public void initialize()
	{
		outport_num = -1;
		
		user = new user("none", 0, 0, 0, 0);
		
		holdIn("passive", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					user = (user)x.getValOnPort("in", i);
					
					// airplaneFlag == 1 -> A비행기 배정  
					// airplaneFlag == 2 -> B비행기 배정  
					outport_num = user._airplaneFlag; 
					
					holdIn("busy", scheduling_time);
				}
			}
		}
	}
	
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			outport_num = -1;
			
			user = new user("none", 0, 0, 0, 0);
			
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
			m.add(makeContent("out"+outport_num, user));
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText();
	}

}

