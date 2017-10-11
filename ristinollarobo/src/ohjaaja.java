import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import lejos.robotics.geometry.Line;
import lejos.robotics.geometry.Rectangle;
import lejos.robotics.mapping.LineMap;
public class ohjaaja {

public static void main(String[] args) {


		try {
			//String viesti = "";
			Socket s = new Socket("10.0.1.1", 1111);
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			DataInputStream in = new DataInputStream(s.getInputStream());



			Rectangle suorakulmio = new Rectangle(0, 0, 150, 100);
			Line[] janat = new Line[6];

			// rajaavan suorakulmion sivut
			janat[0] = new Line(0, 0, 150, 0);
			janat[1] = new Line(150, 0, 150, 100);
			janat[2] = new Line(0, 100, 150, 100);
			janat[3] = new Line(0, 0, 0, 100);

			// pystysuora este
			janat[4] = new Line(60, -20, 60, 70);
			janat[5] = new Line(80, 50, 150, 50);

			LineMap kartta = new LineMap(janat, suorakulmio);
			kartta.dumpObject(out);



		}catch (Exception iOException){
			iOException.printStackTrace();
		}
}
}






