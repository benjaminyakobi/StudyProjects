#version 130

//Fragment shader operates on each pixel in a given polygon
in vec2 fragmentPosition;
in vec4 fragmentColor;

//4 component float vector we use to output to the screen for each pixel
out vec4 color; 

uniform float time;

void main() {
	color = vec4(fragmentColor.r * (cos(fragmentPosition.x*4.0+time) + 1.0) * 0.5,
				 fragmentColor.g * (cos(fragmentPosition.y*8.0+time) + 1.0) * 0.5,
				 fragmentColor.b * (cos(fragmentPosition.x*2.0+time) + 1.0) * 0.5, 
				 fragmentColor.a );


//	color = fragmentColor + vec4(1.0 * (cos(time)+1.0) * 0.5, 
//								1.0 * (cos(time+2.0)+1.0) * 0.5,
//								1.0 * (sin(time+1.0)+1.0) * 0.5, 0.0);
}