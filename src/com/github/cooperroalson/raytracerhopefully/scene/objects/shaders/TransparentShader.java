package com.github.cooperroalson.raytracerhopefully.scene.objects.shaders;

import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.Color;
import com.github.cooperroalson.raytracerhopefully.scene.Scene;
import com.github.cooperroalson.raytracerhopefully.scene.camera.CameraRay;
import com.github.cooperroalson.raytracerhopefully.scene.objects.SceneObject;

public class TransparentShader implements Shader {
	
	private RefractionShader refractionShader;
	private ReflectionShader reflectionShader;
	
	
	public TransparentShader(RefractionShader refractionShader, ReflectionShader reflectionShader) {
		this.refractionShader = refractionShader;
		this.reflectionShader = reflectionShader;
	}
	
	public TransparentShader(double refractiveIndex, Color tint) {
		this.refractionShader = new RefractionShader(refractiveIndex, tint);
		this.reflectionShader = new ReflectionShader();
	}
	
	public TransparentShader(double refractiveIndex) {
		this(refractiveIndex, new Color(255, 255, 255));
	}

	public double getReflectedRatio(SceneObject object, Vector3f intersection, CameraRay ray) {
		// From www.scratchapixel.com/lessons/3d-basic-rendering/introduction-to-shading/reflection-refraction-fresnel
		
		Vector3f normal = object.getNormal(intersection);
		
		double cos1 = normal.dot(ray.getDirection());
		
		double n1 = 1;
		double n2 = refractionShader.getRefractiveIndex();
		
		if (cos1 < 0) {cos1 = -cos1;} else {double tmp = n1; n1 = n2; n2 = tmp;}
		
		double sin = (n1/n2) * Math.sqrt(Math.max(0, 1 - cos1*cos1));
		
		if (sin > 1) {return 1;}
		
		double cos2 = Math.sqrt(Math.max(0, 1 - sin*sin));
		
		double rs = (n2*cos1 - n1*cos2) / (n2*cos1 + n1*cos2);
		double rp = (n1*cos1 - n2*cos2) / (n1*cos1 + n2*cos2);
		
		return (rs*rs + rp*rp) / 2;
		
	}

	@Override
	public Color getIntersectionColor(SceneObject object, Scene scene, Vector3f intersection, CameraRay ray) {
		double reflected = getReflectedRatio(object, intersection, ray);
		
		Color reflectionColor = this.reflectionShader.getIntersectionColor(object, scene, intersection, ray);
		Color refractionColor = this.refractionShader.getIntersectionColor(object, scene, intersection, ray);
		
		return reflectionColor.multiply(reflected).add(refractionColor.multiply(1 - reflected));
	}
	
}
