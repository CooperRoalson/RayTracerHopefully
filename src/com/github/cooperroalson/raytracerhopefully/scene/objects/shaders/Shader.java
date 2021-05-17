package com.github.cooperroalson.raytracerhopefully.scene.objects.shaders;

import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.Color;
import com.github.cooperroalson.raytracerhopefully.scene.Scene;
import com.github.cooperroalson.raytracerhopefully.scene.camera.CameraRay;
import com.github.cooperroalson.raytracerhopefully.scene.objects.SceneObject;

public interface Shader {
	
	public Color getIntersectionColor(SceneObject object, Scene scene, Vector3f intersection, CameraRay ray);
	
}
