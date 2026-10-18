#version 150

in vec3 Position;
in vec4 Color; // Raw vertex colors packed by the mod

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec4 vertexColor;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    vertexColor = Color; // Forward raw color bytes
}
