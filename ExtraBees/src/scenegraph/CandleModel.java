package scenegraph;

import javax.media.opengl.GLAutoDrawable;

public class CandleModel extends SceneGraphNode {

	public CandleModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/candle", scale);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(GLAutoDrawable drawable) {
		drawable.getGL().glCallList(this.getObjectList());

	}

	@Override
	public void postDraw(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

}
