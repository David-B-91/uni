package nodes;

import java.util.Scanner;

import Interfaces.RobotProgramNode;
import game.Parser;
import game.Robot;

public class ShieldOffNode implements RobotProgramNode {

	@Override
	public void execute(Robot robot) {
		robot.setShield(false);

	}

	@Override
	public RobotProgramNode parse(Scanner s) {
		Parser.require(Parser.SHIELDOFF, "Fail. Expecting: "+Parser.SHIELDOFF.toString(), s);
		return this;
	}

	@Override
	public String toString(){
		return "shieldOff";
	}
}
