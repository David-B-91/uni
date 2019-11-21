package nodes;

import java.util.Scanner;

import Interfaces.RobotExpressionNode;
import game.Parser;
import game.Robot;

public class NumBarrelsNode implements RobotExpressionNode {

	@Override
	public Integer evaluate(Robot robot) {
		
		return robot.numBarrels();
	}

	@Override
	public RobotExpressionNode parse(Scanner s) {
		Parser.require(Parser.NUMBARRELS, "Fail. Expected: "+Parser.NUMBARRELS.toString(), s);
		return this;
	}
	
	@Override
	public String toString(){
		return Parser.NUMBARRELS.toString();
	}

}
