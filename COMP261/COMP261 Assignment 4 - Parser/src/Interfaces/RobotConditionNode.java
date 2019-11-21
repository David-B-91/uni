package Interfaces;

import java.util.Scanner;
import game.Robot;

public interface RobotConditionNode {
	
	public boolean evaluate(Robot robot);
	
	public RobotConditionNode parse(Scanner s);

}
