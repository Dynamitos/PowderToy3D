package powdertoy;

import java.util.LinkedList;
import java.util.List;

import data.Vector3f;

public class Property {
	private float temp;
	private float pressure;
	private Vector3f velocity;
	private List<Vector3f> forces;
	
	public float getTemp() {
		return temp;
	}
	public void setTemp(float temp) {
		this.temp = temp;
	}
	public float getPressure() {
		return pressure;
	}
	public void setPressure(float pressure) {
		this.pressure = pressure;
	}
	public Property(float temp, float pressure) {
		this.temp = temp;
		this.pressure = pressure;
		forces = new LinkedList<>();
		velocity = new Vector3f();
	}
	public void addForce(Vector3f force){
		forces.add(force);
	}
	public Vector3f calcSpeed(){
		Vector3f result = new Vector3f();
		for (Vector3f vector3f : forces) {
			result.add(vector3f);
		}
		forces.clear();
		velocity = velocity.add(result);
		forces.add(velocity);
		forces.add(Level.GRAVITY);
		return velocity;
	}
}
