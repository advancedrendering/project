package scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import particlesystem.Fire;
import particlesystem.Fireflies;
import particlesystem.Rain;

import com.sun.opengl.cg.CGparameter;
import com.sun.opengl.cg.CGprogram;
import com.sun.opengl.cg.CgGL;

import shadermanager.ShaderManager;

public class SceneRoot extends SceneGraphNode{

	// singleton pattern
	private static SceneRoot scene = null;

	private CampusModel campus = null;
	private HeliModel heli = null;
	private SkyBox skydome = null;
	private Rain rain = null;
	private Fire fire = null;
	private float scale = 1f;
	
	private CGparameter cgFogDensity = null;
	private float fogDensity = 0.02f;
	
	private CGparameter cgFogColor = null;
	private float[] fogColor = {0.7f, 0.7f, 0.7f};
	private CGparameter cgFogTex2DSampler = null;

	private Fireflies fireflies;
	
	private SceneRoot(GLAutoDrawable drawable) {
		super(drawable);
				
		this.getShaderManager().loadFragShader("shader/fp_phongPerPixelNoTexture.cg", "phongNoTex");
		this.getShaderManager().loadFragShader("shader/toon_shading.cg", "toon");
		this.getShaderManager().loadFragShader("shader/toon_shadingNoTexture.cg", "toonNoTex");
		this.getShaderManager().loadFragShader("shader/fp_fog.cg", "fog");
		this.getShaderManager().loadVertexShader("shader/vp_fog.cg", "fog");
		
		this.getShaderManager().setVertexShaderEnabled(true);
		this.getShaderManager().setFragShaderEnabled(true);
		
		
		skydome = new SkyBox(drawable, "models/skydome", scale);
		this.addChild(skydome);
		
		campus = new CampusModel(drawable, "models/campus", scale);
		this.addChild(campus);
		
		rain = new Rain(drawable);
		this.addChild(rain);
		
		fire = new Fire(drawable);
		this.addChild(fire);
		
		fireflies = new Fireflies(drawable);
		this.addChild(fireflies);
		
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
	public void init(GLAutoDrawable drawable) {
		// get the gl object
		GL gl = drawable.getGL();
	}

	@Override
	public void bindParameters() {
		this.getShaderManager().addFragShaderParam("phong", "decal");
		this.getShaderManager().addVertexShaderParam("fog", "fogDensity");
		this.getShaderManager().addFragShaderParam("fog", "fogColor");
		this.getShaderManager().addFragShaderParam("fog", "decal");
		this.getShaderManager().addFragShaderParam("toon", "decal");
	}

	@Override
	public void animate(GLAutoDrawable drawable) {}

	@Override
	public void draw(GLAutoDrawable drawable) {
		CgGL.cgGLSetParameter1f(this.getShaderManager().getVertexShaderParam("fog", "fogDensity"), this.fogDensity);
		CgGL.cgGLSetParameter3fv(this.getShaderManager().getFragShaderParam("fog", "fogColor"), this.fogColor, 0);
		drawable.getGL().glCallList(this.getObjectList());
	}
}
