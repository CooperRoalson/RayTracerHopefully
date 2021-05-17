package com.github.cooperroalson.raytracerhopefully.scene.objects;

import com.github.cooperroalson.raytracerhopefully.geometry.Ray;
import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.Color;
import com.github.cooperroalson.raytracerhopefully.scene.objects.shaders.DiffuseShader;

public class TestFloorObject extends SceneObject {
	private double y;
		
	public TestFloorObject(double y, Color color) {
		super(new DiffuseShader(pnt -> {return getColor(pnt, color);}));
		
		this.y = y;
	}

	public double getY() {
		return y;
	}


	public static Color getColor(Vector3f pnt, Color color) {
		return ((Math.floor(pnt.x)+Math.floor(pnt.z))%2 == 0) ? new Color(0,0,0) : color;
	}
	

	@Override
	public Vector3f getIntersection(Ray ray) {
		double t0 = (this.y - ray.getOrigin().y)/ray.getDirection().y;
		if (t0 < 0) {return null;}
		
		return ray.getPoint(t0);
	}

	@Override
	public Vector3f getNormal(Vector3f point) {
		return new Vector3f(0,1,0);
	}

}
