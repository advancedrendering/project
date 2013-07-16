package particlesystem;


public class PlanarEmitter extends ParticleEmitter {

	private float[] position_vector;
	private float[] first_dir_vector;
	private float[] second_dir_vector;
	private float min_first_scalar;
	private float max_first_scalar;
	private float min_second_scalar;
	private float max_second_scalar;
	
	public PlanarEmitter(float emitRate, ParticleEmitterSettings settings, float[] position_vector , float[] first_dir_vector, float[] second_dir_vector, 
			float min_first_scalar, float max_first_scalar, float min_second_scalar, float max_second_scalar) {
		super(emitRate, settings);
		
		this.position_vector = position_vector;
		this.first_dir_vector = first_dir_vector;
		this.second_dir_vector = second_dir_vector;
		this.min_first_scalar = min_first_scalar;
		this.max_first_scalar = max_first_scalar;
		this.min_second_scalar = min_second_scalar;
		this.max_second_scalar = max_second_scalar;
	}
	
	/**
	 * Calculate the emission position.
	 * @return Return the emission position for a new particle.
	 */
	public float[] getEmissionPosition(){
		float[] rand_pos = new float[3];
		//generate random scalars.
		float rand_first_scalar = Utilities.createRandomf(this.min_first_scalar, this.max_first_scalar);
		float rand_second_scalar = Utilities.createRandomf(this.min_second_scalar, this.max_second_scalar);
		//calc position of particle
		rand_pos[0] = this.position_vector[0] + rand_first_scalar * this.first_dir_vector[0] + rand_second_scalar * this.second_dir_vector[0];
		rand_pos[1] = this.position_vector[1] + rand_first_scalar * this.first_dir_vector[1] + rand_second_scalar * this.second_dir_vector[1];
		rand_pos[2] = this.position_vector[2] + rand_first_scalar * this.first_dir_vector[2] + rand_second_scalar * this.second_dir_vector[2];
		return rand_pos;
	}

	@Override
	public void setEmitterPosition(float[] pos) {
		// TODO Auto-generated method stub
		
	}
}
