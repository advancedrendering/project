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
		float[] result = {a[0]/length(a),a[1]/length(a),a[2]/length(a)};
		return result;
	}
	
	public static float dot(float[] a, float[] b){
		float sum = 0;
		for(int i = 0; i < a.length; i++){
			sum += a[i] * b[i];
		}
		return sum;
	}
	
	public static float[] angles(float[] a, float[] b){
		float[] result = new float[3];
		result[0] = (float) Math.toDegrees(Math.atan2(b[0], a[0]));
		result[1] = (float) Math.toDegrees(Math.atan2(b[1], a[1]));
		result[2] = (float) Math.toDegrees(Math.atan2(b[2], a[2]));
		return result;
	}
	public static float angleY(float[] a, float[] b){
		return 0f;
	}
	
	public static float angleZ(float[] a, float[] b){
		return 0f;
	}
	
	public static float length(float[] a){
		return (float) Math.sqrt(Math.pow(a[0], 2)+Math.pow(a[1], 2)+Math.pow(a[2], 2));
	}
}
