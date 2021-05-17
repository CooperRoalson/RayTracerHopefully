package com.github.cooperroalson.raytracerhopefully.geometry;

public class MatrixTransform3d {
	private Matrix matrix;
	
	public MatrixTransform3d() {
		matrix = Matrix.identity(4);
	}
	
	public MatrixTransform3d(Matrix m) {
		if (m.getWidth() != 4 || m.getHeight() != 4) {
			throw new IllegalArgumentException("Matrix must be 4x4");
		}
		
		matrix = m;
	}
	
	public Vector3f applyToPoint(Vector3f point) {
		Matrix pointMatrix = new Matrix(new double[][] {{point.x}, {point.y}, {point.z}, {1}});
		double[] result = matrix.multiply(pointMatrix).getCol(0);
		return new Vector3f(result[0]/result[3], result[1]/result[3], result[2]/result[3]);
	}
	
	public Vector3f applyToVector(Vector3f vector) {
		Matrix vectorMatrix = new Matrix(new double[][] {{vector.x}, {vector.y}, {vector.z}});
		double[] result = matrix.slice(3,3,0,0).multiply(vectorMatrix).getCol(0);
		return new Vector3f(result[0], result[1], result[2]);
	}
	
	public Ray applyToRay(Ray ray) {
		return new Ray(this.applyToPoint(ray.getOrigin()), this.applyToVector(ray.getDirection()).normalized());
	}
	
	public static MatrixTransform3d fromAxes(Vector3f xAxis, Vector3f yAxis, Vector3f zAxis, Vector3f origin) {
		return new MatrixTransform3d(new Matrix(new double[][] {
			{xAxis.x, yAxis.x, zAxis.x, origin.x},
			{xAxis.y, yAxis.y, zAxis.y, origin.y},
			{xAxis.z, yAxis.z, zAxis.z, origin.z},
			{      0,       0,       0,        1}
		}));
	}
	
	
	public MatrixTransform3d scale(double sx, double sy, double sz) {
		this.matrix = this.matrix.multiply(new Matrix(new double[][] {
			{sx,  0,  0, 0},
			{ 0, sy,  0, 0},
			{ 0,  0, sz, 0},
			{ 0,  0,  0, 1}
		}));
		return this;
	}
	
	public MatrixTransform3d translate(double tx, double ty, double tz) {
		this.matrix = this.matrix.multiply(new Matrix(new double[][] {
			{ 1,  0,  0, tx},
			{ 0,  1,  0, ty},
			{ 0,  0,  1, tz},
			{ 0,  0,  0,  1},
		}));
		return this;
	}
	public MatrixTransform3d translate(Vector3f pos) {
		return this.translate(pos.x, pos.y, pos.z);
	}
	
	private double c(double a) {return Math.cos(a);}
	private double s(double a) {return Math.sin(a);}
	
	public MatrixTransform3d rotateYaw(double a) {return this.rotateXZ(a);}
	public MatrixTransform3d rotatePitch(double a) {return this.rotateYZ(a);}
	public MatrixTransform3d rotateRoll(double a) {return this.rotateXY(a);}
	
	// Yaw
	public MatrixTransform3d rotateXZ(double a) {
		this.matrix = this.matrix.multiply(new Matrix(new double[][] {
			{  c(a),     0,  s(a),     0},
			{     0,     1,     0,     0},
			{ -s(a),     0,  c(a),     0},
			{     0,     0,     0,     1},
		}));
		return this;
	}
	// Pitch
	public MatrixTransform3d rotateYZ(double a) {
		this.matrix = this.matrix.multiply(new Matrix(new double[][] {
			{     1,     0,     0,     0},
			{     0,  c(a), -s(a),     0},
			{     0,  s(a),  c(a),     0},
			{     0,     0,     0,     1},
		}));
		return this;
	}
	// Roll
	public MatrixTransform3d rotateXY(double a) {
		this.matrix = this.matrix.multiply(new Matrix(new double[][] {
			{  c(a), -s(a),     0,     0},
			{  s(a),  c(a),     0,     0},
			{     0,     0,     1,     0},
			{     0,     0,     0,     1},
		}));
		return this;
	}

	
	public MatrixTransform3d append(MatrixTransform3d transformation) {
		this.matrix = this.matrix.multiply(transformation.getMatrix());
		return this;
	}
	public MatrixTransform3d prepend(MatrixTransform3d transformation) {
		this.matrix = transformation.getMatrix().multiply(this.matrix);
		return this;
	}
	
	public Matrix getMatrix() {
		return this.matrix;
	}

	public MatrixTransform3d inverse() {
		return new MatrixTransform3d(this.matrix.inverse());
	}

	public Vector3f getTranslate() {
		double[] col = this.matrix.getCol(3);
		return new Vector3f(col[0], col[1], col[2]);
	}
}
