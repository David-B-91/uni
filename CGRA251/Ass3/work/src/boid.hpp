
#pragma once

// glm
#include <glm/glm.hpp>

// project
#include "scene.hpp"


class Boid {
private:

	glm::vec3 m_position;
	glm::vec3 m_velocity;
	glm::vec3 m_acceleration;
	float mass = 1;
	void checkConfinement(Scene *scene);
	float distance(glm::vec3 v1, glm::vec3 v2);
	int flockId;

public:
	Boid(glm::vec3 pos, glm::vec3 dir, int id) : m_position(pos), m_velocity(dir) { flockId = id; }
	Boid() {}
	glm::vec3 position() const { return m_position; }
	glm::vec3 velocity() const { return m_velocity; }
	glm::vec3 acceleration() const { return m_acceleration; }

	glm::vec3 virtual color() const;

	void calculateForces(Scene *scene);
	void update(float timestep, Scene *scene);

	
};

