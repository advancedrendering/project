package scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import templates.MainTemplate;

public class GlassModel extends SceneGraphNode {

	

	public static boolean visible = true;

	public GlassModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/glass", scale);
		// TODO Auto-generated constructor stub

		this.setFragShaderEnabled(false);
		this.setVertexShaderEnabled(false);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		gl.glEnable(GL.GL_TEXTURE_GEN_S);
		gl.glEnable(GL.GL_TEXTURE_GEN_T);
		gl.glEnable(GL.GL_TEXTURE_GEN_R);
		gl.glEnable(GL.GL_TEXTURE_CUBE_MAP);
		
		// generate texture space
		gl.glGenTextures(1, MainTemplate.cubemap, 0);
		
		// create textures
		gl.glBindTexture(GL.GL_TEXTURE_CUBE_MAP, MainTemplate.cubemap[0]);
		
		for (int i = 0; i < 6; i++) {
			gl.glTexImage2D(GL.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL.GL_RGB, MainTemplate.CUBEMAP_SIZE , MainTemplate.CUBEMAP_SIZE, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, null);
		}
		
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_R, GL.GL_REPEAT);
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
		
		gl.glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
		gl.glTexGeni(GL.GL_T, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
		gl.glTexGeni(GL.GL_R, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);

		gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);

		//create new frame- and renderbuffer
		gl.glGenFramebuffersEXT(1, MainTemplate.framebuffer, 0);
		gl.glGenRenderbuffersEXT(1, MainTemplate.renderbuffer, 0);
				
		gl.glDisable(GL.GL_TEXTURE_GEN_S);
		gl.glDisable(GL.GL_TEXTURE_GEN_T);
		gl.glDisable(GL.GL_TEXTURE_GEN_R);
		gl.glDisable(GL.GL_TEXTURE_CUBE_MAP);
		
	}

	@Override
	public void bindParameters() {
		// TODO Auto-generated method stub

	}

	@Override
	public void animate(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}
	
	public void render(GLAutoDrawable drawable){
		this.draw(drawable);
	}
	
	@Override
	public void draw(GLAutoDrawable drawable) {

			GL gl=drawable.getGL();
	
			gl.glEnable(GL.GL_TEXTURE_GEN_S);
			gl.glEnable(GL.GL_TEXTURE_GEN_T);
			gl.glEnable(GL.GL_TEXTURE_GEN_R);
			
			
			gl.glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
			gl.glTexGeni(GL.GL_T, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
			gl.glTexGeni(GL.GL_R, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
			
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_R, GL.GL_REPEAT);
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
			gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			
			gl.glEnable(GL.GL_TEXTURE_CUBE_MAP);
			gl.glBindTexture(GL.GL_TEXTURE_CUBE_MAP, MainTemplate.cubemap[0]);
			
			if(visible){
//				gl.glPushMatrix();
//				gl.glTranslatef(17.96f, 2.45f, 23.346f);
	//			MainTemplate.getGlut().glutSolidCube(0.5f);
//				MainTemplate.getGlut().glutSolidSphere(0.2f,20,20);
				drawable.getGL().glCallList(this.getObjectList());
//				gl.glPopMatrix();
			}
			gl.glDisable(GL.GL_TEXTURE_GEN_S);
			gl.glDisable(GL.GL_TEXTURE_GEN_T);
			gl.glDisable(GL.GL_TEXTURE_GEN_R);
			
			gl.glDisable(GL.GL_TEXTURE_CUBE_MAP);
	}
	
	@Override
	public void postDraw(GLAutoDrawable drawable) {
		drawable.getGL().glCallList(this.getObjectList());
	}

}
