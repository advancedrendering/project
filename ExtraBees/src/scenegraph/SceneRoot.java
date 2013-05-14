package scenegraph;

import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.cg.CGprogram;
import com.sun.opengl.cg.CgGL;

import shadermanager.ShaderManager;

public class SceneRoot extends SceneGraphNode{

	// singleton pattern
	private static SceneRoot scene = null;

	private CampusModel campus = null;
	private HeliModel heli = null;
	private SkyBox skybox = null;
	private float scale = 1f;
	
	private SceneRoot(GLAutoDrawable drawable) {
		super(drawable);
		
		CGprogram loc_vp_prog = ShaderManager.loadShader(this.getShaderManager().getCgContext(),this.getShaderManager().getCgFragProfile(), "shader/toon_shading.cg");
		this.getShaderManager().addFragmentShaderProgram("toon", loc_vp_prog);
		
//		loc_vp_prog = ShaderManager.loadShader(this.getShaderManager().getCgContext(),this.getShaderManager().getCgVertexProfile(), "shader/basicparticlesystem.cg");
//		this.getShaderManager().addVertexShaderProgram("particle", loc_vp_prog);
		
		this.getShaderManager().setUse_vertex_shader(false);
		this.getShaderManager().setUse_frag_shader(false);
		
		skybox = new SkyBox(drawable, "models/skybox", scale*2f);
		skybox.setRotation(0, -100, 0);
		this.addChild(skybox);
		
		campus = new CampusModel(drawable, "models/campus", scale);
		this.addChild(campus);
		
		heli = new HeliModel(drawable, scale*0.2f);
		heli.setTranslation(0f, 0.6f, 0f);
		heli.setRotation(0, -100, 0);
		this.addChild(heli);
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
	public void bindParameters() {}

	@Override
	public void animate(GLAutoDrawable drawable) {}

	@Override
	public void draw(GLAutoDrawable drawable) {
		drawable.getGL().glCallList(this.getObjectList());
	}
}
