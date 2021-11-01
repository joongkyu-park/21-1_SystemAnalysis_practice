package Lab8_practice;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class server extends ViewableAtomic
{
	
	protected Queue q;
	protected int result; 
	protected request req_msg; 
	protected result res_msg; 
	protected double processing_time;
	
	public server()
	{
		this("server", 20);
	}

	public server(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
	
	public void initialize()
	{
		q = new Queue();
		result = 0;
		req_msg = new request("none", 0, 0, "none");
		res_msg = new result("none", 0);
		
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
					req_msg = (request)x.getValOnPort("in", i);
				
//					if(req_msg.getName() == "none") {
//						result = 0;
//						System.out.println("result is "+ result);
//						holdIn("busy", processing_time);
//					}
//					else {
						if(req_msg.op == "+") {
							result = req_msg.num1 + req_msg.num2;
							processing_time = 10;
						}
						else if(req_msg.op == "-") {
							result = req_msg.num1 - req_msg.num2;
							processing_time = 20;
						}
						else if(req_msg.op == "*") {
							result = req_msg.num1 * req_msg.num2;
							processing_time = 40;
						}
						else {
							result = req_msg.num1 % req_msg.num2;
							processing_time = 50;
						}
						
						System.out.println("result is "+ result);
						
						holdIn("busy", processing_time);
//					}
				}
			}
		}
		else if (phaseIs("busy"))
		{
			for (int i = 0; i < x.size(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					request temp = (request)x.getValOnPort("in", i);
					q.add(temp);
				}
			}
		}
	}
	
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			if (!q.isEmpty())
			{
				req_msg = (request) q.removeFirst();
				
				if(req_msg.op == "+") {
					result = req_msg.num1 + req_msg.num2;
					processing_time = 10;
				}
				else if(req_msg.op == "-") {
					result = req_msg.num1 - req_msg.num2;
					processing_time = 20;
				}
				else if(req_msg.op == "*") {
					result = req_msg.num1 * req_msg.num2;
					processing_time = 40;
				}
				else {
					result = req_msg.num1 % req_msg.num2;
					processing_time = 50;
				}
				
				System.out.println("result is "+ result);
				
				holdIn("busy", processing_time);
			}
			else
			{
				result = 0;
				req_msg = new request("none", 0, 0, "none");
				res_msg = new result("none", 0);
				
				holdIn("passive", INFINITY);
			}
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
        super.getTooltipText()
        + "\n" + "queue length: " + q.size()
        + "\n" + "queue itself: " + q.toString();
	}

}



