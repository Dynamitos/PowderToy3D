package data;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import data.Model.Material;

public class VertexArray {

	private int vao, vbo, ibo, tbo, nbo;
	private int count;
	private Matrix4f rotation;
	private Matrix4f scale;
	private Matrix4f model;
	private Matrix4f translation;
	private Material material;

	public VertexArray(float[] vertices, int[] indices,
			float[] textureCoordinates, float[] normals, Material material) {
		count = indices.length;

		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices),
				GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.VERTEX_ATTRIB);

		tbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, tbo);
		glBufferData(GL_ARRAY_BUFFER,
				BufferUtils.createFloatBuffer(textureCoordinates),
				GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.TCOORD_ATTRIB, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.TCOORD_ATTRIB);

		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,
				BufferUtils.createIntBuffer(indices), GL_STATIC_DRAW);

		nbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, nbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(normals), GL_STATIC_DRAW);
		
		glEnableVertexAttribArray(2);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0l);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		this.material = material;
		translation = new Matrix4f();
		scale = new Matrix4f();
		rotation = new Matrix4f();
		translation.setIdentity();
		scale.setIdentity();
		rotation.setIdentity();
	}

	public void bind() {
		glBindVertexArray(vao);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		Shader.standart.setUniform4f("ambientColor", new Vector4f(material.getAmbient().x, material.getAmbient().y, material.getAmbient().z, 1.0f));
		Shader.standart.setUniform4f("specularColor", new Vector4f(material.getSpecular().x, material.getSpecular().y, material.getSpecular().z, 1.0f));
		Shader.standart.setUniform4f("diffuseColor", new Vector4f(material.getDiffuse().x, material.getDiffuse().y, material.getDiffuse().z, 1.0f));
		Shader.standart.setUniformMat4f("model", model);
	}

	public void unbind() {
		glBindVertexArray(0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public void draw() {
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
	}

	public void render() {
		bind();
		draw();
	}

	public Matrix4f calcMatrices() {
		return (setModel(rotation.multiply(scale.multiply(translation))));
	}

	public void rotate(float degrees, float x, float y, float z) {
		rotation = rotation.multiply(Matrix4f.rotate(degrees, x, y, z));
	}
	
	public void translate(float x, float y, float z){
		translation = translation.multiply(Matrix4f.translate(x, y, z));
	}
	
	public void scale(float x, float y, float z){
		scale = scale.multiply(Matrix4f.scale(x, y, z));
	}
	
	public Matrix4f getModel() {
		return model;
	}

	public Matrix4f setModel(Matrix4f model) {
		this.model = model;
		return model;
	}
}
