package model.RN;

import controller.SessionManager;
import model.dao.CotacaoDAO;
import model.VO.Cotacao;
import model.VO.Hospital;
import model.VO.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class CotacaoRN {
    private final CotacaoDAO cotacaoDAO;

    public CotacaoRN() {
        this.cotacaoDAO = new CotacaoDAO();
    }

    public Cotacao salvar(Hospital hospital, Cotacao cotacao) {
        garantirHospitalLogado();
        if (cotacao == null) {
            throw new RuntimeException("Cotação inválida");
        }
        if (cotacao.getDescricao() == null || cotacao.getDescricao().isBlank()) {
            throw new RuntimeException("Descrição é obrigatória");
        }
        if (cotacao.getStatus() == null || cotacao.getStatus().isBlank()) {
            cotacao.setStatus(Cotacao.STATUS_ABERTA);
        }
        if (cotacao.getId() > 0) {
            Cotacao atual = cotacaoDAO.buscarPorId(cotacao.getId());
            if (atual != null && !Cotacao.STATUS_ABERTA.equalsIgnoreCase(atual.getStatus())) {
                throw new RuntimeException("Só é possível editar cotações ABERTAS");
            }
        } else {
            cotacao.setStatus(Cotacao.STATUS_ABERTA);
        }
        if (cotacao.getDataAbertura() == null) {
            cotacao.setDataAbertura(LocalDateTime.now());
        }
        cotacao.setHospitalId(hospital.getId());
        if (cotacao.getId() > 0) {
            return cotacaoDAO.atualizar(cotacao);
        }
        return cotacaoDAO.inserir(cotacao);
    }

    public List<Cotacao> listarPorHospital(Hospital hospital) {
        garantirHospitalLogado();
        return cotacaoDAO.listarPorHospital(hospital.getId());
    }

    public List<Cotacao> listarAbertas() {
        garantirFornecedorLogado();
        return cotacaoDAO.listarAbertas();
    }

    public void fecharCotacao(int cotacaoId) {
        garantirHospitalLogado();
        Cotacao atual = cotacaoDAO.buscarPorId(cotacaoId);
        if (atual == null) {
            throw new RuntimeException("Cotação não encontrada");
        }
        if (!Cotacao.STATUS_ABERTA.equalsIgnoreCase(atual.getStatus())) {
            throw new RuntimeException("Só é possível fechar cotações ABERTAS");
        }
        cotacaoDAO.fecharCotacao(cotacaoId);
    }

    private void garantirHospitalLogado() {
        Usuario usuario = SessionManager.getUsuarioLogado();
        if (usuario == null || !SessionManager.isHospital()) {
            throw new RuntimeException("Ação permitida apenas para hospital logado");
        }
    }

    private void garantirFornecedorLogado() {
        Usuario usuario = SessionManager.getUsuarioLogado();
        if (usuario == null || !SessionManager.isFornecedor()) {
            throw new RuntimeException("Ação permitida apenas para fornecedor logado");
        }
    }
}

