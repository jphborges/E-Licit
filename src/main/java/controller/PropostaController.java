package controller;

import model.RN.PropostaRN;
import model.VO.Cotacao;
import model.VO.Proposta;

import java.util.List;

public class PropostaController {
    private final PropostaRN propostaService;

    public PropostaController() {
        this.propostaService = new PropostaRN();
    }

    public Proposta enviarProposta(Cotacao cotacao, Proposta proposta, int fornecedorId) {
        return propostaService.enviarProposta(cotacao, fornecedorId, proposta);
    }

    public List<Proposta> listarPorCotacao(int cotacaoId) {
        return propostaService.listarPorCotacao(cotacaoId);
    }

    public List<Proposta> listarPorFornecedor(int fornecedorId) {
        return propostaService.listarPorFornecedor(fornecedorId);
    }

    public void aceitarProposta(Cotacao cotacao, int propostaId) {
        propostaService.aceitarProposta(cotacao, propostaId);
    }

    public void recusarProposta(int propostaId) {
        propostaService.recusarProposta(propostaId);
    }
}


