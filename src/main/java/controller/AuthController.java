package controller;

import model.RN.AuthRN;
import model.VO.Fornecedor;
import model.VO.Hospital;
import model.VO.Usuario;

public class AuthController {
    private final AuthRN authService;

    public AuthController() {
        this.authService = new AuthRN();
    }

    public Usuario login(String email, String senha) {
        return authService.login(email, senha);
    }

    public Hospital registrarHospital(Hospital hospital) {
        if (hospital == null) {
            throw new RuntimeException("Dados do hospital são obrigatórios");
        }
        return authService.registrarHospital(hospital);
    }

    public Fornecedor registrarFornecedor(Fornecedor fornecedor) {
        if (fornecedor == null) {
            throw new RuntimeException("Dados do fornecedor são obrigatórios");
        }
        return authService.registrarFornecedor(fornecedor);
    }

    public void logout() {
        authService.logout();
    }
}


