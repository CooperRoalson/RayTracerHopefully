package com.github.cooperroalson.raytracerhopefully.scene.objects;

import com.github.cooperroalson.raytracerhopefully.geometry.Ray;
import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.objects.shaders.Shader;

public class Sphere extends SceneObject {
	private Vector3f center;
	private double radius;
		
	public Sphere(Vector3f center, double radius, Shader shader) {
		super(shader);
		
		this.center = center;
		this.radius = radius;
	}

	public Vector3f getCenter() {
		return center;
	}

	public double getRadius() {
		return radius;
	}



	@Override
	public Vector3f getIntersection(Ray ray) {
		// From www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection
		
		double a = 1;
		double b = ray.getDirection().times(2).dot(ray.getOrigin().minus(this.center));
		double c = ray.getOrigin().minus(this.center).squared() - this.radius*this.radius;
		
		double determinant = b*b - 4*a*c;
		if (determinant < 0) {return null;}
		
		double t0 = (-b - Math.sqrt(determinant)) / 2*a;
		
		return ray.getPoint(t0);
	}

	@Override
	public Vector3f getNormal(Vector3f point) {
		return point.minus(center).normalized();
	}

}
