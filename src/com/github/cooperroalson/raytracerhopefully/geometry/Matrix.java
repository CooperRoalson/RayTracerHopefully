package com.github.cooperroalson.raytracerhopefully.geometry;

import java.util.Arrays;

public class Matrix {
	private double[][] array;

	public Matrix(double[][] array) {
		this.array = array;
	}
	
	public Matrix(int height, int width) {
		this.array = new double[height][width];
	}
	
	public static Matrix identity(int size) {
		double[][] result = new double[size][size];
		for (int i = 0; i < size; i++) {
			result[i][i] = 1;
		}
		return new Matrix(result);
	}
	
	public int getWidth() {
		return array[0].length;
	}
	
	public int getHeight() {
		return array.length;
	}
	
	public double get(int y, int x) {
		return array[y][x];
	}
	
	protected double[] getRow(int y) {
		return array[y];
	}
	
	protected double[] getCol(int x) {
		double[] col = new double[getHeight()];
		for (int y = 0; y < getHeight(); y++) {
			col[y] = array[y][x];
		}
		return col;
	}
	
	public String getDimensionsAsString() {
		return getHeight() + "x" + getWidth();
	}
	
	public Matrix slice(int xSize, int ySize, int xStart, int yStart) {
		double[][] result = new double[ySize][xSize];
		
		for (int y = yStart; y < yStart+ySize; y++) {
			System.arraycopy(getRow(y), xStart, result[y], xStart, xSize);
		}
		
		return new Matrix(result);
	}
	
	public Matrix multiply(Matrix other) {
		if (this.getWidth() != other.getHeight()) {
			throw new ArithmeticException("Can't multiply a " + this.getDimensionsAsString() + " matrix by a " + other.getDimensionsAsString() + " matrix!");
		}
		
		double[][] result = new double[this.getHeight()][other.getWidth()];
		
		for (int y = 0; y < this.getHeight(); y++) {
			double[] row = this.getRow(y);

			for (int x = 0; x < other.getWidth(); x++) {
				double[] col = other.getCol(x);
				
				for (int i = 0; i < this.getWidth(); i++) {
					result[y][x] += row[i] * col[i];
				}
			}
		}
		
		return new Matrix(result);
	}
	
	public Matrix add(Matrix other) {
		if (this.getWidth() != other.getWidth() || this.getHeight() != other.getHeight()) {
			throw new ArithmeticException("Can't add a " + this.getDimensionsAsString() + " matrix and a " + other.getDimensionsAsString() + " matrix!");
		}
		
		double[][] result = new  double[this.getHeight()][this.getWidth()];
		
		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < other.getWidth(); x++) {
				result[y][x] = array[y][x] + other.get(y, x);
			}
		}
		
		return new Matrix(result);
	}
	
	
	
	
	
	public double determinant() {
		if (this.getWidth() != this.getHeight()) {
			throw new ArithmeticException("You can only take the determinant of a square matrix!");
		}
		
		if (this.getWidth() == 2) {
			return array[0][0]*array[1][1] - array[0][1]*array[1][0];
		} else {
			double result = 0;
			
			int sign = 1;
			
			for (int x = 0; x < this.getWidth(); x++) {
				double[][] smallerArray = new double[this.getHeight()-1][this.getWidth()-1];
				
				for (int y2 = 1; y2 < this.getHeight(); y2++) {					
					int xi = 0;
					for (int x2 = 0; x2 < this.getWidth(); x2++) {
						if (x2 == x) {continue;}
						smallerArray[y2-1][xi++] = this.array[y2][x2];
					}
				}
				
				result += sign * array[0][x] * new Matrix(smallerArray).determinant();
				sign *= -1;
			}
			
			return result;
		}
	}
	
	public Matrix inverse() {
		double[][] adjugateArray = new double[this.getWidth()][this.getHeight()];
		
		int sign = 1;
		
		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {
				double[][] smallerArray = new double[this.getHeight()-1][this.getWidth()-1];
				
				int yi = 0;
				for (int y2 = 0; y2 < this.getHeight(); y2++) {
					if (y2 == y) {continue;}
					
					int xi = 0;
					for (int x2 = 0; x2 < this.getWidth(); x2++) {
						if (x2 == x) {continue;}
						smallerArray[yi][xi++] = this.array[y2][x2];
					}
					yi++;
				}
				
				adjugateArray[x][y] = sign * new Matrix(smallerArray).determinant();
				sign *= -1;
			}
			if (this.getWidth() % 2 == 0) {
				sign *= -1;
			}
		}
		
		double determinant = 0;
		for (int x = 0; x < this.getWidth(); x++) {
			determinant += array[0][x] * adjugateArray[x][0];
		}
		
		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {
				adjugateArray[x][y] /= determinant;
			}
		}
		
		return new Matrix(adjugateArray);
	}
	
	
	public String toString() {
		String result = "";
		
		result += "[";
		for (double[] row : this.array) {
			result += Arrays.toString(row) + "\n";
		}
		result = result.substring(0, result.length()-1) + "]";
		
		return result;
	}
}
