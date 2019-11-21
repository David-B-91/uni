package nodes;

import java.util.Scanner;

import Interfaces.RobotExpressionNode;
import game.Parser;
import game.Robot;

public class WallDistNode implements RobotExpressionNode {

	@Override
	public Integer evaluate(Robot robot) {
		return robot.getDistanceToWall();
		
	}

	@Override
	public RobotExpressionNode parse(Scanner s) {
		Parser.require(Parser.WALLDIST, "Fail. Expected: "+Parser.WALLDIST.toString(), s);
		return this;
	}
	
	@Override
	public String toString(){
		return Parser.WALLDIST.toString();
	}

}
