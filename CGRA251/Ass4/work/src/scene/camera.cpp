
#include <iostream>

// glm
#include <glm/gtc/matrix_transform.hpp>

// project
#include "camera.hpp"
#include "opengl.hpp"


using namespace std;
using namespace glm;


void Camera::setPositionOrientation(const vec3 &pos, float yaw, float pitch) {
	m_position = pos;
	m_yaw = yaw;
	m_pitch = pitch;

	// update rotation matrix (based on yaw and pitch)
	m_rotation = rotate(mat4(1), m_yaw, vec3(0, 1, 0)) * rotate(mat4(1), m_pitch, vec3(1, 0, 0));
}


Ray Camera::generateRay(const vec2 &pixel, int m_render_width, int m_render_height) {
	//-------------------------------------------------------------
	// [Assignment 4] :
	// Generate a ray in the scene using the camera position,
	// rotation field of view on the y axis (fovy) and the image
	// size. The pixel is given in image coordinates [0, imagesize]
	// This COULD be done by first creating the ray in ViewSpace
	// then transforming it by the position and rotation to get
	// it into worldspace.
	//-------------------------------------------------------------
	
	Ray ray;

	// YOUR CODE GOES HERE
	float imgW = m_render_width;
	float imgH = m_render_height;
	if (imgW < imgH) std::swap(imgW, imgH);
	float aspectRatio = imgW/imgH;
	float Px = ((2 * (pixel[0] / imgW) - 1) * m_fovy) * aspectRatio;
	float Py = (1-2 * (pixel[1]/ imgH)) * m_fovy;
	vec3 origin(0);
	//-Py because it was upside down
	vec3 direction = vec3(Px, -Py, -1) - origin;
	mat3 rot = m_rotation;
	ray.origin = rot * origin + m_position;
	ray.direction = normalize(rot * direction + m_position);
	return ray;
}