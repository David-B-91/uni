package nodes;

import java.util.Scanner;

import Interfaces.RobotExpressionNode;
import game.Parser;
import game.Robot;

public class OppFBNode implements RobotExpressionNode {

	@Override
	public Integer evaluate(Robot robot) {
		
		return robot.getOpponentFB();
	}

	@Override
	public RobotExpressionNode parse(Scanner s) {
		Parser.require(Parser.OPPFB, "Fail. Expected: "+Parser.OPPFB.toString(), s);
		return this;
	}

	@Override
	public String toString(){
		return Parser.OPPFB.toString();
	}
}
