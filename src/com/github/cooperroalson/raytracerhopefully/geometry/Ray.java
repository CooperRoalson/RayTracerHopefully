package com.github.cooperroalson.raytracerhopefully.geometry;

public class Ray {
	private Vector3f origin;
	private Vector3f direction;
	
	public Ray(Vector3f origin, Vector3f direction) {
		this.origin = origin;
		this.direction = direction.normalized();
	}
	
	public static Ray from(Vector3f origin, Vector3f dest) {
		return new Ray(origin, dest.minus(origin).normalized());
	}
	
	public Vector3f getOrigin() {
		return origin;
	}
	public Vector3f getDirection() {
		return direction;
	}
	
	public Vector3f getPoint(double pos) {
		return this.origin.plus(this.direction.times(pos));
	}
}
