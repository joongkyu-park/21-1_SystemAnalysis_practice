package finalexam;
import java.awt.*;
import simView.*;

public class AIRPLANE_BOOKING extends ViewableDigraph
{
	final int AIRPLANES = 2;

	public AIRPLANE_BOOKING()
	{
		super("AIRPLANE_BOOKING");
    	
		ViewableAtomic user_genr = new user_genr("user_genr", 10);
		ViewableAtomic airplane_scheduler = new airplane_scheduler("airplane_scheduler", 0, AIRPLANES);
		ViewableAtomic checking = new checking("checking", 50000, AIRPLANES);
    	
		add(user_genr);
		add(airplane_scheduler);
		add(checking);
  
		addCoupling(user_genr, "out", airplane_scheduler, "in");     
		addCoupling(checking, "out", user_genr, "in");
		
		for(int i=1; i<=AIRPLANES; i++) {
			ViewableAtomic airplane;
			if(i == 1) {
				airplane = new airplane("airplane_A", 5);
			}
			else {
				airplane = new airplane("airplane_B", 5);
			}
			add(airplane);
			
			addCoupling(airplane_scheduler, "out"+i, airplane, "in");
			addCoupling(airplane, "out", checking, "arrived");
		}
	}

    public void layoutForSimView()
    {
        preferredSize = new Dimension(988, 646);
        ((ViewableComponent)withName("user_genr")).setPreferredLocation(new Point(0, 200));
        ((ViewableComponent)withName("airplane_scheduler")).setPreferredLocation(new Point(200, 200));
        ((ViewableComponent)withName("airplane_A")).setPreferredLocation(new Point(400, 100));
        ((ViewableComponent)withName("airplane_B")).setPreferredLocation(new Point(400, 300));
        ((ViewableComponent)withName("checking")).setPreferredLocation(new Point(600, 200));
        
        
    }
}
