package Lab2;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class advanced_genr extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int count;
  
	public advanced_genr() 
	{
		this("advanced_genr", 20);
		// 시작값이 20이므로 20으로 설정.
	}
  
	public advanced_genr(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		count = 1;
		// job count가 1부터 시작므로 1로 설정.
		
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
			// if-else 문을 통하여 시그마 값을 변경해 주었다.
			count += 1;
			if(count == 2) {
				int_arr_time = 50;
				holdIn("active", int_arr_time);
			}
			else {
				if(count%2 == 0) {
					int_arr_time = 50;
					holdIn("active", int_arr_time);
				} else {
					int_arr_time = 40;
					holdIn("active", int_arr_time);
				}
			}
			
		}
	}

	public message out()
	{	
		message m = new message();
		m.add(makeContent("out",new entity("job" + count)));

		
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