package theroom;

import java.util.LinkedList;
import java.util.List;

import data.Model;
import data.Vector3f;

public class Entity {
	protected Vector3f position;
	protected Vector3f lookingDirection;
	protected float speed;
	protected Vector3f movingDirection;
	protected Model model;
	protected int health;
	protected int maxHealth;
	protected List<Vector3f> forces;

	public Entity(Vector3f position, Vector3f lookingDirection, int maxHealth, Model model) {
		this.position = position;
		this.lookingDirection = lookingDirection;
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		this.model = model;
		movingDirection = new Vector3f(0, 0, 0);
		this.speed = 0;
		forces = new LinkedList<>();
	}

	public void move(Vector3f direction) {
		movingDirection = direction;
	}

	public void accelerate(float amount) {
		speed += amount;
	}

	public void lookAt(Vector3f direction) {
		lookingDirection = direction;
	}

	public void translate(Vector3f destination) {
		position = destination;
	}

	public void damage(int damage) {
		health -= damage;
		if (health <= 0) {
			die();
		}
	}
	
	public void render(){
		model.render(position);
	}

	public void die() {
		// TODO add death
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

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Vector3f getMovingDirection() {
		return movingDirection;
	}

	public void setMovingDirection(Vector3f movingDirection) {
		this.movingDirection = movingDirection;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void update() {
		movingDirection = new Vector3f();
		for (Vector3f vector3f : forces) {
			movingDirection.add(vector3f);
		}
		forces.clear();
	}
	public void addForce(Vector3f force){
		forces.add(force);
	}
}
