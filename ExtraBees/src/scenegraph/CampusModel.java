package scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import particlesystem.Fireflies;

import com.sun.opengl.cg.CgGL;

import shadermanager.ShaderManager;
import templates.MainTemplate;

public class CampusModel extends SceneGraphNode {

	
	private LampModel lamp = null;
	private LampModel lamp2 = null;
	private LampModel lamp3 = null;
	private TempleModel temple = null;

	private FloorModel floor = null;
	private WallModel wall = null;


	
	public CampusModel(GLAutoDrawable drawable, String modelPath, float scale) {
		super(drawable, modelPath, scale);

		floor = new FloorModel(drawable,scale);
		this.addChild(floor);
		
		wall = new WallModel(drawable, scale);
		this.addChild(wall);		
		
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
		
		lamp3 = new LampModel(drawable, scale);
		lamp3.setTranslation(-17.571f,0f,-12.463f);
//		lamp3.setTranslation(-3f,0f,0f);
		lamp3.setRotation(0, 0, 0);
		this.addChild(lamp3);
	
		
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
//		
		
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "useTexture"), this.getShaderManager().FALSE);
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "toon"), this.getShaderManager().TRUE);
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
		gl.glCallList(this.getObjectList()); // Call Your Display List
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "toon"), this.getShaderManager().FALSE);
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "useTexture"), this.getShaderManager().TRUE);
	}

	@Override
	public void postDraw(GLAutoDrawable drawable) {
		
		drawable.getGL().glCallList(this.getObjectList());
	}
}
