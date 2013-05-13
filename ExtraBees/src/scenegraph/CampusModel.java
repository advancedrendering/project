package scenegraph;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class CampusModel extends SceneGraphNode {
	


	private TableModel table = null;
	

	public CampusModel(GLAutoDrawable drawable, String modelPath, float scale) {
		super(drawable, modelPath, scale);
		table = new TableModel(drawable, scale * 0.2f);
		table.setTranslation(0, 0, 0);
		table.setRotation(0, 45, 0);
		this.addChild(table);
		this.init(drawable);

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
		drawable.getGL().glCallList(this.getObjectList());
	}
}
