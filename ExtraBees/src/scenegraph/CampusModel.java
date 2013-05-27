package scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.cg.CgGL;

import shadermanager.ShaderManager;

public class CampusModel extends SceneGraphNode {

	
	private LampModel lamp = null;
	private LampModel lamp2 = null;
	private TempleModel temple = null;


	public CampusModel(GLAutoDrawable drawable, String modelPath, float scale) {
		super(drawable, modelPath, scale);

		
		
		temple = new TempleModel(drawable, scale);
		this.addChild(temple);
		
		lamp = new LampModel(drawable, scale);
		lamp.setTranslation(0,0,0);
		lamp.setRotation(0, 0, 0);
		this.addChild(lamp);
		
		lamp2 = new LampModel(drawable, scale);
		lamp2.setTranslation(-4.544f,0.875f,13.8f);
		lamp2.setRotation(0, 0, 0);
		this.addChild(lamp2);
		this.init(drawable);
		this.setVertexShaderEnabled(true);
		this.setFragShaderEnabled(true);

	}

	@Override
	public void init(GLAutoDrawable drawable) {}

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
		
//		this.getShaderManager().bindFP("fog");
//		this.getShaderManager().bindVP("fog");
		
		GL gl = drawable.getGL();
		gl.glPolygonMode(GL.GL_BACK, GL.GL_LINE); // Draw As Wireframes
		gl.glCullFace(GL.GL_FRONT); // Don't Draw Any Front-Facing Polygons
		gl.glDepthFunc(GL.GL_LEQUAL); // Change The Depth Mode
		gl.glColor3f(0, 0, 0); // Set The Outline Color
		gl.glLineWidth(4); // Set The Line Width
		gl.glCallList(this.getObjectList()); // Call Your Display List
		gl.glDepthFunc(GL.GL_LESS); // Reset The Depth-Testing Mode
		gl.glCullFace(GL.GL_BACK); // Reset The Face To Be Culled
		gl.glPolygonMode(GL.GL_BACK, GL.GL_FILL); // Reset Polygon Drawing Mode
		this.getShaderManager().bindFP("toonNoTex");
		gl.glCallList(this.getObjectList()); // Call Your Display List
	}
}
