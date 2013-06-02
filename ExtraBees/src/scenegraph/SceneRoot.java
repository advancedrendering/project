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
import templates.Blocks;
import templates.JoglTemplate;

public class SceneRoot extends SceneGraphNode{

	// singleton pattern
	private static SceneRoot scene = null;

	private CampusModel campus = null;
	private HeliModel heli = null;
	private SkyBox skydome = null;
	private Rain rain = null;
	private Fire fire = null;
	private CameraModel camera = null;
	private float scale = 1f;
	
	private CGparameter cgFogDensity = null;
	private float fogDensity = 0.05f;
	
	private CGparameter cgFogColor = null;
	private CGparameter cgFogTex2DSampler = null;

	private Fireflies fireflies;
	
	private SceneRoot(GLAutoDrawable drawable) {
		super(drawable);
						
		this.getShaderManager().setVertexShaderEnabled(true);
		this.getShaderManager().setFragShaderEnabled(true);
		
		
		skydome = new SkyBox(drawable, "models/skydome", scale);
		this.addChild(skydome);
		
		campus = new CampusModel(drawable, "models/campus", scale);
		this.addChild(campus);
		
		
		fire = new Fire(drawable);
		this.addChild(fire);
		
		fireflies = new Fireflies(drawable);
		this.addChild(fireflies);
		
		heli = new HeliModel(drawable, scale*0.2f);
		this.addChild(heli);
		
		camera = new CameraModel(drawable, scale*0.8f);
		this.addChild(camera);
		
		rain = new Rain(drawable);
		this.addChild(rain);
		init(drawable);
		bindParameters();
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
		// fragment shader parameter
		this.getShaderManager().addFragShaderParam("phong", "useTexture");
		this.getShaderManager().addFragShaderParam("phong", "toon");
		this.getShaderManager().addFragShaderParam("phong", "fog");
		this.getShaderManager().addFragShaderParam("phong", "fogColor");
		this.getShaderManager().addFragShaderParam("phong", "decal");
		this.getShaderManager().addFragShaderParam("phong", "bumpDecal");
		this.getShaderManager().addFragShaderParam("phong", "bump");
		
		// vertex shader parameter
		this.getShaderManager().addVertexShaderParam("phong", "fog");
		this.getShaderManager().addVertexShaderParam("phong", "fogDensity");
	}

	@Override
	public void animate(GLAutoDrawable drawable) {
		
		if(Blocks.animationActive && Blocks.fogActive){
			if(fogDensity >= 0.02f)
				fogDensity-= 0.00001f;
		}
		
	}

	@Override
	public void draw(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		float[] fogColor = {0.4f, 0.4f, 0.4f};
		//enable drawing of textures
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "useTexture"), this.getShaderManager().TRUE);
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "fog"), this.getShaderManager().TRUE);
		CgGL.cgGLSetParameter1f(this.getShaderManager().getVertexShaderParam("phong", "fog"), this.getShaderManager().TRUE);
		CgGL.cgGLSetParameter1f(this.getShaderManager().getVertexShaderParam("phong", "fogDensity"), this.fogDensity);
		CgGL.cgGLSetParameter3fv(this.getShaderManager().getFragShaderParam("phong", "fogColor"), fogColor, 0);
	}
	
	@Override
	public void postDraw(GLAutoDrawable drawable) {
	}
}
