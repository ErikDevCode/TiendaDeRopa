package datos;

public class Marca {
    private int id;
    private String marcaNombre;

    public Marca(int id, String marcaNombre) {
        this.id = id;
        this.marcaNombre = marcaNombre;
    }

    public int getId() { return id; }
    public String getMarcaNombre() { return marcaNombre; }

    @Override
    public String toString() {
        return marcaNombre;
    }
}
