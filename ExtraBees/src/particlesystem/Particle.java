package particlesystem;

public class Particle {

	private float age;
	/**
	 * @return the acceleration
	 */
	public float[] getAcceleration() {
		return acceleration;
	}
	/**
	 * @param acceleration to be set.
	 */
	public void setAcceleration(float[] acceleration) {
		this.acceleration = acceleration;
	}
	/**
	 * @return the external_force
	 */
	public float[] getExternal_force() {
		return external_force;
	}
	/**
	 * @param external force to be set.
	 */
	public void setExternal_force(float[] external_force) {
		this.external_force = external_force;
	}
	/**
	 * @return the age
	 */
	public float getAge() {
		return age;
	}
	/**
	 * @return the isAlive
	 */
	public boolean isAlive() {
		return isAlive;
	}
	/**
	 * @return the velocity
	 */
	public float[] getVelocity() {
		return velocity;
	}
	/**
	 * @return the position
	 */
	public float[] getPosition() {
		return position;
	}
	private boolean isAlive;
	private float lifetime;
	/**
	 * @return the lifetime
	 */
	public float getLifetime() {
		return lifetime;
	}
	/**
	 * @param lifetime the lifetime to set
	 */
	public void setLifetime(float lifetime) {
		this.lifetime = lifetime;
	}
	private float[] velocity = null;
	private float[] acceleration = null;;
	private float[] position = null;
	private float[] external_force = null;;
	private float[] color = {1.0f, 1.0f, 1.0f};
	
	private ParticleSystemSettings settings = null;
	
	/**
	 * @return the settings
	 */
	public ParticleSystemSettings getSettings() {
		return settings;
	}
	/**
	 * @param settings the settings to set
	 */
	public void setSettings(ParticleSystemSettings settings) {
		this.settings = settings;
	}
	
	public Particle(ParticleSystemSettings settings, boolean isAlive){
		this.external_force = settings.general_external_force;
		this.isAlive = isAlive;
		this.lifetime = settings.lifetime;
		this.age = 0;
		this.position = new float[3];
		this.velocity = new float[3];
		this.acceleration = new float[3];
		this.settings = settings;
	}
	
	public void initialize(float[] position, float[] velocity, float[] acceleration, float initial_lifetime, boolean isAlive){
		this.external_force = settings.general_external_force;
		this.lifetime = settings.lifetime;
		this.age = initial_lifetime;
		this.isAlive = isAlive;
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
	}
	
	public void update(float elapsed_time){
		age += elapsed_time;
		if (age > lifetime){
			isAlive = false;
		}
		else{
			float loc_elapsed_time = elapsed_time / 1000.0f;
			for (int i = 0; i < velocity.length; i++){
				//calc the overall acceleration (note: external force means acceleration due to an external force e.g. gravity)
				float loc_acc = acceleration[i] + external_force[i];
				//add acceleration to velocity
				velocity[i] += loc_acc * loc_elapsed_time;
				//calculate new position
				position[i] += 0.5f * loc_acc * loc_elapsed_time * loc_elapsed_time;
			}
		}
	}
	public float[] getColor() {
		return color;
	}
	public void setColor(float[] color) {
		this.color = color;
	}
}
