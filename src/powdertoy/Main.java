package powdertoy;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.system.MemoryUtil.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map.Entry;

import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import data.Input;
import data.Model;
import data.Vector3f;

public class Main {
	
	private long windowID;
	private int width, height;
	private Input input;
	private boolean running;
	private Level level;
	
	
	public static void main(String[] args) {
		new Main().start();
	}
	public void start(){
		init();
		loop();
		destroy();
	}
	public void init(){
		width = 600;
		height = 300;
		level = new Level();
//		try {
//			player = new Player(new Vector3f(0, 0, 0), new Vector3f(1, 0, 0), 100, new Model("res/elucidatorobj.obj"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		level.add(player);
		if (glfwInit() == GL_FALSE) {

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
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glfwSwapInterval(1);
	}
	
	public void loop(){
		running = true;
		while(running){
			render();
			update();
			updateKeys();
			sleep();
		}
	}
	private void sleep() {
		
		glfwSwapBuffers(windowID);
	}
	private void update() {
		glfwPollEvents();
		
	}
	private void render() {
		
	}
	public void destroy(){
		glfwWindowShouldClose(windowID);
		glfwTerminate();
	}
	private void updateKeys(){
		
		if(Input.keys[GLFW_KEY_ESCAPE]){
			running = false;
		}
	}
}
