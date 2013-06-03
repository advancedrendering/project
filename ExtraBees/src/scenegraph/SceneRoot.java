package scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import particlesystem.Fire;
import particlesystem.FireCannon;
import particlesystem.Fireflies;
import particlesystem.FireflyParticle;
import particlesystem.ParticleSystemSettings;
import particlesystem.Rain;

import com.sun.opengl.cg.CGparameter;
import com.sun.opengl.cg.CGprogram;
import com.sun.opengl.cg.CgGL;

import shadermanager.ShaderManager;
import templates.Blocks;
import templates.JoglTemplate;
import templates.MainTemplate;

public class SceneRoot extends SceneGraphNode{

	// singleton pattern
	private static SceneRoot scene = null;

	private CampusModel campus = null;
	private HeliModel heli = null;
	private SkyBox skydome = null;
	private Rain rain = null;
	private Fire fire = null;
	private FireCannon fireCannon = null;
	private CameraModel camera = null;
	private Fireflies fireflies1=null;
	private Fireflies fireflies2=null;
	private Fireflies fireflies3=null;
	private float scale = 1f;
	
	
	 float[] firefliesPos1 = new float[] {20.5072f-25.0f,6.1059f-3.5f, 35.7706f-20.0f };
	 float[] firefliesPos2 = new float[] {25.099f-25.0f,4.89f-3.5f, 21.971f-20.0f};
	 float[] firefliesPos3 = new float[] {7.516f-25.0f,4.89f-3.5f, 9.5f-20.0f };
	
	 float[] velocity = {7.0f,0.0f,0.0f};
	 float[] acceleration= {0.0f,0.0f,0.0f};
	
	private CGparameter cgFogDensity = null;
	private float fogDensity = 0.05f;
	
	private CGparameter cgFogColor = null;
	private CGparameter cgFogTex2DSampler = null;


	
	
	private SceneRoot(GLAutoDrawable drawable) {
		super(drawable);
						
		this.getShaderManager().setVertexShaderEnabled(true);
		this.getShaderManager().setFragShaderEnabled(true);
		
		this.getShaderManager().loadVertexShader("shader/vp_cube_mapping.cg", "cube");
		this.getShaderManager().loadFragShader("shader/fp_cube_mapping.cg", "cube");
		this.getShaderManager().loadVertexShader("shader/vp_motion_blur.cg", "motion");
		this.getShaderManager().loadFragShader("shader/fp_motion_blur.cg", "motion");
		this.getShaderManager().loadVertexShader("shader/vp_refraction.cg", "refraction");
		this.getShaderManager().loadFragShader("shader/fp_refraction.cg", "refraction");
		this.getShaderManager().loadFragShader("shader/fp_postProcessing.cg", "post");
		this.getShaderManager().loadVertexShader("shader/vp_postProcessing.cg", "post");
				
		skydome = new SkyBox(drawable, "models/skydome", scale);
		this.addChild(skydome);
		
		campus = new CampusModel(drawable, "models/campus", scale);
	
		this.addChild(campus);
		

		
		fire = new Fire(drawable);
		this.addChild(fire);
		
		
		fireflies1 = new Fireflies(drawable);
		fireflies1.setTranslation(firefliesPos1);
		fireflies1.setRotation(0, 0, 0);
		this.addChild(fireflies1);
		
		fireflies2 = new Fireflies(drawable);
		fireflies2.setTranslation(firefliesPos2);
		fireflies2.setRotation(0, 0, 0);
		this.addChild(fireflies2);
		
		fireflies3 = new Fireflies(drawable);
		fireflies3.setTranslation(firefliesPos3);
		fireflies3.setRotation(0, 0, 0);
		this.addChild(fireflies3);
		
		fireCannon = new FireCannon(drawable);
		this.addChild(fireCannon);
		
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
	public void init(GLAutoDrawable drawable) {
		// get the gl object
		GL gl = drawable.getGL();
		
		//enable 
		CgGL.cgGLSetManageTextureParameters(this.getShaderManager().getCgContext(), true);
	}

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
		this.getShaderManager().addFragShaderParam("phong", "lightning");
		
		this.getShaderManager().addFragShaderParam("cube", "environmentMap");
		this.getShaderManager().addVertexShaderParam("cube", "modelToWorld");
		this.getShaderManager().addVertexShaderParam("cube", "worldEyePosition");
		this.getShaderManager().addFragShaderParam("refraction", "environmentMap");
		this.getShaderManager().addVertexShaderParam("refraction", "modelToWorld");
		this.getShaderManager().addVertexShaderParam("refraction", "worldEyePosition");
		
		// vertex shader parameter
		this.getShaderManager().addVertexShaderParam("phong", "fog");
		this.getShaderManager().addVertexShaderParam("phong", "fogDensity");
		
		//post processing parameter
		this.getShaderManager().addFragShaderParam("post", "sceneTex");
		this.getShaderManager().addFragShaderParam("post", "gaussian_horizontal");
		this.getShaderManager().addFragShaderParam("post", "gaussian_blur");
		this.getShaderManager().addFragShaderParam("motion", "sceneTex");
		this.getShaderManager().addFragShaderParam("motion", "blurScale");
		this.getShaderManager().addVertexShaderParam("motion", "blurScale");
		this.getShaderManager().addVertexShaderParam("motion", "modelView");
		this.getShaderManager().addVertexShaderParam("motion", "modelProj");
		this.getShaderManager().addVertexShaderParam("motion", "prevModelView");
		this.getShaderManager().addVertexShaderParam("motion", "prevModelProj");
		this.getShaderManager().addVertexShaderParam("motion", "halfWindowSize");
		
	}

	@Override
	public void animate(GLAutoDrawable drawable) {
		
//		if(Blocks.animationActive && Blocks.fogActive){
//			if(fogDensity >= 0.02f)
//				fogDensity-= 0.00001f;
//		}
		
	}

	@Override
	public void draw(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		//enable drawing of textures
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "useTexture"), this.getShaderManager().TRUE);
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "fog"), this.getShaderManager().TRUE);
		CgGL.cgGLSetParameter1f(this.getShaderManager().getVertexShaderParam("phong", "fog"), this.getShaderManager().TRUE);
		CgGL.cgGLSetParameter1f(this.getShaderManager().getVertexShaderParam("phong", "fogDensity"), this.fogDensity);

		//enable lightning
		CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("phong", "lightning"), this.getShaderManager().TRUE);
	}
	
	@Override
	public void postDraw(GLAutoDrawable drawable) {
		CgGL.cgGLSetParameter1f(this.getShaderManager().getFragShaderParam("motion", "blurScale"), 200.0f);
		CgGL.cgGLSetParameter1f(this.getShaderManager().getVertexShaderParam("motion", "blurScale"), 100.0f);
		CgGL.cgGLSetParameter3f(this.getShaderManager().getVertexShaderParam("motion", "halfWindowSize"), MainTemplate.xResolution / 2.0f, MainTemplate.yResolution / 2.0f, 0);
	}

	public HeliModel getHeli() {
		return this.heli;
	}
}
