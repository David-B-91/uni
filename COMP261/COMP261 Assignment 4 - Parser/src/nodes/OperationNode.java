package nodes;

import java.util.Scanner;

import Interfaces.RobotExpressionNode;
import game.Parser;
import game.Robot;

public class OperationNode implements RobotExpressionNode {
	
	RobotExpressionNode operation = null;

	@Override
	public Integer evaluate(Robot robot) {
		return operation.evaluate(robot);
	}

	@Override
	public RobotExpressionNode parse(Scanner s) {
		if (s.hasNext(Parser.ADD)){
			operation = new AddNode();
		} else if (s.hasNext(Parser.SUB)){
			operation = new SubNode();
		} else if (s.hasNext(Parser.MUL)){
			operation = new MulNode();
		} else if (s.hasNext(Parser.DIV)){
			operation = new DivNode();
		} else {
			Parser.fail("Invalid Operation", s);
		}
		operation.parse(s);
		return operation;
	}
	
	@Override
	public String toString(){
		return operation.toString();
	}

}
