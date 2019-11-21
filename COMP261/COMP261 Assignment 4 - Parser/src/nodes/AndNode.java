package nodes;

import java.util.Scanner;

import Interfaces.RobotConditionNode;
import game.Parser;
import game.Robot;

public class AndNode implements RobotConditionNode {
	
	ConditionNode conditionOne = null;
	ConditionNode conditionTwo = null;

	@Override
	public boolean evaluate(Robot robot) {
		if (conditionOne.evaluate(robot) && conditionTwo.evaluate(robot)){
			return true;
		} 
		return false;
	}

	@Override
	public RobotConditionNode parse(Scanner s) {
		Parser.require(Parser.AND, "Fail. Expected: "+Parser.AND.toString(), s);
		Parser.require(Parser.OPENPAREN, "Fail. Expected: "+Parser.OPENPAREN.toString(), s);
		conditionOne = new ConditionNode();
		conditionOne.parse(s);
		Parser.require(Parser.COMMA, "Fail. Expected: "+Parser.COMMA.toString(), s);
		conditionTwo = new ConditionNode();
		conditionTwo.parse(s);
		Parser.require(Parser.CLOSEPAREN, "Fail. Expected: "+Parser.CLOSEPAREN.toString(), s);
		return this;
	}
	
	@Override
	public String toString(){
		return "and("+conditionOne.toString()+", "+conditionTwo.toString()+")";
	}

}
