package com.github.cooperroalson.raytracerhopefully.scene.camera;

import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.objects.SceneObject;

public class CameraRayIntersection {
	private SceneObject object;
	private double distance;
	private Vector3f intersection;
	
	public CameraRayIntersection(SceneObject object, double distance, Vector3f intersection) {
		this.object = object;
		this.distance = distance;
		this.intersection = intersection;
	}

	public SceneObject getObject() {
		return object;
	}

	public double getDistance() {
		return distance;
	}

	public Vector3f getIntersection() {
		return intersection;
	}
}
