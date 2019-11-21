package nodes;

import java.util.Scanner;

import Interfaces.RobotExpressionNode;
import game.Parser;
import game.Robot;

public class SensorNode implements RobotExpressionNode {
	
	RobotExpressionNode sensor = null;

	@Override
	public Integer evaluate(Robot robot) {
		
		return sensor.evaluate(robot);
	}

	@Override
	public RobotExpressionNode parse(Scanner s) {
		if (s.hasNext(Parser.FUELLEFT)){
			sensor = new FuelLeftNode();
		} else if (s.hasNext(Parser.OPPLR)){
			sensor = new OppLRNode();
		} else if (s.hasNext(Parser.OPPFB)){
			sensor = new OppFBNode();
		} else if (s.hasNext(Parser.NUMBARRELS)){
			sensor = new NumBarrelsNode();
		} else if (s.hasNext(Parser.BARRELLR)){
			sensor = new BarrelLRNode();
		} else if (s.hasNext(Parser.BARRELFB)){
			sensor = new BarrelFBNode();
		} else if (s.hasNext(Parser.WALLDIST)){
			sensor = new WallDistNode();
		} else {
			Parser.fail("Invlaid Sensor", s);
		}
		sensor.parse(s);
		return this;
	}
	
	@Override
	public String toString(){
		return sensor.toString();
	}

}
