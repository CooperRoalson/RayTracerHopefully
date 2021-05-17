package com.github.cooperroalson.raytracerhopefully.scene.objects.shaders;

import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.Color;
import com.github.cooperroalson.raytracerhopefully.scene.Scene;
import com.github.cooperroalson.raytracerhopefully.scene.camera.CameraRay;
import com.github.cooperroalson.raytracerhopefully.scene.objects.SceneObject;

public class RefractionShader implements Shader {
	
	private double refractiveIndex;
	
	private Color tint;
	
	public RefractionShader(double refractiveIndex, Color tint) {
		this.refractiveIndex = refractiveIndex;
		this.tint = tint;
	}
	
	public RefractionShader(double refractiveIndex) {
		this(refractiveIndex, new Color(255, 255, 255));
	}

	@Override
	public Color getIntersectionColor(SceneObject object, Scene scene, Vector3f intersection, CameraRay ray) {		
		if (ray.getDepth() < scene.getMaxDepth()) {
			// From www.scratchapixel.com/lessons/3d-basic-rendering/introduction-to-shading/reflection-refraction-fresnel
		
			Vector3f normal = object.getNormal(intersection);
			
			double c1 = normal.dot(ray.getDirection());
			
			double n = 1/this.refractiveIndex;
			
			if (c1 < 0) {c1 *= -1;} else {n = 1/n; normal = normal.times(-1);}
		
			double c2 = 1 - n*n * (1 - c1*c1);
			if (c2 < 0) {return new Color(0,0,0);}
			
			Vector3f refractionDir = ray.getDirection().times(n).plus(normal.times(n * c1 - Math.sqrt(c2))).normalized();
			
			CameraRay refractionRay = new CameraRay(intersection, refractionDir, ray.getDepth() + 1).offset(scene.getRayOffset());
			
			return refractionRay.cast(scene).multiply(this.tint);
		}
		
		return new Color(0, 0, 0);
	}

	public double getRefractiveIndex() {
		return refractiveIndex;
	}
}
