package Lab8;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class add extends ViewableAtomic
{
	protected double processing_time;
	
	protected int result; // 내가 메세지를 통해 보낼 숫자  
	protected request req_msg; // 클라이언트로부터 받을 메세지  
	protected result res_msg; // 클라이언트에게 보낼 메세지  

	public add()
	{
		this("add", 20);
	}

	public add(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		result = 0;
		req_msg = new request("none", 0, 0);
		res_msg = new result("none", 0);
		
		holdIn("passive", INFINITY);
	}

	public void deltext(double e, message x)  
	{
		// 클라이언트가 보내준 메세지를 받는다.  
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					req_msg = (request)x.getValOnPort("in", i);
					
					result = req_msg.num1 + req_msg.num2;
					
					System.out.println("result is "+ result);
					
					holdIn("busy", processing_time);
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			result = 0;
			req_msg = new request("none", 0, 0);
			res_msg = new result("none", 0);
			
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
			m.add(makeContent("out", new result(Integer.toString(result), result)));
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText();
	}

}

