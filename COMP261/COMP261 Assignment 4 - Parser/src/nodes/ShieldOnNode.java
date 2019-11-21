package nodes;

import java.util.Scanner;

import Interfaces.RobotProgramNode;
import game.Parser;
import game.Robot;

public class ShieldOnNode implements RobotProgramNode {

	@Override
	public void execute(Robot robot) {
		robot.setShield(true);

	}

	@Override
	public RobotProgramNode parse(Scanner s) {
		
		Parser.require(Parser.SHIELDON, "Fail. Expecting: "+Parser.SHIELDON.toString(), s);
		return this;
	}

	@Override
	public String toString(){
		return "shieldOn";
	}
}
