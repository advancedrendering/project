package scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import templates.BezierCurve;
import templates.Blocks;
import templates.Paths;

public class HeliModel extends SceneGraphNode {

	HeliRotorTop heli_rotor_top = null;
	HeliRotorTail heli_rotor_back = null;
	SimpleNode heli_rotor_window = null;
	int rot = 0;
	
	private final float[] REDPLASTIC_MATERIAL = { 0.3f, 0.0f, 0.0f, 1.0f, 0.6f, 0.0f, 0.0f, 1.0f, 0.8f, 0.4f, 0.4f, 1.0f, 10.0f };
	
	public HeliModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/heli_chassis", scale);
		heli_rotor_top = new HeliRotorTop(drawable, "models/heli_rotor_top", scale);
		heli_rotor_back = new HeliRotorTail(drawable, "models/heli_rotor_back", scale);
		heli_rotor_window = new SimpleNode(drawable, "models/heli_window", scale);
		heli_rotor_top.setPivot(0f, 0.6263f, -0.1793f);
		heli_rotor_back.setPivot(0.1168f, 0.5053f, -2.5441f);
		this.addChild(heli_rotor_top);
		this.addChild(heli_rotor_back);
		this.addChild(heli_rotor_window);
		this.setUse_frag_shader(false);
		this.setUse_vertex_shader(false);
//		initCg();
	}

	@Override
	public void init(GLAutoDrawable drawable) {}
	
	@Override
	public void bindParameters() {}

	@Override
	public void animate(GLAutoDrawable drawable) {
		
		float[] heliPosition = BezierCurve.getCoordsAt(Paths.HELI_1,Paths.HELI_1_U);
		float[] heliRotation = {0.0f,0.0f,0.0f};
		float[] heliTarget = {Paths.CAMERA_1[0]-heliPosition[0],
							  (Paths.CAMERA_1[1]-0.5f)-heliPosition[1],
							  Paths.CAMERA_1[2]-heliPosition[2]};
		
		if(Blocks.heliPathActive){
			if(Paths.HELI_1_U >=0.15f){
				Blocks.rain = true;
			}
			if(Paths.HELI_1_U >=0.4f){
					Blocks.heliPathActive = false;
					Blocks.camera_1_PathActive = true;
			}else{
				heliRotation[0] = -(float)Math.toDegrees(Math.atan2((double) heliTarget[1],(double) heliTarget[2]));
				double sqrt = Math.sqrt((Math.pow((double) heliTarget[1], 2))+(Math.pow((double) heliTarget[2], 2)));
				heliRotation[1] =  (float)Math.toDegrees(Math.atan2((double) heliTarget[0],sqrt));
				heliRotation[2] = 180f;
				Paths.HELI_1_U += Paths.getHeliSpeed();
			}
		}
		
		this.setRotation(heliRotation);
		this.setTranslation(heliPosition);
		
		
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
