package Lab3;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class proc extends ViewableAtomic
{
  
	protected entity job;
	protected double processing_time;

	public proc()
	{
		this("proc", 40);
	}

	public proc(String name, double Processing_time)
	{
		super(name);
    
		addOutport("out");
		addInport("in");
    
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		job = new entity("");
		// 지난 시간엔 input이 없었기 때문에 job에 대한 초기화가 없었었다. processor input 있으므로 job 초기화 실행.
		
		holdIn("passive", INFINITY);
		// 여기서 INFINIY의 뜻은, 간섭이 있기 전까지 현재 상태(passive)를 무한정 유지히라.

	}

	public void deltext(double e, message x)
	// input이 들어오는 순간, busy가 시작되는 순간을 판단 	
	{
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if(messageOnPort(x, "in", i))  // message x를 통해서, in 포트로, i번째 job(input) 들어오게 된다. 여기선 input이 1개니까 message의 길이도 1이겠지? 	
				{
					job = x.getValOnPort("in", i);
					// in포트로 i번째 job이 들어온다면,
					
					holdIn("busy", processing_time);
					// processing_time 만큼 "busy"상태 유지하라. 
				}

			}
		}
	}
  
	public void deltint()
	// busy 상태가 끝나는 순간을 판단. 초기화 상태와 같다. 다시 job을 초기화해서 input받을준비, 간섭올 때 까지 passive.
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
		
		if(phaseIs("busy")) {
			m.add(makeContent("out",job));
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