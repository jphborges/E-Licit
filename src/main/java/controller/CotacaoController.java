package controller;

import model.RN.CotacaoRN;
import model.VO.Cotacao;
import model.VO.Hospital;

import java.util.List;

public class CotacaoController {
    private final CotacaoRN cotacaoService;

    public CotacaoController() {
        this.cotacaoService = new CotacaoRN();
    }

    public Cotacao salvar(Cotacao cotacao) {
        Hospital hospital = (Hospital) SessionManager.getUsuarioLogado();
        return cotacaoService.salvar(hospital, cotacao);
    }

    public List<Cotacao> listarPorHospital(Hospital hospital) {
        return cotacaoService.listarPorHospital(hospital);
    }

    public List<Cotacao> listarAbertas() {
        return cotacaoService.listarAbertas();
    }

    public void fecharCotacao(int cotacaoId) {
        cotacaoService.fecharCotacao(cotacaoId);
    }
}


