
#pragma once

// glm
#include <glm/glm.hpp>

// project
#include "opengl.hpp"
#include "basic_model.hpp"


// Main application class
//
class Application {
private:
	// window
	glm::vec2 m_windowsize;
	GLFWwindow *m_window;

	// oribital camera
	float m_distance = 20.0;
	float m_pitch = 100.0;
	float m_yaw = 0.0;
	float camX = 0.0;
	float camZ = 0.0;
	float rotationSpeed = 0.06f;
	glm::vec3 cameraPos = glm::vec3(0.0f, 0.0f, 3.0f);
	glm::vec3 cameraTarget = glm::vec3(0.0f, 2.0f, 0.0f);
	glm::vec3 up = glm::vec3(0.0f, 1.0f, 0.0f);
	glm::mat4 view;

	//mouse stuff
	bool leftButtonDown = false;
	float mouseX;
	float mouseY;
	float lastMouseX;
	float lastMouseY;

	//lighting stuff
	bool l_light_cam = true;
	glm::vec3 lightPosSet = { 0,0,0 };
	float phongValues[3] = { 0.1,0.5,32 }; // amb/spec/shine

	//texture stuff
	GLuint tex;
	bool texture_toggle;

	//instancing stuff
	unsigned int amount = 100; //also need to change in cgra_mesh.cpp
	glm::mat4* modelMatrices;

	// drawing flags
	bool m_show_axis = false;
	bool m_show_grid = false;
	bool m_showWireframe = false;

	// basic model
	// contains a shader, a model transform
	// a mesh, and other model information (color etc.)
	basic_model m_model;

public:
	// setup
	Application(GLFWwindow *);

	// disable copy constructors (for safety)
	Application(const Application&) = delete;
	Application& operator=(const Application&) = delete;

	// rendering callbacks (every frame)
	void render();
	void renderGUI();

	//instancing methods
	void generateInstances();
	float genDisplacement(float offset);

	// input callbacks
	void cursorPosCallback(double xpos, double ypos);
	void mouseButtonCallback(int button, int action, int mods);
	void scrollCallback(double xoffset, double yoffset);
	void keyCallback(int key, int scancode, int action, int mods);
	void charCallback(unsigned int c);
};