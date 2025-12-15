package model.RN;

import controller.SessionManager;
import model.dao.CotacaoDAO;
import model.dao.PropostaDAO;
import model.VO.Cotacao;
import model.VO.Proposta;
import model.VO.Usuario;
import model.dao.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PropostaRN {
    private final PropostaDAO propostaDAO;
    private final CotacaoDAO cotacaoDAO;

    public PropostaRN() {
        this.propostaDAO = new PropostaDAO();
        this.cotacaoDAO = new CotacaoDAO();
    }

    public Proposta enviarProposta(Cotacao cotacao, int fornecedorId, Proposta proposta) {
        garantirFornecedorLogado();
        if (cotacao == null || proposta == null) {
            throw new RuntimeException("Dados da proposta inválidos");
        }
        Cotacao atual = cotacaoDAO.buscarPorId(cotacao.getId());
        if (atual == null) {
            throw new RuntimeException("Cotação não encontrada");
        }
        if (!Cotacao.STATUS_ABERTA.equalsIgnoreCase(atual.getStatus())) {
            throw new RuntimeException("Só é possível propor em cotações ABERTAS");
        }
        if (proposta.getValorTotal() <= 0) {
            throw new RuntimeException("Valor total deve ser maior que zero");
        }
        if (propostaDAO.existePropostaDoFornecedor(cotacao.getId(), fornecedorId)) {
            throw new RuntimeException("Você já enviou proposta para esta cotação");
        }
        proposta.setFornecedorId(fornecedorId);
        proposta.setCotacaoId(cotacao.getId());
        proposta.setStatus(Proposta.STATUS_ENVIADA);
        return propostaDAO.inserir(proposta);
    }

    public List<Proposta> listarPorCotacao(int cotacaoId) {
        garantirHospitalLogado();
        return propostaDAO.listarPorCotacao(cotacaoId);
    }

    public List<Proposta> listarPorFornecedor(int fornecedorId) {
        garantirFornecedorLogado();
        return propostaDAO.listarPorFornecedor(fornecedorId);
    }

    public void aceitarProposta(Cotacao cotacao, int propostaId) {
        garantirHospitalLogado();
        Cotacao atual = cotacaoDAO.buscarPorId(cotacao.getId());
        if (atual == null) {
            throw new RuntimeException("Cotação não encontrada");
        }
        if (!Cotacao.STATUS_ABERTA.equalsIgnoreCase(atual.getStatus())) {
            throw new RuntimeException("Só é possível aceitar propostas de cotação ABERTA");
        }
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            propostaDAO.atualizarStatus(propostaId, Proposta.STATUS_ACEITA, conn);
            propostaDAO.recusarOutrasPropostas(atual.getId(), propostaId, conn);
            cotacaoDAO.fecharCotacao(atual.getId(), conn);
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ignore) { }
            }
            throw new RuntimeException("Erro ao aceitar proposta: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) { }
            }
        }
    }

    public void recusarProposta(int propostaId) {
        garantirHospitalLogado();
        propostaDAO.atualizarStatus(propostaId, Proposta.STATUS_RECUSADA);
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

