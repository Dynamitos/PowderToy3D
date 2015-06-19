package powdertoy;

public class Property {
	private float temp;
	private float pressure;
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
	}
	
}
