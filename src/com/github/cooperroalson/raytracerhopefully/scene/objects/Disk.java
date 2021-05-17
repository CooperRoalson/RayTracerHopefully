package com.github.cooperroalson.raytracerhopefully.scene.objects;

import com.github.cooperroalson.raytracerhopefully.geometry.Ray;
import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.objects.shaders.Shader;

public class Disk extends SceneObject {
	
	private Vector3f center;
	private double radius;
	private Vector3f normal;
		
	public Disk(Vector3f center, double radius, Vector3f normal, Shader shader) {
		super(shader);
		
		this.center = center;
		this.radius = radius;
		this.normal = normal.normalized();
	}

	@Override
	public Vector3f getIntersection(Ray ray) {
		double denominator = this.normal.dot(ray.getDirection());
		
		if (Math.abs(denominator) < 0.0001) {
			return null;
		}
		
		double t = this.center.minus(ray.getOrigin()).dot(this.normal)/denominator;
		
		Vector3f intersection = ray.getPoint(t);
		
		if (intersection.minus(this.center).squared() > this.radius) {
			return null;
		}
		
		return intersection;
	}

	@Override
	public Vector3f getNormal(Vector3f point) {
		return this.normal;
	}

}
