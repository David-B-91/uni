package nodes;

import java.util.Scanner;

import Interfaces.RobotProgramNode;
import game.Parser;
import game.Robot;

public class TakeFuelNode implements RobotProgramNode {

	@Override
	public void execute(Robot robot) {
		robot.takeFuel();

	}

	@Override
	public RobotProgramNode parse(Scanner s) {
		Parser.require(Parser.TAKEFUEL, "Fail. Exception: "+Parser.TAKEFUEL.toString(), s);
		
		return this;
	}

	public String toString(){
		return "takeFuel.";
	}
}
