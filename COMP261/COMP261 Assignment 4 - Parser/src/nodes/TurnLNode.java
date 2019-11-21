package nodes;

import java.util.Scanner;

import Interfaces.RobotProgramNode;
import game.Parser;
import game.Robot;

public class TurnLNode implements RobotProgramNode {

	@Override
	public void execute(Robot robot) {
		robot.turnLeft();
	}

	@Override
	public RobotProgramNode parse(Scanner s) {
		Parser.require(Parser.TURNL, "Fail. Expecting: "+Parser.TURNL.toString(), s);
		return this;
	}
	
	public String toString(){
		return "turnL";
	}

}
