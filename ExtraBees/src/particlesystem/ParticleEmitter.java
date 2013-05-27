package particlesystem;

import java.util.ArrayList;

public abstract class ParticleEmitter {

	protected ParticleEmitterSettings settings = null;
	
	/**
	 * @return the settings
	 */
	public ParticleEmitterSettings getSettings() {
		return settings;
	}

	private float emitRate = 1.0f;
	
	//holds the number of particles to be inserted to hold on the average emission rate.
	private int num_of_part_insert;
	
	public ParticleEmitter(float emitRate, ParticleEmitterSettings settings){
		this.emitRate = emitRate;
		this.settings = settings;
		this.num_of_part_insert = 0;
	}
	
	/**
	 * Calculate the emission position.
	 * @return Return the emission position for a new particle.
	 */
	public abstract float[] getEmissionPosition();
	
	/**
	 * This methods emits particles at a predefined rate.
	 * @param elapsed_time The elapsed time since the last emitParticles call. (in nano seconds).
	 */
	public void emitParticles(ArrayList<Particle> inactive_particles, ArrayList<Particle> active_particles, float elapsed_time){
		//calculate the number of particles to emitted.
		this.num_of_part_insert = Math.round(this.getEmitRate() * elapsed_time);
		
		//list to collect particles which are alive again i.e. should be removed from inactive list.
		ArrayList<Particle> to_be_removed = new ArrayList<Particle>();
		
		//iterate over all inactive particles and emit new one (but only as long as the emission rate is obeyed.)
		for (Particle par : inactive_particles){
			//check whether are allowed to insert a particle.
			if (this.num_of_part_insert == 0){
				break;
			}
			else{
				//generate new random position (should lie on plane)
				float[] rand_pos = this.getEmissionPosition();
				
				//generate new random velocity
				float[] rand_velocity = Utilities.createRandom3f(this.settings.min_init_velocity, this.settings.max_init_velocity);
				
				//generate new random acceleration
				float[] rand_acc = Utilities.createRandom3f(this.settings.min_init_acceleration, this.settings.max_init_acceleration);
				
				//init age
				float rand_age = Utilities.createRandomf(this.settings.min_init_lifetime, this.settings.max_init_lifetime);
				//initialize Particle
				par.initialize(rand_pos, rand_velocity, rand_acc, rand_age, true);
				
				if (this.settings.colors.length != 0){
					//select new random color for particle.
					//number of possible colors
					int num_colors = this.settings.colors.length;
					int rand_color_index = Utilities.createRandom(0, num_colors);
					par.setColor(this.settings.colors[rand_color_index]);
				}
				
				//append newly initialized particle to active ones
				active_particles.add(par);
				
				//add particle to to_be_removed list
				to_be_removed.add(par);
				
				//decrement number of particles to be inserted.
				this.num_of_part_insert -= 1;			
			}
		}
		
		
		//delete active particles from inactive list.
		for (Particle par : to_be_removed){
			//delete particle from inactive list
			inactive_particles.remove(par);
		}
	}
	public void setColor(float[][] color){
		this.settings.colors = color;
	}

	/**
	 * Returns the emission rate in particle emissions per second.
	 * @return Rate in particle emissions per second.
	 */
	public float getEmitRate() {
		return emitRate;
	}

	/**
	 * Sets the emission rate in particles emissions per second.
	 * @param emitRate New particle emission rate.
	 */
	public void setEmitRate(float emitRate) {
		this.emitRate = emitRate;
	}
}
