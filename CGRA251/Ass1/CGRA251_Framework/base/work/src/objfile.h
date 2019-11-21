#pragma once

// glm
#include <glm/glm.hpp>

// project
#include "opengl.hpp"
#include "triangle.hpp"

using namespace std;


// Main application class
//
class Objfile {
private:
	// window
	glm::vec2 m_windowsize;
	GLFWwindow* m_window;

	// basic shader
	GLuint m_shader;

	// test triangle model
	TestTriangle m_model;

	//CPU-side data
	//arrays for .obj vertices information
	std::vector< glm::vec3 > vertices;
	std::vector< glm::vec2 > uvs;
	std::vector< glm::vec3 > normals;
	std::vector< unsigned int > indices;

	//GPU-side data
	GLuint m_vao = 0;
	GLuint m_vbo_pos = 0;
	GLuint m_vbo_norm = 0;
	GLuint m_ibo = 0;

	//colour for shaders?
	float my_colour[3] = {0.066, 0.341, 0.215};
	float lightdir[3] = { 0,0,0 };


public:
	// setup
	Objfile(GLFWwindow*);

	// disable copy constructors (for safety)
	Objfile(const Objfile&) = delete;
	Objfile& operator=(const Objfile&) = delete;

	//loads data from .obj file into private variables
	void loadObj(string filepath);

	//OpenGL data buffer operations
	void build();
	void draw();
	void destroy();

	//prints raw mesh data to console
	void printMeshData();

	// rendering callbacks (every frame)
	void render();
	void renderGUI();

	float limit(float input);

	// input callbacks
	void cursorPosCallback(double xpos, double ypos);
	void mouseButtonCallback(int button, int action, int mods);
	void scrollCallback(double xoffset, double yoffset);
	void keyCallback(int key, int scancode, int action, int mods);
	void charCallback(unsigned int c);
};