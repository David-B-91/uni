package nodes;

import java.util.Scanner;

import Interfaces.RobotConditionNode;
import game.Parser;
import game.Robot;

public class NotNode implements RobotConditionNode {

	ConditionNode condition = null;

	@Override
	public boolean evaluate(Robot robot) {
		if (condition.evaluate(robot)){
			return false;
		} else {
			return true;
		}
	}

	@Override
	public RobotConditionNode parse(Scanner s) {
		Parser.require(Parser.NOT, "Fail. Expected: "+Parser.NOT.toString(), s);
		Parser.require(Parser.OPENPAREN, "Fail. Expected: "+Parser.OPENPAREN.toString(), s);
		condition = new ConditionNode();
		condition.parse(s);
		Parser.require(Parser.CLOSEPAREN, "Fail. Expected: "+Parser.CLOSEPAREN.toString(), s);
		return this;
	}
	
	@Override
	public String toString(){
		return "not("+condition.toString()+")";
	}

}
