package scenegraph;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;

import com.sun.opengl.cg.CgGL;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class TableModel extends SceneGraphNode {
	
	private GlassModel glass = null;
	private EiffelModel eiffel = null;
	private CandleModel candle = null;
	
	
	public TableModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/table", scale);	
		
		glass = new GlassModel(drawable, scale);
//		glass.setTranslation(0.8f, 0.832f, 0);
		this.addChild(glass);
		
		eiffel = new EiffelModel(drawable, scale);
		this.addChild(eiffel);
		
		candle = new CandleModel(drawable, scale);
		this.addChild(candle);
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
		//this.getShaderManager().bindFP("normalMap");
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "bump"), this.getShaderManager().TRUE);
		drawable.getGL().glCallList(this.getObjectList());
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "bump"), this.getShaderManager().FALSE);
		drawable.getGL().glActiveTexture(GL.GL_TEXTURE0);
		drawable.getGL().glBindTexture(GL.GL_TEXTURE_2D, 0);
		drawable.getGL().glActiveTexture(GL.GL_TEXTURE1);
		drawable.getGL().glBindTexture(GL.GL_TEXTURE_2D, 0);
	}
	
	@Override
	public void postDraw(GLAutoDrawable drawable) {
		drawable.getGL().glCallList(this.getObjectList());
	}
}
