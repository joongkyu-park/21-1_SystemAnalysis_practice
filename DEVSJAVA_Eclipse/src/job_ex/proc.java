package job_ex;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class proc extends ViewableAtomic
{
  
	protected entity job;
	protected job job_ex;
	protected double processing_time;

	public proc()
	{
		this("proc", 20);
	}

	public proc(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
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
					job = (job)x.getValOnPort("in", i);
					
					// genr에서 보낸 job을 in-port로 받는데,
					// proc에서 job을 2개이상 받아서 계산을 한다던지, 특정 처리를 거쳐서 내보낼 수 있다.
					// 위와 같이하려면 job.java에서 그에 맞는 자료형을 추가해주고
					// genr.java의 messege_out 부분을 잘 설정하면 된다.
					holdIn("busy", processing_time);
					
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("busy"))
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

