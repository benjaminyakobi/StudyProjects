#pragma once
#include <string>
#include <GL/glew.h>

class GLSLProgram //GLSL = GL Shading Language
{
public:
	GLSLProgram();
	~GLSLProgram();

	void compileShaders(const std::string& vertexShaderFilePath, const std::string& fragmentShaderFilePath); //reads our vertex & fragment shaders

	void linkShaders(); //link the shaders to our program

	void addAttribute(const std::string& attributeName);

	GLuint getUniformLocation(const std::string& uniformName);

	void use();
	void unuse();

private:
	void compileShader(const std::string& filePath, GLuint id);
	int _numAttributes;
	GLuint _programID;
	GLuint _vertexShaderID;
	GLuint _fragmentShaderID;


};

