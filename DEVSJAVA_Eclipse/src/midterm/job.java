package midterm;
import GenCol.*;

public class job extends entity
{   
	int _randNum;
	double _sqrNum;
	boolean _isPrime;
	
	public job(String name, int randNum, double sqrNum, boolean isPrime)
	{  
		super(name); 
		_randNum = randNum;
		_sqrNum = sqrNum;
		_isPrime = isPrime;
	}
	
}
