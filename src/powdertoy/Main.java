package powdertoy;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import data.Input;
import data.Matrix4f;
import data.Model;
import data.Shader;
import data.Vector3f;

public class Main {

	private long windowID;
	private int width, height;
	private Input input;
	private boolean running;
	private Level level;
	private Player player;
	private Matrix4f projection;
	private Matrix4f view;
	private Matrix4f mvpMatrix;

	public static void main(String[] args) {
		new Main().start();
	}

	public void start() {
		init();
		loop();
		destroy();
	}

	public void init() {
		width = 1920/2;
		height = 1080/2;
		level = new Level();
		
		if (glfwInit() == GL_FALSE) {
			return;
		}
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		windowID = glfwCreateWindow(width, height, "", NULL, NULL);
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(windowID, (GLFWvidmode.width(vidmode) - width) / 2,
				(GLFWvidmode.height(vidmode) - height) / 2);

		input = new Input();
		glfwSetKeyCallback(windowID, input);

		glfwMakeContextCurrent(windowID);
		glfwShowWindow(windowID);

		GLContext.createFromCurrent();
		glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		glEnable(GL_DEPTH_TEST);
		glfwSwapInterval(1);
		Shader.loadAll();
		try {
			player = new Player(new Vector3f(0, 0, 0), new Vector3f(1, 0, 0),
					100, new Model("res/elucidatorobj.obj"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//TODO player zu level hinzufügen
		projection = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);
		view = new Matrix4f();
		Shader.standart.setUniformMat4f("projection", projection);
	}

	public void loop() {
		running = true;
		while (running) {
			update();
			render();
			sleep();
		}
	}

	private void sleep() {
		glfwSwapBuffers(windowID);
	}

	private void update() {
		glfwPollEvents();
		
		updateKeys();
		level.update();
		Shader.standart.setUniformMat4f("view", view);
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		level.render(player);
	}

	public void destroy() {
		glfwWindowShouldClose(windowID);
		glfwTerminate();
	}

	private void updateKeys() {
		if (Input.keys[GLFW_KEY_ESCAPE]) {
			running = false;
		}
		if(Input.keys[GLFW_KEY_SPACE]){
			try {
				level.addPixel(new Pixel(new Model("res/cube.obj"), new Property(0f, 0f), player.getPosition()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(Input.keys[GLFW_KEY_W]){
			view = view.multiply(Matrix4f.translate(1.0f, 0.0f, 0.0f));
		}
		if(Input.keys[GLFW_KEY_S]){
			view = view.multiply(Matrix4f.translate(1.0f, 0.0f, 0.0f));
		}
	}
}
