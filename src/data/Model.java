package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Model {

	private float[] vertices;
	private float[] tcs;
	private float[] normals;
	private Map<String, Model.Material> materials;
	private Map<Model.Material, int[]> buffers;
	private Model.Material currentMaterial;
	private List<VertexArray> vertexArrays;

	public Model(String file) throws IOException {
		File f = new File(file);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String buffer;
		List<Float> vertexList = new LinkedList<>();
		List<Float> textureList = new LinkedList<>();
		List<Float> normalList = new LinkedList<>();
		
		vertexArrays = new LinkedList<>();

		buffers = new HashMap<>();
		materials = new HashMap<>();
		int currentSmoothness = -1;
		while ((buffer = br.readLine()) != null) {
			buffer.trim();
			if (buffer.startsWith("mtllib ")) {
				String[] tokens = buffer.split(" ");
				parseMaterialLibary(new File(f.getParentFile()
						.getAbsolutePath() + "/" + tokens[1]));
			}
			if (buffer.startsWith("v ")) {
				String[] tokens = buffer.split(" ");
				if(tokens.length == 4){
				vertexList.add(Float.parseFloat(tokens[1]));
				vertexList.add(Float.parseFloat(tokens[2]));
				vertexList.add(Float.parseFloat(tokens[3]));
				}
				else if (tokens.length == 5){
					vertexList.add(Float.parseFloat(tokens[2]));
					vertexList.add(Float.parseFloat(tokens[3]));
					vertexList.add(Float.parseFloat(tokens[4]));
				}
			}
			if (buffer.startsWith("o ")) {
				continue;
			}
			if (buffer.startsWith("vn ")) {
				String[] tokens = buffer.split(" ");
				normalList.add(Float.parseFloat(tokens[1]));
				normalList.add(Float.parseFloat(tokens[2]));
				normalList.add(Float.parseFloat(tokens[3]));
			}
			if (buffer.startsWith("vt ")) {
				String[] tokens = buffer.split(" ");
				textureList.add(Float.parseFloat(tokens[1]));
				textureList.add(Float.parseFloat(tokens[2]));
			}
			if(buffer.startsWith("usemtl ")){
				String[] tokens = buffer.split(" ");
				currentMaterial = materials.get(tokens[1]);
			}
			if (buffer.startsWith("s ")){
				String[] tokens = buffer.split(" ");
				if(tokens[1].equalsIgnoreCase("off")){
					currentSmoothness = -1;
					continue;
				}
				currentSmoothness = Integer.parseInt(tokens[1]);
			}
			if (buffer.startsWith("f ")) {
				String[] tokens = buffer.split(" ");
				Model.Face face = new Model.Face();
				face.setMaterial(currentMaterial);
				int[] verts = new int[] {
						Integer.parseInt(tokens[1].split("/")[0])-1,
						Integer.parseInt(tokens[2].split("/")[0])-1,
						Integer.parseInt(tokens[3].split("/")[0])-1, };

				int[] texs = new int[] {
						Integer.parseInt((tokens[1].split("/")[1]) != "" ? "0"
								: tokens[1].split("/")[1])-1,
						Integer.parseInt((tokens[2].split("/")[1]) != "" ? "0"
								: tokens[1].split("/")[1])-1,
						Integer.parseInt((tokens[3].split("/")[1]) != "" ? "0"
								: tokens[1].split("/")[1])-1 };

				int[] norms = new int[] {
						Integer.parseInt((tokens[1].split("/")[1]) != "" ? "0"
								: tokens[1].split("/")[2])-1,
						Integer.parseInt((tokens[1].split("/")[1]) != "" ? "0"
								: tokens[1].split("/")[2])-1,
						Integer.parseInt((tokens[1].split("/")[1]) != "" ? "0"
								: tokens[1].split("/")[2])-1 };

				face.setVertexIndices(verts);
				face.setTexIndices(texs);
				face.setNormalIndices(norms);
				face.setSmoothness(currentSmoothness);

				int[] ibo = buffers.get(face.getMaterial());
				if (ibo == null) {
					ibo = new int[] { face.getVertexIndices()[0],
							face.getVertexIndices()[1],
							face.getVertexIndices()[2], };
					buffers.put(face.getMaterial(), ibo);
					continue;
				}
				int[] newbo = new int[ibo.length + 3];
				System.arraycopy(ibo, 0, newbo, 0, ibo.length);
				newbo[newbo.length - 3] = face.getVertexIndices()[0];
				newbo[newbo.length - 2] = face.getVertexIndices()[1];
				newbo[newbo.length - 1] = face.getVertexIndices()[2];
				buffers.put(face.getMaterial(), newbo);
			}
		}
		br.close();
		vertices = new float[vertexList.size()];
		for (int i = 0; i < vertexList.size(); i++) {
			vertices[i] = vertexList.get(i);
		}
		tcs = new float[textureList.size()];
		for (int i = 0; i < textureList.size(); i++) {
			tcs[i] = textureList.get(i);
		}
		normals = new float[normalList.size()];
		for (int i = 0; i < normalList.size(); i++) {
			normals[i] = normalList.get(i);
		}
		for(Map.Entry<Model.Material, int[]> entry : buffers.entrySet()){
			vertexArrays.add(new VertexArray(vertices, entry.getValue(), tcs, normals, entry.getKey()));
		}
	}

	public void parseMaterialLibary(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String buffer = null;
		while ((buffer = br.readLine()) != null) {
			buffer.trim();
			if (buffer.startsWith("newmtl ")) {
				currentMaterial = new Model.Material();
				currentMaterial.setName(buffer.split(" ")[1]);
				materials.put(currentMaterial.getName(), currentMaterial);
			}
			if (buffer.startsWith("Ka ")||buffer.startsWith("\tKa ")) {
				String[] tokens = buffer.split(" ");
				currentMaterial.setAmbient(new Vector3f(Float
						.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
						Float.parseFloat(tokens[3])));
			}
			if (buffer.startsWith("Kd ")||buffer.startsWith("\tKd ")) {
				String[] tokens = buffer.split(" ");
				currentMaterial.setDiffuse(new Vector3f(Float
						.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
						Float.parseFloat(tokens[3])));
			}
			if (buffer.startsWith("Ks ")||buffer.startsWith("\tKs ")) {
				String[] tokens = buffer.split(" ");
				currentMaterial.setSpecular(new Vector3f(Float
						.parseFloat(tokens[1]), Float.parseFloat(tokens[2]),
						Float.parseFloat(tokens[3])));
			}
			if (buffer.startsWith("Ns ")||buffer.startsWith("\tNs ")) {
				String[] tokens = buffer.split(" ");
				currentMaterial.setSpecularCoefficient(Float
						.parseFloat(tokens[1]));
			}
			if (buffer.startsWith("Ni ")||buffer.startsWith("\tNi ")) {
				String[] tokens = buffer.split(" ");
				currentMaterial.setSpec(Float.parseFloat(tokens[1]));
			}
			if (buffer.startsWith("d ")||buffer.startsWith("\td ")) {
				String[] tokens = buffer.split(" ");
				currentMaterial.setTransparency(Float.parseFloat(tokens[1]));
			}
			if (buffer.startsWith("illum ")||buffer.startsWith("\tillum ")) {
				String[] tokens = buffer.split(" ");
				currentMaterial.setIllum(Integer.parseInt(tokens[1]));
			}
			if (buffer.startsWith("map_Kd ")||buffer.startsWith("\tmap_Kd ")) {
				String[] tokens = buffer.split(" ");
				currentMaterial.setTexture(new Texture(file.getParentFile().getAbsolutePath()+"/"+tokens[1]));
			}
		}
		br.close();
	}

	public float[] getNormals() {
		return normals;
	}

	public void setNormals(float[] normals) {
		this.normals = normals;
	}

	public float[] getVertices() {
		return vertices;
	}

	public void setVertices(float[] vertices) {
		this.vertices = vertices;
	}

	public float[] getTcs() {
		return tcs;
	}

	public void setTcs(float[] tcs) {
		this.tcs = tcs;
	}


	public void render() {
		for (VertexArray vertexArray : vertexArrays) {
			vertexArray.render();
		}
	}
	public void translate(float x, float y, float z){
		for (VertexArray vertexArray : vertexArrays) {
			vertexArray.translate(x, y, z);
		}
	}
	
	public void rotate(float degrees, float x, float y, float z){
		for (VertexArray vertexArray : vertexArrays) {
			vertexArray.rotate(degrees, x, y, z);
		}
	}
	
	public void scale(float x, float y, float z){
		for (VertexArray vertexArray : vertexArrays) {
			vertexArray.scale(x, y, z);
		}
	}
	public Matrix4f calcMatrices(){
		return vertexArrays.get(0).calcMatrices();
	}
	
	public Model() {
	}

	public static class Material {
		private Vector3f ambient;
		private Vector3f diffuse;
		private Vector3f specular;
		private Vector3f transmissionFilter;
		private int illum;
		private float spec;
		private float transparency;
		private float specularCoefficient;
		private String name;
		private Texture texture;

		public Vector3f getAmbient() {
			return ambient;
		}

		public void setAmbient(Vector3f ambient) {
			this.ambient = ambient;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Vector3f getDiffuse() {
			return diffuse;
		}

		public void setDiffuse(Vector3f diffuse) {
			this.diffuse = diffuse;
		}

		public Vector3f getSpecular() {
			return specular;
		}

		public void setSpecular(Vector3f specular) {
			this.specular = specular;
		}

		public int getIllum() {
			return illum;
		}

		public void setIllum(int illum) {
			this.illum = illum;
		}

		public float getSpec() {
			return spec;
		}

		public void setSpec(float spec) {
			this.spec = spec;
		}

		public float getTransparency() {
			return transparency;
		}

		public void setTransparency(float transparency) {
			this.transparency = transparency;
		}

		public float getSpecularCoefficient() {
			return specularCoefficient;
		}

		public void setSpecularCoefficient(float specularCoefficient) {
			this.specularCoefficient = specularCoefficient;
		}

		public Vector3f getTransmissionFilter() {
			return transmissionFilter;
		}

		public void setTransmissionFilter(Vector3f transmissionFilter) {
			this.transmissionFilter = transmissionFilter;
		}

		public Texture getTexture() {
			return texture;
		}

		public void setTexture(Texture texture) {
			this.texture = texture;
		}

		@Override
		public String toString() {
			return "Material [name=" + name + ", ambient=" + ambient
					+ ", diffuse=" + diffuse + ", specular=" + specular
					+ ", transmissionFilter=" + transmissionFilter + ", illum="
					+ illum + ", spec=" + spec + ", transparency="
					+ transparency + ", specularCoefficient="
					+ specularCoefficient + "]";
		}
	}

	public static class Face {
		private int smoothness;
		private int[] vertexIndices = new int[] { -1, -1, -1 };
		private int[] texIndices = new int[] { -1, -1, -1 };
		private int[] normalIndices = new int[] { -1, -1, -1 };

		private Model.Material material;

		public int[] getVertexIndices() {
			return vertexIndices;
		}

		public void setVertexIndices(int[] vertexIndices) {
			this.vertexIndices = vertexIndices;
		}

		public int[] getTexIndices() {
			return texIndices;
		}

		public void setTexIndices(int[] texIndices) {
			this.texIndices = texIndices;
		}

		public int[] getNormalIndices() {
			return normalIndices;
		}

		public void setNormalIndices(int[] normalIndices) {
			this.normalIndices = normalIndices;
		}

		public Model.Material getMaterial() {
			return material;
		}

		public void setMaterial(Model.Material material) {
			this.material = material;
		}

		public int getSmoothness() {
			return smoothness;
		}

		public void setSmoothness(int smoothness) {
			this.smoothness = smoothness;
		}

	}

	public void render(Vector3f position) {
		for (VertexArray vertexArray : vertexArrays) {
			vertexArray.translate(position.x, position.y, position.z);
			vertexArray.render();
		}
	}
}
