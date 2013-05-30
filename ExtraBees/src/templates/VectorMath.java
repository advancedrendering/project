package templates;

public class VectorMath {
	
	public static float[] minus(float[] a, float[] b){
		float[] result = {a[0]-b[0],a[1]-b[1],a[2]-b[2]};
		return result;
	}

	public static float[] cross(float[] a, float[] b) {
		float[] result = {a[1]*b[2]-a[2]*b[1],
						  a[2]*b[0]-a[0]*b[2],
						  a[0]*b[1]-a[1]*b[0]};
		return result;
	}
	
	public static float[] normalize(float[] a){
		float length = (float) Math.sqrt(Math.pow(a[0], 2)+Math.pow(a[1], 2)+Math.pow(a[2], 2)); 
		float[] result = {a[0]/length,a[1]/length,a[2]/length};
		return result;
	}

}
