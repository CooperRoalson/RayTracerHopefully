package com.github.cooperroalson.raytracerhopefully.scene.camera;

import com.github.cooperroalson.raytracerhopefully.geometry.Ray;
import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.Color;
import com.github.cooperroalson.raytracerhopefully.scene.Scene;
import com.github.cooperroalson.raytracerhopefully.scene.objects.SceneObject;

public class CameraRay extends Ray {
	
	int depth;
	
	public CameraRay(Vector3f origin, Vector3f direction, int depth) {
		super(origin, direction);
		this.depth = depth;
	}
	
	public CameraRay(Ray ray, int depth) {
		this(ray.getOrigin(), ray.getDirection(), depth);
	}
	
	public CameraRay offset(double amount) {
		return new CameraRay(this.getPoint(amount), this.getDirection(), this.getDepth());
	}

	public CameraRayIntersection intersect(Scene scene) {
		CameraRayIntersection closestIntersection = null;
		
		for (SceneObject object : scene.getObjects()) {
			Vector3f intersection = object.getIntersection(this);
			if (intersection != null) {
				double distance = intersection.minus(this.getOrigin()).dot(this.getDirection());
				if (distance >= 0 && (closestIntersection == null || distance < closestIntersection.getDistance())) {
					closestIntersection = new CameraRayIntersection(object, distance, intersection);
				}
			}
		}
		
		return closestIntersection;
	}

	public Color cast(Scene scene) {
		CameraRayIntersection closestIntersection = this.intersect(scene);
		
		if (closestIntersection != null) {
			return closestIntersection.getObject().getIntersectionColor(scene, closestIntersection.getIntersection(), this);
		} else {
			return scene.getBackgroundColor();
		}
	}
	
	public int getDepth() {
		return this.depth;
	}
	
}
