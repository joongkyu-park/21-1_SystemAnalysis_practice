package finalexam;
import GenCol.*;

public class user extends entity
{   
	int _count;
	int _age;
	int _action;
	int _airplaneFlag;
	
	public user(String name, int count, int age, int action, int airplaneFlag)
	{  
		super(name); 
		_count = count;
		_age = age;
		_action = action;
		_airplaneFlag = airplaneFlag;
	}
	
}
