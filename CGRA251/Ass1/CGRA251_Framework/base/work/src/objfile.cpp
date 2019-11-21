// std
#include <iostream>
#include <string>
#include <chrono>
#include <fstream>
#include <sstream>
#include <algorithm>

// glm
#include <glm/gtc/constants.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include <glm/gtc/type_ptr.hpp>

// project
#include "objfile.h"
#include "cgra/cgra_gui.hpp"
#include "cgra/cgra_shader.hpp"


using namespace std;
using namespace cgra;
using namespace glm;


Objfile::Objfile(GLFWwindow* window) : m_window(window) {

	// build the shader
	shader_builder color_sb;
	color_sb.set_shader(GL_VERTEX_SHADER, CGRA_SRCDIR + std::string("//res//shaders//default_vert.glsl"));
	color_sb.set_shader(GL_FRAGMENT_SHADER, CGRA_SRCDIR + std::string("//res//shaders//default_frag.glsl"));
	m_shader = color_sb.build();

	// build the mesh for the triangle
	m_model.setup();
}


void Objfile::render() {

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
	mat4 view = translate(mat4(1), vec3(0, -5, -20));
	float r = my_colour[0];
	float g = my_colour[1];
	float b = my_colour[2];
	float x = limit(lightdir[0]);
	float y = limit(lightdir[1]);
	float z = limit(lightdir[2]);


	// set shader and upload variables
	glUseProgram(m_shader);
	glUniformMatrix4fv(glGetUniformLocation(m_shader, "uProjectionMatrix"), 1, false, value_ptr(proj));
	glUniformMatrix4fv(glGetUniformLocation(m_shader, "uModelViewMatrix"), 1, false, value_ptr(view));
	glUniform1f(glGetUniformLocation(m_shader, "r"), r);
	glUniform1f(glGetUniformLocation(m_shader, "g"), g);
	glUniform1f(glGetUniformLocation(m_shader, "b"), b);
	glUniform1f(glGetUniformLocation(m_shader, "x"), x);
	glUniform1f(glGetUniformLocation(m_shader, "y"), y);
	glUniform1f(glGetUniformLocation(m_shader, "z"), z);
	

	// draw the model
	draw();
}


void Objfile::renderGUI() {

	// setup window
	ImGui::SetNextWindowPos(ImVec2(5, 5), ImGuiSetCond_Once);
	ImGui::SetNextWindowSize(ImVec2(600, 150), ImGuiSetCond_Once);
	ImGui::Begin("Mesh loader", 0);

	// Loading buttons
	static char filename[512] = "";
	ImGui::InputText("", filename, 512);
	ImGui::SameLine();
	if (ImGui::Button("Load")) {
		// TODO load mesh from 'filename'
		loadObj(filename);
		//"E:\\My Documents\\Compsci Yr5\\CGRA251\\Ass1\\CGRA251_Framework\\base\\work\\res\\assets\\teapot.obj"
	}

	ImGui::SameLine();
	if (ImGui::Button("Print")) {
		printMeshData();
	}

	ImGui::SameLine();
	if (ImGui::Button("Unload")) {
		vertices.clear();
		uvs.clear();
		normals.clear();
	}
	ImGui::NewLine();
	ImGui::ColorEdit3("Colour", my_colour);
	ImGui::SameLine();
	/*if (ImGui::Button("OK")) {
		cout << "r: " << my_colour[0] << ", g: " << my_colour[1] << ", b: " << my_colour[2] << '\n';
	}*/
	ImGui::NewLine();
	ImGui::Text("Light 'location': Left/Right, Above/Below, Intensity. (not correct implementation)");
	ImGui::InputFloat3("",lightdir,-1);
	//ImGui::SameLine();
	/*if (ImGui::Button("yes")) {
		cout << "x: " << limit(lightdir[0]) << ", y: " << limit(lightdir[1]) << ", z: " << limit(lightdir[2]) << '\n';
	}*/

	// finish creating window
	ImGui::End();
}

/*ASSIGNMENT ADDITIONS*/

/*
called from ImGui "load" button, loads data from .obj file into private variables
@filepath filepath location of selected .obj file, as a string.
*/
void Objfile::loadObj(string filepath) {
	destroy();
	//FILE* inFile = fopen(filepath.c_str(), "r");
	ifstream inFile (filepath.c_str());
	
	if (!inFile) {
		cerr << "Unable to open selected file\n";
		cerr << filepath;
		return;
	}

	std::vector< unsigned int > vertexIndices, uvIndices, normalIndices;
	std::vector< glm::vec3 > temp_vertices;
	std::vector< glm::vec2 > temp_uvs;
	std::vector< glm::vec3 > temp_normals;

	unsigned int f_count = 0;

	while (!inFile.eof()) {
		
		string line;
		getline(inFile, line);
	

		//parsing time
		//if the first char of line is "v", parse as a vertex with 3 floats.
		if (line.substr(0,2) == "v ") {
			glm::vec3 vertex;
			stringstream ss(line.substr(2,line.size()));
			ss >> vertex.x;
			ss >> vertex.y;
			ss >> vertex.z;
			//fscanf(inFile, "%f %f %f\n", &vertex.x, &vertex.y, &vertex.z);
			temp_vertices.push_back(vertex);
		}
		//if the first chars of lineHeader is "vt" parse it as a uv vector with 2 floats.
		else if (line.substr(0,2) == "vt") {
			glm::vec2 uv;
			stringstream ss(line.substr(2,line.size()));
			ss >> uv.x;
			ss >> uv.y;
			//fscanf(inFile, "%f %f\n", &uv.x, &uv.y);
			temp_uvs.push_back(uv);
		}
		//if the first chars of lineHeader is "vn" parse it as a normal vector with 3 floats.
		else if (line.substr(0,2) == "vn") {
			glm::vec3 normal;
			stringstream ss(line.substr(2, line.size()));
			ss >> normal.x;
			ss >> normal.y;
			ss >> normal.z;
			//fscanf(inFile, "%f %f %f\n", &normal.x, &normal.y, &normal.z);
			temp_normals.push_back(normal);
		}
		//if the first char of lineHeader is "f" then we are dealing with a list of indices, as integers seperated by "/"
		else if (line.substr(0, 2) == "f ") {
			indices.push_back(f_count);
			f_count++;
			unsigned int vertexIndex[3], uvIndex[3], normalIndex[3];
			std::size_t l = line.size();
			for (std::size_t i = 0; i < l; i++) {
				if (line[i] == '/') line[i] = ' ';
			}
			stringstream ss(line.substr(2, line.size()));
			ss >> vertexIndex[0];
			ss >> uvIndex[0];
			ss >> normalIndex[0];
			ss >> vertexIndex[1];
			ss >> uvIndex[1];
			ss >> normalIndex[1];
			ss >> vertexIndex[2];
			ss >> uvIndex[2];
			ss >> normalIndex[2];
			
			/*int matches = sscanf(line.c_str(), "%d/%d/%d %d/%d/%d %d/%d/%d\n", &vertexIndex[0], &vertexIndex[1], &vertexIndex[2],
				&uvIndex[0], &uvIndex[1], &uvIndex[2], 
				&normalIndex[0], &normalIndex[1], &normalIndex[2]);
			if (matches != 9) {
				cerr << "MATCHES:" << matches << "\n";
				cerr << "ERR "<<vertexIndex[0] << "/" << uvIndex[0] << "/" << normalIndex[0];
				return;
			}*/
			vertexIndices.push_back(vertexIndex[0]);
			vertexIndices.push_back(vertexIndex[1]);
			vertexIndices.push_back(vertexIndex[2]);
			uvIndices.push_back(uvIndex[0]);
			uvIndices.push_back(uvIndex[1]);
			uvIndices.push_back(uvIndex[2]);
			normalIndices.push_back(normalIndex[0]);
			normalIndices.push_back(normalIndex[1]);
			normalIndices.push_back(normalIndex[2]);
		}
	}


	//process data after read into std::vectors.
	//vertices
	cout << "loading " << vertexIndices.size() << " vertex indices..\n";

	
	for (unsigned int i = 0; i < vertexIndices.size(); i++) {
		unsigned int vertexIndex = vertexIndices[i];
		glm::vec3 vertex = temp_vertices[vertexIndex-1]; //indexing difference between C++ and .obj
		vertices.push_back(vertex);
		unsigned int uvIndex = uvIndices[i];
		glm::vec2 uv = temp_uvs[uvIndex - 1]; //indexing difference between C++ and .obj
		uvs.push_back(uv);
		unsigned int normalIndex = normalIndices[i];
		glm::vec3 normal = temp_normals[normalIndex - 1]; //indexing difference between C++ and .obj
		normals.push_back(normal);
	} cout << "vertices loaded\n";
	/*//uvs
	cout << "loading uvs..\n";
	for (unsigned int i = 0; i < uvIndices.size(); i++) {
		unsigned int uvIndex = uvIndices[i];
		glm::vec2 uv = temp_uvs[uvIndex - 1]; //indexing difference between C++ and .obj
		uvs.push_back(uv);
	} cout << "uvs loaded\n";
	//normals
	cout << "loading normals..\n";
	for (unsigned int i = 0; i < normalIndices.size(); i++) {
		unsigned int normalIndex = normalIndices[i];
		glm::vec3 normal = temp_normals[normalIndex - 1]; //indexing difference between C++ and .obj
		normals.push_back(normal);
	} cout << "normals loaded'n\'";*/


	cout << vertices.size() << '\n';
	cout << normals.size() << '\n';
	cout << uvs.size() << '\n';
	cout << indices.size() << '\n';
	build();
}

void Objfile::build() {
	//adapting triangle::setup
	if (m_vao == 0) {
		//generate buffers
		glGenVertexArrays(1, &m_vao); // VAO stores information about how the buffers are set up
		glGenBuffers(1, &m_vbo_pos);  // VBO stores the vertex data
		glGenBuffers(1, &m_vbo_norm); // presume this stores normal data?
		glGenBuffers(1, &m_ibo);      // IBO stores the indices that make up primitives

		// VAO
	
		glBindVertexArray(m_vao);

		// VBO
		// upload Positions to this buffer
		glBindBuffer(GL_ARRAY_BUFFER, m_vbo_pos);
		glBufferData(GL_ARRAY_BUFFER, vertices.size() * sizeof(glm::vec3), vertices.data(), GL_STATIC_DRAW);
		// this buffer will use location=0 when we use our VAO
		glEnableVertexAttribArray(0);
		// tell opengl how to treat data in location=0 - the data is treated in lots of 3 (3 floats = vec3)
		glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, nullptr);

		// do the same thing for Normals but bind it to location=1;
		glBindBuffer(GL_ARRAY_BUFFER, m_vbo_norm);
		glBufferData(GL_ARRAY_BUFFER, normals.size() * sizeof(glm::vec3), normals.data(), GL_STATIC_DRAW);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 0, nullptr);

		// IBO
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, m_ibo);
		// upload the indices for drawing primitives
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(unsigned int) * indices.size(), indices.data(), GL_STATIC_DRAW);

	

		// clean upo by binding VAO 0 (good practice)
		glBindVertexArray(0);
	}
}

void Objfile::draw() {
	if (m_vao == 0) return;
	// bind our VAO which sets up all our buffers and data for us
	glBindVertexArray(m_vao);
	// tell opengl to draw our VAO using the draw mode and how many verticies to render
	glDrawArrays(GL_TRIANGLES, 0, vertices.size());
}

void Objfile::destroy() {
	// delete the data buffers
	glDeleteVertexArrays(1, &m_vao);
	glDeleteBuffers(1, &m_vbo_pos);
	glDeleteBuffers(1, &m_vbo_norm);
	glDeleteBuffers(1, &m_ibo);
	m_vao = 0;
	// clear the CPU-side data
	vertices.clear();
	uvs.clear();
	normals.clear();
	indices.clear();
}

float Objfile::limit(float input) {
	if (input > 1) return 1;
	else if (input < -1) return -1;
}

void Objfile::printMeshData() {
	for (unsigned int i = 0; i < vertices.size(); i++) {
		cout << "x: " << vertices[i].x << ", y: " << vertices[i].y << ", z: " << vertices[i].z <<'\n';
	}
}


void Objfile::cursorPosCallback(double xpos, double ypos) {
	(void)xpos, ypos; // currently un-used
}


void Objfile::mouseButtonCallback(int button, int action, int mods) {
	(void)button, action, mods; // currently un-used
}


void Objfile::scrollCallback(double xoffset, double yoffset) {
	(void)xoffset, yoffset; // currently un-used
}


void Objfile::keyCallback(int key, int scancode, int action, int mods) {
	(void)key, (void)scancode, (void)action, (void)mods; // currently un-used
}


void Objfile::charCallback(unsigned int c) {
	(void)c; // currently un-used
}