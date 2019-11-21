package nodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Interfaces.RobotProgramNode;
import game.Robot;

public class ProgramNode implements RobotProgramNode {

	private List<RobotProgramNode> statements = new ArrayList<RobotProgramNode>();

	@Override
	public void execute(Robot robot) {
		for (RobotProgramNode node : statements){
			node.execute(robot);
		}
	}

	@Override
	public RobotProgramNode parse(Scanner s) {
		StatementNode newStatement;
		while (s.hasNext()){
			newStatement = new StatementNode();
			statements.add(newStatement.parse(s));
		}
		
		return this;
	} 
	
	@Override
	public String toString() {
		String s = "";
		for (RobotProgramNode node : statements) {
			s += node.toString() + '\n';
		}
		return s;
	}
	
}
