package programaciondmi.dca.ecosistemas.piedrahitarinconrojasrojas;

import java.util.List;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import programaciondmi.dca.core.EcosistemaAbstracto;
import programaciondmi.dca.core.EspecieAbstracta;
import programaciondmi.dca.core.interfaces.ICanibal;
import programaciondmi.dca.ejecucion.Mundo;

public class Cannibalbot extends EspecieAbstracta implements ICanibal {
	private int vida;
	private int velocidad;
	private int ciclo;
	private float fuerza;
	private float energia;
	private PVector dire;
	private PImage[] pjCanibal = new PImage[20];
	private int posicionPj = 0;
	private int contador = 7;
	private int moverCannibal = 0;
	private Random randomico;

	public Cannibalbot(EcosistemaAbstracto ecosistema) {
		super(ecosistema);
		this.randomico = new Random();
		this.x = randomico.nextInt(Mundo.ObtenerInstancia().getApp().width);
		this.y = randomico.nextInt(Mundo.ObtenerInstancia().getApp().height);
		this.vida = 4;
		this.velocidad = 3;
		this.fuerza = 110;
		this.energia = 100;

		int targetX = randomico.nextInt();
		int targetY = randomico.nextInt();
		cambiarDireccion(new PVector(targetX, targetY));
		ciclo = 0;

		// IMAGENES DEL PERSONAJE
		PApplet app = Mundo.ObtenerInstancia().getApp();

		// Canibal Trasero
		pjCanibal[0] = app.loadImage("DataTikiBots/Cannibalbot/cA001.png");
		pjCanibal[1] = app.loadImage("DataTikiBots/Cannibalbot/cA002.png");
		pjCanibal[2] = app.loadImage("DataTikiBots/Cannibalbot/cA003.png");
		pjCanibal[3] = app.loadImage("DataTikiBots/Cannibalbot/cA004.png");
		pjCanibal[4] = app.loadImage("DataTikiBots/Cannibalbot/cA005.png");

		// Canibal Perfil derecho
		pjCanibal[5] = app.loadImage("DataTikiBots/Cannibalbot/cD001.png");
		pjCanibal[6] = app.loadImage("DataTikiBots/Cannibalbot/cD002.png");
		pjCanibal[7] = app.loadImage("DataTikiBots/Cannibalbot/cD003.png");
		pjCanibal[8] = app.loadImage("DataTikiBots/Cannibalbot/cD004.png");
		pjCanibal[9] = app.loadImage("DataTikiBots/Cannibalbot/cD005.png");

		// Cannibal Frente
		pjCanibal[11] = app.loadImage("DataTikiBots/Cannibalbot/cF002.png");
		pjCanibal[12] = app.loadImage("DataTikiBots/Cannibalbot/cF003.png");
		pjCanibal[13] = app.loadImage("DataTikiBots/Cannibalbot/cF004.png");
		pjCanibal[14] = app.loadImage("DataTikiBots/Cannibalbot/cF005.png");

		// Cannibal perfil Izquierdo
		pjCanibal[15] = app.loadImage("DataTikiBots/Cannibalbot/cZ001.png");
		pjCanibal[16] = app.loadImage("DataTikiBots/Cannibalbot/cZ002.png");
		pjCanibal[17] = app.loadImage("DataTikiBots/Cannibalbot/cZ003.png");
		pjCanibal[18] = app.loadImage("DataTikiBots/Cannibalbot/cZ004.png");
		pjCanibal[19] = app.loadImage("DataTikiBots/Cannibalbot/cZ005.png");

		Thread nt = new Thread(this);
		nt.start();
	}



	public void mover() {
		if (energia > 0) {
			buscarAlimento();
			if (ciclo % 30 == 0) {
				// Definir una direccion aleatoria cada 3 segundos
				int targetX = randomico.nextInt();
				int targetY = randomico.nextInt();
				cambiarDireccion(new PVector(targetX, targetY));
				//System.out.println("CAMBIO DIRECCION!");
			}

			// moverse en la dirección asignada actualmente
			this.x += dire.x;
			this.y += dire.y;
			energia -= 0.01;
		}

		if (this.x > Mundo.ObtenerInstancia().getApp().width || this.x < 0) {
			this.dire.x *= -1;
		}
		if (this.y > Mundo.ObtenerInstancia().getApp().height || this.y < 0) {
			this.dire.y *= -1;
		}

	}

	public void run() {
		while (vida > 0) {
			mover();
			try {
				Thread.sleep(33);
				ciclo++;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	public void dibujar() {
		PApplet app = Mundo.ObtenerInstancia().getApp();

		// Pinto el personaje
		app.image(pjCanibal[contador], x, y, 100, 100);
		System.out.println("Personaje");
		
	}

	private void cambiarDireccion(PVector target) {
		PVector location = new PVector(x, y);
		dire = PVector.sub(target, location);
		dire.normalize();
		dire.mult(velocidad);
	}

	public void comer(EspecieAbstracta victima) {
		// TODO Auto-generated method stub

	}

	private void buscarAlimento() {
		List<EspecieAbstracta> todas = Mundo.ObtenerInstancia().getEspecies();
		for (int i = 0; i < todas.size(); i++) {
			comer(todas.get(i));
		}
	}

	@Override
	public boolean recibirDano(EspecieAbstracta lastimador) {
		if (PApplet.dist(x, y, lastimador.getX(), lastimador.getY()) <= (vida / 2)) {
			vida -= 4;
			try {
				lastimador.setEstado(EXTASIS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

}
