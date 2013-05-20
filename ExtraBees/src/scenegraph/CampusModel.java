package scenegraph;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;

import shadermanager.ShaderManager;

import com.sun.opengl.cg.CgGL;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class CampusModel extends SceneGraphNode {

	private TableModel table = null;
	private LampModel lamp = null;

	public CampusModel(GLAutoDrawable drawable, String modelPath, float scale) {
		super(drawable, modelPath, scale);
		table = new TableModel(drawable, scale * 0.2f);
		table.setTranslation(0, 0, 0);
		table.setRotation(0, 45, 0);
		this.addChild(table);
		this.init(drawable);
		
		lamp = new LampModel(drawable, scale*0.5f);
		lamp.setTranslation(0,0,0);
		lamp.setRotation(0, 0, 0);
		this.addChild(lamp);
		this.init(drawable);
//		this.setUse_vertex_shader(false);
//		this.setUse_frag_shader(false);

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
		ShaderManager.getInstance().bindFP("toon");
		gl.glCallList(this.getObjectList()); // Call Your Display List
	}
}
