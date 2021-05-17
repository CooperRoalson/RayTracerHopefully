package com.github.cooperroalson.raytracerhopefully.scene.lights;

import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.Color;
import com.github.cooperroalson.raytracerhopefully.scene.Scene;

public interface SceneLight {
	
	public boolean reaches(Scene scene, Vector3f point); // Returns whether the point is illuminated

	public Color getColor();
	
	public Vector3f vectorTo(Vector3f point);
}
