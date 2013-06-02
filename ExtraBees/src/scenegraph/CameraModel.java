package scenegraph;


import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import templates.BezierCurve;
import templates.Paths;
import templates.VectorMath;

public class CameraModel extends SceneGraphNode {

	public CameraModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/camera", scale);
		this.setFragShaderEnabled(false);
		this.setVertexShaderEnabled(false);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void bindParameters() {
		// TODO Auto-generated method stub

	}

	@Override
	public void animate(GLAutoDrawable drawable) {
		float[] camPosition = BezierCurve.getCoordsAt(Paths.CAMERA_1,Paths.CAMERA_1_U);
		float[] camRotation = VectorMath.getEulerAngles(camPosition,Paths.GLASS_ON_TABLE);
		this.setRotation(-camRotation[0],camRotation[1]+180,camRotation[2]);
		this.setTranslation(camPosition);
//		this.setRotation(0,0,0);

	}

	@Override
	public void draw(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		gl.glDisable(GL.GL_CULL_FACE);
		drawable.getGL().glCallList(this.getObjectList());
		gl.glEnable(GL.GL_CULL_FACE);
	}

	@Override
	public void postDraw(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

}
