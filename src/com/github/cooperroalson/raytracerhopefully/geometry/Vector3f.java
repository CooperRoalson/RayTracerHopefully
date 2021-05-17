package com.github.cooperroalson.raytracerhopefully.geometry;

public class Vector3f {
	public double x, y, z;

	public Vector3f(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f normalized() {
		double m = getMagnitude();
		return new Vector3f(x/m, y/m, z/m);
	}

	public double getMagnitude() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	public Vector3f times(double scalar) {
		return new Vector3f(this.x * scalar, this.y * scalar, this.z * scalar);
	}
	
	public Vector3f plus(Vector3f other) {
		return new Vector3f(this.x + other.x, this.y + other.y, this.z + other.z);
	}
	
	public Vector3f minus(Vector3f other) {
		return new Vector3f(this.x - other.x, this.y - other.y, this.z - other.z);
	}
	
	public double dot(Vector3f other) {
		return this.x * other.x + this.y * other.y + this.z * other.z;
	}

	public Vector3f cross(Vector3f other) {
		return new Vector3f(
			this.y * other.z - this.z * other.y,
			this.z * other.x - this.x * other.z,
			this.x * other.y - this.y * other.x
		);
	}
	
	public double squared() {
		return this.dot(this);
	}
	
	
	
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
