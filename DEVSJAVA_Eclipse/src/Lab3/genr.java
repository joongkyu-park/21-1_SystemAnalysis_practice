package Lab3;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class genr extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int count;
  
	public genr() 
	{
		this("gene", 30); // 이 30이 Int_arr_time으로 들어간다.
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
		count = 0; // Job이 0부터 시작하기 때문
		holdIn("active", int_arr_time); // 이 함수가 state를 결정하는 듯. 처음 상태가 active이므로.
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

	public void deltint() // 시간(int_arr_time = 30)이 지남에 따라 count가 1씩 증가하고, 상태는 active로 유지된다.
	{
		if (phaseIs("active")) // 상태가 active 이면
		{
			count += 1;
			holdIn("active", int_arr_time);
		}
	}

	public message out()
	{	
		message m = new message(); // 메세지 객체 생성
		m.add(makeContent("out", new entity("Job" + count)));
		// 메세지가 out에서 나간다, "Job"+count 라는 아이템을 가지고

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