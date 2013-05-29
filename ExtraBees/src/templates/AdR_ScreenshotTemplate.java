package templates;
/**
 * @author Andreas Elsner / Stephan Arens / Gitta Domik
 * 
 * AdR Shading Template
 * Department of Computer Science at the University of Paderborn, Germany
 * Research Group of Prof. Gitta Domik - Computer Graphics, Visualization and Digital Image Processing
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import modelloader.MeshLoader;

import com.sun.opengl.cg.CGcontext;
import com.sun.opengl.cg.CGparameter;
import com.sun.opengl.cg.CGprogram;
import com.sun.opengl.cg.CgGL;

public class AdR_ScreenshotTemplate extends JoglTemplate
{
	// TODO: Assignment 3_3: create your own toon shader and load it here
	protected static final String FRAGMENT_SHADER = "shader/fp_phongPerPixel.cg";

	protected static final String VERTEX_SHADER = "shader/vp_phongPerPixel.cg";

	static final float[] BRASS_MATERIAL = { 0.33f, 0.22f, 0.03f, 1.0f, 0.78f,
			0.57f, 0.11f, 1.0f, 0.99f, 0.91f, 0.81f, 1.0f, 5.0f };

	static final float[] MOVING_LIGHT_ADS = { 0.2f, 0.2f, 0.2f, 1f, 0.95f, 0.95f,
			0.95f, 1f, 0.95f, 0.95f, 0.95f, 1f };

	private CGcontext cgContext;

	private CGprogram cgVertexProg = null, cgFragmentProg = null;

	private CGparameter cgBlackHolePosition, cgTime;

	private int cgVertexProfile, cgFragProfile;

	protected int dList;

	/* necessary for time dependent rendering */
	private long timeOfFirstFrame = 0;
	private int timeSinceFirstFrame = 0, frameCounter = 0;
	/* take screenshots? */
	static boolean takeScreenshots = true;
	static int xResolution = 1280, yResolution = 720;

	public static void main(String[] args)
	{
		AdR_ScreenshotTemplate frame = new AdR_ScreenshotTemplate();
		frame.setVisible(true);
		
		/* resize to 1280x720. 
		 * First frames might be wrong, but did not find a way to do 
		 * correct resizing without touching the JoglTemplate. 
		 * Problem: insets are only available after frame is set to
		 * visible.
		 * */
		if(takeScreenshots){
			frame.setBounds(0, 0, 
					xResolution + frame.getInsets().left + frame.getInsets().right, 
					yResolution + frame.getInsets().bottom + frame.getInsets().top);
			/*
			 * Create folder "screenshots/"
			 */
			File dir = new File("screenshots");
			try {
				dir.mkdir();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
	}



	public void display(GLAutoDrawable drawable)
	{
		/* Either get time since beginning of the rendering, 
		 * or set time manually to 30fps when taking screenshots. 
		 * */
		if(!takeScreenshots){ // normal time measurement
			if(timeOfFirstFrame == 0){
				timeOfFirstFrame = System.currentTimeMillis();
			} else {
				timeSinceFirstFrame = (int)(System.currentTimeMillis() - timeOfFirstFrame);
			}
		} else { // take screenshots with 30fps
			int fps = 30;
			timeSinceFirstFrame = (int)(frameCounter * 1000.0/fps);
			frameCounter++;
		}
		
		// get the gl object
		GL gl = drawable.getGL();
		// set the erasing color (black)
		gl.glClearColor(0.5f, 0.5f, 0.5f, 0.0f);
		// clear screen with the defined erasing color and depth buffer
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glPushMatrix();
		applyMouseTranslation(gl);
		applyMouseRotation(gl);
		
		/* Make all animations dependent on timeSinceFirstFrame */
		gl.glRotatef(-0.01f * timeSinceFirstFrame, 1, 0, 0);

		// set material for cg
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, BRASS_MATERIAL, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, BRASS_MATERIAL, 4);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, BRASS_MATERIAL, 8);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, BRASS_MATERIAL, 12);

		// blackHole for cg
		float[] blackHolePosition = new float[] { -5f, 5f, 0f, 1f };
		CgGL.cgGLSetParameter3fv(cgBlackHolePosition, blackHolePosition, 0);
		/* Make all animations dependent on timeSinceFirstFrame */
		CgGL.cgGLSetParameter1f(cgTime, (float)timeSinceFirstFrame);

		// set light properties
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, MOVING_LIGHT_ADS, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, MOVING_LIGHT_ADS, 4);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, MOVING_LIGHT_ADS, 8);

		// calculate light position
		float dLightHeight = 5.0f;
		double dLightRadius = 5.0d;
		/* Make all animations dependent on timeSinceFirstFrame */
		float[] lightPos = new float[] {
				(float) (dLightRadius * Math.cos(timeSinceFirstFrame * 3.14 / 2000.0)),
				(float) (dLightRadius * Math.sin(timeSinceFirstFrame * 3.14 / 2000.0)),
				dLightHeight, 1.0f };
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPos, 0);

		// draw light as sphere (without shader)
		gl.glPushMatrix();
		gl.glTranslatef(lightPos[0], lightPos[1], lightPos[2]);
		gl.glColor3f(1f, 1f, 1f);
		getGlu().gluSphere(getGlu().gluNewQuadric(), 0.3, 10, 10);
		gl.glPopMatrix();

		// enable profiles, bind shaders
		CgGL.cgGLEnableProfile(getCgVertexProfile());
		CgGL.cgGLBindProgram(cgVertexProg);
		CgGL.cgGLEnableProfile(getCgFragProfile());
		CgGL.cgGLBindProgram(cgFragmentProg);
		// draw mesh
		gl.glCallList(dList);
		// disable profiles, unload shaders
		CgGL.cgGLDisableProfile(getCgVertexProfile());
		CgGL.cgGLDisableProfile(getCgFragProfile());

		gl.glPopMatrix();
		
		/* 
		 * Save screenshots as 0000.png, 0001.png ...
		 * Dont change the file-names!!
		 * */
		if(takeScreenshots) {
			String fileName = new DecimalFormat("0000").format(frameCounter);
			File file = new File("screenshots/" + fileName + ".png");
			writeBufferToFile(drawable, file);
		}
	}

	/*
	 * Modified from http://www.java-gaming.org/index.php/topic,5386.
	 * */
	private static void writeBufferToFile(GLAutoDrawable drawable, File outputFile) {

		int width = drawable.getWidth();
		int height = drawable.getHeight();

		ByteBuffer pixelsRGB = ByteBuffer.allocateDirect(width * height * 3);

		GL gl = drawable.getGL();

		gl.glReadBuffer(GL.GL_BACK);
		gl.glPixelStorei(GL.GL_PACK_ALIGNMENT, 1);

		gl.glReadPixels(0, 0, width, height, GL.GL_RGB, GL.GL_UNSIGNED_BYTE,
				pixelsRGB);

		int[] pixelInts = new int[width * height];

		// Convert RGB bytes to ARGB ints with no transparency. Flip image
		// vertically by reading the
		// rows of pixels in the byte buffer in reverse - (0,0) is at bottom
		// left in OpenGL.

		int p = width * height * 3; // Index to first byte (red) in each row.
		int q; // Index into ByteBuffer
		int i = 0; // Index into target int[]
		int w3 = width * 3; // Number of bytes in each row

		for (int row = 0; row < height; row++) {
			p -= w3;
			q = p;
			for (int col = 0; col < width; col++) {
				int iR = pixelsRGB.get(q++);
				int iG = pixelsRGB.get(q++);
				int iB = pixelsRGB.get(q++);

				pixelInts[i++] = 0xFF000000 
						| ((iR & 0x000000FF) << 16)
						| ((iG & 0x000000FF) << 8) 
						| (iB & 0x000000FF);
			}

		}

		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);

		bufferedImage.setRGB(0, 0, width, height, pixelInts, 0, width);

		try {
			ImageIO.write(bufferedImage, "PNG", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/* The rest is untouched */
	public void init(GLAutoDrawable drawable)
	{
		super.init(drawable);
		GL gl = drawable.getGL();
		// init Cg
		initCg();
		// load and compile shader
		cgVertexProg = loadShader(getCgVertexProfile(), VERTEX_SHADER);
		cgFragmentProg = loadShader(getCgFragProfile(), FRAGMENT_SHADER);
		// bind CGParameters to vertex and fragment program
		bindParameters();
		// z-buffer test
		gl.glEnable(GL.GL_DEPTH_TEST);
		// backface culling
		gl.glEnable(GL.GL_CULL_FACE);
		// load mesh
		dList = MeshLoader.loadObj(gl, "models/dragon.obj", 0.5f);
	}

	public void initCg()
	{
		cgContext = CgGL.cgCreateContext();
		cgVertexProfile = CgGL.cgGLGetLatestProfile(CgGL.CG_GL_VERTEX);
		if (cgVertexProfile == CgGL.CG_PROFILE_UNKNOWN)
		{
			System.err.println("Invalid vertex profile");
			System.exit(1);
		}
		CgGL.cgGLSetOptimalOptions(cgVertexProfile);

		cgFragProfile = CgGL.cgGLGetLatestProfile(CgGL.CG_GL_FRAGMENT);
		if (cgFragProfile == CgGL.CG_PROFILE_UNKNOWN)
		{
			System.err.println("Invalid fragment profile");
			System.exit(1);
		}
		CgGL.cgGLSetOptimalOptions(cgFragProfile);
	}

	public CGprogram loadShader(int profile, String filename)
	{
		CGprogram shaderprog = CgGL.cgCreateProgramFromFile(getCgContext(),
				CgGL.CG_SOURCE, filename, profile, null, null);
		if (shaderprog == null)
		{
			int err = CgGL.cgGetError();
			System.err.println("Compile shader [" + filename + "] "
					+ CgGL.cgGetErrorString(err));
			if (CgGL.cgGetLastListing(getCgContext()) != null)
			{
				System.err.println(CgGL.cgGetLastListing(getCgContext()) + "\n");
			}
			System.exit(1);
		}

		CgGL.cgGLLoadProgram(shaderprog);

		int err = CgGL.cgGetError();
		if (err != CgGL.CG_NO_ERROR)
		{
			System.out.println("Load shader [" + filename + "]: "
					+ CgGL.cgGetErrorString(err));
			System.exit(1);
		}

		return shaderprog;
	}

	protected void bindParameters()
	{
		cgBlackHolePosition = CgGL.cgGetNamedParameter(cgFragmentProg, "blackHolePosition");
		cgTime = CgGL.cgGetNamedParameter(cgFragmentProg, "time");
	}
	
	public int getCgVertexProfile()
	{
		return cgVertexProfile;
	}

	public int getCgFragProfile()
	{
		return cgFragProfile;
	}

	public CGcontext getCgContext()
	{
		return cgContext;
	}
}