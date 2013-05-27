package particlesystem;

import java.util.Random;

public class FireEmitter extends ParticleEmitter {

	private float[] position_vector;
	private float[] first_dir_vector;
	private float[] second_dir_vector;
	private float min_first_scalar;
	private float max_first_scalar;
	private float min_second_scalar;
	private float max_second_scalar;
	private float radius;
	
	public FireEmitter(float emitRate, ParticleEmitterSettings settings, float[] position_vector,float[] first_dir_vector,float[] second_dir_vector, float radius,
			float min_first_scalar, float max_first_scalar, float min_second_scalar, float max_second_scalar)  {
		super(emitRate, settings);
		
		this.position_vector = position_vector;
		this.first_dir_vector = first_dir_vector;
		this.second_dir_vector = second_dir_vector;
		this.min_first_scalar = min_first_scalar;
		this.max_first_scalar = max_first_scalar;
		this.min_second_scalar = min_second_scalar;
		this.max_second_scalar = max_second_scalar;
		this.radius = radius;
	}

	@Override
	public float[] getEmissionPosition() {
		float[] rand_pos = new float[3];
		//generate random scalars.
		float rand_first_scalar = Utilities.createRandomf(this.min_first_scalar, this.max_first_scalar);
		float rand_second_scalar = Utilities.createRandomf(this.min_second_scalar, this.max_second_scalar);
		//calc xyz-position of particle in ellipsoid 
		rand_pos[0] = this.position_vector[0] + Utilities.createRandomf(0f, radius) * (float)Math.cos(rand_first_scalar)* (float)Math.cos(rand_second_scalar);
		rand_pos[1] = this.position_vector[1] + Utilities.createRandomf(0f, 1.2f*radius) * (float)Math.cos(rand_first_scalar)* (float)Math.sin(rand_second_scalar);
		rand_pos[2] = this.position_vector[2] + Utilities.createRandomf(0f, radius) * (float)Math.sin(rand_second_scalar);
		
		float[][] orange = {{1.0f,0.5f,0.0f}};
		float[][] blue = {{0.0f,0.0f,1.0f}};
		float[][] white = {{1.0f,1.0f,1.0f}};
		
		// flame colors
		if((rand_pos[1]-this.position_vector[1]) < (-0.25f*radius)) {
			// every particle in the lower part of the ellipsoid will be blue
			this.setColor(blue);
		}else if (Math.abs(rand_pos[0]-this.position_vector[0])>(0.005f*radius) && Math.abs(rand_pos[2]-this.position_vector[2])>(0.005f*radius)){
			// every particle at the border of the ellipsoid is orange
			this.setColor(orange);
		}else{
			// every particle within the ellipsoid will be white
			this.setColor(white);
		}
		return rand_pos;
	}

}
