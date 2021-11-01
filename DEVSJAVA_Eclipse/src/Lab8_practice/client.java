package Lab8_practice;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class client extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int num1, num2;
	protected String op;
	protected request req_msg; 
	protected result res_msg;   
	
	public client() 
	{
		this("client", 30);
	}
  
	public client(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		num1 = (int)(Math.random()*100)+1;
		num2 = (int)(Math.random()*100)+1;
		
		int forOp = (int)(Math.random()*4)+1;
		
		if(forOp == 1) {
			op = "+";
		}
		else if(forOp == 2) {
			op = "-";
		}
		else if(forOp == 3) {
			op = "*";
		}
		else {
			op = "%";
		}
		req_msg = new request(num1 + " " + op + " " + num2, num1, num2, op);
		
		res_msg = new result("none", 0);
		
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
				}		
			}
		}
	}

	public void deltint()
	{		
		if (phaseIs("active"))
		{
			num1 = (int)(Math.random()*100)+1;
			num2 = (int)(Math.random()*100)+1;
			
			int forOp = (int)(Math.random()*4)+1;
			
			if(forOp == 1) {
				op = "+";
			}
			else if(forOp == 2) {
				op = "-";
			}
			else if(forOp == 3) {
				op = "*";
			}
			else {
				op = "%";
			}
			
			req_msg = new request(num1 + " " + op + " " + num2, num1, num2, op);
			
			holdIn("active", int_arr_time);
		}
	}

	public message out()
	{
		message m = new message();
		m.add(makeContent("out", req_msg));
		return m;
	}
  
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time;
	}

}
