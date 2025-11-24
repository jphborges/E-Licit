package model.VO;

import java.time.LocalDateTime;
import java.util.Objects;

public class Cotacao {
    private int id;
    private String descricao;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;
    private String status;
    private int hospitalId;

    public static final String STATUS_ABERTA = "ABERTA";
    public static final String STATUS_FECHADA = "FECHADA";

    public Cotacao(int id, String descricao, LocalDateTime dataAbertura,
                   LocalDateTime dataFechamento, String status, int hospitalId) {
        this.id = id;
        this.descricao = descricao;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
        this.status = status;
        this.hospitalId = hospitalId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDateTime getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(LocalDateTime dataFechamento) { this.dataFechamento = dataFechamento; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getHospitalId() { return hospitalId; }
    public void setHospitalId(int hospitalId) { this.hospitalId = hospitalId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cotacao)) return false;
        Cotacao cotacao = (Cotacao) o;
        return id == cotacao.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "Cotacao{id=" + id + ", status='" + status + "', hospitalId=" + hospitalId + "}";
    }
}