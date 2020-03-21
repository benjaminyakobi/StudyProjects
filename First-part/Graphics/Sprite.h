#pragma once
#include <GL/glew.h>

class Sprite
{
public:
	Sprite();
	~Sprite();

	void init(float x, float y, float width, float height);
	void draw();


private:
	float _x, _y, _width, _height;
	GLuint _vboID; //vertec buffer ID for CPU/GPU. GLuint is unsigned int but guaranteed to be 32bit

	 
};

