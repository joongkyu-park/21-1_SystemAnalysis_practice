package job_ex;
import java.awt.*;
import simView.*;

public class gpt extends ViewableDigraph
{

	public gpt()
	{
		super("gpt");
    	
		ViewableAtomic g = new genr("g", 10);
		ViewableAtomic p = new proc("p", 10);
		ViewableAtomic t = new transd("t", 70);
    	
		add(g);
		add(p);
		add(t);
  
		addCoupling(g, "out", p, "in");
		addCoupling(g, "out", t, "ariv");
		
		addCoupling(p, "out", t, "solved");
     
		addCoupling(t, "out", g, "in");
	}

    
    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(988, 646);
        ((ViewableComponent)withName("t")).setPreferredLocation(new Point(639, 117));
        ((ViewableComponent)withName("p")).setPreferredLocation(new Point(465, 476));
        ((ViewableComponent)withName("g")).setPreferredLocation(new Point(59, 148));
    }
}
