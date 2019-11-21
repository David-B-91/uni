package nodes;

import java.util.Scanner;

import Interfaces.RobotProgramNode;
import game.Parser;
import game.Robot;

public class LoopNode implements RobotProgramNode {

	RobotProgramNode nextNode = null;

	@Override
	public void execute(Robot robot) {
		while (true){
			nextNode.execute(robot);
		}

	}

	@Override
	public RobotProgramNode parse(Scanner s) {
		Parser.require(Parser.LOOP, "Fail. Expecting: "+Parser.LOOP.toString(), s);
		
		nextNode = new BlockNode();
		nextNode.parse(s);
		return nextNode;
	}
	
	@Override
	public String toString() {
		String s = nextNode.toString();
		return String.format("loop %s", s);
	}
}
