package datos;

public class Producto {
    private int id;
    private int marcaId;
    private String nombreProducto;
    private double precio;
    private String talla;
    private String color;
    private String material;
    private int stock;

    public Producto(int id, int marcaId, String nombreProducto, double precio, String talla, String color, String material, int stock) {
        this.id = id;
        this.setMarcaId(marcaId);
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.talla = talla;
        this.color = color;
        this.material = material;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getNombreProducto() { return nombreProducto; }
    public double getPrecio() { return precio; }
    public String getTalla() { return talla; }
    public String getColor() { return color; }
    public String getMaterial() { return material; }
    public int getStock() { return stock; }

    @Override
    public String toString() {
        return nombreProducto;
    }

	public int getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(int marcaId) {
		this.marcaId = marcaId;
	}
	
	public void setId(int id) {
	    this.id = id;
	}

	public void setNombreProducto(String nombreProducto) {
	    this.nombreProducto = nombreProducto;
	}

	public void setPrecio(double precio) {
	    this.precio = precio;
	}

	public void setTalla(String talla) {
	    this.talla = talla;
	}

	public void setColor(String color) {
	    this.color = color;
	}

	public void setMaterial(String material) {
	    this.material = material;
	}

	public void setStock(int stock) {
	    this.stock = stock;
	}

}
