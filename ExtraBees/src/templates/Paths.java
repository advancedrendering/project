package templates;

//** storage class for animation paths and inherent stuff **//
public class Paths {

	public static final float[] HELI_1 = {22.399f, 3.046f, 11.647f,
										  22.844f, 9.472f, 8.524f,
										  
										  28.338f, 6.921f, -2.016f,
										  28.303f, 10.658f, -1.808f,
										  28.277f, 13.533f, -1.648f,
										  
										  28.66f, 11.785f, -2.87f,
										  28.656f, 11.55f, -2.882f };
	
	public static final float[] HELI_TARGET_1 = {22.399f, 3.046f, 12.85f,
												 26.396f, 3.285f, 12.613f,
												 
												 23.005f, 2.254f, 10.175f,
												 22.967f, 3.098f, 10.189f,
												 22.571f, 11.9f, 10.331f,
												 
												 28.442f, 8.717f, -2.227f,
												 28.536f, 9.86f, -2.882f};
	
	public static final float[] CAMERA_1 = {28.378f,10.819f,-2.169f,
										  28.381f,18.858f,-3.69f,
										  
										  28.755f,4.523f,16.266f,
										  24.757f,3.795f,23.72f,
										  24.131f,3.68f,24.888f,
										  
										  19.533f,3.986f,23.634f,
										  19.306f,2.45f,23.239f};
	
	
	public static final float[] CAMERA_2  ={19.4f,2.243f,23.779f,19.701f,2.197f,24.958f,17.04f,4.477f,26.789f,16.078f,3.699f,25.683f,14.181f,2.166f,23.503f,14.272f,3.494f,25.292f,14.175f,4.231f,24.531f,13.228f,11.429f,17.113f,-1.623f,26.818f,16.366f,-1.435f,27.294f,15.424f};
	
	
	public static float HELI_1_U = 0.0f;
	public static float HELI_1_TARGET_U = 0.0f;
	public static float CAMERA_1_U = 0.0f;
	public static float CAMERA_2_U = 0.0f;
	
	public static final float[] GLASS_ON_TABLE = {17.972f, 2.45f, 23.340f};


	public static float getCamera1Speed() {
		return (float) (MainTemplate.getFPSCounter().getTimePassedMillis()*0.0001);
//		return (float) (MainTemplate.getFPSCounter().getFPS()*0.0001);
	}	
	public static float getCamera2Speed() {
		//return (float) (Math.pow(MainTemplate.getFPSCounter().getTimePassedMillis()*0.0001,4));
		return (float) (MainTemplate.getFPSCounter().getFPS()*0.000001);
		
	}


	public static float getHeliSpeed() {
		//return  (float)(MainTemplate.getFPSCounter().getTimePassedMillis()*0.0007*Math.pow(HELI_1_U, 2));
		return  (float)(0.00005f*(Math.pow(MainTemplate.getFPSCounter().getAccumulatedTimePassedMillis()*0.0004,2))+0.000001);
	}
	public static float getHeliTargetSpeed() {
		return   (float)(0.0005f*(Math.pow(MainTemplate.getFPSCounter().getAccumulatedTimePassedMillis()*0.0005,2)));
	}

}
