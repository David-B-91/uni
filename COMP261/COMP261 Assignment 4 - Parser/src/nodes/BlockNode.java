package nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Interfaces.RobotProgramNode;
import game.Parser;
import game.Robot;

public class BlockNode implements RobotProgramNode {
	
	private List<RobotProgramNode> blockNode = new ArrayList<RobotProgramNode>();

	@Override
	public void execute(Robot robot) {
		for (RobotProgramNode n : blockNode){
			n.execute(robot);
		}

	}

	@Override
	public RobotProgramNode parse(Scanner s) {
		RobotProgramNode statement = null;
		
		Parser.require(Parser.OPENBRACE, "Fail. Expecting: "+Parser.OPENBRACE.toString(), s);
		
		while(!s.hasNext(Parser.CLOSEBRACE)){
			
			if(s.hasNext()){
				statement = new StatementNode();
				statement.parse(s);
				blockNode.add(statement);
			} else {
				Parser.fail("Fail. Expecting a statement", s);
			}
		}
		Parser.require(Parser.CLOSEBRACE, "Fail. Expecting: "+Parser.CLOSEBRACE.toString(), s);
		return this;
	}
	
	@Override
	public String toString(){
		String s = "{";
		for (RobotProgramNode n : blockNode){
			s += " " + n.toString();
		}
		return (s+"}\n");
	}
}
