
#pragma once

// glm
#include <glm/glm.hpp>
#include <glm/gtc/type_ptr.hpp>

// project
#include "opengl.hpp"
#include "cgra/cgra_mesh.hpp"


// Basic model that holds the shader, mesh and transform for drawing.
// Can be copied and/or modified for adding in extra information for drawing
// including colors for diffuse/specular, and textures for texture mapping etc.
struct basic_model {
	GLuint shader = 0;
	cgra::gl_mesh mesh;
	glm::vec3 color{1, 0, 0};
	glm::vec3 lightColor;
	glm::vec3 lightPos;
	glm::mat4 modelTransform{1.0};
	glm::vec3 cameraPos;
	float ambStr;
	float specStr;
	float shine;

	void draw(const glm::mat4 &view, const glm::mat4 proj) {
		using namespace glm;

		// cacluate the modelview transform
		mat4 modelview = view * modelTransform;

		// load shader and variables
		glUseProgram(shader);
		glUniformMatrix4fv(glGetUniformLocation(shader, "uProjectionMatrix"), 1, false, value_ptr(proj));
		glUniformMatrix4fv(glGetUniformLocation(shader, "uModelViewMatrix"), 1, false, value_ptr(modelview));
		glUniform3fv(glGetUniformLocation(shader, "uColor"), 1, value_ptr(color));
		glUniform3fv(glGetUniformLocation(shader, "lightColor"), 1, value_ptr(lightColor));
		glUniform3fv(glGetUniformLocation(shader, "lightPos"), 1, value_ptr(lightPos));
		glUniform3fv(glGetUniformLocation(shader, "viewPos"), 1, value_ptr(cameraPos));
		glUniform1f(glGetUniformLocation(shader, "ambientStrength"), ambStr);
		glUniform1f(glGetUniformLocation(shader, "specularStrength"), specStr);
		glUniform1f(glGetUniformLocation(shader, "shine"), shine);

		// draw the mesh
		mesh.draw(); 
	}
};