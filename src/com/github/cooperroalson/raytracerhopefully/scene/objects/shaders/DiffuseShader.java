package com.github.cooperroalson.raytracerhopefully.scene.objects.shaders;

import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.Color;
import com.github.cooperroalson.raytracerhopefully.scene.Scene;
import com.github.cooperroalson.raytracerhopefully.scene.camera.CameraRay;
import com.github.cooperroalson.raytracerhopefully.scene.lights.SceneLight;
import com.github.cooperroalson.raytracerhopefully.scene.objects.SceneObject;

public class DiffuseShader implements Shader {
	
	ColorMap colorMap;
	
	public DiffuseShader(ColorMap colorMap) {
		this.colorMap = colorMap;
	}
	
	public DiffuseShader(Color color) {
		this(pnt -> {return color;});
	}
	
	@Override
	public Color getIntersectionColor(SceneObject object, Scene scene, Vector3f intersection, CameraRay ray) {		
		Color result = new Color(0,0,0);
		
		for (SceneLight light : scene.getLights()) {
			if (light.reaches(scene, intersection)) {
				double cosine = light.vectorTo(intersection).times(-1).dot(object.getNormal(intersection));
				cosine = Math.max(cosine, 0);
				Color lightColor = light.getColor().multiply(cosine);
				result = result.add(lightColor);
			}
		}

		return result.multiply(colorMap.getColor(intersection)).multiply(1/Math.PI);
	}
}
