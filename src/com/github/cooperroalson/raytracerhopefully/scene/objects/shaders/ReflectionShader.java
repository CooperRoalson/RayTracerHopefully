package com.github.cooperroalson.raytracerhopefully.scene.objects.shaders;

import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.Color;
import com.github.cooperroalson.raytracerhopefully.scene.Scene;
import com.github.cooperroalson.raytracerhopefully.scene.camera.CameraRay;
import com.github.cooperroalson.raytracerhopefully.scene.objects.SceneObject;

public class ReflectionShader implements Shader {
	
	private Color tint;
	
	public ReflectionShader() {
		this(new Color(255, 255, 255));
	}
	
	public ReflectionShader(Color tint) {
		this.tint = tint;
	}
	
	@Override
	public Color getIntersectionColor(SceneObject object, Scene scene, Vector3f intersection, CameraRay ray) {		
		if (ray.getDepth() < scene.getMaxDepth()) {
			// From https://www.scratchapixel.com/lessons/3d-basic-rendering/introduction-to-shading/reflection-refraction-fresnel
			Vector3f incDir = ray.getDirection();
			Vector3f normal = object.getNormal(intersection);
			
			if (normal.dot(incDir) < 0) {normal = normal.times(-1);}
			
			Vector3f reflDir = incDir.minus(normal.times(2 * incDir.dot(normal)));
			CameraRay mirrorRay = new CameraRay(intersection, reflDir, ray.getDepth() + 1).offset(scene.getRayOffset());
			
			return mirrorRay.cast(scene).multiply(this.tint);
		}
		
		return new Color(0, 0, 0);
	}

}
