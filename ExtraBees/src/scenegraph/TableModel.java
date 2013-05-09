package scenegraph;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class TableModel extends SceneGraphNode {
	
	private GlassModel glass = null;
	private TempleModel temple = null;
	private EiffelModel eiffel = null;
	
	private Texture tex = null;
	
	
	public TableModel(GLAutoDrawable drawable, float scale,
			String vpShaderPath, String fpShaderPath) {
		super(drawable, "models/table", scale);
		glass = new GlassModel(drawable, 0.01f, vpShaderPath, fpShaderPath);
		glass.setTranslation(0.4f, 0.336f, 0);
		this.addChild(glass);
		
		temple = new TempleModel(drawable, 0.02f, vpShaderPath, fpShaderPath);
		temple.setTranslation(-0.4f, 0.336f, 0);
		this.addChild(temple);
		
		eiffel = new EiffelModel(drawable, 0.005f, vpShaderPath, fpShaderPath);
		eiffel.setTranslation(1f, 0, 1f);
		this.addChild(eiffel);
		this.init(drawable);
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		try {
			tex = TextureIO.newTexture(new File("models/baked-baked_blinn1SG_pCube1SG-pCube1.jpg"),false);
		} catch (GLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		gl.glEnable(GL.GL_TEXTURE_2D);
		tex.bind();
		drawable.getGL().glCallList(this.getObjectList());
		tex.disable();
		gl.glDisable(GL.GL_TEXTURE_2D);
	}
}
