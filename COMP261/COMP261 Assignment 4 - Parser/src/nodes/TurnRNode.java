package nodes;

import java.util.Scanner;

import Interfaces.RobotProgramNode;
import game.Parser;
import game.Robot;

public class TurnRNode implements RobotProgramNode {

	@Override
	public void execute(Robot robot) {
		robot.turnRight();

	}

	@Override
	public RobotProgramNode parse(Scanner s) {
		
		Parser.require(Parser.TURNR, "Fail. Expecting: "+Parser.TURNR.toString(), s);
		return this;
	}

	public String toString(){
		return "turnR";
	}
}
