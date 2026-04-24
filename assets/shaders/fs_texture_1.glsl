#version 460 compatibility

uniform sampler2D TEX_SAMPLER;
uniform vec4 COLOR_FACTOR;
in vec2 fTexCoords;

out vec4 color;

void main()
{
    //color = texture(TEX_SAMPLER, fTexCoords) + COLOR_FACTOR ;
    color = COLOR_FACTOR;
}
