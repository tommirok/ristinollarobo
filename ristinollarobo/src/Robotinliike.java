import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;

public class Robotinliike {
	LineMap kartta = new LineMap();
	Pose alku = new Pose();

	final double halkaisija = 2.4f;
	final double raideleveys = 12.2f;

	RegulatedMotor vasen = new EV3LargeRegulatedMotor(MotorPort.C);
	RegulatedMotor kurki = new EV3LargeRegulatedMotor(MotorPort.A);
	RegulatedMotor saksi = new EV3LargeRegulatedMotor(MotorPort.B);
	RegulatedMotor oikea = new EV3LargeRegulatedMotor(MotorPort.D);
	@SuppressWarnings("deprecation")
	DifferentialPilot pilotti  = new DifferentialPilot(halkaisija, raideleveys, vasen, oikea);
	Navigator navi = new Navigator(pilotti);
	PoseProvider pp = new OdometryPoseProvider(pilotti);


}

