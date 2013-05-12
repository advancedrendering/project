package scenegraph;

import java.io.File;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;


import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

public class HeliModel extends SceneGraphNode {

	HeliRotorTop heli_rotor_top = null;
	HeliRotorTail heli_rotor_back = null;
	SimpleNode heli_rotor_window = null;
	int rot = 0;
	
	private final float[] REDPLASTIC_MATERIAL = { 0.3f, 0.0f, 0.0f, 1.0f, 0.6f, 0.0f, 0.0f, 1.0f, 0.8f, 0.4f, 0.4f, 1.0f, 10.0f };
	
	public HeliModel(GLAutoDrawable drawable, float scale, String vpShaderName, String fpShaderName) {
		super(drawable, "models/heli_chassis", scale);
		heli_rotor_top = new HeliRotorTop(drawable, "models/heli_rotor_top", scale);
		heli_rotor_back = new HeliRotorTail(drawable, "models/heli_rotor_back", scale);
		heli_rotor_window = new SimpleNode(drawable, "models/heli_window", scale);
		heli_rotor_top.setPivot(0f, 0.6263f, -0.1793f);
		heli_rotor_back.setPivot(0.1168f, 0.5053f, -2.5441f);
		this.addChild(heli_rotor_top);
		this.addChild(heli_rotor_back);
		this.addChild(heli_rotor_window);
//		initCg();
	}

	@Override
	public void init(GLAutoDrawable drawable) {}
	
	@Override
	public void bindParameters() {}

	@Override
	public void animate(GLAutoDrawable drawable) {
		drawable.getGL().glTranslatef(0f,
				(float)(this.getTranslation()[1]+(0.01*Math.sin(rot*0.005))),
				0f);
		rot = rot > Integer.MAX_VALUE ? 0 : rot+30;
	}
	
	@Override
	public void draw(GLAutoDrawable drawable) {
		setMaterial(drawable.getGL(), REDPLASTIC_MATERIAL);
		drawable.getGL().glCallList(this.getObjectList());
	}
	
	
	private class HeliRotorTop extends SceneGraphNode{

		public HeliRotorTop(GLAutoDrawable drawable, String modelPath,
				float scale) {
			super(drawable, modelPath, scale);
			// TODO Auto-generated constructor stub
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
			
		}

		@Override
		public void draw(GLAutoDrawable drawable) {
			GL gl = drawable.getGL();
			gl.glPushMatrix();
			gl.glTranslatef(0f, 0.12526f, -0.05586f);
			gl.glRotatef(rot, 0, 1, 0);
			gl.glTranslatef(0f, -0.12526f, +0.05586f);
			gl.glCallList(this.getObjectList());
			gl.glPopMatrix();
			
		}
		
	}
	
	private class HeliRotorTail extends SceneGraphNode{

		public HeliRotorTail(GLAutoDrawable drawable, String modelPath,
				float scale) {
			super(drawable, modelPath, scale);
			// TODO Auto-generated constructor stub
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
			GL gl = drawable.getGL();
			gl.glPushMatrix();
			gl.glTranslatef(0.1168f*0.2f, 0.5053f*0.2f, -2.5441f*0.2f);
			gl.glRotatef(10, 0, 0, 1);
			gl.glRotatef(rot, 1, 0, 0);
			gl.glRotatef(-10, 0, 0, 1);
			gl.glTranslatef(-0.1168f*0.2f, -0.5053f*0.2f, +2.5441f*0.2f);
			gl.glCallList(this.getObjectList());
			gl.glPopMatrix();
			
		}
	}
}
