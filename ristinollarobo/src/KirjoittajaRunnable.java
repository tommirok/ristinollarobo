import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class KirjoittajaRunnable implements Runnable {

	private String viesti = "";
	private Thread saie;

	public KirjoittajaRunnable(String viesti) {

		this.viesti = viesti;
	}

	public void start() {
		saie = new Thread(this, "");
		saie.start();
	}

	@Override
	public void run() {
		while(true){
			System.out.println("syötetään dataa robotille");
			try {Socket s = new Socket("10.0.1.1", 1111);
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			DataInputStream in = new DataInputStream(s.getInputStream());
			out.writeUTF(viesti);
			Thread.sleep(1000);
			out.flush();
			}catch (Exception interruptedException) {
				interruptedException.printStackTrace();
			}

	}

	}

}
