package com.github.cooperroalson.raytracerhopefully.scene.objects.shaders;

import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.Color;

public interface ColorMap {
	
	public Color getColor(Vector3f point);
	
}
