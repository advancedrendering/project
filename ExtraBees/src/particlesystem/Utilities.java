package particlesystem;

import java.util.Random;

public class Utilities {
	
	
	/**
	 * Generates a new random vector within certain boundaries given by min_vec and max_vec.
	 * @param min_vec Lower boundary for random vector.
	 * @param max_vec Upper boundary for random vector.
	 * @return New random vector.
	 */
	public static float[] createRandom3f(float[] min_vec,float[] max_vec){		
		float[] rand_vec = new float[3];
		for (int i = 0; i < 3; i++){
			rand_vec[i] = Utilities.createRandomf(min_vec[i], max_vec[i]);
		}
		return rand_vec;
	}
	
	/**
	 * Generates a new random number within certain boundaries given by min and max.
	 * @param min Lower boundary for random number.
	 * @param max Upper boundary for random number.
	 * @return New random number.
	 */
	public static float createRandomf(float min, float max){
		if (min == max){
			return min;
		}
		else{
			//get new random generator (with new seed)
			Random rand = new Random();
			//get random number.
			return rand.nextFloat() * (max -min) + min;
		}
	}
	
	/**
	 * Generates a new random number within certain boundaries given by min and max.
	 * @param min Lower boundary for random number.
	 * @param max Upper boundary for random number.
	 * @return New random number.
	 */
	public static int createRandom(int min, int max){
		if (min == max){
			return min;
		}
		else{			
			return (int)(Math.random() * (max - min) + min); 
		}
	}
}
