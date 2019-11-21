package nodes;

import java.util.Scanner;

import Interfaces.RobotExpressionNode;
import game.Parser;
import game.Robot;

public class NumberNode implements RobotExpressionNode {
	
	private int value;
	
	public NumberNode(){
		this.value = -1;
	}
	
	public NumberNode(int value){
		this.value = value;
	}

	@Override
	public Integer evaluate(Robot robot) {
		
		return value;
	}

	@Override
	public RobotExpressionNode parse(Scanner s) {
		if (s.hasNext(Parser.NUM)){
			String numString = s.next(Parser.NUM);
			value = Integer.parseInt(numString);
		} else {
			Parser.fail("Num Fail. Expected: "+Parser.NUM.toString(), s);
		}
		return this;
	}

	@Override
	public String toString(){
		return Integer.toString(value);
	}
}
