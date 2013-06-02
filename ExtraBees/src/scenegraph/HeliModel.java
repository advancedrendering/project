package scenegraph;

import java.io.File;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.sound.midi.Patch;

import templates.BezierCurve;
import templates.Blocks;
import templates.MainTemplate;
import templates.Paths;
import templates.VectorMath;


import com.sun.opengl.cg.CgGL;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

public class HeliModel extends SceneGraphNode {

	HeliRotorTop heli_rotor_top = null;
	HeliRotorTail heli_rotor_back = null;
	HeliWindow heli_window = null;
	int rot = 0;
	
	private final float[] REDPLASTIC_MATERIAL = { 0.3f, 0.0f, 0.0f, 1.0f, 0.6f, 0.0f, 0.0f, 1.0f, 0.8f, 0.4f, 0.4f, 1.0f, 10.0f };
	
	public HeliModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/heli_chassis", scale);
		heli_rotor_top = new HeliRotorTop(drawable, "models/heli_rotor_top", scale);
		heli_rotor_back = new HeliRotorTail(drawable, "models/heli_rotor_back", scale);
		heli_window = new HeliWindow(drawable, "models/heli_window", scale);
		heli_rotor_top.setPivot(0f*scale, 1.4023f*scale, -0.2793f*scale);
		heli_rotor_back.setPivot(0.1168f*scale, 1.2813f*scale, -2.5441f*scale);
		this.addChild(heli_rotor_top);
		this.addChild(heli_rotor_back);
		this.addChild(heli_window);
		this.setFragShaderEnabled(false);
		this.setVertexShaderEnabled(false);
	}

	@Override
	public void init(GLAutoDrawable drawable) {}
	
	@Override
	public void bindParameters() {}

	@Override
	public void animate(GLAutoDrawable drawable) {

		float[] heliPosition = BezierCurve.getCoordsAt(Paths.HELI_1,Paths.HELI_1_U);

//		float[] heliPosition = BezierCurve.getCoordsAt(Paths.HELI_1,Paths.HELI_1_U);
		float[] heliTarget = BezierCurve.getCoordsAt(Paths.HELI_TARGET_1,Paths.HELI_1_TARGET_U);

		if(Blocks.animationActive && (Blocks.heliPath1Active)){
			heliPosition = BezierCurve.getCoordsAt(Paths.HELI_1,Paths.HELI_1_U);
			heliTarget = BezierCurve.getCoordsAt(Paths.HELI_TARGET_1,Paths.HELI_1_TARGET_U);
			if(Paths.HELI_1_U < 1.0f)
				Paths.HELI_1_U += Paths.getHeliSpeed();
		}
		if(Blocks.animationActive && (Blocks.heliPath1TargetActive)){
			if(Paths.HELI_1_TARGET_U < 1.0f)
				Paths.HELI_1_TARGET_U += Paths.getHeliTargetSpeed();
		}
		
		if(Blocks.animationActive && (Blocks.heliPath2Active)){
			heliPosition = BezierCurve.getCoordsAt(Paths.CAMERA_1,Paths.CAMERA_1_U);
			heliPosition[1] = heliPosition[1]+0.2f;
			heliTarget = Paths.GLASS_ON_TABLE;
		}
		

		float[] heliRotation = VectorMath.getEulerAngles(heliPosition, heliTarget);
		this.setRotation(-heliRotation[0],heliRotation[1]+180,heliRotation[2]);
		this.setTranslation(heliPosition);


		rot = rot > Integer.MAX_VALUE ? 0 : rot+30;
	}
	
	@Override
	public void draw(GLAutoDrawable drawable) {
		drawable.getGL().glCallList(this.getObjectList());
	}
	
	@Override
	public void postDraw(GLAutoDrawable drawable) {
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
			gl.glTranslatef(this.getPivot()[0], this.getPivot()[1], this.getPivot()[2]);
			gl.glRotatef(rot, 0, 1, 0);
			gl.glTranslatef(-this.getPivot()[0], -this.getPivot()[1], -this.getPivot()[2]);
			gl.glCallList(this.getObjectList());
			gl.glPopMatrix();
			
		}
		
		@Override
		public void postDraw(GLAutoDrawable drawable) {
			drawable.getGL().glCallList(this.getObjectList());
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
			gl.glTranslatef(this.getPivot()[0], this.getPivot()[1], this.getPivot()[2]);
			gl.glRotatef(10, 0, 0, 1);
			gl.glRotatef(rot, 1, 0, 0);
			gl.glRotatef(-10, 0, 0, 1);
			gl.glTranslatef(-this.getPivot()[0], -this.getPivot()[1], -this.getPivot()[2]);
			gl.glCallList(this.getObjectList());
			gl.glPopMatrix();
			
		}
		
		@Override
		public void postDraw(GLAutoDrawable drawable) {
			drawable.getGL().glCallList(this.getObjectList());
		}
	}
	
	public static class HeliWindow extends SceneGraphNode{
		public static boolean visible = true;

		public HeliWindow(GLAutoDrawable drawable, String modelPath, float scale) {
			super(drawable, modelPath, scale);
			this.setFragShaderEnabled(false);
			this.setVertexShaderEnabled(false);
		}

		@Override
		public void init(GLAutoDrawable drawable) {
			GL gl = drawable.getGL();
			gl.glEnable(GL.GL_TEXTURE_GEN_S);
			gl.glEnable(GL.GL_TEXTURE_GEN_T);
			gl.glEnable(GL.GL_TEXTURE_GEN_R);
			gl.glEnable(GL.GL_TEXTURE_CUBE_MAP);
			
			// generate texture space
			gl.glGenTextures(1, MainTemplate.cubemap2, 0);
			
			// create textures
			gl.glBindTexture(GL.GL_TEXTURE_CUBE_MAP, MainTemplate.cubemap2[0]);
			
			for (int i = 0; i < 6; i++) {
				gl.glTexImage2D(GL.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL.GL_RGB, MainTemplate.CUBEMAP_SIZE , MainTemplate.CUBEMAP_SIZE, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, null);
			}
			
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_R, GL.GL_REPEAT);
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			
			gl.glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
			gl.glTexGeni(GL.GL_T, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
			gl.glTexGeni(GL.GL_R, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);

			gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);

			//create new frame- and renderbuffer
			gl.glGenFramebuffersEXT(1, MainTemplate.framebuffer, 0);
			gl.glGenRenderbuffersEXT(1, MainTemplate.renderbuffer, 0);
					
			gl.glDisable(GL.GL_TEXTURE_GEN_S);
			gl.glDisable(GL.GL_TEXTURE_GEN_T);
			gl.glDisable(GL.GL_TEXTURE_GEN_R);
			gl.glDisable(GL.GL_TEXTURE_CUBE_MAP);
			
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
			GL gl=drawable.getGL();
			gl.glEnable(GL.GL_TEXTURE_GEN_S);
			gl.glEnable(GL.GL_TEXTURE_GEN_T);
			gl.glEnable(GL.GL_TEXTURE_GEN_R);
			
			
			gl.glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
			gl.glTexGeni(GL.GL_T, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
			gl.glTexGeni(GL.GL_R, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
			
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_R, GL.GL_REPEAT);
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			
			gl.glEnable(GL.GL_TEXTURE_CUBE_MAP);
			gl.glBindTexture(GL.GL_TEXTURE_CUBE_MAP, MainTemplate.cubemap2[0]);
			
			if(visible){
//					gl.glPushMatrix();
//					gl.glTranslatef(17.96f, 2.45f, 23.346f);
//				MainTemplate.getGlut().glutSolidCube(0.5f);
//					MainTemplate.getGlut().glutSolidSphere(0.2f,20,20);
				gl.glDisable(GL.GL_CULL_FACE);
				drawable.getGL().glCallList(this.getObjectList());
				gl.glEnable(GL.GL_CULL_FACE);
//					gl.glPopMatrix();
			}
			gl.glDisable(GL.GL_TEXTURE_GEN_S);
			gl.glDisable(GL.GL_TEXTURE_GEN_T);
			gl.glDisable(GL.GL_TEXTURE_GEN_R);
			
			gl.glDisable(GL.GL_TEXTURE_CUBE_MAP);
		}
		
		@Override
		public void postDraw(GLAutoDrawable drawable) {
		}
	}
}
