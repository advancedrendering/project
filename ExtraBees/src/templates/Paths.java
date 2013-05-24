package templates;

//** storage class for animation paths and inherent stuff **//
public class Paths {
	public static final float[] HELI_1 = {28.21f,5.278f,-0.906f,
										  28.21f,17.278f,-0.906f,
										  28.21f,15.345f,-2.883f,
										  28.21f,15.545f,-2.883f};
	public static final float[] HELI_2 = {};
	public static final float[] CAMERA_1 = {28.671f,10.299f,-2.169f,
										  28.305f,20.044f,-4.007f,
										  28.99f,1.047f,26.066f,
										  19.9f,2.3f,23.779f};
	public static float HELI_1_U = 0.1f;
	public static float CAMERA_1_U = 0.0f;
	
	
	public static final float[] CAMERA_TARGET_1 = {17.972f, 2.45f, 23.340f};


	public static float getCamera1Speed() {
		return 0.001f;
	}


	public static float getHeliSpeed() {
		return  (float)(0.01*Math.pow(HELI_1_U, 2));
	}

}
