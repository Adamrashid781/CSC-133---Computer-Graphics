#version 410 core

layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoords;
layout (location = 2) in vec4 aColor;

uniform mat4 uProjMatrix;
uniform mat4 uViewMatrix;

out vec2 fTexCoords;
out vec4 fColor;

void main(void) {
    fTexCoords = aTexCoords;
    fColor     = aColor;
    gl_Position = uProjMatrix * uViewMatrix * vec4(aPos, 1.0);
}
