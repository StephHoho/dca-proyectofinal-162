package programaciondmi.dca.ecosistemas.piedrahitarinconrojasrojas;


import processing.core.PApplet;  
import processing.core.PImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import processing.core.PVector;
import programaciondmi.dca.core.EcosistemaAbstracto;
import programaciondmi.dca.core.EspecieAbstracta;
import programaciondmi.dca.core.interfaces.ICarnivoro;
import programaciondmi.dca.ejecucion.Mundo;

public class SaberBot extends EspecieAbstracta implements ICarnivoro{
	

	
	private int vida;
	private float fuerza;
	private int velocidad;
	private float energia;
	private int ciclo;
	private PVector dir;
	PImage[] pjcarnivoro = new PImage[14];
	int posicionpj = 0;
	int contador=7;
	int movSaberBot=0;
	private Random random;
	public SaberBot(EcosistemaAbstracto ecosistema) {
		super(ecosistema);
		this.random = new Random();
		this.x = random.nextInt(Mundo.ObtenerInstancia().getApp().width);
		this.y = random.nextInt(Mundo.ObtenerInstancia().getApp().height);
		this.vida = 5;
		this.velocidad =2;
		this.energia = 250;
		this.fuerza = 100;
		
		int targetX = random.nextInt();
		int targetY = random.nextInt();
		cambiarDireccion(new PVector(targetX, targetY));
		ciclo = 0;
		// PERSONAJE ASPECTO
		PApplet app = Mundo.ObtenerInstancia().getApp();
		//espalda del saberbot
		pjcarnivoro[0] = app.loadImage("DataTikiBots/saberbot/sA01.png");
		pjcarnivoro[1] = app.loadImage("DataTikiBots/saberbot/sA02.png");
		pjcarnivoro[2] = app.loadImage("DataTikiBots/saberbot/sA03.png");
		pjcarnivoro[3] = app.loadImage("DataTikiBots/saberbot/sA04.png");
		//perfil derecho del saberbot
		pjcarnivoro[4] = app.loadImage("DataTikiBots/saberbot/sD01.png");
		pjcarnivoro[5] = app.loadImage("DataTikiBots/saberbot/sD02.png");
		pjcarnivoro[6] = app.loadImage("DataTikiBots/saberbot/sD03.png");
		//frente del saberbot
		pjcarnivoro[7] = app.loadImage("DataTikiBots/saberbot/sF01.png");
		pjcarnivoro[8] = app.loadImage("DataTikiBots/saberbot/sF02.png");
		pjcarnivoro[9] = app.loadImage("DataTikiBots/saberbot/sF03.png");
		pjcarnivoro[10] = app.loadImage("DataTikiBots/saberbot/sF04.png");
		//perfil izquierdo del saberbot
		pjcarnivoro[11] = app.loadImage("DataTikiBots/saberbot/sZ01.png");
		pjcarnivoro[12] = app.loadImage("DataTikiBots/saberbot/sZ02.png");
		pjcarnivoro[13] = app.loadImage("DataTikiBots/saberbot/sZ03.png");
		
		
		Thread nt = new Thread(this);
		nt.start();
		
	}
	

	


	@Override
	public void comer(EspecieAbstracta victima) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mover() {
		if (energia > 0) {
			
			buscarComida();
			
					if (ciclo % 30 == 0) {
					// Definir una direccion aleatoria cada 3 segundos
					int targetX = random.nextInt();
					int targetY = random.nextInt();
					cambiarDireccion(new PVector(targetX, targetY));
					System.out.println("CAMBIO DIRECCION!");
					}
		
				
			// moverse en la dirección asignada actualmente
			this.x += dir.x;
			this.y += dir.y;
			energia -= 0.01;
		}
		
		if(this.x > Mundo.ObtenerInstancia().getApp().width || this.x < 0){
			this.dir.x*=-1;			
		}
		if(this.y > Mundo.ObtenerInstancia().getApp().height || this.y < 0){
			this.dir.y *= -1;			
		}
		

	}
		
	@Override
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
	


	@Override
	public void dibujar() {
				PApplet app = Mundo.ObtenerInstancia().getApp();
			

		// Image Personaje
		
		app.image(pjcarnivoro[contador], x, y, 100,100);
		app.fill(255,0,0);
		
	
		
	}
	
	private void cambiarDireccion(PVector target) {
		PVector location = new PVector(x, y);
		dir = PVector.sub(target, location);
		dir.normalize();
		dir.mult(velocidad);
		//System.out.println("[id=" + id + ", direcion=" + dir + "]");
	}
	
	
	@Override
	public boolean recibirDano(EspecieAbstracta lastimador) {
		if(PApplet.dist(x, y, lastimador.getX(), lastimador.getY()) <= (vida/2)){
			vida -= 5;
			try {
				lastimador.setEstado(EXTASIS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		
		return false;
	}
	
	private void buscarComida() {
		List<EspecieAbstracta> todas = Mundo.ObtenerInstancia().getEspecies();
		for (int i = 0; i < todas.size(); i++) {
			comer(todas.get(i));
		}
	}
	
	
	
}
