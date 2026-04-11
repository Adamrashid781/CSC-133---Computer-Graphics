#version 410 core

in vec2 fTexCoords;
in vec4 fColor;

out vec4 outColor;

void main(void) {
    outColor = fColor;
}
