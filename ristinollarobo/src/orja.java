import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.DestinationUnreachableException;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.pathfinding.ShortestPathFinder;

public class orja {

static void reitinAjo(){

	}

	public static void main(String[] args) {

		try {
			// Muuttujat
			String viesti = "";
			LineMap kartta = new LineMap();

			// Yhteys
			ServerSocket serveri = new ServerSocket(1111);
			Socket s = serveri.accept();
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());

			// Pilotti
			final double halkaisija = 2.4f;
			final double raideleveys = 12.2f;
			RegulatedMotor vasen = new EV3LargeRegulatedMotor(MotorPort.C);
			RegulatedMotor oikea = new EV3LargeRegulatedMotor(MotorPort.D);
			RegulatedMotor kurki = new EV3LargeRegulatedMotor(MotorPort.A);
			RegulatedMotor saksi = new EV3LargeRegulatedMotor(MotorPort.B);

			DifferentialPilot pilotti = new DifferentialPilot(halkaisija, raideleveys, vasen, oikea);
			Navigator navi = new Navigator(pilotti);
			PoseProvider pp = new OdometryPoseProvider(pilotti);

			// Ladataan kartta
			kartta.loadObject(in);
			viesti = "Kartta ladattu.";
			out.writeUTF(viesti);
			out.flush();

			// Lyhimmän reitin etsiminen
			ShortestPathFinder search = new ShortestPathFinder(kartta);

			// Pisteet
			Pose nSij = new Pose(0, 0, 0);
			Waypoint kohteet[] = new Waypoint[3];
			kohteet[0] = new Waypoint(120.0, 80.0);
			kohteet[1] = new Waypoint(120.0, 20.0);
			kohteet[2] = new Waypoint(2.0, 2.0);

			nSij = pp.getPose();

			for(int i = 0; i < kohteet.length; i++){
				Path polku = null;
				try {
					viesti = "Etsitään parasta reittiä...";
					out.writeUTF(viesti);
					out.flush();
					polku = search.findRoute(nSij, kohteet[i]);
					viesti = "Reitti löydetty.";
					out.writeUTF(viesti);
					out.flush();
					navi.setPath(polku);
					navi.followPath(polku);
					navi.waitForStop();
					nSij = pp.getPose();
				} catch (DestinationUnreachableException e) {
					System.out.println("Virhe sijainneissa.");
				}
			}

			viesti = "Exit";
			out.writeUTF(viesti);
			out.flush();
			serveri.close();

		} catch (IOException e) {
			System.out.println("Virhe robotissa.");
		}



	}



	}


