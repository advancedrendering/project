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

	public String getDefaultVertexProgName() {
		return defaultVertexProgName;
	}

	public void setDefaultVertexProgName(String defaultVertexProgName) {
		this.defaultVertexProgName = defaultVertexProgName;
	}

	public String getDefaultFragmentProgName() {
		return defaultFragmentProgName;
	}

	public void setDefaultFragmentProgName(String defaultFragmentProgName) {
		this.defaultFragmentProgName = defaultFragmentProgName;
	}

	private int cgVertexProfile;
	private int cgFragProfile;
	private CGcontext cgContext;

	public String getBindedVertexProgram() {
		return cgVertexProgName;
	}

	public void bindVertexShaderProgram(String cgNewVertexProgName) throws IllegalArgumentException{
		if (this.vpshaderprograms.containsKey(cgNewVertexProgName)){
			if (!this.cgVertexProgName.equals(cgNewVertexProgName)){
				//disable profile
				CgGL.cgGLDisableProfile(cgVertexProfile);
				//unbind old vertex shader
				CgGL.cgGLUnbindProgram(cgVertexProfile);
				this.cgVertexProgName = cgNewVertexProgName;
				//enable profile
				CgGL.cgGLEnableProfile(cgVertexProfile);
				//bind the new one
				CgGL.cgGLBindProgram(this.vpshaderprograms.get(this.cgVertexProgName));
			}
		}
		else{
			throw new IllegalArgumentException("The shader program " + cgVertexProgName + " is unknown.");
		}
	}

	public String getBindedFragmentProg(){
		return cgFragmentProgName;
	}

	public void bindFragmentShaderProgram(String cgNewFragmentProgName) throws IllegalArgumentException {
		if (this.fpshaderprograms.containsKey(cgNewFragmentProgName)){
			if (!this.cgVertexProgName.equals(cgNewFragmentProgName)){
				//disable profile
				CgGL.cgGLDisableProfile(cgFragProfile);
				//unbind old fragment shader
				CgGL.cgGLUnbindProgram(cgFragProfile);
				this.cgFragmentProgName = cgNewFragmentProgName;
				//enable profile
				CgGL.cgGLEnableProfile(cgFragProfile);
				//bind the new one
				CgGL.cgGLBindProgram(this.fpshaderprograms.get(this.cgFragmentProgName));
			}
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
		
		//set default shader programs.
		defaultVertexProgName = cgVertexProgName;
		defaultFragmentProgName = cgFragmentProgName;

		//enable profile
		CgGL.cgGLEnableProfile(cgVertexProfile);
		CgGL.cgGLEnableProfile(cgFragProfile);
		
		
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
			//check whether shader program is default shader
			//if is default then throw error because is not allowed to remove default shader program from list.
			if (this.cgVertexProgName.equals(name)){
				throw new IllegalArgumentException("Could not remove the default shader program.");
			}
			//check whether shader program is currently active.
			if (this.cgVertexProgName.equals(name)){
				//disable profile
				CgGL.cgGLDisableProfile(cgVertexProfile);
				//unbind vertex shader
				CgGL.cgGLUnbindProgram(cgVertexProfile);
				//reset to default.
				this.cgVertexProgName = this.defaultVertexProgName;
				//enable profile
				CgGL.cgGLEnableProfile(cgVertexProfile);
				//bind the default
				CgGL.cgGLBindProgram(this.vpshaderprograms.get(this.cgVertexProgName));
			}
			//now remove shader program from hashtable.
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
			//check whether shader program is default shader
			//if is default then throw error because is not allowed to remove default shader program from list.
			if (this.cgFragmentProgName.equals(name)){
				throw new IllegalArgumentException("Could not remove the default shader program.");
			}
			//check whether shader program is currently active.
			if (this.cgFragmentProgName.equals(name)){
				//disable profile
				CgGL.cgGLDisableProfile(cgFragProfile);
				//unbind vertex shader
				CgGL.cgGLUnbindProgram(cgFragProfile);
				//reset to default.
				this.cgFragmentProgName = this.defaultFragmentProgName;
				//enable profile
				CgGL.cgGLEnableProfile(cgFragProfile);
				//bind the default
				CgGL.cgGLBindProgram(this.fpshaderprograms.get(this.cgFragmentProgName));
			}
			//now remove shader program from hashtable.
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
