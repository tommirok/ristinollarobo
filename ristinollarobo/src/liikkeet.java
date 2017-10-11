import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

public class liikkeet implements Behavior {
	private volatile boolean supressed;
	@Override
	public boolean takeControl() {
		// TODO Auto-
		return false;
	}

	@Override
	public void action() {
		
		supressed = false;

		while(supressed){

		}

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
