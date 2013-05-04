package scenegraph;

import java.util.ArrayList;
import javax.media.opengl.GL;
import modelloader.MeshLoader;

/**
 * Modified implementation of the simple scene graph example from kaoLA.
 * 
 * 
 * @author richard
 * 
 */

public class SceneGraphNode {

	// path to file
	private String path = "";

	// list with all the children of this node object
	private ArrayList<SceneGraphNode> children;

	// objectList returned by the meshloader
	private int objectList;

	protected float[] translation, rotation, scale;

	// constructor
	public SceneGraphNode(GL gl, String path) {
		this.children = new ArrayList<SceneGraphNode>();
		this.path = path;
		objectList = MeshLoader.loadObj(gl, path);
	}

	/**
	 * Add a child to this node.
	 * 
	 * @param child
	 */
	public void addChild(SceneGraphNode child) {
		this.children.add(child);
	}

	/**
	 * remove child from this parent with referred path.
	 * 
	 * @param path - Path to file
	 */
	public void removeChild(String path) {
		for (SceneGraphNode n : this.children) {
			if (path.equals(n.getPath()))
				this.children.remove(n);
		}
	}

	/**
	 * Method for rendering this node and its children.
	 * 
	 * @param gl
	 */
	public void render(GL gl) {
		gl.glPushMatrix(); // save matrix
		gl.glTranslatef(translation[0], translation[1], translation[2]);
		gl.glRotatef(rotation[0], 1, 0, 0);
		gl.glRotatef(rotation[1], 0, 1, 0);
		gl.glRotatef(rotation[2], 0, 0, 1);

		gl.glCallList(objectList); // draw the current object

		for (SceneGraphNode child : children) { // render every child
			child.render(gl);
		}
		gl.glPopMatrix(); // restore matrix
	}

	// ////////////////////
	// getter and setter//
	// ////////////////////

	public String getPath() {
		return path;
	}

	public void setTranslation(float[] trans) {
		this.translation = trans;
	}

	public void setRotation(float[] rot) {
		this.rotation = rot;
	}

	public void setScale(float[] scale) {
		this.scale = scale;
	}

}
