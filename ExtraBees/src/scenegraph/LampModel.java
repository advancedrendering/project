package scenegraph;

import javax.media.opengl.GLAutoDrawable;

public class LampModel extends SceneGraphNode {
	public LampModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/lamp", scale);
		// TODO Auto-generated constructor stub
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
}
