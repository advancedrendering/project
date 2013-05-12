package scenegraph;

import javax.media.opengl.GLAutoDrawable;

public class SceneRoot extends SceneGraphNode{

	// singleton pattern
	private static SceneRoot scene = null;

	private CampusModel campus = null;
	private HeliModel heli = null;
	private SkyBox skybox = null;
	private float scale = 1f;
	
	private SceneRoot(GLAutoDrawable drawable) {
		super(drawable);
		skybox = new SkyBox(drawable, "models/skybox", scale*2f);
		skybox.setRotation(0, -100, 0);
		this.addChild(skybox);
		
		campus = new CampusModel(drawable, "models/campus", scale,"shader/vp_phongPerPixel.cg","shader/fp_phongPerPixel.cg");
		this.addChild(campus);
		
		heli = new HeliModel(drawable, scale*0.2f, "shader/vp_phongPerPixel.cg", "shader/fp_phongPerPixel.cg");
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
