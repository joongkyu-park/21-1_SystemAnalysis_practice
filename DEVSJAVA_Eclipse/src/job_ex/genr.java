package job_ex;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class genr extends ViewableAtomic
{
	protected job job; // job 자료형식으로 job 변수선언  
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
		job = new job("none", 0); // job 변수 선언했기 때문에 초기화 해줘야겠지~?
		count = 1;
		
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
		job = new job("none", 0); 
		// deltint에서도 초기화 마찬가지~
		// 이렇게 초기화하지않으면 기본에 남아있던 job이 영향을 미쳐서 꼬일수 있다.
		
		if (phaseIs("active"))
		{
			count = count + 1;
			
			holdIn("active", int_arr_time);
		}
	}

	public message out()
	{
		message m = new message();
		// m.add(makeContent("out", new entity("job" + count)));
		// entity라는 자료형은 라이브러리에서 기본적으로 제공해주는거라
		// 인자로 새로운 걸 추가하고싶으면 job타입의 변수를 선언해줘야한다! 
		m.add(makeContent("out", new job("job", count)));
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
