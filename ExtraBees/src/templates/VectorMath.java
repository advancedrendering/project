package templates;

public class VectorMath {
	
	private static final float[] X = {1f,0f,0f}, Y = {1f,1f,0f},Z = {0f,0f,1f};
	
	
	public static void main(String[] args){
		float result = angle(X,Y);
//		System.out.println(result[0]+" "+result[1]+" "+result[2]);
		System.out.println(result);
	}
	
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
	
	public static float angle(float[] a, float[] b){
		float result = (float) Math.toDegrees(Math.acos(dot(normalize(a), normalize(b))/(length(a)*length(b))));
		return result;
	}
	
	public static float angleX(float[] a){
//		return (float) Math.toDegrees(Math.acos((dot(normalize(a), X))/(length(a))));
		return (float) Math.toDegrees(Math.atan2(a[2],a[1]));
	}
	
	public static float angleY(float[] a){
//		return (float) Math.toDegrees(Math.acos((dot(normalize(a), Y))/(length(a))));
		return (float) Math.toDegrees(Math.atan2(a[1],1));
	}
	
	public static float angleZ(float[] a){
//		return (float) Math.toDegrees(Math.acos((dot(normalize(a), Z))/(length(a))));
		return (float) Math.toDegrees(Math.atan2(a[2],1));
	}
	
	public static float length(float[] a){
		return (float) Math.sqrt(Math.pow(a[0], 2)+Math.pow(a[1], 2)+Math.pow(a[2], 2));
	}
}
