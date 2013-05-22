package particlesystem;

import javax.media.opengl.GLAutoDrawable;

public class Rain extends ParticleSystem {

	public Rain(GLAutoDrawable drawable) {
		super(drawable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ParticleSystemSettings initializeSettings() {
		ParticleSystemSettings loc_settings = new ParticleSystemSettings();
		
		loc_settings.capacity = 1000;
		loc_settings.emitRate = 0.05f; //100 particles per seconds i.e. 0.1 per millisecond
		//create external force
		float[] loc_external_force = {5.0f, 10.0f, 0.0f};
		loc_settings.general_external_force = loc_external_force;
		//lifetime
		loc_settings.lifetime = 12000.0f; //5 seconds
		
		ParticleEmitterSettings loc_emi_settings = new ParticleEmitterSettings();
		float[] min_init_acc = {0.0f, 0.0f, 0.0f};
		loc_emi_settings.min_init_acceleration = min_init_acc;
		float[] max_init_acc = {0.0f, 0.0f, 0.0f};
		loc_emi_settings.max_init_acceleration = max_init_acc;
		float[] min_init_velocity = {0.0f, -5.0f,0.0f};
		loc_emi_settings.min_init_velocity = min_init_velocity;
		float[] max_init_velocity = {0.0f, -5.0f, 0.0f};
		loc_emi_settings.max_init_velocity = max_init_velocity;
		loc_emi_settings.min_init_lifetime = 0.0f; //5 seconds
		loc_emi_settings.max_init_lifetime = 0.0f; //5 seconds
		
		loc_settings.emitter_settings = loc_emi_settings;
		//create Planar Emitter
		float[] position_vector = {0.0f, 5.0f, 0.0f};
		float[] first_dir_vector = {1.0f, 0.0f, 0.0f};
		float[] second_dir_vector = {0.0f, 0.0f, 1.0f};
		float min_first_scalar = 0.0f;
		float max_first_scalar = 5.0f;
		float min_second_scalar = 0.0f;
		float max_second_scalar = 5.0f;
		loc_settings.emitter = new PlanarEmitter(loc_settings.emitRate, loc_settings.emitter_settings
				, position_vector, first_dir_vector, second_dir_vector, min_first_scalar, max_first_scalar, min_second_scalar, max_second_scalar);
		
		return loc_settings;
	}

}
