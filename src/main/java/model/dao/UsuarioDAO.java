package model.dao;

import model.VO.Fornecedor;
import model.VO.Hospital;
import model.VO.Usuario;

import java.sql.*;

public class UsuarioDAO {

    public boolean emailExiste(String email) {
        String sql = "SELECT 1 FROM usuario WHERE email = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar email: " + e.getMessage(), e);
        }
    }

    public Usuario autenticar(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUsuario(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuário: " + e.getMessage(), e);
        }
    }

    public Hospital inserirHospital(Hospital hospital) {
        String sql = "INSERT INTO usuario (tipo, nome, email, senha, cnpj, endereco) VALUES (?,?,?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, Usuario.TIPO_HOSPITAL);
            ps.setString(2, hospital.getNome());
            ps.setString(3, hospital.getEmail());
            ps.setString(4, hospital.getSenha());
            ps.setString(5, hospital.getCnpj());
            ps.setString(6, hospital.getEndereco());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    hospital.setId(rs.getInt(1));
                }
            }
            return hospital;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir hospital: " + e.getMessage(), e);
        }
    }

    public Fornecedor inserirFornecedor(Fornecedor fornecedor) {
        String sql = "INSERT INTO usuario (tipo, nome, email, senha, cnpj, catalogo) VALUES (?,?,?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, Usuario.TIPO_FORNECEDOR);
            ps.setString(2, fornecedor.getNome());
            ps.setString(3, fornecedor.getEmail());
            ps.setString(4, fornecedor.getSenha());
            ps.setString(5, fornecedor.getCnpj());
            ps.setString(6, fornecedor.getCatalogoProdutos());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    fornecedor.setId(rs.getInt(1));
                }
            }
            return fornecedor;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir fornecedor: " + e.getMessage(), e);
        }
    }

    private Usuario mapUsuario(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String email = rs.getString("email");
        String senha = rs.getString("senha");
        String cnpj = rs.getString("cnpj");
        if (Usuario.TIPO_HOSPITAL.equalsIgnoreCase(tipo)) {
            Hospital hospital = new Hospital();
            hospital.setId(id);
            hospital.setNome(nome);
            hospital.setEmail(email);
            hospital.setSenha(senha);
            hospital.setCnpj(cnpj);
            hospital.setEndereco(rs.getString("endereco"));
            return hospital;
        }
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(id);
        fornecedor.setNome(nome);
        fornecedor.setEmail(email);
        fornecedor.setSenha(senha);
        fornecedor.setCnpj(cnpj);
        fornecedor.setCatalogoProdutos(rs.getString("catalogo"));
        return fornecedor;
    }
}


