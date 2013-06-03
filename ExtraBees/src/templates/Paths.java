package templates;

//** storage class for animation paths and inherent stuff **//
public class Paths {

	public static float[] HELI_TO_CAMERA_1 = {22.399f, 3.046f, 11.647f,
										  22.844f, 9.472f, 8.524f,
										  
										  28.693f, 6.896f, -2.016f,
										  28.659f, 10.633f, -1.808f,
										  28.633f, 13.503f, -1.648f,
										  
										  28.66f, 13.785f, -3.37f,
										  28.656f, 13.55f, -3.382f };
	
	public static float[] HELI_TO_CANDLE = {19.306f,2.65f,23.239f,
									19.588f,3.156f,23.31f,
									
									18.276f,2.462f,22.183f,
									17.964f,2.449f,22.741f,
									18.276f,2.462f,22.183f,
									19.588f,3.156f,23.31f,
									19.306f,2.65f,23.239f};
	
	public static float[] HELI_TO_CANDLE_AND_BACK_TO_CAMERA = {19.306f,2.65f,23.739f, 
															   19.332f, 2.78f, 23.357f,
															   
															   18.448f, 2.382f, 22.766f,
															   18.146f, 2.479f, 22.674f,
															   17.994f, 2.528f, 22.628f,
															   
															   17.811f, 2.467f, 23.086f,
															   18.053f, 2.494f, 22.8f,
															   18.326f, 2.525f, 22.477f,
															   
															   19.483f, 2.906f, 23.287f,
															   19.306f,2.65f,23.739f };
	
	public static float[] HELI_BACK_TO_CAMERA = {17.964f,2.449f,22.741f,
												18.276f,2.462f,22.183f,
												19.588f,3.156f,23.31f,
												19.306f,2.65f,23.239f};
	
	public static float[] HELI_TO_CAMERA_TARGET_1 = {22.399f, 3.046f, 12.85f,
												 26.396f, 3.285f, 12.613f,
												 
												 23.005f, 2.254f, 10.175f,
												 22.967f, 3.098f, 10.189f,
												 22.571f, 11.9f, 10.331f,
												 
												 28.442f, 8.717f, -2.227f,
												 28.536f, 9.86f, -2.882f};
	
	public static float[] HELI_BACK_TO_CAMERA_TARGET ={17.972f, 2.45f, 23.340f,
														18.472f, 2.45f, 23.340f,
														18.506f,2.65f,23.239f,
										  				19.306f,2.65f,23.239f};
	
	public static float[] CAMERA_TO_TABLE = {28.378f,10.819f,-2.169f,
										  28.381f,18.858f,-3.69f,
										  
										  29.055f,4.523f,16.266f,
										  25.057f,3.795f,23.72f,
										  24.431f,3.68f,24.888f,
										  
										  19.533f,3.986f,24.134f,
										  19.306f,2.45f,23.739f};
	
	
	public static float[] CAMERA_2  ={19.4f,2.243f,23.779f,19.701f,2.197f,24.958f,17.04f,4.477f,26.789f,16.078f,3.699f,25.683f,14.181f,2.166f,23.503f,14.272f,3.494f,25.292f,14.175f,4.231f,24.531f,13.228f,11.429f,17.113f,-1.623f,26.818f,16.366f,-1.435f,27.294f,15.424f};
	
	
	public static float HELI_TO_CAMERA_1_U = 0.0f;
	public static float HELI_TO_CANDLE_U = 0.0f;
	public static float HELI_BACK_TO_CAMERA_U = 0.0f;
	public static float HELI_TO_CAMERA_1_TARGET_U = 0.0f;
	public static float HELI_BACK_TO_CAMERA_TARGET_U = 0.0f;
	public static float HELI_2_TARGET_U = 0.0f;
	public static float CAMERA_TO_TABLE_1_U = 0.0f;
	public static float CAMERA_2_U = 0.0f;
	
	public static final float[] GLASS_ON_TABLE = {17.972f, 2.45f, 23.340f};
	public static final float[] CANDLE = {17.831f, 2.472f, 23.023f};


	public static float getCamera1Speed() {
		return (float) (MainTemplate.getFPSCounter().getTimePassedMillis()*0.0001);
//		return (float) (MainTemplate.getFPSCounter().getFPS()*0.0001);
	}	
	public static float getCamera2Speed() {
		//return (float) (Math.pow(MainTemplate.getFPSCounter().getTimePassedMillis()*0.0001,4));
		return (float) (MainTemplate.getFPSCounter().getFPS()*0.000001);
		
	}


	public static float getHeliSpeed() {
//		return  (float)(MainTemplate.getFPSCounter().getTimePassedMillis()*0.0007*Math.pow(HELI_1_U, 2)+0.000001f);
		return  (float)(MainTemplate.getFPSCounter().getTimePassedMillis()*0.000005+0.000001);
	}
	public static float getHeliTargetSpeed() {
		return   (float)(MainTemplate.getFPSCounter().getTimePassedMillis()*0.00004);
	}
	
	public static float getHeli2TargetSpeed() {
		return   (float)(MainTemplate.getFPSCounter().getTimePassedMillis()*0.00004+0.000001);
	}
	
	public static float getHeli2Speed() {
//		return  (float)(MainTemplate.getFPSCounter().getTimePassedMillis()*0.0007*Math.pow(HELI_1_U, 2)+0.000001f);
		return  (float)(MainTemplate.getFPSCounter().getTimePassedMillis()*0.00002+0.0000008);
	}
}
