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

import scenegraph.SceneRoot;

public class MainTemplate extends JoglTemplate
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final float[] MOVING_LIGHT_ADS = { 0.2f, 0.2f, 0.2f, 1f, 0.95f, 0.95f,
			0.95f, 1f, 0.95f, 0.95f, 0.95f, 1f };
	
	private int frameCounter = 0;

	private boolean animation = true;

	
	public static void main(String[] args)
	{
		MainTemplate assignment = new MainTemplate();
		assignment.setVisible(true);
	}

	public void init(GLAutoDrawable drawable)
	{
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
	
	}

	public void display(GLAutoDrawable drawable)
	{
		// if animation is on, increase frame counter
		if (animation)
			incFrameCounter();
		// get the gl object
		GL gl = drawable.getGL();
		// set the erasing color (black)
		gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		// clear screen with the defined erasing color and depth buffer
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glPushMatrix();
		applyMouseTranslation(gl);
		applyMouseRotation(gl);

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
				(float) (dLightRadius * Math.cos(getFrameCounter() * 3.14 / 200.0)),
				(float) (dLightRadius * Math.sin(getFrameCounter() * 3.14 / 200.0)),
				dLightHeight, 1.0f };
		float[] lightPos_Cam = new float[] {0f,5f,-5f,1f	};
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

	/**
	 * This method increases the frame counter
	 * 
	 * @see getFrameCounter()
	 */
	private void incFrameCounter()
	{
		if (frameCounter < Integer.MAX_VALUE)
		{
			frameCounter++;
		}
		else
			frameCounter = 0;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		super.keyPressed(e);
		if (e.getKeyCode() == KeyEvent.VK_M)
		{

		}
		else if (e.getKeyCode() == KeyEvent.VK_S)
		{
	
		}
		else if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			animation = !animation;
			System.out.println("Animation: " + animation);
		}
	}

	public int getFrameCounter()
	{
		return frameCounter;
	}
}