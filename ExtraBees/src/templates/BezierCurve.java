package templates;

public class BezierCurve {
	
	public static float[] getCoordsAt(float[] controlPoints, float u){
		float[] xyz = new float[3];
		int L = (controlPoints.length/3)-1;
		
		for(int i = 0; i <= (L); i++){
			xyz[0] += controlPoints[(i*3)] * getBernsteinPolynomial(L, i, u);
			xyz[1] += controlPoints[1+(i*3)] * getBernsteinPolynomial(L, i, u);
			xyz[2] += controlPoints[2+(i*3)] * getBernsteinPolynomial(L, i, u);
		}
		return xyz;
	}
	
	// bernstein polynomials
	private static float getBernsteinPolynomial(int L, int k, float u){
		return (float) (factorial(L) / (factorial(k) * factorial(L-k))* Math.pow(u, k) * Math.pow(1-u, L-k));
	}
	
	private static  int factorial(int f){
		if(f == 0 || f == 1)
			return 1;
		else
			return f * factorial(f-1);
	}
}