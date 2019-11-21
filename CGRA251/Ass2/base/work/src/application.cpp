
// std
#include <iostream>
#include <string>
#include <chrono>
#include <algorithm>
#include <regex>

// glm
#include <glm/gtc/constants.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

// project
#include "application.hpp"
#include "bounding_box.hpp"
#include "cgra/cgra_geometry.hpp"
#include "cgra/cgra_gui.hpp"
#include "cgra/cgra_image.hpp"
#include "cgra/cgra_shader.hpp"
#include "cgra/cgra_wavefront.hpp"


using namespace std;
using namespace cgra;
using namespace glm;


Application::Application(GLFWwindow *window) : m_window(window) {
	
	// build the shader for the model
	shader_builder color_sb;
	color_sb.set_shader(GL_VERTEX_SHADER, CGRA_SRCDIR + std::string("//res//shaders//default_vert.glsl"));
	color_sb.set_shader(GL_FRAGMENT_SHADER, CGRA_SRCDIR + std::string("//res//shaders//default_frag.glsl"));
	GLuint color_shader = color_sb.build();

	//load texture file?
	rgba_image texImg = rgba_image(CGRA_SRCDIR + std::string("//res//textures//uv_texture.jpg"));
	rgba_image checkImg = rgba_image(CGRA_SRCDIR + std::string("//res//textures//checkerboard.jpg"));
	tex = texImg.uploadTexture();
	// build the mesh for the model
	mesh_builder teapot_mb = load_wavefront_data(CGRA_SRCDIR + std::string("//res//assets//teapot.obj"));
	gl_mesh teapot_mesh = teapot_mb.build();

	// put together an object
	m_model.shader = color_shader;
	m_model.mesh = teapot_mesh;
	m_model.color = glm::vec3(1, 0, 0);
	m_model.modelTransform = glm::mat4(1);

	//lighting stuff
	m_model.lightColor = { 1, 1, 1 };
	m_model.ambStr = 0.1;
	m_model.specStr = 0.5;
	m_model.shine = 32;

	//instancing?
	generateInstances();
}


void Application::render() {

	// retrieve the window hieght
	int width, height;
	glfwGetFramebufferSize(m_window, &width, &height);


	m_windowsize = vec2(width, height); // update window size
	glViewport(0, 0, width, height); // set the viewport to draw to the entire window

	// clear the back-buffer
	glClearColor(0.3f, 0.3f, 0.4f, 1.0f);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// enable flags for normal/forward rendering
	glEnable(GL_DEPTH_TEST);
	glDepthFunc(GL_LESS);

	// calculate the projection and view matrix
	mat4 proj = perspective(1.f, float(width) / height, 0.1f, 1000.f);
	//mat4 view = translate(mat4(1), vec3(0, -5, -m_distance)); // TODO replace view matrix with the camera transform

	//camera stuff
	if (m_yaw > 126.0) m_yaw = -126.0;
	if (m_yaw < -126.0) m_yaw = 126.0;
	if (m_pitch > 360.0) m_pitch = 0; // TODO fix pitch at some point too
	if (m_pitch < 0) m_pitch = 360.0;

	//mouse input stuff
	if (leftButtonDown) {
		float mouseXOffset = mouseX - lastMouseX;
		float mouseYOffset = mouseY - lastMouseY;
		if (mouseXOffset > 0) m_yaw += (mouseXOffset * rotationSpeed);
		else if (mouseXOffset < 0) m_yaw += (mouseXOffset * rotationSpeed);
		if (mouseYOffset > 0) m_pitch += (mouseYOffset * rotationSpeed);
		else if (mouseYOffset < 0) m_pitch += (mouseYOffset * rotationSpeed);
	}

	lastMouseX = mouseX; //store mouseX for next rendered frame to compare
	lastMouseY = mouseY; //store mouseY for next rendered frame to compare

	//camera stuff
	glm::mat4 rotMat = glm::mat4(1.0f);
	rotMat = glm::rotate(rotMat, m_yaw * rotationSpeed, glm::vec3(0.0f, 1.0f, 0.0f));
	rotMat = glm::rotate(rotMat, m_pitch * rotationSpeed, glm::vec3(1.0f, 0.0f, 0.0f));
	glm::vec4 camPos = rotMat * glm::vec4(0.0, 0.0, 1.0, 1.0);
	cameraPos = m_distance * glm::vec3(camPos);
	view = glm::lookAt(cameraPos, cameraTarget, up);

	//pass phong values from gui to shader
	m_model.ambStr = phongValues[0];
	m_model.specStr = phongValues[1];
	m_model.shine = phongValues[2];
	//glUniform1f(glGetUniformLocation(m_model.shader, "ambientStrength"), phongValues[0]);
	//glUniform1f(glGetUniformLocation(m_model.shader, "specularStrength"), phongValues[1]);
	//glUniform1f(glGetUniformLocation(m_model.shader, "shine"), phongValues[2]);

	//update lightposisition to shader 
	if (l_light_cam){
		m_model.lightPos = cameraPos;
		lightPosSet = cameraPos;
	}
	else m_model.lightPos = lightPosSet;
	//update camera posistion to shader
	m_model.cameraPos = cameraPos;



	// draw options
	if (m_show_grid) cgra::drawGrid(view, proj);
	if (m_show_axis) cgra::drawAxis(view, proj);
	glPolygonMode(GL_FRONT_AND_BACK, (m_showWireframe) ? GL_LINE : GL_FILL);

	//bind textures
	glBindTexture(GL_TEXTURE_2D, tex);
	// draw the model
	m_model.draw(view, proj);
}

void Application::generateInstances() {
	modelMatrices = new glm::mat4[amount];

	float offset = 10.0f;
	for (unsigned int i = 0; i < amount; i++) {
		glm::mat4 model = glm::translate(glm::mat4(1.0f), glm::vec3(
			genDisplacement(offset),	//xdisplacement
			genDisplacement(offset),	//ydisplacement
			genDisplacement(offset)));	//zdisplacement
		//scale by 0.05f to 0.25f
		float scale = (rand() % 20) / 100.0f + 0.05;
		model = glm::scale(model, glm::vec3(scale));
		//rotate by random angle
		float rotAngle(rand() % 360);
		model = glm::rotate(model, rotAngle, glm::vec3(1.0f, 1.0f, 1.0f));
		//store new model matrix
		modelMatrices[i] = model;
	}
	//VBO for instanced models
	unsigned int buffer;
	glGenBuffers(1, &buffer);
	glBindBuffer(GL_ARRAY_BUFFER, buffer);
	glBufferData(GL_ARRAY_BUFFER, amount * sizeof(glm::mat4), &modelMatrices[0], GL_STATIC_DRAW);
	//VAO for instanced models
	glBindVertexArray(m_model.mesh.vao);
	GLsizei vec4size = sizeof(glm::vec4);
	//store each vector of the matrix in a attribute array, using slots 3-6
	glEnableVertexAttribArray(3); //(location = 3)
	glVertexAttribPointer(3, 4, GL_FLOAT, GL_FALSE, 4 * vec4size, (void*)0);
	glEnableVertexAttribArray(4); //(location = 4)
	glVertexAttribPointer(4, 4, GL_FLOAT, GL_FALSE, 4 * vec4size, (void*)(vec4size));
	glEnableVertexAttribArray(5); //(location = 5)
	glVertexAttribPointer(5, 4, GL_FLOAT, GL_FALSE, 4 * vec4size, (void*)(2 * vec4size));
	glEnableVertexAttribArray(6); //(location = 6)
	glVertexAttribPointer(6, 4, GL_FLOAT, GL_FALSE, 4 * vec4size, (void*)(3 * vec4size));
	//tell opengl that the vertex attribute at each location is an instanced array 
	glVertexAttribDivisor(3, 1);
	glVertexAttribDivisor(4, 1);
	glVertexAttribDivisor(5, 1);
	glVertexAttribDivisor(6, 1);
	glBindVertexArray(0);
}


void Application::renderGUI() {

	// setup window
	ImGui::SetNextWindowPos(ImVec2(5, 5), ImGuiSetCond_Once);
	ImGui::SetNextWindowSize(ImVec2(325, 250), ImGuiSetCond_Once);
	ImGui::Begin("Camera", 0);

	// display current camera parameters
	ImGui::Text("Application %.3f ms/frame (%.1f FPS)", 1000.0f / ImGui::GetIO().Framerate, ImGui::GetIO().Framerate);
	ImGui::SliderFloat("Distance", &m_distance, 0, 100, "%.1f");
	ImGui::SliderFloat("Pitch", &m_pitch, 0, 360.1, "%.1f");
	ImGui::SliderFloat("Yaw", &m_yaw, -126.0, 126.1, "%.1f"); //this syncs up for some reason (value 31.5 = 90 degrees)
	ImGui::SliderFloat3("Model Color", value_ptr(m_model.color), 0, 1, "%.2f");
	ImGui::SliderFloat3("Light Color", value_ptr(m_model.lightColor), 0, 1, "%.2f");
	ImGui::SliderFloat3("Light Offset", value_ptr(lightPosSet), -10, 10, "%.2f");
	ImGui::InputFloat3("Amb/Spec/Shine", phongValues, -1);

	// extra drawing parameters
	ImGui::Checkbox("Show axis", &m_show_axis);
	ImGui::SameLine();
	ImGui::Checkbox("Show grid", &m_show_grid);
	ImGui::SameLine();
	ImGui::Checkbox("Light@Cam", &l_light_cam);
	ImGui::Checkbox("Wireframe", &m_showWireframe);
	ImGui::SameLine();
	if (ImGui::Button("Screenshot")) rgba_image::screenshot(true);

	// finish creating window
	ImGui::End();
}

float Application::genDisplacement(float offset) {
	return (rand() % (int)(2 * offset * 100)) / 100.0f - offset;
}


void Application::cursorPosCallback(double xpos, double ypos) {
	mouseX = xpos;
	mouseY = ypos;
}


void Application::mouseButtonCallback(int button, int action, int mods) {
	if (button == GLFW_MOUSE_BUTTON_LEFT) {
		if (action == GLFW_PRESS) leftButtonDown = true;
		else if (action == GLFW_RELEASE) leftButtonDown = false;
	}
}


void Application::scrollCallback(double xoffset, double yoffset) {
	m_distance += yoffset;
}


void Application::keyCallback(int key, int scancode, int action, int mods) {
	(void)key, (void)scancode, (void)action, (void)mods; // currently un-used
}


void Application::charCallback(unsigned int c) {
	(void)c; // currently un-used
}