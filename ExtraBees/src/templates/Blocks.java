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
	public static boolean cubemappingGlass = true;
	public static boolean cubemappingHeli = false;

	
	public static void update(){
		
		if(timeIsBetween(0, 1000)){
			cubemappingGlass = true;
		}else{
			cubemappingGlass = false;
		}
		if(timeIsBetween(0, 12000)){
			heliPath1Active = true;
			cubemappingHeli = true;
		}else{
			heliPath1Active = false;
			cubemappingHeli = false;
		}
		
		if(timeIsBetween(0, 14000)){
			heliPath1TargetActive = true;
		}else{
			heliPath1TargetActive = false;
		}
		
		if(timeIsBetween(14000, 45000)){
			camera_1_PathActive = true;
		}else{
			camera_1_PathActive = false;
		}
		
		if(timeIsBetween(2000, 45000)){
			rainActive = true;
		}else{
			rainActive = false;
		}
		
		if(timeIsBetween(19800, 45000)){
			cubemappingGlass = true;
			heliPath2Active = true;
		}else{
			cubemappingGlass = false;
		}
		if(timeIsBetween(50000,300000))
		{
			camera_2_PathActive=true;
		}
	}
	
	
	private static boolean timeIsBetween(long begin, long end){
		long time = MainTemplate.getFPSCounter().getAccumulatedTimePassedMillis();
		return ((time > begin && time <= end) ? true : false);
	}
	
	
	
}
