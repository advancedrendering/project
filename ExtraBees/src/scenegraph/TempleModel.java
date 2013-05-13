package scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

public class TempleModel extends SceneGraphNode {

	private final float[] WHITE_MATERIAL = { 0f, 0f, 0f, 0.5f, 0.5f, 0.5f, 0.7f, 0.7f, 0.7f, 0.4f, 0.4f, 0.4f, 10.0f };

	public TempleModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/temple", scale);

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
		// TODO Auto-generated method stub

	}
	
	@Override
	public void draw(GLAutoDrawable drawable) {
//		setMaterial(drawable.getGL(), WHITE_MATERIAL);
		drawable.getGL().glDisable(GL.GL_COLOR_MATERIAL);
		drawable.getGL().glCallList(this.getObjectList());
	}
}
