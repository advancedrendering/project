package scenegraph;

import javax.media.opengl.GLAutoDrawable;

import particlesystem.Rain;

import com.sun.opengl.cg.CGparameter;
import com.sun.opengl.cg.CgGL;

public class SceneRoot extends SceneGraphNode{

	// singleton pattern
	private static SceneRoot scene = null;

	private CampusModel campus = null;
	private HeliModel heli = null;
	private SkyBox skydome = null;
	private Rain rain = null;
	private float scale = 1f;
	
	private CGparameter cgFogDensity = null;
	private float fogDensity = 0.02f;
	
	private CGparameter cgFogColor = null;
	private float[] fogColor = {0.7f, 0.7f, 0.7f};
	
	private SceneRoot(GLAutoDrawable drawable) {
		super(drawable);
				
		this.getShaderManager().loadFragShader("shader/toon_shading.cg", "toon");
		this.getShaderManager().loadFragShader("shader/fp_fog.cg", "fog");
		this.getShaderManager().loadVertexShader("shader/vp_fog.cg", "fog");
				
		this.getShaderManager().setUse_vertex_shader(true);
		this.getShaderManager().setUse_frag_shader(true);
		
		
		skydome = new SkyBox(drawable, "models/skydome", scale);
		this.addChild(skydome);
		
		campus = new CampusModel(drawable, "models/campus", scale);
		this.addChild(campus);
		
		rain = new Rain(drawable);
		this.addChild(rain);
				
		heli = new HeliModel(drawable, scale*0.2f);
		this.addChild(heli);
		
		init(drawable);
		bindParameters();
		// TODO: add camera, lightsources?!	
	}

	public static SceneRoot getInstance(GLAutoDrawable drawable) {
		if (scene == null) {
			scene = new SceneRoot(drawable);
		}
		return scene;
	}

	@Override
	public void init(GLAutoDrawable drawable) {}

	@Override
	public void bindParameters() {
		this.cgFogDensity = CgGL.cgGetNamedParameter(this.getShaderManager().getBindedVertexProg("fog"), "fogDensity");
		this.cgFogColor = CgGL.cgGetNamedParameter(this.getShaderManager().getBindedFragProg("fog"), "fogColor");
	}

	@Override
	public void animate(GLAutoDrawable drawable) {}

	@Override
	public void draw(GLAutoDrawable drawable) {
		CgGL.cgGLSetParameter1f(cgFogDensity, this.fogDensity);
		CgGL.cgGLSetParameter3fv(cgFogColor, fogColor, 0);
		drawable.getGL().glCallList(this.getObjectList());
	}
	
	public HeliModel getHeli(){
		return this.heli;
	}
}
