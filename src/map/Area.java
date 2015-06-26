package map;

import powdertoy.Pixel;
import data.Matrix4f;
import data.Vector3f;
import data.Vector4f;

public class Area {
	private Pixel[][][] pixels;
	public Area(){
		pixels = new Pixel[64][64][256];//Größe?
	}
	public void addPixel(Pixel pixel){
		pixels[(int) pixel.getPosition().x][(int) pixel.getPosition().y][(int) pixel.getPosition().z] = pixel;//???
	}
	public void render(){
		for (Pixel[][] pixels2 : pixels) {
			for (Pixel[] pixels : pixels2) {
				for (Pixel pixel : pixels) {
					if(pixel==null)
						continue;
					pixel.render();
				}
			}
		}
	}
	public void update() {
		for (Pixel[][] pixels2 : pixels) {
			for (Pixel[] pixels : pixels2) {
				for (Pixel pixel : pixels) {
					if(pixel==null)
						continue;
					pixel.update();
				}
			}
		}
	}
	public Matrix4f getMatrix() {
		Matrix4f result = new Matrix4f();
		result.setIdentity();
		for (Pixel[][] pixels2 : pixels) {
			for (Pixel[] pixels : pixels2) {
				for (Pixel pixel : pixels) {
					if(pixel==null)
						continue;
					result = result.multiply(pixel.getMatrix());
				}
			}
		}
		return result;
	}
	public void translate(float x, float y, float z) {
		for (Pixel[][] pixels2 : pixels) {
			for (Pixel[] pixels : pixels2) {
				for (Pixel pixel : pixels) {
					if(pixel==null)
						continue;
					pixel.translate(x, y, z);
				}
			}
		}
	}
	public void rotate(float degrees, float x, float y, float z) {
		for (Pixel[][] pixels2 : pixels) {
			for (Pixel[] pixels : pixels2) {
				for (Pixel pixel : pixels) {
					if(pixel==null)
						continue;
					pixel.rotate(degrees, x, y, z);
				}
			}
		}
	}
	public void scale(float x, float y, float z) {
		for (Pixel[][] pixels2 : pixels) {
			for (Pixel[] pixels : pixels2) {
				for (Pixel pixel : pixels) {
					if(pixel==null)
						continue;
					pixel.scale(x, y, z);
				}
			}
		}
	}
}
