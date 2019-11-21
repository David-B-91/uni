package nodes;

import java.util.Scanner;

import Interfaces.RobotExpressionNode;
import game.Parser;
import game.Robot;

public class ExpressionNode implements RobotExpressionNode {
	
	private RobotExpressionNode expressionNode = null;

	@Override
	public Integer evaluate(Robot robot) {
		return expressionNode.evaluate(robot);
	}

	@Override
	public RobotExpressionNode parse(Scanner s) {
		if (s.hasNext(Parser.SENSOR)){
			expressionNode = new SensorNode();
		} else if (s.hasNext(Parser.NUM)){
			expressionNode = new NumberNode();
		} else  if (s.hasNext(Parser.OPERATION)){
			expressionNode = new OperationNode();
		} else {
			Parser.fail("Invalid expression", s);
		}
		expressionNode.parse(s);
		return this;
	}
	
	@Override
	public String toString(){
		return expressionNode.toString();
	}

}
