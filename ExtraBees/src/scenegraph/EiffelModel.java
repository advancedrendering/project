package scenegraph;

import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.cg.CGparameter;
import com.sun.opengl.cg.CgGL;

public class EiffelModel extends SceneGraphNode {

	private CGparameter cgTime = null;
	private int counter;
	
	public EiffelModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/eiffel", scale);
		this.setUse_frag_shader(true);
		counter = 1;
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bindParameters() {
//		cgTime = CgGL.cgGetNamedParameter(cgVertexProg, "time");
		
	}

	@Override
	public void animate(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(GLAutoDrawable drawable) {
//		counter += 1;
//		System.out.println(Math.sin(counter));
//		CgGL.cgGLSetParameter1f(cgTime, counter);
//		this.getShaderManager().bindVP("particle");
		this.getShaderManager().bindFP("toon");
		drawable.getGL().glCallList(this.getObjectList());
	}
}
