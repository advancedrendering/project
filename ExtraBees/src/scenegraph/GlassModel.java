package scenegraph;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.cg.CgGL;

import templates.MainTemplate;

public class GlassModel extends SceneGraphNode {

	

	public static boolean visible = true;
	
	public GlassModel(GLAutoDrawable drawable, float scale) {
		super(drawable, "models/glass", scale);
		// TODO Auto-generated constructor stub
		
		this.setFragShaderEnabled(true);
		this.setVertexShaderEnabled(true);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		gl.glEnable(GL.GL_TEXTURE_GEN_S);
		gl.glEnable(GL.GL_TEXTURE_GEN_T);
		gl.glEnable(GL.GL_TEXTURE_GEN_R);
		gl.glEnable(GL.GL_TEXTURE_CUBE_MAP);
		
		// generate texture space
		gl.glGenTextures(1, MainTemplate.cubemapGlass, 0);
		
		// create textures
		gl.glBindTexture(GL.GL_TEXTURE_CUBE_MAP, MainTemplate.cubemapGlass[0]);
		
		for (int i = 0; i < 6; i++) {
			gl.glTexImage2D(GL.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL.GL_RGB, MainTemplate.CUBEMAP_SIZE , MainTemplate.CUBEMAP_SIZE, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, null);
		}
		
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_WRAP_R, GL.GL_REPEAT);
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);//GL.GL_LINEAR);
		
		// Generate mipmaps, by the way.
		gl.glGenerateMipmapEXT(GL.GL_TEXTURE_CUBE_MAP);
		
		gl.glTexGeni(GL.GL_S, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
		gl.glTexGeni(GL.GL_T, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);
		gl.glTexGeni(GL.GL_R, GL.GL_TEXTURE_GEN_MODE, GL.GL_REFLECTION_MAP);

		gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE);
				
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
		gl.glTexParameteri(GL.GL_TEXTURE_CUBE_MAP, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);//GL.GL_LINEAR);
		
		
		gl.glEnable	(GL.GL_TEXTURE_CUBE_MAP);
		gl.glBindTexture(GL.GL_TEXTURE_CUBE_MAP, MainTemplate.cubemapGlass[0]);

		if(visible){
//				gl.glPushMatrix();
//				gl.glTranslatef(17.96f, 2.45f, 23.346f);
////			this.getShaderManager().bindVP("cube");
////			this.getShaderManager().bindFP("cube");
////			CgGL.cgGLSetStateMatrixParameter(this.getShaderManager().getVertexShaderParam("cube", "modelToWorld"), CgGL.CG_GL_MODELVIEW_PROJECTION_MATRIX,CgGL.CG_GL_MATRIX_INVERSE_TRANSPOSE);
//			this.getShaderManager().setFragShaderEnabled(true);
//			this.getShaderManager().setVertexShaderEnabled(true);
//			this.getShaderManager().bindVP("cube");
//			this.getShaderManager().bindFP("cube");
//			//update camera position
//			CgGL.cgGLSetParameter3f(SceneRoot.getInstance(drawable).getShaderManager().getVertexShaderParam("cube", "worldEyePosition"), MainTemplate.view_transx,  MainTemplate.view_transy, MainTemplate.view_transz);
//			CgGL.cgGLSetStateMatrixParameter(this.getShaderManager().getVertexShaderParam("cube", "modelToWorld"), CgGL.CG_GL_MODELVIEW_MATRIX,CgGL.CG_GL_MATRIX_INVERSE);
//			CgGL.cgGLSetTextureParameter(SceneRoot.getInstance(drawable).getShaderManager().getFragShaderParam("cube", "environmentMap"), MainTemplate.cubemap[0]); 
//			CgGL.cgGLEnableTextureParameter(SceneRoot.getInstance(drawable).getShaderManager().getFragShaderParam("cube", "environmentMap"));
			gl.glDisable(GL.GL_CULL_FACE);
			drawable.getGL().glCallList(this.getObjectList());
//				MainTemplate.getGlut().glutSolidSphere(0.2f,20,20);
			gl.glEnable(GL.GL_CULL_FACE);
			
//			CgGL.cgGLDisableTextureParameter(SceneRoot.getInstance(drawable).getShaderManager().getFragShaderParam("cube", "environmentMap"));
//			CgGL.cgGLSetParameter1d(this.getShaderManager().getFragShaderParam("cube", "useTexture"), this.getShaderManager().FALSE);
//				gl.glPopMatrix();
		}
		gl.glDisable(GL.GL_TEXTURE_GEN_S);
		gl.glDisable(GL.GL_TEXTURE_GEN_T);
		gl.glDisable(GL.GL_TEXTURE_GEN_R);
//		
		gl.glDisable(GL.GL_TEXTURE_CUBE_MAP);
	}
	
	@Override
	public void postDraw(GLAutoDrawable drawable) {
		GL gl=drawable.getGL();
		gl.glDisable(GL.GL_CULL_FACE);
		drawable.getGL().glCallList(this.getObjectList());
		gl.glEnable(GL.GL_CULL_FACE);
	}

}
