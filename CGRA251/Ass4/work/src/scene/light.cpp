
// glm
#include <glm/gtc/constants.hpp>
#include <glm/gtc/epsilon.hpp>

// project
#include "light.hpp"
#include <iostream>

using namespace glm;


bool DirectionalLight::occluded(Scene *scene, const vec3 &point) const {
	//-------------------------------------------------------------
	// [Assignment 4] :
	// Determine whether the given point is being occluded from
	// this directional light by an object in the scene.
	// Remember that directional lights are "infinitely" far away
	// so any object in the way would cause an occlusion.
	//-------------------------------------------------------------

	// YOUR CODE GOES HERE
	//Create a ray from the point in the opposite direction to the light
	Ray ray;
	ray.origin = point;
	ray.direction = -m_direction;
	//Check if that ray intersects with anything in the scene
	RayIntersection intersection = scene->intersect(ray);
	//Return whether that intersection was valid
	return intersection.m_valid;
}


vec3 DirectionalLight::incidentDirection(const vec3 &) const {
	return m_direction;
}


vec3 DirectionalLight::irradiance(const vec3 &) const {
	return m_irradiance;
}


bool PointLight::occluded(Scene *scene, const vec3 &point) const {
	//-------------------------------------------------------------
	// [Assignment 4] :
	// Determine whether the given point is being occluded from
	// this directional light by an object in the scene.
	// Remember that point lights are somewhere in the scene and
	// an occulsion has to occur somewhere between the light and 
	// the given point.
	//-------------------------------------------------------------

	// YOUR CODE GOES HERE
	//Create a vector from the point to the light source and save its length
	vec3 vector_from_point_to_light = m_position - point;
	int length = glm::length(vector_from_point_to_light);
	//Check if the length of that vector is > 0
	if (length > 0) {
		//If yes, create a ray from the point in the direction of that vector (normalise it first)
		Ray ray;
		ray.origin = point;
		ray.direction = normalize(vector_from_point_to_light);
		//Check if the ray intersects with the scene.
		RayIntersection intersection = scene->intersect(ray);
		//If the intersection is valid, return whether it’s between the light and the point
		if (intersection.m_valid) {
			return true;
			//if (ray.direction == normalize(intersection.m_position)) {
			//	return true;
			//}
			//vec3 v1 = m_position - point;
			//vec3 v2 = intersection.m_position - point;
			//if (v1 == -v2) return false; //on line but preceeds point.
			//if (v1 == v2) {
			//	if (glm::length(v2) <= glm::length(v1)) { 
			//		std::cout << "true\n"; 
			//		return true; }
			vec3 crosspr = cross(point - m_position, intersection.m_position - m_position);
			//if (epsilonEqual(crosspr,vec3(0), 1.0f)) return true;
			 // on line in section. if v2 length grater than v1 then it would be beyond light
		}
	}
	return false;
	} 



vec3 PointLight::incidentDirection(const vec3 &point) const {
	//-------------------------------------------------------------
	// [Assignment 4] :
	// Return the direction of the incoming light (light to point)
	//-------------------------------------------------------------

	// YOUR CODE GOES HERE
	//Create a vector from the light to the point
	vec3 vector_from_light_to_point = point - m_position;
	//Check if the length of that vector is > 0
	int length = glm::length(vector_from_light_to_point);
	if (length > 0) {
		//Return the normalised vector
		return normalize(vector_from_light_to_point);
	}
	//Return a placeholder vector e.g. 0
	return vec3(0);
}


vec3 PointLight::irradiance(const vec3 &point) const {
	//-------------------------------------------------------------
	// [Assignment 4] :
	// Return the total irradiance on the given point.
	// This requires you to convert the flux of the light into
	// irradiance by dividing it by the surface of the sphere
	// it illuminates. Remember that the surface area increases
	// as the sphere gets bigger, ie. the point is further away.
	//-------------------------------------------------------------

	// YOUR CODE GOES HERE
	//Create a vector from the point to the light
	vec3 vector_from_point_to_light = m_position - point;
	//Check if that vector has length
	int length = glm::length(vector_from_point_to_light);

	if (length > 0) {
		// Return flux / (4π * length2)
		return (m_flux / (4 * pi<float>() * (float) pow(length,2)));
	}
	return vec3(0);
}
