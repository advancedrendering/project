package scenegraph;

import javax.media.opengl.GLAutoDrawable;


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
		drawable.getGL().glCallList(this.getObjectList());
	}
	
	
	class LampLightModel extends SceneGraphNode{

		public LampLightModel(GLAutoDrawable drawable, float scale) {
			super(drawable, "models/lamp_light", scale);
			this.setVertexShaderEnabled(false);
			this.setFragShaderEnabled(false);
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
			this.getShaderManager().bindFP("phongNoTex");
			drawable.getGL().glCallList(this.getObjectList());
		}
		
	}
}
