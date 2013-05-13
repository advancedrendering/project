package scenegraph;

import javax.media.opengl.GLAutoDrawable;

public class SimpleNode extends SceneGraphNode {
	
	public SimpleNode(GLAutoDrawable drawable, String modelPath, float scale) {
		super(drawable, modelPath, scale);
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
