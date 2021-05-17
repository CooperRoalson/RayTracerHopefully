package com.github.cooperroalson.raytracerhopefully.scene.camera;

import java.awt.image.BufferedImage;

import com.github.cooperroalson.raytracerhopefully.geometry.MatrixTransform3d;
import com.github.cooperroalson.raytracerhopefully.geometry.Vector3f;
import com.github.cooperroalson.raytracerhopefully.scene.Color;
import com.github.cooperroalson.raytracerhopefully.scene.Scene;

public abstract class Camera {
	private int imageWidth, imageHeight;
	
	public Camera(int imageWidth, int imageHeight) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}

	public BufferedImage render(Scene scene) {
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, 1); // 8-bit RGB color model
		
		for (int x = 0; x < imageWidth; x++) {
			for (int y = 0; y < imageHeight; y++) {
				image.setRGB(x, y, castRay(scene, x, y).getRGB());
			}
		}
		
		return image;
	}
	
	public abstract Color castRay(Scene scene, int x, int y);
	
	
	public int getWidth() {
		return imageWidth;
	}

	public int getHeight() {
		return imageHeight;
	}
	
	public abstract Vector3f getPos();
	public abstract Vector3f getViewDirection();

	public abstract void transformAbsolute(MatrixTransform3d transformation);
	public abstract void transformRelative(MatrixTransform3d transformation);
}
