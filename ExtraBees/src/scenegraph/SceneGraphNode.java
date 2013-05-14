package scenegraph;

import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import shadermanager.ShaderManager;

import modelloader.OBJLoader;

import com.sun.opengl.cg.CGcontext;
import com.sun.opengl.cg.CGprogram;
import com.sun.opengl.cg.CgGL;

public abstract class SceneGraphNode {
	
	// path to file
	private String modelPath = "";
	
	// list with all the children of this node object
	protected ArrayList<SceneGraphNode> children;
	
	// objectList returned by the OBJLoader 
	private int objectList;
	
	protected ShaderManager shaderManager = null; 
	
	public ShaderManager getShaderManager() {
		return shaderManager;
	}
	
	//flags which determine whether SceneGraphNode uses a shader or not.
	private boolean use_vertex_shader = true;
	private boolean use_frag_shader = true;
	
	/**
	 * @return Returns whether SceneGraphNode uses any vertex shader program.
	 */
	public boolean isUse_vertex_shader() {
		return use_vertex_shader;
	}

	/**
	 * @param Sets whether SceneGraphNode uses any vertex shader program.
	 */
	public void setUse_vertex_shader(boolean use_vertex_shader) {
		this.use_vertex_shader = use_vertex_shader;
	}

	/**
	 * @return Returns whether SceneGraphNode uses any fragment shader program.
	 */
	public boolean isUse_frag_shader() {
		return use_frag_shader;
	}

	/**
	 * @param Sets whether SceneGraphNode uses any fragment shader program.
	 */
	public void setUse_frag_shader(boolean use_frag_shader) {
		this.use_frag_shader = use_frag_shader;
	}

	protected float[] translation = new float[3],
						rotation = new float[3], 
						scale = new float[3],
						pivot = new float[3];
		
	public SceneGraphNode(GLAutoDrawable drawable, String modelPath, float scale){
		this.shaderManager = ShaderManager.getInstance();
		this.children = new ArrayList<SceneGraphNode>();
		this.modelPath = modelPath;
		objectList = new OBJLoader(modelPath,scale,drawable.getGL()).getDisplayList();
		init(drawable);
	}
	
	// constructor
	public SceneGraphNode(GLAutoDrawable drawable){
		this.shaderManager = ShaderManager.getInstance();
		this.children = new ArrayList<SceneGraphNode>();
	}
	
	public abstract void init(GLAutoDrawable drawable);
	public abstract void bindParameters();
	public abstract void animate(GLAutoDrawable drawable);
	
	/**
	 * Add a child to this node.
	 * 
	 * @param child
	 */
	public void addChild(SceneGraphNode child){
		this.children.add(child);
	}
	
	/**
	 * remove child from this parent with referred path.
	 * @param path
	 */
	public void removeChild(String path){
		for(SceneGraphNode n : this.children){
			if(path.equals(n.getPath()))
				this.children.remove(n);
		}
	}
	
	/**
	 * get child from this parent with referred path.
	 * @param path
	 */
	public SceneGraphNode getChild(String path){
		for(SceneGraphNode n : this.children){
			if(path.equals(n.getPath()))
				return n;
		}
		return null;
	}	
	
	/**
	 * Method for rendering an model and its children. 
	 * 
	 * @param gl
	 */
	public void render(GLAutoDrawable drawable){
		GL gl = drawable.getGL();
		gl.glPushMatrix(); // save matrix
		animate(drawable); // call virtual animate method to animate current node
		gl.glTranslatef(translation[0], translation[1], translation[2]);
		gl.glRotatef(rotation[0], 1, 0, 0);
		gl.glRotatef(rotation[1], 0, 1, 0);
		gl.glRotatef(rotation[2], 0, 0, 1);
		//enable or disable shader usage depending on the fields set
		this.getShaderManager().setUse_vertex_shader(this.isUse_vertex_shader());
		//bind default shader. (if no shader is used then nothing will happen)
		this.getShaderManager().bindVP();
		//enable or disable shader usage depending on the fields set
		this.getShaderManager().setUse_frag_shader(this.isUse_frag_shader());
		//bind default shader. (if no shader is used then nothing will happen)
		this.getShaderManager().bindFP();
		this.draw(drawable);// draw the current object
		this.getShaderManager().setUse_vertex_shader(false);
		this.getShaderManager().setUse_frag_shader(false);
		for(SceneGraphNode child : children){ // render every child
			child.render(drawable);
		}

		gl.glPopMatrix(); // restore matrix
	}
	
	public abstract void draw(GLAutoDrawable drawable);
	
	public void setMaterial(GL gl, float[] material) {
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, material, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, material, 4);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, material, 8);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, material, 12);
	}

	
	//////////////////////	
	// getter and setter//
	//////////////////////
	
	public int getObjectList(){
		return this.objectList;
	}
	
	public String getPath() {
		return modelPath;
	}
	
	public void setTranslation(float[] trans){
		this.translation = trans;
	}
	
	public void setTranslation(float x, float y, float z){
		this.translation[0] = x;
		this.translation[1] = y;
		this.translation[2] = z;
	}
	
	public float[] getTranslation() {
		return this.translation;
	}
	
	public void setRotation(float[] rot){
		this.rotation = rot;
	}
	public void setRotation(float x, float y, float z){
		this.rotation[0] = x;
		this.rotation[1] = y;
		this.rotation[2] = z;
	}
	
	public float[] getRotation() {
		return this.rotation;
	}
	
	public void setScale(float[] scale){
		this.scale = scale;
	}
	
	public void setScale(float x, float y, float z){
		this.scale[0] = x;
		this.scale[1] = y;
		this.scale[2] = z;
	}
	
	public void setPivot(float[] trans){
		this.pivot = trans;
	}
	
	public void setPivot(float x, float y, float z){
		this.pivot[0] = x;
		this.pivot[1] = y;
		this.pivot[2] = z;
	}
	
	public float[] getPivot(){
		return this.pivot;
	}
}
