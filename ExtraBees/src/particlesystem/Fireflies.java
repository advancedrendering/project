package particlesystem;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

import templates.Blocks;

public class Fireflies extends ParticleSystem {

	private Texture firefly;
	private float[] midPoint;

	public Fireflies(GLAutoDrawable drawable) {
		super(drawable);
		//load texture
		try {
			this.firefly = TextureIO.newTexture(new File("textures/particle.bmp"), false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.particles.clear();
		this.inactive_particles.clear();
		this.active_particles.clear();
		
		//create all particles and add them to the inactive particles list.
		for (int i = 0; i < this.settings.capacity; i++){
			Particle loc_par = new FireflyParticle(this.settings, false);
			this.particles.add(loc_par);
			this.inactive_particles.add(loc_par);
		}
	}

	@Override
	public ParticleSystemSettings initializeSettings() {
		ParticleSystemSettings loc_settings = new ParticleSystemSettings();
		
		loc_settings.capacity = 25;
		loc_settings.emitRate = 1.0f; //particles per millisecond
		//create external force
		float[] loc_external_force = {0.0f, 0.0f, 0.0f};
		loc_settings.general_external_force = loc_external_force;
		//lifetime
		loc_settings.lifetime = 15000.0f; //15 seconds
		loc_settings.point_size = 64.0f;
		
		ParticleEmitterSettings loc_emi_settings = new ParticleEmitterSettings();
		float[] min_init_acc = {0.0f, 0.0f, 0.0f};
		loc_emi_settings.min_init_acceleration = min_init_acc;
		float[] max_init_acc = {0.0f, 0.0f, 0.0f};
		loc_emi_settings.max_init_acceleration = max_init_acc;
		float[] min_init_velocity = {7.0f, 0.0f,0.0f}; //only the first component is interpreted... 
		loc_emi_settings.min_init_velocity = min_init_velocity;
		float[] max_init_velocity = {12.0f, 0.0f, 0.0f};
		loc_emi_settings.max_init_velocity = max_init_velocity;
		loc_emi_settings.min_init_lifetime = 0.0f; //0 milliseconds
		loc_emi_settings.max_init_lifetime = 2000.0f; //1200 milliseconds
		
		//create colors for particle system.
		float[] purplenavy = {0.3f, 0.31f, 0.57f};
		float[] forestgreen = {0.13f, 0.54f, 0.13f};
		float[] coral = {1.0f, 0.49f, 0.31f};
		float[] red = {1.0f, 0.0f, 0.0f};
		float[] lava = {0.28f, 0.23f, 0.19f};
		float[] violet = {0.55f, 0.0f, 1.0f};
		float[] golden_yellow = {1.0f, 0.87f, 0.0f};
		float[] golden_brown = {0.71f, 0.52f, 0.15f};
		float[] jade = {0.29f, 0.73f, 0.08f};
		float[] aeroblue = {0.49f, 0.73f, 0.91f};
		float[] white = {1.0f, 1.0f, 1.0f};
		
		float[][] colors = new float[11][4];
		colors[0] = purplenavy;
		colors[1] = forestgreen;
		colors[2] = coral;
		colors[3] = red;
		colors[4] = violet;
		colors[5] = golden_yellow;
		colors[6] = golden_brown;
		colors[7] = jade;
		colors[8] = white;
		colors[9] = aeroblue;
		colors[10] = lava;

		loc_emi_settings.colors = colors;
		
		loc_settings.emitter_settings = loc_emi_settings;
		//cretae firefly emitter
		float[] min_midPoint = {25.0f, 2.0f, 20.0f};
		float[] max_midPoint = {25.0f, 5.0f, 20.0f};
		float min_radius = 1.0f;
		float max_radius = 2.0f;
		float min_pitch =  0.5f *(float)Math.PI;
		float max_pitch = 1.5f * (float)Math.PI;
		float min_roll = - 0.5f * (float)Math.PI;
		float max_roll = 0.5f * (float)Math.PI;
		
		loc_settings.emitter = new FireflyEmitter(loc_settings.emitRate, loc_settings.emitter_settings
				, min_midPoint, max_midPoint, min_radius, max_radius, min_pitch, max_pitch, min_roll, max_roll);
		
		return loc_settings;
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
			//update the particle system
			update(elapsed_time);
			//draw particles
			
			//use point sprites
			
			FloatBuffer buffer_sizes = FloatBuffer.allocate(2);
			gl.glGetFloatv(GL.GL_ALIASED_POINT_SIZE_RANGE, buffer_sizes);
			
			gl.glEnable(GL.GL_TEXTURE_2D);
			
			this.firefly.bind();
			
			//Enable blending and set for particles
			gl.glEnable(GL.GL_BLEND);
			gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
		
			gl.glEnable(GL.GL_POINT_SPRITE_ARB);
			float[] quadratic = {1.0f, 0.0f, 0.01f};
			FloatBuffer buffer_quadratic = FloatBuffer.wrap(quadratic);
			
			gl.glPointParameterfvARB(GL.GL_POINT_DISTANCE_ATTENUATION_ARB, buffer_quadratic);
			gl.glPointParameterf(GL.GL_POINT_FADE_THRESHOLD_SIZE_ARB, 60.0f);
			FloatBuffer point_size_arg = FloatBuffer.allocate(1);
			gl.glGetFloatv(GL.GL_POINT_SIZE_MAX_ARB, point_size_arg);
			gl.glPointSize(settings.point_size);
			gl.glPointParameterfARB(GL.GL_POINT_SIZE_MAX_ARB, point_size_arg.get(0));
			gl.glPointParameterfARB(GL.GL_POINT_SIZE_MIN_ARB, 0.0f);
			
			//Tell it the max and min sizes we can use using our pre-filled array.
			gl.glPointParameterfARB(GL.GL_POINT_SIZE_MIN_ARB, buffer_sizes.get(0) );
			gl.glPointParameterfARB(GL.GL_POINT_SIZE_MAX_ARB, buffer_sizes.get(1) );
			
			//Tell OGL to replace the coordinates upon drawing.
			gl.glTexEnvi(GL.GL_POINT_SPRITE_ARB, GL.GL_COORD_REPLACE_ARB, GL.GL_TRUE);
					
			//Turn off depth masking so particles in front will not occlude particles behind them.
			gl.glDepthMask(false);
			
			gl.glEnable(GL.GL_COLOR_MATERIAL);
			
			for (Particle par : this.active_particles){
				FireflyParticle fpar = (FireflyParticle)par;
				gl.glPushMatrix();
				gl.glTranslatef(fpar.getMidPoint()[0], fpar.getMidPoint()[1], fpar.getMidPoint()[2]);
				gl.glRotatef((float)Math.toDegrees(fpar.getPitch()), 1.0f, 0.0f, 0.0f);
				gl.glRotatef((float)Math.toDegrees(fpar.getRoll()), 0.0f, 0.0f, 1.0f);
				gl.glBegin(GL.GL_POINTS);
				gl.glColor3fv(par.getColor(), 0); //last parameter (i.e. 0) is offset
				float[] loc_pos = par.getPosition();
				gl.glVertex3f(loc_pos[0],loc_pos[1] , loc_pos[2]);
				gl.glEnd();
				gl.glPopMatrix();
			}
			
			gl.glDepthMask(true);	
			gl.glDisable(GL.GL_POINT_SPRITE_ARB);
			gl.glDisable(GL.GL_TEXTURE_2D);
			gl.glDisable(GL.GL_BLEND);
			gl.glDisable(GL.GL_COLOR_MATERIAL);
		}
	}
}
