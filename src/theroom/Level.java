package theroom;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import data.Vector3f;

public class Level {
	private Map<Vector3f, Entity> entities;
	
	
	public Level() {
		entities = new HashMap<Vector3f, Entity>();
	}

	public void add(Entity entity) {
		entities.put(entity.getPosition(), entity);
	}

	public Map<Vector3f, Entity> getEntities() {
		return entities;
	}

	public void update() {
		for (Entry<Vector3f, Entity> entry : entities.entrySet()) {
			entry.getValue().update();
		}
	}
}
