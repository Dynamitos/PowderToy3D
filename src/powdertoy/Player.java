package powdertoy;

import data.Model;
import data.Vector3f;

public class Player{
	
	private final float FOV = 45;
	private Vector3f position;
	private Vector3f lookingDirection;
	private int maxHealth;
	private int health;
	private Model model;
	
	
	public Player(Vector3f position, Vector3f lookingDirection,
			int maxHealth, Model model) {
		this.position = position;
		this.lookingDirection = lookingDirection;
		this.maxHealth = maxHealth;
		this.model = model;
		health = maxHealth;
	}


	public Vector3f getPosition() {
		return position;
	}


	public void setPosition(Vector3f position) {
		this.position = position;
	}


	public Vector3f getLookingDirection() {
		return lookingDirection;
	}


	public void setLookingDirection(Vector3f lookingDirection) {
		this.lookingDirection = lookingDirection;
	}


	public int getMaxHealth() {
		return maxHealth;
	}


	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}


	public int getHealth() {
		return health;
	}


	public void setHealth(int health) {
		this.health = health;
	}


	public Model getModel() {
		return model;
	}


	public void setModel(Model model) {
		this.model = model;
	}


	public float getFOV() {
		return FOV;
	}
	
}
