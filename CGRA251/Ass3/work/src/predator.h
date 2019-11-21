#pragma once
// glm
#include <glm/glm.hpp>

// project
#include "scene.hpp"
#include "boid.hpp"

class Predator : public Boid {
private:
public:
	Predator(glm::vec3 pos, glm::vec3 dir) : Boid(pos,dir,0) {  }
	Predator() {}
};