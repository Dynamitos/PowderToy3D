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
		mvpMatrix = level.getMatrix();
		updateKeys();
		level.update();
		Shader.standart.setUniformMat4f("MVP", mvpMatrix);
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
			level.translate(0f, 1f, 0f);
			mvpMatrix = mvpMatrix.multiply(level.getMatrix());
		}
		if(Input.keys[GLFW_KEY_S]){
			level.translate(0f, -1f, 0f);
			mvpMatrix = mvpMatrix.multiply(level.getMatrix());
		}
	}
}
