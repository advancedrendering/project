package scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.cg.CgGL;
 
public class FloorModel extends SceneGraphNode {

	public FloorModel(GLAutoDrawable drawable, float scale) {
		super(drawable,"models/floor", scale);
		
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
		GL gl = drawable.getGL();
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "bump"), this.getShaderManager().TRUE);
		gl.glCallList(this.getObjectList());
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "bump"), this.getShaderManager().FALSE);
	}

	@Override
	public void postDraw(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

}
