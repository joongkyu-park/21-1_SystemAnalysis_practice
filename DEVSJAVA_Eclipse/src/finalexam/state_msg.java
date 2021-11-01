package finalexam;
import GenCol.*;

public class state_msg extends entity
{   
	int _count;
	int _typeOfPlane;
	int _typeOfAction;
	int _airlineFood;
	int _ageOfUser;
	int _numberOfSeat;

	
	public state_msg(String name, int count, int typeOfPlane, int typeOfAction, int airlineFood, int ageOfUser, int numberOfSeat)
	{  
		super(name); 
		_count = count;
		_typeOfPlane = typeOfPlane;
		_typeOfAction = typeOfAction;
		_airlineFood = airlineFood;
		_ageOfUser = ageOfUser;
		_numberOfSeat = numberOfSeat;
	}
	
}
