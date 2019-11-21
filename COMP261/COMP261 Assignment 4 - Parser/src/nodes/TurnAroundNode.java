package nodes;

import java.util.Scanner;

import Interfaces.RobotProgramNode;
import game.Parser;
import game.Robot;

public class TurnAroundNode implements RobotProgramNode {

	@Override
	public void execute(Robot robot) {
		robot.turnAround();

	}

	@Override
	public RobotProgramNode parse(Scanner s) {
		Parser.require(Parser.TURNAROUND, "Fail. Expecting: "+Parser.TURNAROUND.toString(), s);
		return this;
	}
	
	public String toString(){
		return "turnAround";
	}

}
