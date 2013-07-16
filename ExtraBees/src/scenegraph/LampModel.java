package scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.cg.CgGL;


public class LampModel extends SceneGraphNode {
	public LampModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/lamp_pillar", scale);
		this.addChild(new LampLightModel(drawable, scale));
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
		gl.glDisable(GL.GL_CULL_FACE);
		gl.glCallList(this.getObjectList());
		gl.glEnable(GL.GL_CULL_FACE);
	}
	
	@Override
	public void postDraw(GLAutoDrawable drawable) {
		drawable.getGL().glCallList(this.getObjectList());
	}
	
	
	static class LampLightModel extends SceneGraphNode{
		private static int i = 0;
		
		final float[] MOVING_LIGHT_ADS = { 0.2f, 0.2f, 0.2f, 1f, 0.95f,
			0.95f, 0.95f, 1f, 0.95f, 0.95f, 0.95f, 1f };

		public LampLightModel(GLAutoDrawable drawable, float scale) {
			super(drawable, "models/lamp_light", scale);
			this.setVertexShaderEnabled(false);
			this.setFragShaderEnabled(false);
			this.setPivot(25.1f*scale, 4.890f*scale, 21.971f*scale);
			i++;
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
			CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "useTexture"), this.getShaderManager().FALSE);
			GL gl = drawable.getGL();
			gl.glEnable(GL.GL_BLEND);
			gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE);
			gl.glCallList(this.getObjectList());
			gl.glDisable(GL.GL_BLEND);
			CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "useTexture"), this.getShaderManager().TRUE);
		}
		
		@Override
		public void postDraw(GLAutoDrawable drawable) {
			drawable.getGL().glCallList(this.getObjectList());
		}
		
	}
}
