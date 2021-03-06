package scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import templates.MainTemplate;

public class TempleModel extends SceneGraphNode {
	
	private TableModel table = null;
	public TempleModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/temple", scale);
		
		table = new TableModel(drawable, scale);
		this.addChild(table);
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
		drawable.getGL().glCallList(this.getObjectList());
	}
	
	@Override
	public void postDraw(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		drawable.getGL().glCallList(this.getObjectList());		
	}
}
