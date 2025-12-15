package model.dao;

import model.VO.Proposta;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PropostaDAO {

    public Proposta inserir(Proposta proposta) {
        String sql = "INSERT INTO proposta (cotacao_id, fornecedor_id, valor_total, condicoes_pagamento, status, data_envio, validade) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, proposta.getCotacaoId());
            ps.setInt(2, proposta.getFornecedorId());
            ps.setDouble(3, proposta.getValorTotal());
            ps.setString(4, proposta.getCondicoesPagamento());
            ps.setString(5, proposta.getStatus());
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setDate(7, toSqlDate(proposta.getDataValidade()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    proposta.setId(rs.getInt(1));
                }
            }
            return proposta;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir proposta: " + e.getMessage(), e);
        }
    }

    public Proposta inserir(Proposta proposta, Connection conn) throws SQLException {
        String sql = "INSERT INTO proposta (cotacao_id, fornecedor_id, valor_total, condicoes_pagamento, status, data_envio, validade) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, proposta.getCotacaoId());
            ps.setInt(2, proposta.getFornecedorId());
            ps.setDouble(3, proposta.getValorTotal());
            ps.setString(4, proposta.getCondicoesPagamento());
            ps.setString(5, proposta.getStatus());
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setDate(7, toSqlDate(proposta.getDataValidade()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    proposta.setId(rs.getInt(1));
                }
            }
            return proposta;
        }
    }

    public List<Proposta> listarPorCotacao(int cotacaoId) {
        String sql = "SELECT * FROM proposta WHERE cotacao_id = ? ORDER BY data_envio DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cotacaoId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Proposta> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(mapProposta(rs));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar propostas: " + e.getMessage(), e);
        }
    }

    public List<Proposta> listarPorFornecedor(int fornecedorId) {
        String sql = "SELECT * FROM proposta WHERE fornecedor_id = ? ORDER BY data_envio DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, fornecedorId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Proposta> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(mapProposta(rs));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar propostas do fornecedor: " + e.getMessage(), e);
        }
    }

    public void atualizarStatus(int propostaId, String status) {
        String sql = "UPDATE proposta SET status = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, propostaId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status da proposta: " + e.getMessage(), e);
        }
    }

    public void atualizarStatus(int propostaId, String status, Connection conn) throws SQLException {
        String sql = "UPDATE proposta SET status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, propostaId);
            ps.executeUpdate();
        }
    }

    public void recusarOutrasPropostas(int cotacaoId, int propostaAceitaId, Connection conn) throws SQLException {
        String sql = "UPDATE proposta SET status = ? WHERE cotacao_id = ? AND id <> ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, Proposta.STATUS_RECUSADA);
            ps.setInt(2, cotacaoId);
            ps.setInt(3, propostaAceitaId);
            ps.executeUpdate();
        }
    }

    public boolean existePropostaDoFornecedor(int cotacaoId, int fornecedorId) {
        String sql = "SELECT 1 FROM proposta WHERE cotacao_id = ? AND fornecedor_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cotacaoId);
            ps.setInt(2, fornecedorId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar proposta existente: " + e.getMessage(), e);
        }
    }

    private Proposta mapProposta(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        double valorTotal = rs.getDouble("valor_total");
        String condicoesPagamento = rs.getString("condicoes_pagamento");
        LocalDate validade = toLocalDate(rs.getDate("validade"));
        String status = rs.getString("status");
        int cotacaoId = rs.getInt("cotacao_id");
        int fornecedorId = rs.getInt("fornecedor_id");
        return new Proposta(id, valorTotal, condicoesPagamento, validade, status, cotacaoId, fornecedorId);
    }

    private java.sql.Date toSqlDate(LocalDate date) {
        return date == null ? null : java.sql.Date.valueOf(date);
    }

    private LocalDate toLocalDate(java.sql.Date date) {
        return date == null ? null : date.toLocalDate();
    }
}


