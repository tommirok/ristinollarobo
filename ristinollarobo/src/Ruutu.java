



	public class Ruutu {
		private int x, x2, y, y2;
		private int kaytetty;
		private Ruutu ruutu;

		// Parametreinä koordinaatit
		public Ruutu(int x, int x2, int y, int y2, int kaytetty) {
			this.x = x;
			this.x2 = x2;
			this.y = y;
			this.y2 = y2;

		}

		public int getX(){
			return x;
		}

		public int getY(){
			return y;
		}

		public int getX2(){
			return x2;
		}

		public int getY2(){
			return y2;
		}

		public int getKaytetty(){
			return kaytetty;
		}

		public void setKaytetty(int kaytetty){
			this.kaytetty = kaytetty;
		}


	}

