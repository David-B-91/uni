package nodes;

import java.util.Scanner;

import Interfaces.RobotConditionNode;
import Interfaces.RobotExpressionNode;
import game.Parser;
import game.Robot;

public class EqualNode implements RobotConditionNode {
	
	private RobotExpressionNode expressionOne = null;
	private RobotExpressionNode expressionTwo = null;

	@Override
	public boolean evaluate(Robot robot) {
		if (expressionOne.evaluate(robot) == expressionTwo.evaluate(robot)){
			return true;
		}
		return false;
	}

	@Override
	public RobotConditionNode parse(Scanner s) {
		Parser.require(Parser.EQUAL, "Fail. Expected: "+Parser.EQUAL.toString(), s);
		Parser.require(Parser.OPENPAREN, "Fail. Expected: "+Parser.OPENPAREN.toString(), s);
		expressionOne = new ExpressionNode();
		expressionOne.parse(s);
		Parser.require(Parser.COMMA, "Fail. Expected: "+Parser.COMMA.toString(), s);
		expressionTwo = new ExpressionNode();
		expressionTwo.parse(s);
		Parser.require(Parser.CLOSEPAREN, "Fail. Expected: "+Parser.CLOSEPAREN.toString(), s);
		return this;
	}
	
	@Override
	public String toString(){
		return "eq("+ expressionOne.toString() + ", "+ expressionTwo.toString()+")";
	}

}
