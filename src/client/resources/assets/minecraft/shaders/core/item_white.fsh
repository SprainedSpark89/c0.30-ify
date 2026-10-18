#version 150

// Keep the layout intact so the pipeline links without crashing
layout(std140, row_major) uniform Globals {
    ivec3 CameraBlockPos;
    vec3 CameraOffset;
    vec2 ScreenSize;
    float GlintAlpha;
    float GameTime; 
    int MenuBlurRadius;
    int UseRgss;
}; 

in vec4 vertexColor;
out vec4 fragColor;

void main() {
    // 1. Grab the current pixel's position on the screen
    // 2. Add an offset based on the camera position so moving around shifts the wave
    float waveOffset = gl_FragCoord.x * 0.05 + gl_FragCoord.y * 0.05;
    
    // Add the CameraOffset from the uniform block to make it shift fluidly when moving
    waveOffset += (CameraOffset.x + CameraOffset.y + CameraOffset.z) * 10.0;

    // 3. Compute your classic c0.30 alpha-pulsing drop math
    float pulse = sin(waveOffset); 
    pulse = pulse * 0.5 + 0.5;
    pulse *= pulse * pulse;
    pulse *= 0.4;

    // Output white with the screen-space moving alpha pulse
    fragColor = vec4(1.0, 1.0, 1.0, pulse);
}
