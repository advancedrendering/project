package particlesystem;

import java.util.ArrayList;

public class FireflyEmitter extends ParticleEmitter {

	
	private float[] min_midPoint = null;
	private float[] max_midPoint = null;
	
	private float min_radius;
	private float max_radius;
	
	private float min_pitch;
	private float max_pitch;
	
	private float min_roll;
	private float max_roll;
	
	public FireflyEmitter(float emitRate, ParticleEmitterSettings settings, float[] min_midPoint, float[] max_midPoint, float min_radius, float max_radius, 
			float min_pitch, float max_pitch , float min_roll, float max_roll) {
		super(emitRate, settings);
		this.min_midPoint = min_midPoint;
		this.max_midPoint = max_midPoint;
		this.min_radius = min_radius;
		this.max_radius = max_radius;
		this.min_pitch = min_pitch;
		this.max_pitch = max_pitch;
		this.min_roll = min_roll;
		this.max_roll = max_roll;
	}

	@Override
	public float[] getEmissionPosition(){		
		return new float[3]; //dummy method doing dummy things :-)
	}
	
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
			FireflyParticle fpar = (FireflyParticle)par;
			//check whether are allowed to insert a particle.
			if (this.num_of_part_insert == 0){
				break;
			}
			else{
				//create random midPoint
				float[] rand_midPoint = Utilities.createRandom3f(this.min_midPoint, this.max_midPoint);
				//create random radius (within bounds :-))
				float rand_radius = Utilities.createRandomf(this.min_radius, this.max_radius);
				//create random initial angle.
				float rand_angle = Utilities.createRandomf(0.0f, 2*(float)Math.PI);
				//new position
				float[] rand_pos = new float[3];
				rand_pos[0] = (float)Math.cos(rand_angle) * rand_radius;
				rand_pos[1] = (float)Math.sin(rand_angle) * rand_radius;
				rand_pos[2] = 0;
				
				//create random pitch
				float rand_pitch = Utilities.createRandomf(this.min_pitch, this.max_pitch);
				//create random roll
				float rand_roll = Utilities.createRandomf(this.min_roll, this.max_roll);
				
				//generate new random velocity
				float[] rand_velocity = Utilities.createRandom3f(this.settings.min_init_velocity, this.settings.max_init_velocity);
				
				//draw random direction (clockwise or counterclockwise)
				int rand_direction = Utilities.createRandom(-2, 1);
				if (rand_direction < 0){
					//only first component is important (the other two are irgenored so don't have to set em)
					rand_velocity[0] = rand_velocity[0] * -1;
				}
				
				//generate new random acceleration
				float[] rand_acc = {0.0f, 0.0f};

				//init age
				float rand_age = Utilities.createRandomf(this.settings.min_init_lifetime, this.settings.max_init_lifetime);
				//initialize Particle
				fpar.initialize(rand_pos, rand_velocity, rand_acc, rand_age, true, rand_midPoint, rand_radius, rand_pitch, rand_roll, rand_angle);
				
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

	@Override
	public void setEmitterPosition(float[] pos) {
		// TODO Auto-generated method stub
		
	}

}
