package nodes;

import java.util.Scanner;

import Interfaces.RobotExpressionNode;
import game.Parser;
import game.Robot;

public class FuelLeftNode implements RobotExpressionNode {

	@Override
	public Integer evaluate(Robot robot) {
		return robot.getFuel();
	}

	@Override
	public RobotExpressionNode parse(Scanner s) {
		Parser.require(Parser.FUELLEFT, "Fail. Expected: "+Parser.FUELLEFT.toString(), s);
		return this;
	}
	
	@Override
	public String toString(){
		return Parser.FUELLEFT.toString();
	}

}
