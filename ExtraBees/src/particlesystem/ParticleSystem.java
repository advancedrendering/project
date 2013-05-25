package particlesystem;

import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import scenegraph.SceneGraphNode;

public abstract class ParticleSystem extends SceneGraphNode {

	
	
	protected ArrayList<Particle> particles;
	protected ArrayList<Particle> active_particles;
	protected ArrayList<Particle> inactive_particles;
	
	protected ParticleEmitter emitter = null;
	protected ParticleEmitterSettings emitter_settings = null;
	protected ParticleSystemSettings settings = null;

	
	public ParticleSystem(GLAutoDrawable drawable) {
		super(drawable);
		//create empty list
		this.particles = new ArrayList<Particle>();
		this.active_particles = new ArrayList<Particle>();
		this.inactive_particles = new ArrayList<Particle>();
		
		//initialize the particle system settings.
		this.settings = initializeSettings();
	
		this.emitter_settings = this.settings.emitter_settings;
		this.emitter = this.settings.emitter;
		
		//create all particles and add them to the inactive particles list.
		for (int i = 0; i < this.settings.capacity; i++){
			Particle loc_par = new Particle(this.settings, false);
			this.particles.add(loc_par);
			this.inactive_particles.add(loc_par);
		}
		
		this.setFragShaderEnabled(false);
		this.setVertexShaderEnabled(false);
	}
	
	public abstract ParticleSystemSettings initializeSettings();
	
	/**
	 * Updates the particles system.
	 * @param elapsed_time The elapsed time.
	 */
	public void update(float elapsed_time){
		//list which contains all dead particles which should be removed from the inactive list.
		ArrayList<Particle> to_be_removed = new ArrayList<Particle>();
		
		//put dead particles in inactive-list
		for (Particle par : this.active_particles){
			if (par.isAlive() == false){
				this.inactive_particles.add(par);
				to_be_removed.add(par);
			}
		}
		
		//remove particles from inactive list.
		for (Particle par : to_be_removed){
			this.active_particles.remove(par);
		}
		
		//create new particles using the emitter
		this.emitter.emitParticles(this.inactive_particles, this.active_particles, elapsed_time);
		
		//update all active particles.
		for (Particle par : this.active_particles){
			par.update(elapsed_time);
		}
	}

	protected float lastTime = -1;
	
	@Override
	public void init(GLAutoDrawable drawable) {
		lastTime = -1;
	}

	@Override
	public void bindParameters() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void animate(GLAutoDrawable drawable){
		
	}

	@Override
	public void draw(GLAutoDrawable drawable){
		// get the gl object
		GL gl = drawable.getGL();
		
		if (lastTime  == -1){
			lastTime = (float)System.nanoTime() / 1000000.0f ;
		}
		else{
			float current_time = (float)System.nanoTime() / 1000000.0f;
			float elapsed_time = current_time - lastTime;
			lastTime = current_time;
			//update the particle system.
			update(elapsed_time);
			//draw particles
		
//			gl.glEnable(GL.GL_COLOR_MATERIAL);
//			gl.glColor3f(0.0f, 0.0f, 1.0f);
//			gl.glLineWidth(0.5f); // Set point size
			gl.glPointSize(5.0f);
			gl.glBegin(GL.GL_POINTS);
			for (Particle par : this.active_particles){
				float[] loc_pos = par.getPosition();
				gl.glVertex3f(loc_pos[0],loc_pos[1] , loc_pos[2]);
//				gl.glVertex3f(loc_pos[0],loc_pos[1]+0.4f , loc_pos[2]);
			}
			gl.glEnd();
//			gl.glLineWidth(1.0f); // Set The Line Width
//			gl.glColor3f(1.0f, 1.0f, 1.0f);
//			gl.glDisable(GL.GL_COLOR_MATERIAL);
		}
	}

}
