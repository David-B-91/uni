package Interfaces;

import java.util.Scanner;

import game.Robot;

public interface RobotExpressionNode {
	
	public Integer evaluate(Robot robot);
	
	public RobotExpressionNode parse(Scanner s);

}
