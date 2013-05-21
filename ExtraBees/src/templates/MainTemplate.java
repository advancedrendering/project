package templates;

/**
 * @author Andreas Elsner / Stephan Arens / Gitta Domik
 * 
 * AdR Shading Template
 * Department of Computer Science at the University of Paderborn, Germany
 * Research Group of Prof. Gitta Domik - Computer Graphics, Visualization and Digital Image Processing
 */

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.GLUT;

import scenegraph.SceneRoot;


public class MainTemplate extends JoglTemplate {
	
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

	private boolean animation = true, keyPressedW = false, keyPressedS = false,
			keyPressedA = false, keyPressedD = false, keyPressedQ = false,
			keyPressedE = false;
	private float movementSpeed = 0.5f;

	private float lastTime = 0f;

	private float timePerFrame = 0f;
	
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

		// bind CGParameters to vertex and fragment program
		// z-buffer test
		gl.glEnable(GL.GL_DEPTH_TEST);
		// backface culling
		gl.glEnable(GL.GL_CULL_FACE);
		// load mesh
		lastTime = System.nanoTime();

	}

	public void display(GLAutoDrawable drawable) {

		float currentTime = System.nanoTime();
		if (currentTime - lastTime >= 1000000000.0f) {
			timePerFrame = (1000.0f / ((float) getFrameCounter()));
			resetFrameCounter();
			lastTime = System.nanoTime();
		}

		incFrameCounter();
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
		applyMouseTranslation(gl);
		applyMouseRotation(gl);
	
		// bezier test
		if(u> 1.0)
			u=1.0f;
		float[] cam = BezierCurve.getCoordsAt(CTRL_POINTS,u+=0.007f);
		float[] target = BezierCurve.getCoordsAt(CTRL_POINTS,u+0.01f);
		
		
		
//		getGlu().gluLookAt(	cam[0], cam[1], cam[2], target[0], target[1], target[2], 0, 1, 0);
		getGlu().gluLookAt(	cam[0], cam[1], cam[2], 17.972, 2.302, 23.340, 0, 1, 0);
		
		
		// lightning stuff
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);
		gl.glEnable(GL.GL_LIGHT1);

		// set light properties
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, MOVING_LIGHT_ADS, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, MOVING_LIGHT_ADS, 4);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, MOVING_LIGHT_ADS, 8);

		// set light properties
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, MOVING_LIGHT_ADS, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, MOVING_LIGHT_ADS, 4);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, MOVING_LIGHT_ADS, 8);

		// calculate light position
		float dLightHeight = 5.0f;
		double dLightRadius = 5.0d;
		float[] lightPos = new float[] {
				(float) (dLightRadius * Math
						.cos(System.currentTimeMillis() * 3.14 / 4000.0)),
				(float) (dLightRadius * Math
						.sin(System.currentTimeMillis() * 3.14 / 4000.0)), dLightHeight,
				1.0f };
		float[] lightPos_Cam = new float[] { 0f, 5f, -5f, 1f };
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPos, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPos_Cam, 0);

		// draw light as sphere (without shader)
		gl.glPushMatrix();
		gl.glTranslatef(lightPos[0], lightPos[1], lightPos[2]);
		gl.glColor3f(1f, 1f, 1f);
		getGlu().gluSphere(getGlu().gluNewQuadric(), 0.3, 10, 10);
		gl.glPopMatrix();

		// draw light as sphere (without shader)
		gl.glPushMatrix();
		gl.glTranslatef(lightPos_Cam[0], lightPos_Cam[1], lightPos_Cam[2]);
		gl.glColor3f(1f, 1f, 1f);
		getGlu().gluSphere(getGlu().gluNewQuadric(), 0.3, 10, 10);
		gl.glPopMatrix();
		
		SceneRoot.getInstance(drawable).render(drawable);

		gl.glPopMatrix();
	}
	
	private void drawFPS(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		// Farbe Weiß, für die DevStrings
		gl.glWindowPos2d(5, 5);
		getGlut().glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, "FPS: " + (1000.0f / timePerFrame));
	}

	private void updateCamCoords() {
		if (keyPressedA)
			setView_transx(getView_transx() + movementSpeed);
		if (keyPressedD)
			setView_transx(getView_transx() - movementSpeed);
		if (keyPressedQ)
			setView_transy(getView_transy() + movementSpeed);
		if (keyPressedE)
			setView_transy(getView_transy() - movementSpeed);
		if (keyPressedW)
			setView_transz(getView_transz() + movementSpeed);
		if (keyPressedS)
			setView_transz(getView_transz() - movementSpeed);

	}

	/**
	 * This method increases the frame counter
	 * 
	 * @see getFrameCounter()
	 */
	private void incFrameCounter() {
		if (frameCounter < Integer.MAX_VALUE) {
			frameCounter++;
		} else
			frameCounter = 0;

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
}