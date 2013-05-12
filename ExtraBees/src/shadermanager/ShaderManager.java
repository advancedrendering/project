package shadermanager;

import java.util.HashMap;

import com.sun.opengl.cg.CGcontext;
import com.sun.opengl.cg.CGprogram;
import com.sun.opengl.cg.CgGL;

/**
 * 
 * This class implements a ShaderManager, which manages the usage of shader programs.
 * The ShaderManager manages all shader programs. A certain shader program can be executed 
 * Shaderprograms are kept in a hash map such that they are accessible via their name.
 * Moreover there is always a default shader which is always applied when no other shader was specified.
 */
public class ShaderManager {
	
	/**
	 * Loads a shader program from file.
	 * @param cgcontext A context object.
	 * @param profile The profile i.e. VERTEX_SHADER or FRAGMENT_SHADER.
	 * @param filename Filename of the shader program to be loaded.
	 * @return The shader program.
	 */
	public static CGprogram loadShader(CGcontext cgcontext, int profile, String filename)
	{
		CGprogram shaderprog = CgGL.cgCreateProgramFromFile(cgcontext,
				CgGL.CG_SOURCE, filename, profile, null, null);
		if (shaderprog == null)
		{
			int err = CgGL.cgGetError();
			System.err.println("Compile shader [" + filename + "] "
					+ CgGL.cgGetErrorString(err));
			if (CgGL.cgGetLastListing(cgcontext) != null)
			{
				System.err.println(CgGL.cgGetLastListing(cgcontext) + "\n");
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
	
	
	private static String defaultInitialVPPath = "shader/vp_phongPerPixel.cg";
	private static String defaultInitialFPPath = "shader/fp_phongPerPixel.cg";
	private static String defaultInitialVPName = "phong";
	private static String defaultInitialFPName = "phong";
	
	//use singleton design pattern.
	private static ShaderManager shadermanager = null;
	
	//fields to hold the currently active shader programs.
	private String cgVertexProgName = "", cgFragmentProgName = "";

	private HashMap<String, CGprogram> vpshaderprograms = null, fpshaderprograms = null;
	
	//field to hold the default shader programs.
	private String defaultVertexProgName = "", defaultFragmentProgName = "";

	private int cgFragProfile;
	private int cgVertexProfile;
	private CGcontext cgContext;

	public String getBindedVertexProgram() {
		return cgVertexProgName;
	}

	public void bindVertexShaderProgram(String cgVertexProgName) throws IllegalArgumentException{
		if (this.vpshaderprograms.containsKey(cgVertexProgName)){
			this.cgVertexProgName = cgVertexProgName;
			//TODO: Loading the vertex program  i.e. unload currently active one and load new one
		}
		else{
			throw new IllegalArgumentException("The shader program " + cgVertexProgName + " is unknown.");
		}
	}

	public String getBindedFragmentProg(){
		return cgFragmentProgName;
	}

	public void bindFragmentShaderProgram(String cgFragmentProgName) throws IllegalArgumentException {
		if (this.fpshaderprograms.containsKey(cgFragmentProgName)){
			this.cgFragmentProgName = cgFragmentProgName;
			//TODO: Loading the fragment program i.e. unload currently active one and load new one
		}
		else{
			throw new IllegalArgumentException("The shader program " + cgFragmentProgName + " is unknown.");
		}
	}

	/**
	 * The standard constructor.
	 */
	private ShaderManager(){
		//TODO: Handling of the default shader programs.
		this.vpshaderprograms = new HashMap<String, CGprogram>();
		this.fpshaderprograms = new HashMap<String, CGprogram>();
		
		//create context
		this.cgContext = CgGL.cgCreateContext(); 
		
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
		
		//load default shader programs
		CGprogram loc_vertex_shader = ShaderManager.loadShader(this.cgContext, cgVertexProfile, ShaderManager.defaultInitialVPPath);
		CGprogram loc_fragment_shader = ShaderManager.loadShader(this.cgContext, cgFragProfile,	 ShaderManager.defaultInitialFPPath);
		
		//put them in the hashtable
		this.vpshaderprograms.put(ShaderManager.defaultInitialVPName, loc_vertex_shader);
		this.fpshaderprograms.put(ShaderManager.defaultInitialFPName, loc_fragment_shader);
		
		//set currently loaded shader programs
		cgVertexProgName = ShaderManager.defaultInitialVPName;
		cgFragmentProgName = ShaderManager.defaultInitialFPName;
		
		//bind the programs i.e. load them.
		CgGL.cgGLBindProgram(loc_vertex_shader);
		CgGL.cgGLBindProgram(loc_fragment_shader);
	}
	
	/**
	 * Adds a vertex shader program.
	 * @param name The name of the shader program.
	 * @param shaderprogram The actual shader program.
	 * @throws IllegalArgumentException Is thrown if the shader program was already loaded.
	 */
	public void addVertexShaderProgram(String name, CGprogram shaderprogram) throws IllegalArgumentException{
		if (!this.vpshaderprograms.containsKey(name)){
			this.vpshaderprograms.put(name, shaderprogram);
		}
		else{
			throw new IllegalArgumentException("The vertex shader program " + name + " was already loaded.");
		}
	}
	
	/**
	 * Adds a fragment shader program.
	 * @param name The name of the shader program.
	 * @param shaderprogram The actual shader program.
	 * @throws IllegalArgumentException Is thrown if the shader program was already loaded.
	 */
	public void addFragmentShaderProgram(String name, CGprogram shaderprogram) throws IllegalArgumentException{
		if (!this.fpshaderprograms.containsKey(name)){
			this.fpshaderprograms.put(name, shaderprogram);
		}
		else{
			throw new IllegalArgumentException("The fragment shader program " + name + " was already loaded.");
		}
	}
	
	/**
	 * Remove a vertex shader program from manager.
	 * @param name The name of the shader program to be removed.
	 * @throws IllegalArgumentException
	 */
	public void removeVertexShaderProgram(String name) throws IllegalArgumentException{
		if (this.vpshaderprograms.containsKey(name)){
			//check whether shader program is currently active.
			if (this.cgVertexProgName.equals(name)){
				//TODO: Remove program.
			}
			this.vpshaderprograms.remove(name);
		}
		else{
			throw new IllegalArgumentException("The shader program " + name + " is unknown.");
		}
	}
	
	/**
	 * Remove a fragment shader program from manager.
	 * @param name The name of the shader program to be removed.
	 * @throws IllegalArgumentException
	 */
	public void removeFragmentShaderProgram(String name) throws IllegalArgumentException{
		if (this.fpshaderprograms.containsKey(name)){
			//check whether shader program is currently active.
			if (this.cgFragmentProgName.equals(name)){
				//TODO: Remove program.
			}
			this.fpshaderprograms.remove(name);
		}
		else{
			throw new IllegalArgumentException("The shader program " + name + " is unknown.");
		}
	}
	
	/**
	 * Get instance of shadermanager.
	 * @return Returns the instance of the shadermanager.
	 */
	public static ShaderManager getInstance(){
		if (shadermanager == null){
			shadermanager = new ShaderManager();
		}
		return shadermanager;
	}
	
}
