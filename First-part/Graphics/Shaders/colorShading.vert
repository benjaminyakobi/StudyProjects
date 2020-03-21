#version 130

//input data from the vbo, each vertex is 2 float numbers.
in vec2 vertexPosition;
in vec4 vertexColor;

out vec2 fragmentPosition;
out vec4 fragmentColor;

void main(){
	gl_Position.xy = vertexPosition; //set (x,y) positions on screen
	gl_Position.z = 0.0; //z=0.0 because we program in 2D
	gl_Position.w = 1.0; //Indicates that the coordinates are normalized
	
	fragmentPosition = vertexPosition;
	
	fragmentColor = vertexColor;
}