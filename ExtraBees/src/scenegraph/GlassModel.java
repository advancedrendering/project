package scenegraph;

import java.io.File;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.cg.CgGL;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;

public class GlassModel extends SceneGraphNode {
	
	private int counter=0;
	
	
	public GlassModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/glass", scale);
		// TODO Auto-generated constructor stub
		this.setFragShaderEnabled(false);
		this.setVertexShaderEnabled(false);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		GL gl = drawable.getGL();
		
		// calculate normals automatically
		gl.glEnable(GL.GL_AUTO_NORMAL);
		// normalize normals
		gl.glEnable(GL.GL_NORMALIZE);
		// best perspective calculations
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

		gl.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);

		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_S,
				GL.GL_REPEAT);
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_T,
				GL.GL_REPEAT);
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_R,
				GL.GL_REPEAT);
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MAG_FILTER,
				GL.GL_NEAREST);
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MIN_FILTER,
				GL.GL_LINEAR);
		

	}

	@Override
	public void bindParameters() {
		// TODO Auto-generated method stub

	}

	@Override
	public void animate(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void draw(GLAutoDrawable drawable) {
		GL gl=drawable.getGL();
		
		//only for the static cube map
		//counter=counter%2;
		/*
		 * If we use static cube map, we need to load the face file in draw function.
		 */
		//TODO: complete the array to load the cube map
		String[][] faceFile = {{"quadrangle_back.png","quadrangle_down.png","quadrangle_front.png","quadrangle_left.png","quadrangle_right.png","quadrangle_top.png"} };
	
		int[] faceTarget = { GL.GL_TEXTURE_CUBE_MAP_POSITIVE_X,
				GL.GL_TEXTURE_CUBE_MAP_NEGATIVE_X, GL.GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
				GL.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, GL.GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
				GL.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z };
		// load texture data
		TextureData[] faces = loadTextureData(faceFile[counter], "textures/quadrangle/");
		
				
		
		// assign textures to environment map
		for (int i = 0; i < 6; i++)
		{
			// Image format is GL_BGR for jpg (wrong internal format?), GL_RGB for
			// png!
			gl.glTexImage2D(faceTarget[i], 0, faces[i].getInternalFormat(), faces[i]
					.getWidth(), faces[i].getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE,
					faces[i].getBuffer());
		}		
		
		
		
		
		
		// enable texture coordinates generation mode (mode must been set with
		// glTexGeni before, otherwise glEnable fails... we change the mode
		// afterwards in the display method)
		gl.glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
		gl.glTexGeni(GL.GL_T, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
		gl.glTexGeni(GL.GL_R, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
		gl.glEnable(GL.GL_TEXTURE_GEN_S);
		gl.glEnable(GL.GL_TEXTURE_GEN_T);
		gl.glEnable(GL.GL_TEXTURE_GEN_R);
		gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
		gl.glEnable(GL.GL_TEXTURE_CUBE_MAP);
		// set texture filter mode
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
		// set texture coordinates generation mode
		gl.glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, GL.GL_NORMAL_MAP);
		gl.glTexGeni(GL.GL_T, GL.GL_TEXTURE_GEN_MODE, GL.GL_NORMAL_MAP);
		gl.glTexGeni(GL.GL_R, GL.GL_TEXTURE_GEN_MODE, GL.GL_NORMAL_MAP);
//		this.getShaderManager().bindFP("phongNoTex");
		
		drawable.getGL().glCallList(this.getObjectList());
		gl.glDisable(GL.GL_TEXTURE_GEN_S);
		gl.glDisable(GL.GL_TEXTURE_GEN_T);
		gl.glDisable(GL.GL_TEXTURE_GEN_R);
		// calculate normals automatically
		gl.glDisable(GL.GL_AUTO_NORMAL);
		// normalize normals
		gl.glDisable(GL.GL_NORMALIZE);
		gl.glDisable(GL.GL_TEXTURE_CUBE_MAP);
		
		//only for the static cube map
		
		//counter++;
		
	}
	
	private TextureData[] loadTextureData(String[] files, String path)
	{
		TextureData[] faces = new TextureData[files.length];
		String filename = "<unknown>";
		try
		{
			for (int i = 0; i < files.length; i++)
			{
				filename = path + files[i];
				faces[i] = TextureIO.newTextureData(new File(filename), GL.GL_RGBA,
						GL.GL_RGBA, false, TextureIO.PNG);
			}
			return faces;
		}
		catch (Exception e)
		{
			System.err.println("Load texture data [" + filename + "]: \n"
					+ e.getMessage());
		}
		System.exit(1);
		return null;
	}

}
