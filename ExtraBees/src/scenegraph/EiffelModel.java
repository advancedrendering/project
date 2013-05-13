package scenegraph;

import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.cg.CgGL;

public class EiffelModel extends SceneGraphNode {

	public EiffelModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/eiffel", scale);
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
		this.getShaderManager().bindFP("toon");
		drawable.getGL().glCallList(this.getObjectList());
	}
}
