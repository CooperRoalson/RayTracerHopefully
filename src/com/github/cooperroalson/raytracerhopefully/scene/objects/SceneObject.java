package com.github.cooperroalson.raytracerhopefully.scene.objects;

import com.github.cooperroalson.raytracerhopefully.geometry.Ray;
import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.Color;
import com.github.cooperroalson.raytracerhopefully.scene.Scene;
import com.github.cooperroalson.raytracerhopefully.scene.camera.CameraRay;
import com.github.cooperroalson.raytracerhopefully.scene.objects.shaders.Shader;

public abstract class SceneObject {
	
	private Shader shader;
	
	public SceneObject(Shader shader) {
		this.shader = shader;
	}
	
	public Color getIntersectionColor(Scene scene, Vector3f intersection, CameraRay ray) {
		return this.shader.getIntersectionColor(this, scene, intersection, ray);
	}
		
	public abstract Vector3f getIntersection(Ray ray);
	
	public abstract Vector3f getNormal(Vector3f point);
	
}
