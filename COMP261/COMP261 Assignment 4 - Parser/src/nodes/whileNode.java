package nodes;

import java.util.Scanner;

import Interfaces.RobotProgramNode;
import game.Parser;
import game.Robot;

public class whileNode implements RobotProgramNode {
	
	private ConditionNode condition;
	private BlockNode block;

	@Override
	public void execute(Robot robot) {
		while (true){
			if(condition.evaluate(robot)){
				block.execute(robot);
			} else {
				return;
			}
		}

	}

	@Override
	public RobotProgramNode parse(Scanner s) {
		Parser.require(Parser.WHILE, "Fail. Expected: "+Parser.WHILE.toString(), s);
		if (s.hasNext(Parser.OPENPAREN)){
			Parser.require(Parser.OPENPAREN, "Fail. Expected: "+Parser.OPENPAREN.toString(), s);
			if (s.hasNext(Parser.CONDITION)){
				condition = new ConditionNode();
				condition.parse(s);
			} else {
				Parser.fail("Fail. Expected: "+Parser.CONDITION.toString(), s);
			}
			if (s.hasNext(Parser.CLOSEPAREN)){
				Parser.require(Parser.CLOSEPAREN, "Fail. Expected: "+Parser.CLOSEPAREN.toString(), s);
			}
			block = new BlockNode();
			block.parse(s);
		}
		return this;
	}
	
	@Override 
	public String toString(){
		return "while("+condition.toString()+")"+block.toString();
	}

}
