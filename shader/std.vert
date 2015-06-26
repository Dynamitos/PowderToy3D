#version 330

layout (location = 0) in vec4 vertexPosition_modelspace;

varying mat4 MVP;
uniform mat4 view;
uniform mat4 projection;
uniform mat4 model;

void main(){
	MVP = projection * view * model;
	gl_Position = MVP * vertexPosition_modelspace;
}