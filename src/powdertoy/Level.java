package powdertoy;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import data.Vector3f;
import map.Area;


public class Level {
	private Map<Integer, Area> chunks;
	
	public static final Vector3f GRAVITY = new Vector3f(0, -9.81f, 0f);
	
	public Level(){
		chunks = new HashMap<>();
	}
	public void addPixel(Pixel pixel){
		int id = getChunkId(pixel.getPosition());
		if(chunks.get(id)==null){
			chunks.put(id, new Area());
		}
		chunks.get(id).addPixel(pixel);
	}
	private int getChunkId(Vector3f position) {
		// TODO chunk id berechnen
		int id = 0;
		return id;
	}
	public void render(Player player){
		for (Entry<Integer, Area> entry : chunks.entrySet()) {
			entry.getValue().render();
		}
	}
	public void update(){
		for (Entry<Integer, Area> entry : chunks.entrySet()) {
			entry.getValue().update();
		}
	}
}
