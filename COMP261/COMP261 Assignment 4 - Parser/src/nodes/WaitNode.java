package nodes;

import java.util.Scanner;

import Interfaces.RobotExpressionNode;
import Interfaces.RobotProgramNode;
import game.Parser;
import game.Robot;

public class WaitNode implements RobotProgramNode {
	
	private RobotExpressionNode expressionNode;
	private int expressionValue = -1;

	
	@Override
	public void execute(Robot robot) {
		if (expressionNode != null){
			expressionValue = expressionNode.evaluate(robot);
			for (int i = 0 ; i < expressionValue ; i++){
				robot.idleWait();
			}
		} else {
			robot.idleWait();
		}
	}

	@Override
	public RobotProgramNode parse(Scanner s) {
		Parser.require(Parser.WAIT, "Fail. Expecting: "+Parser.WAIT.toString(), s);
		if(s.hasNext(Parser.OPENPAREN)){
			Parser.require(Parser.OPENPAREN, "Fail. Expected: "+Parser.OPENPAREN.toString(), s);
			expressionNode = new ExpressionNode();
			expressionNode.parse(s);
			Parser.require(Parser.CLOSEPAREN, "Fail. Expected: "+Parser.CLOSEPAREN.toString(), s);
		}
		return this;
	}
	
	@Override
	public String toString() {
		return "wait.";
	}

}
