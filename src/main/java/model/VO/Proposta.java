package model.VO;

import java.time.LocalDate;
import java.util.Objects;

public class Proposta {
    private int id;
    private double valorTotal; //NO DB ESTA DECIMAL(10,2)
    private String condicoesPagamento;
    private LocalDate dataValidade;
    private String status;
    private int cotacaoId;
    private int fornecedorId;

    // Constantes de status
    public static final String STATUS_ENVIADA = "ENVIADA";
    public static final String STATUS_ACEITA  = "ACEITA";
    public static final String STATUS_RECUSADA= "RECUSADA";

    public Proposta(int id, double valorTotal, String condicoesPagamento,
                    LocalDate dataValidade, String status, int cotacaoId, int fornecedorId) {
        this.id = id;
        this.valorTotal = valorTotal;
        this.condicoesPagamento = condicoesPagamento;
        this.dataValidade = dataValidade;
        this.status = status;
        this.cotacaoId = cotacaoId;
        this.fornecedorId = fornecedorId;
    }

    // Getters/Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public String getCondicoesPagamento() { return condicoesPagamento; }
    public void setCondicoesPagamento(String condicoesPagamento) { this.condicoesPagamento = condicoesPagamento; }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getCotacaoId() { return cotacaoId; }
    public void setCotacaoId(int cotacaoId) { this.cotacaoId = cotacaoId; }

    public int getFornecedorId() { return fornecedorId; }
    public void setFornecedorId(int fornecedorId) { this.fornecedorId = fornecedorId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proposta)) return false;
        Proposta proposta = (Proposta) o;
        return id == proposta.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Proposta{id=" + id + ", status='" + status + "', cotacaoId=" + cotacaoId + ", fornecedorId=" + fornecedorId + "}";
    }
}