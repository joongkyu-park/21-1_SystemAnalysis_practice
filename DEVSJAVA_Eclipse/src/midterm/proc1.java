package midterm;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class proc1 extends ViewableAtomic
{
  
	protected job job;
	protected double processing_time;

	public proc1()
	{
		this("proc1", 10);
	}

	public proc1(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		job = new job("none", 0, 0, false);
		
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
					job = (job)x.getValOnPort("in", i);
					
					holdIn("busy", processing_time);
					
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			job = new job("none", 0, 0, false);
			
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
			// 랜덤하게 생성된 숫자를 제곱하여 job에 저장  
			double sqrNum = Math.pow(job._randNum, 2);
			job = new job(job.getName(), job._randNum, sqrNum, false); 
			m.add(makeContent("out", job));
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText()
		+ "\n" + "job: " + job.getName();
	}

}

