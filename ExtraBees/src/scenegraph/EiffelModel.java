package scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.cg.CgGL;

public class EiffelModel extends SceneGraphNode {

	
	public EiffelModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/eiffel", scale);
//		this.setUse_frag_shader(true);
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
		GL gl = drawable.getGL();
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "useTexture"), this.getShaderManager().FALSE);
		gl.glDisable(GL.GL_CULL_FACE);
//		gl.glPolygonMode(GL.GL_BACK, GL.GL_LINE); // Draw As Wireframes
//		gl.glCullFace(GL.GL_FRONT); // Don't Draw Any Front-Facing Polygons
//		gl.glDepthFunc(GL.GL_LEQUAL); // Change The Depth Mode
//		gl.glColor3f(0, 0, 0); // Set The Outline Color
//		gl.glLineWidth(4); // Set The Line Width
//		gl.glCallList(this.getObjectList()); // Call Your Display List
//		gl.glDepthFunc(GL.GL_LESS); // Reset The Depth-Testing Mode
//		gl.glCullFace(GL.GL_BACK); // Reset The Face To Be Culled
//		gl.glPolygonMode(GL.GL_BACK, GL.GL_FILL); // Reset Polygon Drawing Mode
//		ShaderManager.getInstance().bindFP("toon");
		gl.glCallList(this.getObjectList()); // Call Your Display List
		gl.glEnable(GL.GL_CULL_FACE);
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "useTexture"), this.getShaderManager().TRUE);
	}
	
	@Override
	public void postDraw(GLAutoDrawable drawable) {
		drawable.getGL().glCallList(this.getObjectList());
	}
}
