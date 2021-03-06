package Lab9;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class scheduler extends ViewableAtomic
{
  
	protected entity job;
	protected double scheduling_time;
	protected int NODE;
	protected int outport_num;	

	public scheduler()
	{
		this("proc", 0, 4);
		// 4는 노드의 개수를 의미  
	}

	public scheduler(String name, double Scheduling_time, int _node)
	{
		super(name);
		NODE = _node;
		
		addInport("in");
		for(int i=1; i<=NODE; i++) {
			addOutport("out"+i);
		}
		
		scheduling_time = Scheduling_time;
	}
  
	public void initialize()
	{
		outport_num = 1;
		job = new entity("");
		
		holdIn("passive", INFINITY);
	}

	// 외부적인 요인은 수정필요 없다.
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
					
					holdIn("busy", scheduling_time);
				}
			}
		}
	}
	
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			outport_num++;
			if(outport_num>NODE) {
				outport_num = 1;
			}
			job = new entity("");
			
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
			m.add(makeContent("out"+outport_num, job));
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

