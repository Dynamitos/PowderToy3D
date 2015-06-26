package powdertoy;

import data.Matrix4f;
import data.Model;
import data.Vector3f;

public class Pixel {
	private Property property;
	private Model model;
	private Vector3f position;
	
	public Pixel(Model model, Property property, Vector3f position){
		this.property = property;
		this.model = model;
		this.position = position;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void render() {
		model.render();
	}

	public void update() {
		property.addForce(Level.GRAVITY);
		position.add(property.calcSpeed());
	}
	public Matrix4f getMatrix(){
		return model.calcMatrices();
	}
	public void translate(float x, float y, float z){
		model.translate(x, y, z);
	}
	public void scale(float x, float y, float z){
		model.scale(x, y, z);
	}
	public void rotate(float degrees, float x, float y, float z){
		model.rotate(degrees, x, y, z);
	}
}