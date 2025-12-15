package controller;

import model.VO.Usuario;

public class SessionManager {
    private static Usuario usuarioLogado;

    private SessionManager() {
    }

    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static String getTipoUsuario() {
        return usuarioLogado == null ? null : usuarioLogado.getTipoUsuario();
    }

    public static boolean isHospital() {
        return Usuario.TIPO_HOSPITAL.equalsIgnoreCase(getTipoUsuario());
    }

    public static boolean isFornecedor() {
        return Usuario.TIPO_FORNECEDOR.equalsIgnoreCase(getTipoUsuario());
    }

    public static void encerrarSessao() {
        usuarioLogado = null;
    }
}


