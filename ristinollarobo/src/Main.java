import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.stage.Stage;
import lejos.robotics.navigation.Navigator;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
public class Main extends Application {

	int player1, player2, n, ruutujenm‰‰r‰, x, x2, y, y2, k, k2, k3, voitto;
	Ruutu[] ruudut = new Ruutu[16];
	String string, koko, tulos;
	Optional<String> result, valinta;


	private String dialogi() {
		List<String> choices = new ArrayList<>();
		choices.add("3*3");
		choices.add("4*4");
		choices.add("5*5");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("3*3", choices);
		dialog.setTitle("Choice Dialog");
		dialog.setHeaderText("Taulukon koko");
		dialog.setContentText("Valitse koko:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		tulos = result.get();
		return tulos;
	}

	private void voitto(String valinta, GraphicsContext gc) {
		k = 0;
		gc.setFill(Color.ALICEBLUE);
		if (valinta == "3*3") {
			for (int i = 0; i < 7; i += 3) {
				k = ruudut[i].getKaytetty();
				k2 = ruudut[i + 1].getKaytetty();
				k3 = ruudut[i + 2].getKaytetty();
				if (k == 1 && k == k2 && k2 == k3) {
					gc.fillText("Pelaaja1 Voitti", 50, 50, 100);
				}
				else if  (k == 2 && k == k2 && k2 == k3) {
					gc.fillText("Pelaaja2 Voitti", 700, 50);
				}
			}
			for (int i = 0; i < 3; i += 1) {
				k = ruudut[i].getKaytetty();
				k2 = ruudut[i + 3].getKaytetty();
				k3 = ruudut[i + 6].getKaytetty();
				if (k == 1 && k == k2 && k2 == k3) {
					gc.fillText("Pelaaja1 Voitti", 50, 50);
				}
				else if  (k == 2 && k == k2 && k2 == k3) {
					gc.fillText("Pelaaja2 Voitti", 700, 50);
				}
			}
			int i = 0;
			k = ruudut[i].getKaytetty();
			k2 = ruudut[i + 4].getKaytetty();
			k3 = ruudut[i + 8].getKaytetty();
			if (k == 1 && k == k2 && k2 == k3) {
				gc.fillText("Pelaaja1 Voitti", 50, 50);
			}
			else if  (k == 2 && k == k2 && k2 == k3) {
				gc.fillText("Pelaaja2 Voitti", 700, 50);
			}
			k = ruudut[2].getKaytetty();
			k2 = ruudut[4].getKaytetty();
			k3 = ruudut[6].getKaytetty();
			if (k == 1 && k == k2 && k2 == k3) {
				gc.fillText("Pelaaja1 Voitti", 50, 50);
			}
			else if  (k == 2 && k == k2 && k2 == k3) {
				gc.fillText("Pelaaja2 Voitti", 700, 50);
			}
		}
	}

	private void teeRuudut(Ruutu[] ruudut, String valinta) {
		if (valinta == "3*3") {
			ruudut[0] = new Ruutu(100, 300, 100, 300, 0);
			ruudut[1] = new Ruutu(300, 500, 100, 300, 0);
			ruudut[2] = new Ruutu(500, 700, 100, 300, 0);
			ruudut[3] = new Ruutu(100, 300, 300, 500, 0);
			ruudut[4] = new Ruutu(300, 500, 300, 500, 0);
			ruudut[5] = new Ruutu(500, 700, 300, 500, 0);
			ruudut[6] = new Ruutu(100, 300, 500, 700, 0);
			ruudut[7] = new Ruutu(300, 500, 500, 700, 0);
			ruudut[8] = new Ruutu(500, 700, 500, 700, 0);
			ruutujenm‰‰r‰ = 9;
		}
		if (valinta == "4*4") {
			ruutujenm‰‰r‰ = 16;
			for (int i = 0; i < 4; i++) {
				ruudut[i] = new Ruutu(100 + (i * 150), 250 + (i * 150), 100, 250, 0);
			}
			for (int i = 0; i < 4; i++) {
				ruudut[i + 4] = new Ruutu(100 + (i * 150), 250 + (i * 150), 250, 400, 0);
			}
			for (int i = 0; i < 4; i++) {
				ruudut[i + 8] = new Ruutu(100 + (i * 150), 250 + (i * 150), 400, 550, 0);
			}
			for (int i = 0; i < 4; i++) {
				ruudut[i + 12] = new Ruutu(100 + (i * 150), 250 + (i * 150), 550, 700, 0);
			}
		}

	}

	private void handleLayers(GraphicsContext gc, Canvas canvas, Ruutu[] ruudut, String valinta, DataOutputStream out) {
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				gc.setStroke(Color.WHITE);
				int hiirenX, hiirenY;
				hiirenX = (int) e.getX();
				hiirenY = (int) e.getY();
				if (valinta == "3*3") {
					n = 9;
				} else if (valinta == "4*4") {
					n = 16;
				}

				for (int i = 0; i < n; i++) {
					if (hiirenX > ruudut[i].getX() && hiirenX < ruudut[i].getX2() && hiirenY > ruudut[i].getY()
							&& hiirenY < ruudut[i].getY2() && ruudut[i].getKaytetty() == 0 && player1 == player2) {
						gc.strokeLine((ruudut[i].getX() + 40), (ruudut[i].getY() + 40), (ruudut[i].getX2() - 40),
								(ruudut[i].getY2() - 40));
						gc.strokeLine((ruudut[i].getX() + 40), (ruudut[i].getY2() - 40), (ruudut[i].getX2() - 40),
								(ruudut[i].getY() + 40));
						ruudut[i].setKaytetty(1);
						//robo.valittuRuutu(ruudut, i);
						try {
							out.writeUTF(Integer.toString(i));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						voitto(valinta, gc);
						player1++;
					}

					if (hiirenX > ruudut[i].getX() && hiirenX < ruudut[i].getX2() && hiirenY > ruudut[i].getY()
							&& hiirenY < ruudut[i].getY2() && ruudut[i].getKaytetty() == 0 && player2 < player1) {
						if (valinta == "3*3") {
							gc.strokeOval((ruudut[i].getX() + 55), (ruudut[i].getY() + 55), 100, 100);
						} else if (valinta == "4*4") {
							gc.strokeOval((ruudut[i].getX() + 30), (ruudut[i].getY() + 30), 100, 100);
						}
						ruudut[i].setKaytetty(2);
						//robo.valittuRuutu(ruudut, i);
						try {
							out.writeUTF(Integer.toString(i));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						voitto(valinta, gc);
						player2++;
					}
				}
			}
		});
	}

	private void drawShapes(GraphicsContext gc, String valinta, Ruutu[] ruudut) {
		gc.setFill(Color.BLACK);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(5);
		if (valinta == "3*3") {
			gc.setFill(Color.BLACK);
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(5);
			for (int i = 0; i < ruutujenm‰‰r‰; i++) {
				x = ruudut[i].getX();
				y = ruudut[i].getY();
				gc.strokeRect(x, y, 200, 200);
			}
		}

		if (valinta == "4*4") {
			for (int i = 0; i < ruutujenm‰‰r‰; i++) {
				x = ruudut[i].getX();
				y = ruudut[i].getY();
				gc.strokeRect(x, y, 150, 150);
			}
		}
		/*
		 * gc.strokeLine(40, 10, 10, 40); gc.fillOval(10, 60, 30, 30);
		 * gc.strokeOval(60, 60, 30, 30); gc.fillRoundRect(110, 60, 30, 30, 10,
		 * 10); gc.strokeRoundRect(160, 60, 30, 30, 10, 10); gc.fillPolygon(new
		 * double[]{10, 40, 10, 40}, new double[]{210, 210, 240, 240}, 4);
		 * gc.strokePolygon(new double[]{60, 90, 60, 90}, new double[]{210, 210,
		 * 240, 240}, 4); gc.strokePolyline(new double[]{110, 140, 110, 140},
		 * new double[]{210, 210, 240, 240}, 4);
		 */
	}
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {

		primaryStage.setTitle("Ristinolla");
		koko = dialogi();
		// Text tulostus = new Text();
		Group root = new Group();
		// Taustat
		Canvas canvas = new Canvas(800, 800);
		Canvas tausta = new Canvas(800, 800);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		GraphicsContext gc2 = tausta.getGraphicsContext2D();
		gc2.setFill(Color.DARKSLATEGRAY);
		gc2.fillRect(0, 0, 800, 800);
		teeRuudut(ruudut, koko);
		drawShapes(gc, koko, ruudut);
		//Robotti robo = new Robotti();
	//	handleLayers(gc, canvas, ruudut, koko, robo);
		root.getChildren().add(tausta);
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

			try {
				String viesti = "";
				Socket s = new Socket("10.0.1.1", 1111);
				DataInputStream in = new DataInputStream(s.getInputStream());
				DataOutputStream out = new DataOutputStream(s.getOutputStream());

				while(viesti != "Exit"){
					System.out.println(viesti);
					viesti = in.readUTF();
					handleLayers(gc, canvas, ruudut, koko, out);
				}

				System.out.println(viesti);
				s.close();

			} catch (IOException e) {
				System.out.println("Virhe koneella");
			}

	}

}



