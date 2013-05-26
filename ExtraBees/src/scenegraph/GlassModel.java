package scenegraph;

import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.cg.CgGL;

public class GlassModel extends SceneGraphNode {

	public GlassModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/glass", scale);
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
		this.getShaderManager().bindFP("phongNoTex");
		drawable.getGL().glCallList(this.getObjectList());
	}

}
