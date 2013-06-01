package templates;

//** animations are coordinated by blocking variables **//
public class Blocks {
	public static boolean animationActive = false;
	public static boolean heliPath1Active = false;
	public static boolean heliPath2Active = false;
	public static boolean camera_1_PathActive = false;
	public static boolean camera_2_PathActive =false;
	public static boolean rainActive = false;
	public static boolean candleFlameActive = true;
	public static boolean dynamicCubeMappingActive = false;
	public static boolean fogActive = true;
	public static boolean heliPath1TargetActive= false;

	
	public static void update(){
		if(timeIsBetween(0, 19000)){
			heliPath1Active = true;
		}else{
			heliPath1Active = false;
		}
		
		if(timeIsBetween(0, 19000)){
			heliPath1TargetActive = true;
		}else{
			heliPath1TargetActive = false;
		}
		
		if(timeIsBetween(20000, 45000)){
			camera_1_PathActive = true;
		}else{
			camera_1_PathActive = false;
		}
		
		if(timeIsBetween(20000, 45000)){
			heliPath2Active = true;
		}else{
			heliPath2Active = false;
		}
			
	}
	
	
	private static boolean timeIsBetween(long begin, long end){
		long time = MainTemplate.getFPSCounter().getAccumulatedTimePassedMillis();
		return ((time > begin && time <= end) ? true : false);
	}
	
	
	
}
