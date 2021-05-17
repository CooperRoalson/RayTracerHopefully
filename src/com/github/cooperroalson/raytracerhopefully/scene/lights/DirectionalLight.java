package com.github.cooperroalson.raytracerhopefully.scene.lights;

import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.Color;
import com.github.cooperroalson.raytracerhopefully.scene.Scene;
import com.github.cooperroalson.raytracerhopefully.scene.camera.CameraRay;

public class DirectionalLight implements SceneLight {
	private Color color;
	private Vector3f direction;
	
	public DirectionalLight(Vector3f direction, Color color) {
		this.direction = direction.normalized();
		this.color = color;
	}

	@Override
	public boolean reaches(Scene scene, Vector3f point) {
		CameraRay ray = new CameraRay(point, direction.times(-1), 0).offset(scene.getRayOffset());
		return (ray.intersect(scene) == null);
	}

	@Override
	public Vector3f vectorTo(Vector3f point) {
		return direction;
	}

	@Override
	public Color getColor() {
		return color;
	}

}
