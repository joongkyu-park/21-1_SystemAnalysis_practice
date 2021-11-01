package midterm;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class proc2 extends ViewableAtomic
{
  
	protected job job;
	protected double processing_time;

	public proc2()
	{
		this("proc2", 10);
	}

	public proc2(String name, double Processing_time)
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
			// 랜덤하게 생성한 수를 소수판별하여 여부를 job에 저장  
			boolean isPrime;
			int factor = 2;
			while(true) {
				if(job._randNum % factor == 0)
				{
					isPrime = false;
					break;
				}
				else if(job._randNum>factor) {
					isPrime = true;
					break;
				}
				else {
					factor += 1;
				}
			}
			job = new job(job.getName(), job._randNum, job._sqrNum, isPrime);
			
			System.out.println("Random number -> " + job._randNum);
			System.out.println("Square of random number -> " + job._sqrNum);
			System.out.println("isPrime of random number -> " + job._isPrime);
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

