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
										  19.9f,2.3f,23.779f,};
	
	//public static final float[] CAMERA_1  ={-1.435f,27.294f,15.424f,-1.623f,26.818f,16.366f,13.228f,11.429f,17.113f,14.175f,4.231f,24.531f,14.272f,3.494f,25.292f,14.181f,2.166f,23.503f,16.078f,3.699f,25.683f,17.04f,4.477f,26.789f,19.701f,2.197f,24.958f,19.4f,2.243f,23.779f};
	
	public static final float[] CAMERA_2  ={19.4f,2.243f,23.779f,19.701f,2.197f,24.958f,17.04f,4.477f,26.789f,16.078f,3.699f,25.683f,14.181f,2.166f,23.503f,14.272f,3.494f,25.292f,14.175f,4.231f,24.531f,13.228f,11.429f,17.113f,-1.623f,26.818f,16.366f,-1.435f,27.294f,15.424f};
	
	
	public static float HELI_1_U = 0.1f;
	public static float CAMERA_1_U = 0.0f;
	public static float CAMERA_2_U = 0.0f;
	
	public static final float[] GLASS_ON_TABLE = {17.972f, 2.45f, 23.340f};


	public static float getCamera1Speed() {
		return (float) (MainTemplate.getFPSCounter().getTimePassedMillis()*0.0001);
	}	
	public static float getCamera2Speed() {
		return (float) (Math.pow(MainTemplate.getFPSCounter().getTimePassedMillis()*0.0001,4));
		
	}


	public static float getHeliSpeed() {
		return  (float)(MainTemplate.getFPSCounter().getTimePassedMillis()*0.0007*Math.pow(HELI_1_U, 2));
	}

}
