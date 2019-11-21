package nodes;

import java.util.Scanner;

import Interfaces.RobotExpressionNode;
import Interfaces.RobotProgramNode;
import game.Parser;
import game.Robot;

public class MoveNode implements RobotProgramNode {
	
	private RobotExpressionNode expressionNode;
	private int expressionValue = -1;

	@Override
	public void execute(Robot robot) {
		if (expressionNode != null){
			expressionValue = expressionNode.evaluate(robot);
			for (int i = 0 ; i < expressionValue ; i++){
				robot.move();
			}
		} else {
			robot.move();
		}
	}

	@Override
	public RobotProgramNode parse(Scanner s) {
		
		Parser.require(Parser.MOVE, "fail. Expecting: "+Parser.MOVE.toString(), s);
		
		if (s.hasNext(Parser.OPENPAREN)){
			Parser.require(Parser.OPENPAREN, "Fail. Expected: "+Parser.OPENPAREN.toString(), s);
			expressionNode = new ExpressionNode();
			expressionNode.parse(s);
			if (s.hasNext(Parser.CLOSEPAREN)){
				Parser.require(Parser.CLOSEPAREN, "Fail. Expected: "+Parser.CLOSEPAREN, s);
			}
		}
		
		return this;
	}
	
	public String toString(){
		String s = "move";
		if (expressionNode != null) {
			s+= String.format(" %s", expressionNode.toString());
		}
		return s;
	}

}
