package shadermanager;

import java.util.HashMap;

import com.sun.opengl.cg.CGparameter;
import com.sun.opengl.cg.CGprogram;
import com.sun.opengl.cg.CgGL;

public class Shader {
	
	
	private String name = "";
	private CGprogram shaderProg = null;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the shaderProg
	 */
	public CGprogram getShaderProg() {
		return shaderProg;
	}

	private HashMap<String, CGparameter> parameters = null;
	
	public Shader(String name, CGprogram shader_prog){
		this.name = name;
		this.shaderProg = shader_prog;
		this.parameters = new HashMap<String, CGparameter>();
	}

	/**
	 * Adds a new CGparameter to the shader program.
	 * @param name Name of parameter.
	 * @throws IllegalArgumentException
	 */
	public void addParameter(String name) throws IllegalArgumentException{
		if (!this.parameters.containsKey(name)){
			//create new parameter
			CGparameter new_param = CgGL.cgGetNamedParameter(this.shaderProg, name);
			//add parameter to collection.
			this.parameters.put(name, new_param);
		}
		else{
			throw new IllegalArgumentException("The shader parameter is already in the parameter collection.");
		}	
	}
	
	/**
	 * Gets a parameter of shader program by name.
	 * @param name Name of the parameter.
	 * @return Returns the CGparameter.
	 * @throws IllegalArgumentException
	 */
	public CGparameter getParameter(String name) throws IllegalArgumentException{
		if (this.parameters.containsKey(name)){
			return this.parameters.get(name);
		}
		else{
			throw new IllegalArgumentException("The parameter name is unknown.");
		}
	}
}
