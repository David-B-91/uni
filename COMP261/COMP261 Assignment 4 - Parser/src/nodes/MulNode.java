package nodes;

import java.util.Scanner;

import Interfaces.RobotExpressionNode;
import game.Parser;
import game.Robot;

public class MulNode implements RobotExpressionNode {

	private ExpressionNode expressionOne = null;
	private ExpressionNode expressionTwo = null;
	private int valueOne = -1;
	private int valueTwo = -1;

	@Override
	public Integer evaluate(Robot robot) {
		valueOne = expressionOne.evaluate(robot);
		valueTwo = expressionTwo.evaluate(robot);
		return valueOne * valueTwo;
	}

	@Override
	public RobotExpressionNode parse(Scanner s) {
		Parser.require(Parser.MUL, "Fail. Expected: "+Parser.MUL.toString(), s);
		Parser.require(Parser.OPENPAREN, "Fail. Expected: "+Parser.OPENPAREN, s);
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
		return String.format("mul ( %s, %s )", expressionOne.toString(), expressionTwo.toString());
	}

}
