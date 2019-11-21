package nodes;

import java.util.Scanner;

import Interfaces.RobotExpressionNode;
import game.Parser;
import game.Robot;

public class OppLRNode implements RobotExpressionNode {

	@Override
	public Integer evaluate(Robot robot) {
		return robot.getOpponentLR();
		
	}

	@Override
	public RobotExpressionNode parse(Scanner s) {
		Parser.require(Parser.OPPLR, "Fail. Expected: "+Parser.OPPLR.toString(), s);
		return this;
	}
	
	@Override
	public String toString(){
		return Parser.OPPLR.toString();
	}

}
