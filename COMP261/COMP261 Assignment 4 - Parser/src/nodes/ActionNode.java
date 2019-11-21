package nodes;

import java.util.Scanner;

import Interfaces.RobotProgramNode;
import game.Parser;
import game.Robot;

public class ActionNode implements RobotProgramNode {
	
	RobotProgramNode actionNode = null;

	@Override
	public void execute(Robot robot) {
		actionNode.execute(robot);
	}

	@Override
	public RobotProgramNode parse(Scanner s) {
		
		if(s.hasNext(Parser.MOVE)){
			actionNode = new MoveNode();
		} else if (s.hasNext(Parser.TURNL)){
			actionNode = new TurnLNode();
		} else if (s.hasNext(Parser.TURNR)){
			actionNode = new TurnRNode();
		} else if (s.hasNext(Parser.TAKEFUEL)){
			actionNode = new TakeFuelNode();
		} else if (s.hasNext(Parser.WAIT)){
			actionNode = new WaitNode();
		} else if (s.hasNext(Parser.TURNAROUND)){
			actionNode = new TurnAroundNode();
		} else if (s.hasNext(Parser.SHIELDON)){
			actionNode = new ShieldOnNode();
		} else if (s.hasNext(Parser.SHIELDOFF)){
			actionNode = new ShieldOffNode();
		} else {
			Parser.fail("Invalid action node", s);
		}
		actionNode.parse(s);
		Parser.require(Parser.SEMICOL, "Fail. Expecting: ';'", s);
		return actionNode;
	}
	
	public String toString(){
		return actionNode.toString();
	}

}
