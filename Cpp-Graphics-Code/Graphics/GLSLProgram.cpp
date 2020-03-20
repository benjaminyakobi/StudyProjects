#include "GLSLProgram.h"
#include "Errors.h"
#include <fstream>
#include <vector>

GLSLProgram::GLSLProgram() : _programID(0), _vertexShaderID(0), _fragmentShaderID(0), _numAttributes(0) {}


GLSLProgram::~GLSLProgram()
{
}

void GLSLProgram::compileShaders(const std::string & vertexShaderFilePath, const std::string & fragmentShaderFilePath)
{
	// Vertex and fragment shaders are successfully compiled.
	// Now time to link them together into a program.
	// Get a program object.
	this->_programID = glCreateProgram();

	this->_vertexShaderID = glCreateShader(GL_VERTEX_SHADER); //returns 0 if error
	if (this->_vertexShaderID == 0) {
		fatalError("Vertex shader failed to be created.");
	}

	this->_fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER); //returns 0 if error
	if (this->_fragmentShaderID == 0) {
		fatalError("Fragment shader failed to be created.");
	}

	this->compileShader(vertexShaderFilePath, this->_vertexShaderID);
	this->compileShader(fragmentShaderFilePath, this->_fragmentShaderID);
}

/*Code from GLSL wiki*/
void GLSLProgram::linkShaders()
{
	// Attach our shaders to our program
	glAttachShader(this->_programID, this->_vertexShaderID);
	glAttachShader(this->_programID, this->_fragmentShaderID);

	// Link our program
	glLinkProgram(this->_programID);

	// Note the different functions here: glGetProgram* instead of glGetShader*.
	GLint isLinked = 0;
	glGetProgramiv(this->_programID, GL_LINK_STATUS, (int *)&isLinked);
	if (isLinked == GL_FALSE)
	{
		GLint maxLength = 0;
		glGetProgramiv(this->_programID, GL_INFO_LOG_LENGTH, &maxLength);

		// The maxLength includes the NULL character
		std::vector<char> errorLog(maxLength);
		glGetProgramInfoLog(this->_programID, maxLength, &maxLength, &errorLog[0]);

		// We don't need the program anymore.
		glDeleteProgram(this->_programID);
		// Don't leak shaders either.
		glDeleteShader(this->_vertexShaderID);
		glDeleteShader(this->_fragmentShaderID);

		std::printf("%s\n", &(errorLog[0]));
		fatalError("Shaders failed to link.");
	}

	// Always detach shaders after a successful link.
	glDetachShader(this->_programID, this->_vertexShaderID);
	glDetachShader(this->_programID, this->_fragmentShaderID);
	glDeleteShader(this->_vertexShaderID);
	glDeleteShader(this->_fragmentShaderID);
}

void GLSLProgram::addAttribute(const std::string & attributeName)
{
	glBindAttribLocation(this->_programID, this->_numAttributes, attributeName.c_str());
	this->_numAttributes++;
}

GLuint GLSLProgram::getUniformLocation(const std::string & uniformName)
{
	GLuint location = glGetUniformLocation(this->_programID, uniformName.c_str());
	if (location == GL_INVALID_INDEX) {
		fatalError("Uniform " + uniformName + " not found in shader.");
	}

	return location;
}

void GLSLProgram::use()
{
	glUseProgram(this->_programID);
	for (int i = 0; i < this->_numAttributes; i++) {
		glEnableVertexAttribArray(i); //enabling each vertex
	}
}

void GLSLProgram::unuse()
{
	glUseProgram(0);
	for (int i = 0; i < this->_numAttributes; i++) {
		glDisableVertexAttribArray(i); //disabeling each vertex
	}
}

/*Private method used in public compileShaders method*/
void GLSLProgram::compileShader(const std::string & filePath, GLuint id)
{
	std::ifstream vertexFile(filePath);
	if (vertexFile.fail()) {
		perror(filePath.c_str()); //more specific error information
		fatalError("Failed to open " + filePath);
	}

	std::string fileContent = "";
	std::string line;

	//reading the whole file into fileContent string variable
	while (std::getline(vertexFile, line)) {
		fileContent += line + "\n";
	}

	vertexFile.close();

	const char* contentPtr = fileContent.c_str(); //array of c string which is char*
	glShaderSource(id, 1/*number of strings*/, &contentPtr, nullptr);

	glCompileShader(id);

	/*Error checking from GLSL wiki*/
	GLint success = 0;
	glGetShaderiv(id, GL_COMPILE_STATUS, &success);

	if (success == GL_FALSE)
	{
		GLint maxLength = 0;
		glGetShaderiv(id, GL_INFO_LOG_LENGTH, &maxLength);
		// The maxLength includes the NULL character
		std::vector<char> errorLog(maxLength);
		
		glGetShaderInfoLog(id, maxLength, &maxLength, &errorLog[0]);

		// Provide the infolog in whatever manor you deem best.
		// Exit with failure.
		glDeleteShader(id); // Don't leak the shader.

		std::printf("%s\n", &(errorLog[0]));
		fatalError("Shader " + filePath + " failed to compile.");
	}
}
