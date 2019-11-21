
// glm
#include <glm/gtc/random.hpp>

// project
#include "boid.hpp"
#include "predator.h"
#include "scene.hpp"
#include "cgra/cgra_mesh.hpp"


using namespace glm;
using namespace std;


vec3 Boid::color() const {
	if (flockId == 2) return vec3(0, 0, 1);
	if (flockId == 0) return vec3(1, 0, 0);
	return vec3(0, 1, 0);
}


void Boid::calculateForces(Scene *scene) {
	//-------------------------------------------------------------
	// [Assignment 3] :
	// Calculate the forces affecting the boid and update the
	// acceleration (assuming mass = 1).
	// Do NOT update velocity or position in this function.
	// Core : 
	//  - Cohesion
	//  - Alignment
	//  - Avoidance
	//  - Soft Bound (optional)
	// Completion : 
	//  - Cohesion and Alignment with only boids in the same flock
	//  - Predator Avoidance (boids only)
	//  - Predator Chase (predator only)
	// Challenge : 
	//  - Obstacle avoidance
	//-------------------------------------------------------------

	// YOUR CODE GOES HERE
	// ...

	//Avoidance
	float Kav = scene->getAvoid();
	glm::vec3 avoidanceForce = glm::vec3(0);
	for (Boid b : scene->boids()) {
		Boid* bp = &b;
		//check if the pointer to this is not equal to the pointer of b
		if (bp != this) {

			if (distance(bp->position(), m_position) < scene->getNeighbourhood() && distance(bp->position(), m_position) != 0) {
				float invDist = 1 / glm::length(m_position - bp->position());
				glm::vec3 normalisedVec = (m_position - bp->position()) / glm::length(m_position - bp->position());
				avoidanceForce += invDist * normalisedVec;
			}
			avoidanceForce *= Kav;
		}
	}

	//Alignment
	glm::vec3 sumAl = glm::vec3(0);
	float countAl = 0;
	glm::vec3 centroidAl;
	float Kal = scene->getAlign();
	glm::vec3 alignmentForce = glm::vec3(0);
	for (Boid b : scene->boids()) {
		Boid* bp = &b;
		//check if the pointer to this is not equal to the pointer of b
		if (bp != this && bp->flockId == this->flockId) {
			// neighbourhood caculated by distance
			if (distance(bp->position(), m_position) < scene->getNeighbourhood() && distance(bp->position(), m_position) != 0) {
				sumAl += bp->velocity();
				countAl++;
			}
			//calculate centroid of neighbours
			if (countAl > 0) {
				centroidAl = sumAl / countAl;
				alignmentForce = Kal *(centroidAl - m_velocity);
			}
			//case in which there is no neighbours.
			else {
				// TODO handle no neighbour case.
			}
		}
	}

	//** Cohesion **//
	glm::vec3 sumC = glm::vec3(0);
	float countC = 0;
	glm::vec3 centroidC;
	float Kc = scene->getCoh();
	glm::vec3 cohesiveForce = glm::vec3(0);

	for (Boid b : scene->boids()) {
		Boid* bp = &b;
		//check if the pointer to this is not equal to the pointer of b
		if (bp != this && bp->flockId == this->flockId) {
			
			/*cout << "bp: " << bp << '\n';
			cout << "this: " << this << '\n';
			cout <<"bp "<< bp->m_position.x << "," << bp->m_position.y << "," << bp->m_position.z << '\n';
			cout <<"this"<< this->m_position.x << "," << this->m_position.y << "," << this->m_position.z << '\n';*/
			//if distance of bp and this = 0 then they are equivlent, using this to get past an
			//annoying bug in which it would one position for the distance calulation and would always come out as 0
			//even though it got past the identity check
			if (distance(bp->position(), m_position) < scene->getNeighbourhood() && distance(bp->position(),m_position) != 0) {
				sumC += bp->position();
				countC++;
			}
			//calculate centroid of neighbours
			if (countC > 0) { 
				centroidC = sumC / countC;
				cohesiveForce = Kc * (centroidC - m_position);
			}
			//case in which there is no neighbours.
			else {
				// TODO handle no neighbour case.
			}
		}
	}

	//predator stuff
	float closest = 100;
	glm::vec3 targetPos;
	glm::vec3 seekForce = glm::vec3(0);
	if (flockId == 0) {
		for (Boid b : scene->boids()) {
			Boid* bp = &b;
			//check if the pointer to this is not equal to the pointer of b
			if (bp != this) {
				//find nearest boid
				if (distance(bp->position(), m_position) < closest && distance(bp->position(), m_position) != 0) {
					targetPos = bp->position();
				}
				seekForce = 0.5f * (targetPos - m_position);
			}
		}
	}

	//update acceleration: (a = (fx + fv + fa)/m)
	m_acceleration = (cohesiveForce + alignmentForce + avoidanceForce + seekForce) / mass;
}


void Boid::update(float timestep, Scene *scene) {
	//-------------------------------------------------------------
	// [Assignment 3] :
	// Integrate the velocity of the boid using the timestep.
	// Update the position of the boid using the new velocity.
	// Take into account the bounds of the scene which may
	// require you to change the velocity (if bouncing) or
	// change the position (if wrapping).
	//-------------------------------------------------------------

	// YOUR CODE GOES HERE
	// ...

	//Confinement
	checkConfinement(scene);

	//Sensible Speed
	if (glm::length(m_velocity) > scene->getMaxSpeed()) {
		m_velocity = scene->getMaxSpeed() * (m_velocity / glm::length(m_velocity));
	}
	//update velocity: vnew = v + (a x h)
	m_velocity += m_acceleration * timestep;
	//update position: xnew = x + (v + h)
	m_position += m_velocity * timestep;
	
}

/* calculate the distance between 2 vectors */
float Boid::distance(glm::vec3 v1, glm::vec3 v2) {
	glm::vec3 temp = v1 - v2;
	return sqrt(dot(temp, temp));
};

/* checks if this Boid has left the Bounding Box, and if so handle it */
void Boid::checkConfinement(Scene *scene) {
	// +0.1 on wrapping positive escapes to the negative edge, for some reason needed.
	// some clarification on this would be nice, thanks marker :)
	glm::vec3 bb = scene->bound();
	if (m_position.x > bb.x) {
		//wrap
		if (scene->confine_wrap) m_position.x = -m_position.x +0.5;
		//bounce
		if (scene->confine_bounce) m_velocity.x = -m_velocity.x;
		//force
		if (scene->confine_force) m_acceleration = (scene->bound_force * (glm::vec3(0) - m_position))/mass;
	}
	if (m_position.x < -bb.x) {
		//wrap
		if (scene->confine_wrap) m_position.x = -m_position.x;
		//bounce
		if (scene->confine_bounce) m_velocity.x = -m_velocity.x;
		//force
		if (scene->confine_force) m_acceleration = (scene->bound_force * (glm::vec3(0) - m_position)) / mass;
	}
	if (m_position.y > bb.y) {
		//wrap
		if (scene->confine_wrap) m_position.y = -m_position.y +0.5;
		//bounce
		if (scene->confine_bounce) m_velocity.y = -m_velocity.y;
		//force
		if (scene->confine_force) m_acceleration = (scene->bound_force * (glm::vec3(0) - m_position)) / mass;
	}
	if (m_position.y < -bb.y) {
		//wrap
		if (scene->confine_wrap) m_position.y = -m_position.y;
		//bounce
		if (scene->confine_bounce) m_velocity.y = -m_velocity.y;
		//force
		if (scene->confine_force) m_acceleration = (scene->bound_force * (glm::vec3(0) - m_position)) / mass;
	}
	if (m_position.z > bb.z) {
		//wrap
		if (scene->confine_wrap) m_position.z = -m_position.z +0.5;
		//bounce
		if (scene->confine_bounce) m_velocity.z = -m_velocity.z;
		//force
		if (scene->confine_force) m_acceleration = (scene->bound_force * (glm::vec3(0) - m_position)) / mass;
	}
	if (m_position.z < -bb.z) {
		//wrap
		if (scene->confine_wrap) m_position.z = -m_position.z;
		//bounce
		if (scene->confine_bounce) m_velocity.z = -m_velocity.z;
		//force
		if (scene->confine_force) m_acceleration = (scene->bound_force * (glm::vec3(0) - m_position)) / mass;
	}
}

