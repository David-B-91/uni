#version 330 core

// uniform data
uniform mat4 uProjectionMatrix;
uniform mat4 uModelViewMatrix;
uniform vec3 uColor;
uniform vec3 lightColor;
uniform vec3 lightPos;
uniform vec3 viewPos;
uniform float ambientStrength;
uniform float specularStrength;
uniform float shine;
uniform sampler2D objTexture;


// viewspace data (this must match the output of the fragment shader)
in VertexData {
	vec3 position;
	vec3 normal;
	vec2 textureCoord;
} f_in;
in vec3 fColor; //from vert shader

// framebuffer output
out vec4 fb_color;

void main() {
	// calculate lighting (hack)
	//vec3 eye = normalize(-f_in.position);
	//float light = abs(dot(normalize(f_in.normal), eye));
	//vec3 color = mix(uColor / 4, uColor, light);

	//ambient lighting
	vec3 ambient = ambientStrength * lightColor;

	//diffuse lighting
	vec3 norm = normalize(f_in.normal);
	vec3 lightDir = normalize(lightPos - f_in.position);
	float diff = max(dot(norm, lightDir), 0.0);
	vec3 diffuse = diff * lightColor;

	//specular lighting
	vec3 viewDir = normalize(viewPos - f_in.position);			//calculate view direction vector
	vec3 reflectDir = reflect(-lightDir, norm);					//calculate reflection vecotr along normal axis
	float spec = pow(max(dot(viewDir, reflectDir), 0.0), shine);	//calculate specular component including shiny value of 32
	vec3 specular = specularStrength * spec * lightColor;		//calculate specular color

	//calculate final colour
	vec3 color = (ambient + diffuse + specular) * uColor;


	// output to the frambuffer
	fb_color = texture(objTexture, f_in.textureCoord) * vec4(color, 1);
}