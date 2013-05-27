package particlesystem;

public class FireflyParticle extends Particle {

	public FireflyParticle(ParticleSystemSettings settings, boolean isAlive) {
		super(settings, isAlive);
	}
	
	protected float radius;
	protected float pitch;
	protected float roll;
	protected float[] midPoint;
	protected float angle;
	
	/**
	 * @return the midPoint
	 */
	public float[] getMidPoint() {
		return midPoint;
	}

	/**
	 * @return the radius
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * @return the pitch
	 */
	public float getPitch() {
		return pitch;
	}

	/**
	 * @return the roll
	 */
	public float getRoll() {
		return roll;
	}

	public void initialize(float[] position, float[] velocity, float[] acceleration, float initial_lifetime, boolean isAlive, float[] midPoint, float radius, float pitch, float roll, float angle){
		super.initialize(position, velocity, acceleration, initial_lifetime, isAlive);
		this.midPoint = midPoint;
		this.radius = radius;
		this.pitch = pitch;
		this.roll = roll;
		this.angle = angle;
	}
	
	public void update(float elapsed_time){
		age += elapsed_time;
		if (age > lifetime){
			isAlive = false;
		}
		else{
			float loc_elapsed_time = elapsed_time / 1000.0f;
			float angular_velocity = velocity[0] / this.radius; //calculate the angular velocity to set.
			this.angle = angle + (angular_velocity * loc_elapsed_time);
			position[0] = (float)Math.cos(this.angle) * this.radius;
			position[1] = (float)Math.sin(this.angle) * this.radius;
			position[2] = 0;
		}
	}
}
