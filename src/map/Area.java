package map;

import java.util.LinkedList;
import java.util.List;

import powdertoy.Pixel;

public class Area {
	private List<Pixel> pixels;
	public Area(){
		pixels = new LinkedList<>();//Größe?
	}
	public void addPixel(Pixel pixel){
		pixels.add(pixel);
	}
	public void render(){
		for (Pixel pixel : pixels) {
			pixel.render();
		}
	}
	public void update() {
		for (Pixel pixel : pixels) {
			pixel.update();
		}
	}
	
	public void translate(float x, float y, float z) {
		for (Pixel pixel : pixels) {
			pixel.translate(x, y, z);
		}
	}
	public void rotate(float degrees, float x, float y, float z) {
		for (Pixel pixel : pixels) {
			pixel.rotate(degrees, x, y, z);
		}
	}
	public void scale(float x, float y, float z) {
		for (Pixel pixel : pixels) {
			pixel.scale(x, y, z);
		}
	}
}
