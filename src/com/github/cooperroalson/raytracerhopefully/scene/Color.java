package com.github.cooperroalson.raytracerhopefully.scene;

public class Color {
	private double r, g, b;
	
	// 0 - 255
	public Color(int r, int g, int b) {
		this.r = r/255.0;
		this.g = g/255.0;
		this.b = b/255.0;
	}
	
	// 0.0 - 1.0
	public Color(double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	private double clamp(double x) {
		return Math.min(x, 1);
	}

	public double getR() {return r;}
	public double getG() {return g;}
	public double getB() {return b;}
	
	public int getRed() {return (int) (r * 255);}
	public int getGreen() {return (int) (g * 255);}
	public int getBlue() {return (int) (b * 255);}

	public int getRGB() {
		return ((((int) (clamp(r) * 255)) & 0xFF) << 16) |
                ((((int) (clamp(g) * 255)) & 0xFF) << 8)  |
                ((((int) (clamp(b) * 255)) & 0xFF) << 0);
	}

	public Color multiply(Color other) {
		return new Color(this.r * other.getR(), this.g * other.getG(), this.b * other.getB());
	}
	
	public Color multiply(double scale) {
		return this.multiply(new Color(scale, scale, scale));
	}
	
	public Color add(Color other) {
		return new Color(this.r + other.getR(), this.g + other.getG(), this.b + other.getB());
	}
	
}
