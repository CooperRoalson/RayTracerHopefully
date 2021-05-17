package com.github.cooperroalson.raytracerhopefully.scene.camera;

import com.github.cooperroalson.raytracerhopefully.geometry.MatrixTransform3d;
import com.github.cooperroalson.raytracerhopefully.geometry.Ray;
import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.Color;
import com.github.cooperroalson.raytracerhopefully.scene.Scene;

public class PinholeCamera extends Camera {
	
	private CameraData cameraData;
	
	public PinholeCamera(CameraData cameraData) {
		super(cameraData.getImageWidth(), cameraData.getImageHeight());
		this.cameraData = cameraData;
	}

	@Override
	public Color castRay(Scene scene, int x, int y) {
		CameraRay ray = new CameraRay(generatePrimaryRay(x, y), 1);
		return ray.cast(scene);
	}

	public Ray generatePrimaryRay(int pixelX, int pixelY) {
		Vector3f pos = new Vector3f(pixelToCanvasX(pixelX), pixelToCanvasY(pixelY), -cameraData.getFocalLength());
		Ray ray = new Ray(pos, pos.normalized());
		return cameraData.getCameraToWorldTransform().applyToRay(ray);
	}
	
	public double pixelToCanvasX(int pixelX) {
		double ndcX = (pixelX + 0.5)/cameraData.getImageWidth();
		return cameraData.getCanvasWidth() * (1 - 2 * ndcX);
	}
	public double pixelToCanvasY(int pixelY) {
		double ndcY = (pixelY + 0.5)/cameraData.getImageHeight();
		return cameraData.getCanvasHeight() * (1 - 2 * ndcY);
	}
	
	public Vector3f getPos() {
		return this.cameraData.getCameraToWorldTransform().getTranslate();
	}
	public Vector3f getViewDirection() {
		return this.cameraData.getCameraToWorldTransform().applyToVector(new Vector3f(0, 0, -1)).normalized();
	}
	
	@Override
	public void transformAbsolute(MatrixTransform3d transformation) {
		this.cameraData.transformAbsolute(transformation);
	}
	@Override
	public void transformRelative(MatrixTransform3d transformation) {
		this.cameraData.transformRelative(transformation);
	}

	
	public CameraData getCameraData() {
		return cameraData;
	}
}
