package scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.cg.CgGL;

public class SkyBox extends SceneGraphNode {

	public SkyBox(GLAutoDrawable drawable, String modelPath, float scale) {
		super(drawable, modelPath, scale);
		this.setFragShaderEnabled(true);
		this.setVertexShaderEnabled(true);
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
		//disable lightning
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "lightning"), this.getShaderManager().FALSE);
		drawable.getGL().glCallList(this.getObjectList());
		//enable lightning
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "lightning"), this.getShaderManager().TRUE);
	}
	
	@Override
	public void postDraw(GLAutoDrawable drawable) {
		drawable.getGL().glCallList(this.getObjectList());
	}
}
