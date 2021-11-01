package midterm;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class genr extends ViewableAtomic
{
	protected job job;
	protected double int_arr_time;
	protected int count;
  
	public genr() 
	{
		this("genr", 30);
	}
  
	public genr(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		job = new job("none", 0, 0, false);
		count = 1;
		
		holdIn("active", int_arr_time);
	}
  
	public void deltext(double e, message x)
	{
		Continue(e);
//		if (phaseIs("active"))
//		{
//			for (int i = 0; i < x.getLength(); i++)
//			{
//				if (messageOnPort(x, "in", i))
//				{
//					holdIn("stop", INFINITY);
//				}
//			}
//		}
	}

	public void deltint()
	{
		job = new job("none", 0, 0, false);
		
		if (phaseIs("active"))
		{
			count = count + 1;
			
			holdIn("active", int_arr_time);
		}
	}

	public message out()
	{
		message m = new message();
		
		// 1~100 난수생성  
		int randNum = ((int)(Math.random() * 100) + 1); 
		m.add(makeContent("out", new job("job"+count, randNum, 0, false)));
		return m;
	}
  
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time
        + "\n" + " count: " + count;
	}

}
