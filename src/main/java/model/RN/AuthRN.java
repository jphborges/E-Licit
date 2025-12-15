package model.RN;

import controller.SessionManager;
import model.dao.UsuarioDAO;
import model.VO.Fornecedor;
import model.VO.Hospital;
import model.VO.Usuario;

public class AuthRN {
    private final UsuarioDAO usuarioDAO;

    public AuthRN() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario login(String email, String senha) {
        if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
            throw new RuntimeException("Email e senha são obrigatórios");
        }
        Usuario usuario = usuarioDAO.autenticar(email, senha);
        if (usuario == null) {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
        SessionManager.setUsuarioLogado(usuario);
        return usuario;
    }

    public Hospital registrarHospital(Hospital hospital) {
        validarCamposBasicos(hospital.getNome(), hospital.getEmail(), hospital.getSenha());
        validarEmailUnico(hospital.getEmail());
        return usuarioDAO.inserirHospital(hospital);
    }

    public Fornecedor registrarFornecedor(Fornecedor fornecedor) {
        validarCamposBasicos(fornecedor.getNome(), fornecedor.getEmail(), fornecedor.getSenha());
        validarEmailUnico(fornecedor.getEmail());
        return usuarioDAO.inserirFornecedor(fornecedor);
    }

    public void logout() {
        SessionManager.encerrarSessao();
    }

    private void validarCamposBasicos(String nome, String email, String senha) {
        if (nome == null || nome.isBlank() || email == null || email.isBlank() || senha == null || senha.isBlank()) {
            throw new RuntimeException("Nome, email e senha são obrigatórios");
        }
    }

    private void validarEmailUnico(String email) {
        if (usuarioDAO.emailExiste(email)) {
            throw new RuntimeException("Email já cadastrado");
        }
    }
}

