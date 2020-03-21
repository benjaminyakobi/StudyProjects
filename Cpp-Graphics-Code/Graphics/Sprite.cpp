#include "Sprite.h"
#include "Vertex.h"

#include <cstddef>


Sprite::Sprite()
{
	this->_vboID = 0; //always init to 0. is like NULL.
}


Sprite::~Sprite()
{
	//free the buffer 
	if (this->_vboID != 0)
		glDeleteBuffers(1, &this->_vboID);
}

void Sprite::init(float x, float y, float width, float height)
{
	this->_x = x;
	this->_y = y;
	this->_width = width;
	this->_height = height;

	//if 0 than we know that GLuint not generated yet
	if (this->_vboID == 0) {
		glGenBuffers(1, &this->_vboID); //Generate unique number and assign it into _vboID
	}

	Vertex vertexData[6]; //array of positions
	/*first triangle*/
	//first vertex
	vertexData[0].position.x = x + width;
	vertexData[0].position.y = y + height;
	//second vertex
	vertexData[1].position.x = x;
	vertexData[1].position.y = y+height;
	//third vertex
	vertexData[2].position.x = x;
	vertexData[2].position.y = y;

	/*second triangle*/
	//fourth vertex
	vertexData[3].position.x = x;
	vertexData[3].position.y = y;
	//fifth vertex
	vertexData[4].position.x = x + width;
	vertexData[4].position.y = y;
	//sixth vertex
	vertexData[5].position.x = x + width;;
	vertexData[5].position.y = y + width;

	for (int i = 0; i < 6; i++) {
		vertexData[i].color.r = 255;
		vertexData[i].color.g = 0;
		vertexData[i].color.b = 255;
		vertexData[i].color.a = 255;
	}

	vertexData[1].color.r = 0;
	vertexData[1].color.g = 0;
	vertexData[1].color.b = 255;
	vertexData[1].color.a = 255;

	vertexData[4].color.r = 0;
	vertexData[4].color.g = 255;
	vertexData[4].color.b = 0;
	vertexData[4].color.a = 255;

	glBindBuffer(GL_ARRAY_BUFFER, this->_vboID); //basic vertex buffer
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertexData), 
		vertexData, GL_STATIC_DRAW); //uploading the data
	glBindBuffer(GL_ARRAY_BUFFER, 0); //unbinding the buffer
}

void Sprite::draw()
{
	glBindBuffer(GL_ARRAY_BUFFER, this->_vboID);
	
	glEnableVertexAttribArray(0);

	//Position attribute pointer
	glVertexAttribPointer(0, 2/*size of x,y*/, 
		GL_FLOAT/*array type is float*/, GL_FALSE, 
		sizeof(Vertex), (void*)offsetof(Vertex, position));
	
	//Color attribute pointer
	glVertexAttribPointer(1, 4, GL_UNSIGNED_BYTE, GL_TRUE, 
		sizeof(Vertex), (void*)offsetof(Vertex, color));

	glDrawArrays(GL_TRIANGLES, 0, 6);//drawing the data

	glDisableVertexAttribArray(0);

	glBindBuffer(GL_ARRAY_BUFFER, 0); //unbind the buffer at the end
}
