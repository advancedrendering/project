package scenegraph;

import javax.media.opengl.GLAutoDrawable;

public class TempleModel extends SceneGraphNode {
	
	private TableModel table = null;
	public TempleModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/temple", scale);
		
		table = new TableModel(drawable, scale);
		this.addChild(table);
		this.setUse_frag_shader(false);
		this.setUse_vertex_shader(false);

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
}
