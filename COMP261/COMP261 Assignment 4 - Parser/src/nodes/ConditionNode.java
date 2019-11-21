package nodes;

import java.util.Scanner;

import Interfaces.RobotConditionNode;
import game.Parser;
import game.Robot;

public class ConditionNode implements RobotConditionNode {
	private RobotConditionNode node = null;

	@Override
	public boolean evaluate(Robot robot) {
		return node.evaluate(robot);
	}

	@Override
	public RobotConditionNode parse(Scanner s) {
		if(s.hasNext(Parser.LESSTHAN)){
			node = new LessThanNode();
		} else if (s.hasNext(Parser.GREATERTHAN)){
			node = new GreaterThanNode();
		} else if (s.hasNext(Parser.EQUAL)){
			node = new EqualNode();
		} else if (s.hasNext(Parser.AND)){
			node = new AndNode();
		} else if (s.hasNext(Parser.OR)){
			node = new OrNode();
		} else if (s.hasNext(Parser.NOT)){
			node = new NotNode();
		} else {
			Parser.fail("Invlaid Condition", s);
		}
		node.parse(s);
		return node;
	}

	@Override
	public String toString(){
		return node.toString();
	}
}
