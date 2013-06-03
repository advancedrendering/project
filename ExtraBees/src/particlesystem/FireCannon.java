package particlesystem;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Vector;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;

import templates.Blocks;
import templates.VectorMath;
import scenegraph.SceneRoot;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class FireCannon extends ParticleSystem {


	//texture
	private Texture raindrop = null; 
	
	public FireCannon(GLAutoDrawable drawable) {
		super(drawable);
		//load texture
		try {
			this.raindrop = TextureIO.newTexture(new File("textures/particle.bmp"), false);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// get the gl object
		GL gl = drawable.getGL();
		//check whether point size doesn't exceed size which is displayable by the graficscard.
		FloatBuffer point_size_arg = FloatBuffer.allocate(1);
		gl.glGetFloatv(GL.GL_POINT_SIZE_MAX_ARB, point_size_arg);
		if (settings.point_size > point_size_arg.get(0)){
			settings.point_size = point_size_arg.get(0);
		}
		
		this.setFragShaderEnabled(false);
		this.setVertexShaderEnabled(false);
	}

	@Override
	public ParticleSystemSettings initializeSettings() {
		ParticleSystemSettings loc_settings = new ParticleSystemSettings();
		
		loc_settings.capacity = 100;
		loc_settings.emitRate = 10f; //particles per millisecond
		//create external force
		float[] loc_external_force = {0.0f,0.0f, 50.0f};
		loc_settings.general_external_force = loc_external_force;
		//lifetime
		loc_settings.lifetime = 250.0f; //1.3 seconds
		loc_settings.point_size = 16.0f;
		
		ParticleEmitterSettings loc_emi_settings = new ParticleEmitterSettings();
		float[] min_init_acc = {0.0f, 0.0f, 0.0f};
		loc_emi_settings.min_init_acceleration = min_init_acc;
		float[] max_init_acc = {0.0f, 0.0f, 0.0f};
		loc_emi_settings.max_init_acceleration = max_init_acc;
		float[] min_init_velocity = {0.0f, 0.0f,-500.0f};
		loc_emi_settings.min_init_velocity = min_init_velocity;
		float[] max_init_velocity = {0.0f, 0.0f, -500.0f};
		loc_emi_settings.max_init_velocity = max_init_velocity;
		loc_emi_settings.min_init_lifetime = 0.0f; //0 milliseconds
		loc_emi_settings.max_init_lifetime = 200.0f; //200 milliseconds
		
		//create colors for particle system.
		float[] cornflowerblue = {1f, 0.3f, 0f};
		float[] lightblue = {1f, 0.4f, 0f};
//		float[] green = {0.0f, 1.0f, 0.0f};
		float[] aeroblue = {1f, 0.2f, 0f};
		float[] white = {1f, 0f, 0f};
		
		float[][] colors = new float[4][3];
		colors[0] = cornflowerblue;
		colors[1] = lightblue;
//		colors[2] = green;
		colors[2] = aeroblue;
		colors[3] = white;
		loc_emi_settings.colors = colors;
		
		loc_settings.emitter_settings = loc_emi_settings;
		//create Planar Emitter
		float[] position_vector = {0f, 0f, 0f};
		float radius = 0.01f;
		float[] first_dir_vector = {1.0f, 0.0f, 0.0f};
		float[] second_dir_vector = {0.0f, 0.0f, 1.0f};
		float min_first_scalar = -0.5f* (float)Math.PI;
		float max_first_scalar = 0.5f* (float)Math.PI;
		float min_second_scalar = -(float)Math.PI;
		float max_second_scalar = +(float)Math.PI;
		loc_settings.emitter = new FireCannonEmitter(loc_settings.emitRate, loc_settings.emitter_settings
				, position_vector, first_dir_vector, second_dir_vector,radius, min_first_scalar, max_first_scalar, min_second_scalar, max_second_scalar);
		
		return loc_settings;
	}
	
	@Override
	public void draw(GLAutoDrawable drawable){
		if(Blocks.fireCannonActive){
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
				update(15f);
				//draw particles
				float[] position = SceneRoot.getInstance(drawable).getHeli().getTranslation();
				position[0] = position[0];
				position[1] = position[1]+0.04f;
				position[2] = position[2]+0.12f;
				((FireCannonEmitter)settings.emitter).position_vector = position;
				float[] target = VectorMath.normalize(VectorMath.minus(position, SceneRoot.getInstance(drawable).getHeli().heliTarget));
				
				this.settings.general_external_force[0] = target[0]*-100f;
				this.settings.general_external_force[1] = target[1]*-150f;
				this.settings.general_external_force[2] = target[2]*-110f;

				//use point sprites
				
				FloatBuffer buffer_sizes = FloatBuffer.allocate(2);
				gl.glGetFloatv(GL.GL_ALIASED_POINT_SIZE_RANGE, buffer_sizes);
				
				gl.glEnable(GL.GL_TEXTURE_2D);
				
				this.raindrop.bind();
				
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
				
				gl.glBegin(GL.GL_POINTS);
					for (Particle par : this.active_particles){
						gl.glColor3fv(par.getColor(), 0); //last parameter (i.e. 0) is offset
						float[] loc_pos = par.getPosition();
						gl.glVertex3f(loc_pos[0],loc_pos[1] , loc_pos[2]);
					}
				gl.glEnd();
				gl.glDepthMask(true);	
				gl.glDisable(GL.GL_POINT_SPRITE_ARB);
				gl.glDisable(GL.GL_TEXTURE_2D);
				gl.glDisable(GL.GL_BLEND);
				gl.glDisable(GL.GL_COLOR_MATERIAL);
			}
		}
	}

	@Override
	public void postDraw(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}
}
