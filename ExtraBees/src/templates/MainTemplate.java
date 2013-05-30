package templates;

/**
 * @author Andreas Elsner / Stephan Arens / Gitta Domik
 * 
 * AdR Shading Template
 * Department of Computer Science at the University of Paderborn, Germany
 * Research Group of Prof. Gitta Domik - Computer Graphics, Visualization and Digital Image Processing
 */

import java.awt.event.KeyEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import scenegraph.GlassModel;
import scenegraph.SceneRoot;

import com.sun.opengl.util.GLUT;


public class MainTemplate extends JoglTemplate {
	
	private static TimeFPSCounter fpsCounter;
	
	public static int[] cubemap = new int[1];
	public static  int CUBEMAP_SIZE = 2048;
	public static  int[] framebuffer = new int[1];
	public static  int[] renderbuffer = new int[1];
	
	protected final static float CTRL_POINTS[] = {33.519f, 1.071f+9.342f, -9.861f+5.160f,
		33.13f, 8.766f+9.342f, -8.536f+5.160f,
		29.213f,-4.754f+9.342f,10.246f+5.160f,
		20.468f,-5.983f+9.342f,16.304f+5.160f };

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final float[] MOVING_LIGHT_ADS = { 0.2f, 0.2f, 0.2f, 1f, 0.95f,
			0.95f, 0.95f, 1f, 0.95f, 0.95f, 0.95f, 1f };

	private int frameCounter = 0;

	private boolean animation = false, keyPressedW = false, keyPressedS = false,
			keyPressedA = false, keyPressedD = false, keyPressedQ = false,
			keyPressedE = false;
	private float movementSpeed = 0.2f;
	
	private boolean showFPS = false;
	float u = 0.0f;

	public static void main(String[] args) {
		MainTemplate assignment = new MainTemplate();
		assignment.setSize(1280, 720);
		assignment.setVisible(true);
	}

	public void init(GLAutoDrawable drawable) {
		super.init(drawable);
		GL gl = drawable.getGL();
		// init Cg
		// load and compile shader
		fpsCounter = new TimeFPSCounter();

		// bind CGParameters to vertex and fragment program
		// z-buffer test
		gl.glEnable(GL.GL_DEPTH_TEST);
		// backface culling
		gl.glEnable(GL.GL_CULL_FACE);
		// load mesh
//		lastTime = System.nanoTime();
	}
	


	public void display(GLAutoDrawable drawable) {
		fpsCounter.update();

//		float currentTime = System.nanoTime();
//		if (currentTime - lastTime >= 1000000000.0f) {
//			timePerFrame = (1000.0f / ((float) getFrameCounter()));
//			resetFrameCounter();
//			lastTime = System.nanoTime();
//		}
//
//		incFrameCounter();
		updateCamCoords();
		
		// get the gl object
		GL gl = drawable.getGL();
		// set the erasing color (black)
		gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		// clear screen with the defined erasing color and depth buffer
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glPushMatrix();
		if (showFPS){
			gl.glColor3f(0, 1, 0);
			drawFPS(drawable);
		}
		renderToBuffer(drawable,drawable.getGL(),MainTemplate.getGlu());
		applyMouseTranslation(gl);
		applyMouseRotation(gl);
	
		// press space to start animation
		if(animation){
			float[] camPosition = BezierCurve.getCoordsAt(Paths.CAMERA_1,Paths.CAMERA_1_U);
			Blocks.heliPathActive = true; // heli animation starts
			setView_transx(camPosition[0]);
			setView_transy(camPosition[1]);
			setView_transz(camPosition[2]);
			getGlu().gluLookAt(	getView_transx(), getView_transy(), getView_transz(),
					Paths.CAMERA_TARGET_1[0], Paths.CAMERA_TARGET_1[1], Paths.CAMERA_TARGET_1[2], 
					0, 1, 0);
		}
				
		if(Blocks.camera_1_PathActive && Paths.CAMERA_1_U < 1.0f){ // if camera 1 path is active
			Paths.CAMERA_1_U += Paths.getCamera1Speed();
		}
		

		
		gl.glEnable(GL.GL_LIGHTING);
		
			float[] lightPos0 = new float[] {-1f,-1f,-1f, 0f };
			gl.glEnable(GL.GL_LIGHT0);
			// set light properties
			gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, MOVING_LIGHT_ADS, 0);
			gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, MOVING_LIGHT_ADS, 4);
			gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, MOVING_LIGHT_ADS, 8);
			gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPos0, 0);

			float[] lightPos1 = new float[] {20.5072f,6.1059f, 35.7706f, 1f };
			gl.glEnable(GL.GL_LIGHT1);
			// set light properties
			gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, MOVING_LIGHT_ADS, 0);
			gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, MOVING_LIGHT_ADS, 4);
			gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, MOVING_LIGHT_ADS, 8);
			gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPos1, 0);
			
			float[] lightPos2 = new float[] {25.099f,4.89f, 21.971f, 1f };
			gl.glEnable(GL.GL_LIGHT2);
			// set light properties
			gl.glLightfv(GL.GL_LIGHT2, GL.GL_AMBIENT, MOVING_LIGHT_ADS, 0);
			gl.glLightfv(GL.GL_LIGHT2, GL.GL_DIFFUSE, MOVING_LIGHT_ADS, 4);
			gl.glLightfv(GL.GL_LIGHT2, GL.GL_SPECULAR, MOVING_LIGHT_ADS, 8);
			gl.glLightfv(GL.GL_LIGHT2, GL.GL_POSITION, lightPos2, 0);
			
		SceneRoot.getInstance(drawable).render(drawable);

		gl.glPopMatrix();
	}
	
public void renderToBuffer(GLAutoDrawable drawable, GL gl, GLU glu) {
	float[] at = {17.96f, 2.45f, 23.346f};

		int viewport[] = new int[4];
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
		
		gl.glReadBuffer(GL.GL_BACK);
		
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		glu.gluPerspective(90, 1, 0.01, 1000);
		gl.glViewport(0, 0, CUBEMAP_SIZE, CUBEMAP_SIZE);
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glPushMatrix();

		//bind framebuffer
		gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, framebuffer[0]);	
		gl.glBindTexture(GL.GL_TEXTURE_CUBE_MAP, cubemap[0]);	

		//Set up depthbuffer
		gl.glBindRenderbufferEXT(GL.GL_RENDERBUFFER_EXT, renderbuffer[0]);
		gl.glRenderbufferStorageEXT(GL.GL_RENDERBUFFER_EXT, GL.GL_DEPTH_COMPONENT, CUBEMAP_SIZE, CUBEMAP_SIZE);
		gl.glFramebufferRenderbufferEXT(GL.GL_FRAMEBUFFER_EXT, GL.GL_DEPTH_ATTACHMENT_EXT, GL.GL_RENDERBUFFER_EXT, renderbuffer[0]);
	
		int drawBuffers[] = new int[1];
		drawBuffers[0] = GL.GL_COLOR_ATTACHMENT0_EXT;
		gl.glDrawBuffers(1, drawBuffers, 0);
		
		for (int face = 0; face < 6; face++) {
			gl.glFramebufferTexture2DEXT(GL.GL_FRAMEBUFFER_EXT, GL.GL_COLOR_ATTACHMENT0_EXT, GL.GL_TEXTURE_CUBE_MAP_POSITIVE_X + face, cubemap[0], 0);
			
			if(gl.glCheckFramebufferStatusEXT(GL.GL_FRAMEBUFFER_EXT)!=GL.GL_FRAMEBUFFER_COMPLETE_EXT) {
				System.out.println("Failed setting up framebuffer");
//			} else {
//				System.out.println("Hurray!");
			}
			
			gl.glPushMatrix();
			// set the erasing color (black)
			gl.glClearColor(1f, 1f, 1f, 0f);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT|GL.GL_DEPTH_BUFFER_BIT);
			gl.glLoadIdentity();
//			switch (face) {
//				case 0: glu.gluLookAt(0,0,0, u[0], u[1], u[2], 0, -1, 0);break;
//				case 1: glu.gluLookAt(0,0,0,-u[0],-u[1],-u[2], 0, -1, 0);break;
//				case 2: glu.gluLookAt(0,0,0, v[0], v[1], v[2], 0, 0, 1);break;
//				case 3:	glu.gluLookAt(0,0,0,-v[0],-v[1],-v[2], 0, 0, 1);break;
//				case 4:	glu.gluLookAt(0,0,0, n[0], n[1], n[2], 0, -1, 0);break;
//				case 5:	glu.gluLookAt(0,0,0,-n[0],-n[1],-n[2], 0, -1, 0);break;
//			}
			
//			switch (face) {
//			case 0: glu.gluLookAt(at[0],at[1],at[2],  u[0], u[1], u[2], 0, -1, 0);break;  // GL_TEXTURE_CUBE_MAP_POSITIVE_X
//			case 1: glu.gluLookAt(at[0],at[1],at[2], -u[0],-u[1],-u[2], 0, -1, 0);break; // GL_TEXTURE_CUBE_MAP_NEGATIVE_X
//			case 2: glu.gluLookAt(at[0],at[1],at[2],  v[0], v[1], v[2], 0, 0, -1);break;  // GL_TEXTURE_CUBE_MAP_POSITIVE_Y
//			case 3:	glu.gluLookAt(at[0],at[1],at[2], -v[0],-v[1],-v[2], 0, 0, -1);break; // GL_TEXTURE_CUBE_MAP_NEGATIVE_Y
//			case 4:	glu.gluLookAt(at[0],at[1],at[2],  n[0], n[1], n[2], 0, -1, 0);break;  // GL_TEXTURE_CUBE_MAP_POSITIVE_Z
//			case 5:	glu.gluLookAt(at[0],at[1],at[2], -n[0],-n[1],-n[2], 0, -1, 0);break;   // GL_TEXTURE_CUBE_MAP_NEGATIVE_Z
//		}
			
			switch (face) {
			case 0: glu.gluLookAt(0,0,0,  1, 0, 0, 0, -1, 0);break;  // GL_TEXTURE_CUBE_MAP_POSITIVE_X
			case 1: glu.gluLookAt(0,0,0, -1, 0, 0, 0, -1, 0);break; // GL_TEXTURE_CUBE_MAP_NEGATIVE_X
			case 2: glu.gluLookAt(0,0,0,  0, 1, 0, 0, 0, 1);break;  // GL_TEXTURE_CUBE_MAP_POSITIVE_Y
			case 3:	glu.gluLookAt(0,0,0,  0,-1, 0, 0, 0, -1);break; // GL_TEXTURE_CUBE_MAP_NEGATIVE_Y
			case 4:	glu.gluLookAt(0,0,0,  0, 0, 1, 0, -1, 0);break;  // GL_TEXTURE_CUBE_MAP_POSITIVE_Z
			case 5:	glu.gluLookAt(0,0,0,  0, 0,-1, 0, -1, 0);break;   // GL_TEXTURE_CUBE_MAP_NEGATIVE_Z
		}
			
			gl.glRotatef(this.getView_rotx(), 1, 0, 0);
			gl.glRotatef(this.getView_roty(), 0, 1, 0);
			gl.glRotatef(this.getView_rotz(), 0, 0, 1);
			gl.glTranslatef(-at[0],-at[1],-at[2]);
			GlassModel.visible = false;
			SceneRoot.getInstance(drawable).render(drawable);
			GlassModel.visible = true;
			gl.glPopMatrix();
		}
		gl.glPopMatrix();
		
		gl.glViewport(viewport[0], viewport[1], viewport[2], viewport[3]);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glMatrixMode(GL.GL_MODELVIEW);
		
		//finally unbind buffers to return to the normal buffers
		gl.glBindFramebufferEXT(GL.GL_FRAMEBUFFER_EXT, 0); 
		gl.glBindRenderbufferEXT(GL.GL_RENDERBUFFER_EXT, 0);
	}
	
	private void drawFPS(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		// Farbe Wei�, f�r die DevStrings
		gl.glWindowPos2d(5, 5);
		getGlut().glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "FPS: " +fpsCounter.getFPS());
	}

	private void updateCamCoords() {
		if (keyPressedA)
			setView_transx(getView_transx() - movementSpeed);
		if (keyPressedD)
			setView_transx(getView_transx() + movementSpeed);
		if (keyPressedQ)
			setView_transy(getView_transy() + movementSpeed);
		if (keyPressedE)
			setView_transy(getView_transy() - movementSpeed);
		if (keyPressedW)
			setView_transz(getView_transz() - movementSpeed);
		if (keyPressedS)
			setView_transz(getView_transz() + movementSpeed);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		if (e.getKeyCode() == KeyEvent.VK_F) {
			showFPS = !showFPS;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			keyPressedA = true;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			keyPressedD = true;
		} else if (e.getKeyCode() == KeyEvent.VK_Q) {
			keyPressedQ = true;
		} else if (e.getKeyCode() == KeyEvent.VK_E) {
			keyPressedE = true;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			keyPressedW = true;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			keyPressedS = true;
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			animation = !animation;
			System.out.println("Animation: " + animation);
		}
	}

	public void keyReleased(KeyEvent e) {
		super.keyPressed(e);
		if (e.getKeyCode() == KeyEvent.VK_A) {
			keyPressedA = false;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			keyPressedD = false;
		} else if (e.getKeyCode() == KeyEvent.VK_Q) {
			keyPressedQ = false;
		} else if (e.getKeyCode() == KeyEvent.VK_E) {
			keyPressedE = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			keyPressedW = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			keyPressedS = false;
		}
	}

	public int getFrameCounter() {
		return this.frameCounter;
	}

	public void resetFrameCounter() {
		this.frameCounter = 0;
	}

	public static TimeFPSCounter getFPSCounter() {
		return fpsCounter;
	}
}