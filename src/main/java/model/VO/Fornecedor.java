package model.VO;

public class Fornecedor extends Usuario {
    private String catalogoProdutos;

    public Fornecedor() {
        this.tipoUsuario = TIPO_FORNECEDOR;
    }

    public Fornecedor(int id, String nome, String email, String senha, String cnpj, String catalogoProdutos) {
        super(id, nome, email, senha, cnpj, TIPO_FORNECEDOR);
        this.catalogoProdutos = catalogoProdutos;
    }

    public String getCatalogoProdutos() { return catalogoProdutos; }
    public void setCatalogoProdutos(String catalogoProdutos) { this.catalogoProdutos = catalogoProdutos; }
}
