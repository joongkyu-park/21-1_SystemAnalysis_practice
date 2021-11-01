package Lab5;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class procQ extends ViewableAtomic
{
  
	// Queue에 대한 변수 추가
	protected Queue q;
	
	protected entity job;
	protected double processing_time;

	public procQ()
	{
		this("proc", 55);
	}

	public procQ(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		// Queue 초기화  
		q = new Queue();
		
		job = new entity("");
		
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
					job = x.getValOnPort("in", i);
					
					holdIn("busy", processing_time);
				}
			}
		} 
		else if (phaseIs("busy"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					entity tmp = x.getValOnPort("in", i);
					q.add(tmp);
				}
			}
		} 
	}
  
	public void deltint()
	{
		if (!q.isEmpty())
		{
			job = (entity) q.removeFirst();
			holdIn("busy", processing_time);
		}
		else
		{
			job = new entity("");
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
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

