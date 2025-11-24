package model.VO;

public class Hospital extends Usuario {
    private String endereco;

    public Hospital() {
        this.tipoUsuario = TIPO_HOSPITAL;
    }

    public Hospital(int id, String nome, String email, String senha, String cnpj, String endereco) {
        super(id, nome, email, senha, cnpj, TIPO_HOSPITAL);
        this.endereco = endereco;
    }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}