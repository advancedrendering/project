package scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.cg.CgGL;

public class WallModel extends SceneGraphNode {

	public WallModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/wall", scale);
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
		GL gl = drawable.getGL();
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "bump"), this.getShaderManager().TRUE);
		gl.glCallList(this.getObjectList());
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "bump"), this.getShaderManager().FALSE);
	}

	@Override
	public void postDraw(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		gl.glCallList(this.getObjectList());
	}

}
