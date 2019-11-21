package nodes;

import java.util.Scanner;

import Interfaces.RobotExpressionNode;
import game.Parser;
import game.Robot;

public class BarrelLRNode implements RobotExpressionNode {
	
	private RobotExpressionNode expression;

	@Override
	public Integer evaluate(Robot robot) {
		if (expression != null){
			int value = expression.evaluate(robot);
			return robot.getBarrelLR(value);
		} else {
			return robot.getClosestBarrelLR();
		}
		
	}

	@Override
	public RobotExpressionNode parse(Scanner s) {
		Parser.require(Parser.BARRELLR, "Fail. Expected: "+Parser.BARRELLR.toString(), s);
		
		if (s.hasNext(Parser.OPENPAREN)){
			Parser.require(Parser.OPENPAREN, "Fail. Expected: "+Parser.OPENPAREN.toString(), s);
			expression = new ExpressionNode();
			expression.parse(s);
			Parser.require(Parser.CLOSEPAREN, "Fail. Expected: "+Parser.CLOSEPAREN.toString(), s);
		}
		return this;
	}
	@Override
	public String toString(){
		return Parser.BARRELLR.toString();
	}

}
