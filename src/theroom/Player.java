package theroom;

import data.Model;
import data.Vector3f;

public class Player extends Entity{
	
	private final float FOV = 45;
	
	public Player(Vector3f position, Vector3f lookingDirection,
			int maxHealth, Model model) {
		super(position, lookingDirection, maxHealth, model);
		// TODO Auto-generated constructor stub
	}
	
}
