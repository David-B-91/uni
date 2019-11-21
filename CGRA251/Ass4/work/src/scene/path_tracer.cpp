
// glm
#include <glm/gtc/constants.hpp>
#include <glm/gtc/random.hpp>

// std
#include <random>

// project
#include "scene.hpp"
#include "shape.hpp"
#include "light.hpp"
#include "material.hpp"
#include "path_tracer.hpp"


using namespace std;
using namespace glm;


vec3 SimplePathTracer::sampleRay(const Ray &ray, int) {
	// intersect ray with the scene
	RayIntersection intersect = m_scene->intersect(ray);

	// if ray hit something
	if (intersect.m_valid) {

		// simple grey shape shading
		float f = abs(dot(-ray.direction, intersect.m_normal));
		vec3 grey(0.5, 0.5, 0.5);
		return mix(grey / 2.0f, grey, f);
	}

	// no intersection - return background color
	return { 0.3f, 0.3f, 0.4f };
}



vec3 CorePathTracer::sampleRay(const Ray &ray, int) {
	//-------------------------------------------------------------
	// [Assignment 4] :
	// Implement a PathTracer that calculates the ambient, diffuse
	// and specular, for the given ray in the scene, using the 
	// Phong lighting model. Give special consideration to objects
	// that occluded from direct lighting (shadow rays). You do
	// not need to use the depth argument for this implementation.
	//-------------------------------------------------------------

	// YOUR CODE GOES HERE
	// intersect ray with the scene
	RayIntersection intersect = m_scene->intersect(ray);
	vec3 diffuse(0), specular(0), ambient(0);
	vec3 total(0);

	// if ray hit something
	if (intersect.m_valid) {
		//For each light we want to:
		for (auto& light : m_scene->lights()) {
			//Update diffuse
			diffuse += light->ambience() * intersect.m_material->diffuse();
			//Check if not occluded
			if (!light->occluded(m_scene, intersect.m_position)) {
				//Get the irradiance and incident direction of the light
				vec3 intersect_to_light = -light->incidentDirection(intersect.m_position); //vector to LIGHT
				vec3 irradiance = light->irradiance(intersect.m_position);
				float dotNormal = dot(intersect.m_normal, intersect_to_light);
				dotNormal = dotNormal < 0 ? 0.0f : dotNormal;
				diffuse += irradiance * dotNormal * intersect.m_material->diffuse();
				//specular
				vec3 rayDirectionNorm = -normalize(ray.direction); //vector to EYE
				//vector R
				vec3 reflection_vector = (2 * dotNormal) * intersect.m_normal - intersect_to_light;
				float r_dot_eye = dot(reflection_vector, rayDirectionNorm);
				specular += 0.5f * irradiance * intersect.m_material->specular() * (pow(std::max(0.f, r_dot_eye), intersect.m_material->shininess()));
				ambient += 0.1f * light->ambience();

			}
		}
		return diffuse + specular + ambient;
	}

	// no intersection - return background color
	return { 0.3f, 0.3f, 0.4f };
}



vec3 CompletionPathTracer::sampleRay(const Ray &ray, int depth) {
	//-------------------------------------------------------------
	// [Assignment 4] :
	// Using the same requirements for the CorePathTracer add in 
	// a recursive element to calculate perfect specular reflection.
	// That is compute the reflection ray off your intersection and
	// sample a ray in that direction, using the result to additionally
	// light your object. To make this more realistic you may weight
	// the incoming light by the (1 - (1/shininess)).
	//-------------------------------------------------------------

	// YOUR CODE GOES HERE
	// ...

	// no intersection - return background color
	return { 0.3f, 0.3f, 0.4f };
}



vec3 ChallengePathTracer::sampleRay(const Ray &ray, int depth) {
	//-------------------------------------------------------------
	// [Assignment 4] :
	// Implement a PathTracer that calculates the diffuse and 
	// specular, for the given ray in the scene, using the 
	// Phong lighting model. Give special consideration to objects
	// that occluded from direct lighting (shadow rays).
	// Implement support for textured materials (using a texture
	// for the diffuse portion of the material).
	//
	// EXTRA FOR EXPERTS :
	// Additionally implement indirect diffuse and specular instead
	// of using the ambient lighting term.
	// The diffuse is sampled from the surface hemisphere and the
	// specular is sampled from a cone of the phong lobe (which
	// gives a glossy look). For best results you need to normalize
	// the lighting (see http://www.thetenthplanet.de/archives/255)
	//-------------------------------------------------------------

	// YOUR CODE GOES HERE
	// ...

	// no intersection - return background color
	return { 0.3f, 0.3f, 0.4f };
}