package templates;

public class BezierCurve {
	
	public static float[] getCoordsAt(float[] controlPoints, float u){
		float[] xyz = new float[3];
		if(controlPoints.length == (3*4)){ // 4 points with xyz coords each
			xyz[0] = controlPoints[0]*getB0(u)
					+controlPoints[3]*getB1(u)
					+controlPoints[6]*getB2(u)
					+controlPoints[9]*getB3(u);
			
			xyz[1] = controlPoints[0+1]*getB0(u)
					+controlPoints[3+1]*getB1(u)
					+controlPoints[6+1]*getB2(u)
					+controlPoints[9+1]*getB3(u);
			
			xyz[2] = controlPoints[0+2]*getB0(u)
					+controlPoints[3+2]*getB1(u)
					+controlPoints[6+2]*getB2(u)
					+controlPoints[9+2]*getB3(u);
		}
		return xyz;
	}
	
	// bernstein polynomials
	private static float getB0(float u){
		return (1.0f-u)*(1.0f-u)*(1.0f-u);
	}
	
	private static float getB1(float u){
		return 3.0f*u*((1.0f-u)*(1.0f-u));
	}
	
	private static float getB2(float u){
		return (3.0f*(u*u))*(1.0f-u);
	}
	
	private static float getB3(float u){
		return u*u*u;
	}
	

}
