import lejos.robotics.*;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
public class RoBottiArby {

	public static void main(String[] args){

		Behavior b1 = new liikkeet();

		Behavior [] bArray = {b1};

		Arbitrator arby = new Arbitrator(bArray);
		arby.go();
	}
}
