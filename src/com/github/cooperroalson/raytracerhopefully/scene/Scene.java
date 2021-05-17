package com.github.cooperroalson.raytracerhopefully.scene;

import java.awt.image.BufferedImage;
import java.util.Set;

import com.github.cooperroalson.raytracerhopefully.scene.camera.Camera;
import com.github.cooperroalson.raytracerhopefully.scene.lights.SceneLight;
import com.github.cooperroalson.raytracerhopefully.scene.objects.SceneObject;

public class Scene {
	
	private double rayOffset = 0.0001;
	
	private Set<SceneObject> objects;
	private Set<SceneLight> lights;
	
	private Camera camera;
	
	private Integer maxDepth;
	
	private Color backgroundColor;
	
	public BufferedImage render(int maxDepth) {
		this.maxDepth = maxDepth;
		return camera.render(this);
	}
	
	public Set<SceneObject> getObjects() {
		return objects;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	public Set<SceneLight> getLights() {
		return lights;
	}

	public int getMaxDepth() {
		return maxDepth;
	}
	
	public double getRayOffset() {
		return rayOffset;
	}
	
	
	
	
	

	public Scene(Set<SceneObject> objects, Set<SceneLight> lights, Camera camera, Color backgroundColor) {
		this.objects = objects;
		this.lights = lights;
		this.camera = camera;
		this.backgroundColor = backgroundColor;
		
		this.maxDepth = null;
	}
}
