package model.dao;

import model.VO.Cotacao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CotacaoDAO {

    public Cotacao inserir(Cotacao cotacao) {
        String sql = "INSERT INTO cotacao (hospital_id, titulo, descricao, status, data_abertura, data_fechamento) VALUES (?,?,?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, cotacao.getHospitalId());
            ps.setString(2, cotacao.getDescricao());
            ps.setString(3, cotacao.getDescricao());
            ps.setString(4, cotacao.getStatus());
            ps.setTimestamp(5, toTimestamp(cotacao.getDataAbertura()));
            ps.setTimestamp(6, toTimestamp(cotacao.getDataFechamento()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    cotacao.setId(rs.getInt(1));
                }
            }
            return cotacao;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir cotação: " + e.getMessage(), e);
        }
    }

    public Cotacao atualizar(Cotacao cotacao) {
        String sql = "UPDATE cotacao SET titulo = ?, descricao = ?, status = ?, data_abertura = ?, data_fechamento = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cotacao.getDescricao());
            ps.setString(2, cotacao.getDescricao());
            ps.setString(3, cotacao.getStatus());
            ps.setTimestamp(4, toTimestamp(cotacao.getDataAbertura()));
            ps.setTimestamp(5, toTimestamp(cotacao.getDataFechamento()));
            ps.setInt(6, cotacao.getId());
            ps.executeUpdate();
            return cotacao;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cotação: " + e.getMessage(), e);
        }
    }

    public List<Cotacao> listarPorHospital(int hospitalId) {
        String sql = "SELECT * FROM cotacao WHERE hospital_id = ? ORDER BY data_abertura DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hospitalId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Cotacao> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(mapCotacao(rs));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cotações do hospital: " + e.getMessage(), e);
        }
    }

    public List<Cotacao> listarAbertas() {
        String sql = "SELECT * FROM cotacao WHERE status = ? ORDER BY data_abertura DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, Cotacao.STATUS_ABERTA);
            try (ResultSet rs = ps.executeQuery()) {
                List<Cotacao> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(mapCotacao(rs));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cotações abertas: " + e.getMessage(), e);
        }
    }

    public Cotacao buscarPorId(int id) {
        String sql = "SELECT * FROM cotacao WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCotacao(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cotação: " + e.getMessage(), e);
        }
    }

    public void fecharCotacao(int cotacaoId) {
        String sql = "UPDATE cotacao SET status = ?, data_fechamento = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, Cotacao.STATUS_FECHADA);
            ps.setTimestamp(2, toTimestamp(LocalDateTime.now()));
            ps.setInt(3, cotacaoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fechar cotação: " + e.getMessage(), e);
        }
    }

    public void fecharCotacao(int cotacaoId, Connection conn) throws SQLException {
        String sql = "UPDATE cotacao SET status = ?, data_fechamento = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, Cotacao.STATUS_FECHADA);
            ps.setTimestamp(2, toTimestamp(LocalDateTime.now()));
            ps.setInt(3, cotacaoId);
            ps.executeUpdate();
        }
    }

    private Cotacao mapCotacao(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String descricao = rs.getString("descricao");
        LocalDateTime abertura = toLocalDateTime(rs.getTimestamp("data_abertura"));
        LocalDateTime fechamento = toLocalDateTime(rs.getTimestamp("data_fechamento"));
        String status = rs.getString("status");
        int hospitalId = rs.getInt("hospital_id");
        return new Cotacao(id, descricao, abertura, fechamento, status, hospitalId);
    }

    private Timestamp toTimestamp(LocalDateTime dateTime) {
        return dateTime == null ? null : Timestamp.valueOf(dateTime);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
