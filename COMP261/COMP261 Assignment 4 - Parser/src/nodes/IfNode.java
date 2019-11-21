package nodes;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import Interfaces.RobotProgramNode;
import game.Parser;
import game.Robot;

public class IfNode implements RobotProgramNode {
	
	private ConditionNode condition = null;
	private RobotProgramNode ifBlock = null;
	private RobotProgramNode elseBlock = null;
	private Map<ConditionNode, BlockNode> elifConditions = new HashMap<ConditionNode, BlockNode>();
	
	

	@Override
	public void execute(Robot robot) {
		if (condition != null){
			if (condition.evaluate(robot)){
				ifBlock.execute(robot);
			} else if (elifConditions.size() > 0) {
				boolean done = false;
				for (ConditionNode n : elifConditions.keySet()){
					n.evaluate(robot);
					if(n.evaluate(robot)){
						elifConditions.get(n).execute(robot);
						done = true;
						break;
					}
				}
				if (elifConditions != null && !done){
					elseBlock.execute(robot);
				}
			} else if (elseBlock != null) {
				elseBlock.execute(robot);
			}
		}
		

	}

	@Override
	public RobotProgramNode parse(Scanner s) {
		
		Parser.require(Parser.IF, "Fail. Expected: "+Parser.IF.toString(), s);
		Parser.require(Parser.OPENPAREN, "Fail. Expected: "+Parser.OPENPAREN.toString(), s);
		
		condition = new ConditionNode();
		condition.parse(s);
		
		Parser.require(Parser.CLOSEPAREN, "Fail. Expected: "+Parser.CLOSEPAREN.toString(), s);
		
		ifBlock = new BlockNode();
		ifBlock.parse(s);
		
		while (true){
			if (s.hasNext(Parser.ELIF)){
				Parser.require(Parser.ELIF, "Fail. Expected: "+Parser.ELIF.toString(), s);
				Parser.require(Parser.OPENPAREN, "Fail. Expected: "+Parser.OPENPAREN.toString(), s);
				ConditionNode elifCondition = new ConditionNode();
				elifCondition.parse(s);
				Parser.require(Parser.CLOSEPAREN, "Fail. Expected: "+Parser.CLOSEPAREN.toString(), s);
				BlockNode elifBlock = new BlockNode();
				elifBlock.parse(s);
				elifConditions.put(elifCondition, elifBlock);
			} else {
				break;
			}
		}
		if (s.hasNext(Parser.ELSE)){
			Parser.require(Parser.ELSE, "Fail. Expected: "+Parser.ELSE.toString(), s);
			elseBlock = new BlockNode();
			elseBlock.parse(s);
		}
		return this;
	}
	
	@Override
	public String toString(){
		String s = "if(" + ifBlock.toString() + ") " + ifBlock.toString();
		for (ConditionNode n : elifConditions.keySet()){
			s+= "elif("+n.toString() +") "+elifConditions.get(n).toString();
		}
		if (elseBlock != null){
			s += " else "+elseBlock.toString();
		}
		
		return s;
	}
	

}
