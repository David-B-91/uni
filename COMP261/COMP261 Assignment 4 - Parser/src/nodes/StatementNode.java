package nodes;

import java.util.Scanner;

import Interfaces.RobotProgramNode;
import game.Robot;
import game.Parser;

public class StatementNode implements RobotProgramNode {
	
	private RobotProgramNode next = null;

	public RobotProgramNode parse(Scanner s) {
		
		if(s.hasNext(Parser.ACTION)){
			next = new ActionNode();
		} else if (s.hasNext(Parser.LOOP)){
			next = new LoopNode();
		} else if (s.hasNext(Parser.IF)){
			next = new IfNode();
		} else if (s.hasNext(Parser.WHILE)){
			next = new whileNode();
		} else {
			Parser.fail("Unknown statement", s);
		}
		next.parse(s);
		return next;
	}

	@Override
	public void execute(Robot robot) {
		next.execute(robot);
	}

	public String toString(){
		return next.toString();
	}
}
