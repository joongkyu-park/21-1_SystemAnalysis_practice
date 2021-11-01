package Lab8;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int num1, num2; // 서버한테 보내주기 위한 숫자2개  
	protected request req_msg; // 클라이언트가 보내는 메세지 
	protected result res_msg; // 서버측에서 오는 메세지  
	// Q) 서버에서 오는 숫자를 위한 변수는?
	// A) 여기선 메세지만 받으면 됨. 그 숫자를 받아서 재가공 또는 다르게 이용하려면
	// 	  변수를 만들어줘야한다.
	
	public user() 
	{
		this("user", 30);
		// 예제에선 시간대를 이용하지 않으므로, 굳이 값을 변경하지 않는다.
	}
  
	public user(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		num1 = 0;
		num2 = 0;
		req_msg = new request("none", 0, 0);
		res_msg = new result("none", 0);
		
		holdIn("active", int_arr_time);
	}
  
	public void deltext(double e, message x)
	{
		// 외부적 요인 -> 서버측에서 메세지가 올 때 
		
		Continue(e);
		if (phaseIs("active"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					// holdIn("stop", INFINITY);
					
					// 이곳에서, 서버 측이 보낸 데이터를 사용할 수 있는데, 여기선 사용하지 않았다.
				}
			}
		}
	}

	public void deltint()
	{
		// 내부적 요인 -> 클라이언트가 메세지를 보낼 때
		
		if (phaseIs("active"))
		{
			num1 = (int)(Math.random()*100)+1;
			num2 = (int)(Math.random()*100)+1;
			// 1~100 범위의 정수 
			
			req_msg = new request(num1 + " + " + num2, num1, num2);
			
			holdIn("active", int_arr_time);
		}
	}

	public message out()
	{
		message m = new message();
		m.add(makeContent("out", req_msg));
		return m;
	}
  
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time;
	}

}
