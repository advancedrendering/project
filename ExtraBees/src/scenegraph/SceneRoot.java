package scenegraph;

import javax.media.opengl.GLAutoDrawable;

public class SceneRoot extends SceneGraphNode{

	// singleton pattern
	private static SceneRoot scene = null;

	private CampusModel campus = null;
	private HeliModel heli = null;
	
	private SceneRoot(GLAutoDrawable drawable) {
		super(drawable);
		heli = new HeliModel(drawable, 0.2f, "shader/vp_phongPerPixel.cg", "shader/fp_phongPerPixel.cg");
		heli.setTranslation(0f, 0.5f, 0f);
		heli.setRotation(0, -100, 0);
		this.addChild(heli);
		campus = new CampusModel(drawable, "models/campus", 0.5f,"shader/vp_phongPerPixel.cg","shader/fp_phongPerPixel.cg");
		this.addChild(campus);
		// TODO: add camera, skybox?!
		
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
