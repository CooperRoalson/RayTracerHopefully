package com.github.cooperroalson.raytracerhopefully.scene.camera;

import com.github.cooperroalson.raytracerhopefully.geometry.MatrixTransform3d;
import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;

public class CameraData {
	private int imageWidth, imageHeight; // Image pixels
	private double focalLength;
	private double fov;
	
	private MatrixTransform3d cameraToWorld;
	
	public CameraData() {
		cameraToWorld = new MatrixTransform3d();
	}

	public CameraData setImageSize(int imageWidth, int imageHeight) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		
		return this;
	}
	
	public CameraData setFocalLength(double focalLength) {
		this.focalLength = focalLength;
		
		return this;
	}
	
	public CameraData setFOVDegrees(double fov) {
		this.fov = Math.toRadians(fov);
		return this;
	}
	
	public CameraData lookAt(Vector3f position, Vector3f target) {
		// From www.scratchapixel.com/lessons/mathematics-physics-for-computer-graphics/lookat-function
		
		Vector3f forwardAxis = position.minus(target).normalized();
		Vector3f rightAxis = new Vector3f(0, 1, 0).cross(forwardAxis);
		Vector3f upAxis = forwardAxis.cross(rightAxis);
		
		this.cameraToWorld = MatrixTransform3d.fromAxes(rightAxis, upAxis, forwardAxis, position);
		return this;
	}
	
	
	
	public int getImageWidth() {
		return imageWidth;
	}
	public int getImageHeight() {
		return imageHeight;
	}
	public double getAspectRatio() {
		return (double) imageWidth/imageHeight;
	}
	public double getCanvasWidth() {
		return 2 * Math.tan(fov/2) * focalLength;
	}
	public double getCanvasHeight() {
		return getCanvasWidth()/getAspectRatio();
	}
	public double getFocalLength() {
		return focalLength;
	}
	public double getFOV() {
		return fov;	// Returns the horizontal field of view
	}
	public MatrixTransform3d getCameraToWorldTransform() {
		return cameraToWorld;
	}

	public void transformAbsolute(MatrixTransform3d transformation) {
		this.cameraToWorld.prepend(transformation);
	}
	public void transformRelative(MatrixTransform3d transformation) {
		this.cameraToWorld.append(transformation);
	}
}
