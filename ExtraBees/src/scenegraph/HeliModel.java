package scenegraph;

import java.io.File;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;


import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

public class HeliModel extends SceneGraphNode {

	SimpleNode heli_rotor_top = null,
			heli_rotor_back = null,
					heli_rotor_window = null;
	int rot = 0;
	
	private final float[] REDPLASTIC_MATERIAL = { 0.3f, 0.0f, 0.0f, 1.0f, 0.6f, 0.0f, 0.0f, 1.0f, 0.8f, 0.4f, 0.4f, 1.0f, 10.0f };
	
	public HeliModel(GLAutoDrawable drawable, float scale, String vpShaderName, String fpShaderName) {
		super(drawable, "models/heli_chassis", scale);
		heli_rotor_top = new SimpleNode(drawable, "models/heli_rotor_top", scale);
		heli_rotor_back = new SimpleNode(drawable, "models/heli_rotor_back", scale);
		heli_rotor_window = new SimpleNode(drawable, "models/heli_window", scale);
		heli_rotor_top.setPivot(0f, 0.6263f, -0.1793f);
		heli_rotor_back.setPivot(0.1168f, 0.5053f, -2.5441f);
		this.addChild(heli_rotor_top);
		this.addChild(heli_rotor_back);
		this.addChild(heli_rotor_window);
//		initCg();
	}

	public void init(GLAutoDrawable drawable) {
		

		
//		GL gl = drawable.getGL();
//
//		// z-buffer test
//		gl.glEnable(GL.GL_DEPTH_TEST);
//
//		// type of depth test
//		gl.glDepthFunc(GL.GL_LEQUAL);
//		// backface culling
//		gl.glEnable(GL.GL_CULL_FACE);
//		// calculate normals automatically
//		gl.glEnable(GL.GL_AUTO_NORMAL);
//		// normalize normals
//		gl.glEnable(GL.GL_NORMALIZE);
//		// best perspective calculations
//		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
//
//		gl.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);
//
//		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_S,
//				GL.GL_REPEAT);
//		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_T,
//				GL.GL_REPEAT);
//		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_R,
//				GL.GL_REPEAT);
//		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MAG_FILTER,
//				GL.GL_NEAREST);
//		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MIN_FILTER,
//				GL.GL_LINEAR);
//
//		// TODO: complete the array to load the cube map
//		String[] faceFile = { "quadrangle_right.png", "quadrangle_left.png",
//				"quadrangle_top.png", "quadrangle_down.png",
//				"quadrangle_front.png", "quadrangle_back.png" };
//
//		int[] faceTarget = { GL.GL_TEXTURE_CUBE_MAP_POSITIVE_X,
//				GL.GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
//				GL.GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
//				GL.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,
//				GL.GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
//				GL.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z };
//		// load texture data
//		TextureData[] faces = loadTextureData(faceFile, "textures/quadrangle/");
//		// assign textures to environment map
//		for (int i = 0; i < 6; i++) {
//			// Image format is GL_BGR for jpg (wrong internal format?), GL_RGB
//			// for
//			// png!
//			gl.glTexImage2D(faceTarget[i], 0, faces[i].getInternalFormat(),
//					faces[i].getWidth(), faces[i].getHeight(), 0, GL.GL_RGB,
//					GL.GL_UNSIGNED_BYTE, faces[i].getBuffer());
//		}
//
//		// enable texture coordinates generation mode (mode must been set with
//		// glTexGeni before, otherwise glEnable fails... we change the mode
//		// afterwards in the display method)
//		gl.glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
//		gl.glTexGeni(GL.GL_T, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
//		gl.glTexGeni(GL.GL_R, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
//		gl.glEnable(GL.GL_TEXTURE_GEN_S);
//		gl.glEnable(GL.GL_TEXTURE_GEN_T);
//		gl.glEnable(GL.GL_TEXTURE_GEN_R);
//		gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);


	}

	public void animate(GL gl) {

	}

	private TextureData[] loadTextureData(String[] files, String path) {
		TextureData[] faces = new TextureData[files.length];
		String filename = "<unknown>";
		try {
			for (int i = 0; i < files.length; i++) {
				filename = path + files[i];
				faces[i] = TextureIO.newTextureData(new File(filename),
						GL.GL_RGBA, GL.GL_RGBA, false, TextureIO.PNG);
			}
			return faces;
		} catch (Exception e) {
			System.err.println("Load texture data [" + filename + "]: \n"
					+ e.getMessage());
		}
		System.exit(1);
		return null;
	}

	@Override
	public void bindParameters() {}
	
	public void render(GLAutoDrawable drawable){
		setMaterial(drawable.getGL(), REDPLASTIC_MATERIAL);
		super.render(drawable);
		this.setTranslation(0f,
							(float)(this.getTranslation()[1]+(0.001*Math.sin(rot++*0.1))),
							0f);
//		SimpleNode ht = (SimpleNode) this.getChild("models/heli_rotor_top");
//		
//		float[] translation = ht.getTranslation();
//		float[] rotation = ht.getRotation();
//		ht.setTranslation(translation[0]-ht.getPivot()[0], translation[1]-ht.getPivot()[1], translation[2]-ht.getPivot()[2]);
//		ht.setRotation(rotation[0],(rot++),rotation[2]);
//		ht.setTranslation(translation[0]+ht.getPivot()[0], translation[1]+ht.getPivot()[1], translation[2]+ht.getPivot()[2]);
	}

	@Override
	public void animate(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(GLAutoDrawable drawable) {
		drawable.getGL().glCallList(this.getObjectList());
	}
}
