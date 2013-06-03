package templates;
import particlesystem.Rain;

//** animations are coordinated by blocking variables **//
public class Blocks {
	public static boolean animationActive = false;
	public static boolean heliToCameraPath1Active = false;
	public static boolean heliPathWithCameraActive = false;
	public static boolean heliPathWithCamera2Active = false;
	public static boolean heliToCandlePathActive = false;
	public static boolean heliBackToCameraPathActive = false;
	public static boolean camera_1_PathActive = false;
	public static boolean camera_2_PathActive =false;
	public static boolean rainActive = true;
	public static boolean candleFlameActive = false;
	public static boolean fireCannonActive = false;
	public static boolean dynamicCubeMappingActive = false;
	public static boolean fogActive = true;
	public static boolean heliToCameraPath1TargetActive= false;
	public static boolean heliPath3TargetActive= false;
	public static boolean heliBackToCameraTargetActive= false;
	public static boolean cubemappingGlass = false;
	public static boolean cubemappingHeli = false;

	
	public static void update(){
		
		if(frameIsBetween(0, 2) || frameIsBetween(400, 20000)){
			cubemappingGlass = true;
		}else{
			cubemappingGlass = false;
		}
		
		if(frameIsBetween(0, 350) || frameIsBetween(1000, 20000)){
			cubemappingHeli = true;
		}else{
			cubemappingHeli = false;
		}
		
		if(frameIsBetween(0, 350)){
			heliToCameraPath1Active = true;
		}else{
			heliToCameraPath1Active = false;
		}
		
		if(frameIsBetween(0, 1000)){
			heliToCameraPath1TargetActive = true;
		}else{
			heliToCameraPath1TargetActive = false;
		}
		
		if(frameIsBetween(350, 800)){
			camera_1_PathActive = true;
			heliPathWithCameraActive = true;
		}else{
			camera_1_PathActive = false;
			heliPathWithCameraActive = false;
		}
		
		if(frameIsBetween(800, 20000)){
			heliToCandlePathActive = true;
		}else{
			heliToCandlePathActive = false;
		}
		if(frameIsBetween(800, 20000)){
			heliPath3TargetActive = true;
		}else{
			heliPath3TargetActive = false;
		}
		
		if(frameIsBetween(910, 940)){
			fireCannonActive = true;
		}else{
			fireCannonActive = false;
		}
		
		if(frameIsBetween(915, 20000)){
			candleFlameActive = true;
		}else{
			candleFlameActive = false;
		}
		
		if(frameIsBetween(930, 20000)){
			heliBackToCameraPathActive = true;
			heliBackToCameraTargetActive = true;
		}else{
			heliBackToCameraPathActive = false;
			heliBackToCameraTargetActive = false;
		}
		
		// rain
		if(frameIsBetween(0, 200)){
			Rain.emitRate = 5f;
		}
		if(frameIsBetween(200,400)){
			Rain.emitRate = 3f;
		}
		if(frameIsBetween(400, 600)){
			Rain.emitRate = 1f;
		}
		if(frameIsBetween(600, 800)){
			Rain.emitRate = 0.3f;
		}
		if(frameIsBetween(800, 20000)){
			Rain.emitRate = 0.1f;
		}
			
	}
	
	private static boolean frameIsBetween(int begin, int end){
		return ((MainTemplate.animationFrame > begin && MainTemplate.animationFrame <= end) ? true : false);
	}
}
