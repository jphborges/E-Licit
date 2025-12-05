package org.edu.utfpr.elicit;

import model.VO.*;
import view.*;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::launchScreens);
    }

    private static void launchScreens() {
        Hospital hospital = new Hospital(
                1,
                "Hospital Demo",
                "hospital@demo.com",
                "senha123",
                "11.111.111/0001-11",
                "Av. Central, 100"
        );

        Fornecedor fornecedor = new Fornecedor(
                1,
                "Fornecedor Demo",
                "fornecedor@demo.com",
                "senha123",
                "22.222.222/0001-22",
                "Catálogo geral"
        );

        Cotacao cotacao = new Cotacao(
                1,
                "Compra de Equipamentos",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(7),
                Cotacao.STATUS_ABERTA,
                hospital.getId()
        );

        Proposta proposta = new Proposta(
                1,
                15000.00,
                "Pagamento em 30 dias",
                LocalDate.now().plusDays(15),
                Proposta.STATUS_ENVIADA,
                cotacao.getId(),
                fornecedor.getId()
        );

        new LoginView().setVisible(true);
        new HospitalMainView(hospital).setVisible(true);
        new FornecedorMainView(fornecedor).setVisible(true);
        new CotacaoListView(hospital).setVisible(true);
        new CotacaoFormView(hospital, cotacao).setVisible(true);
        new CotacaoAbertaListView(fornecedor).setVisible(true);
        new PropostaListView(hospital, cotacao).setVisible(true);
        new PropostaFormView(fornecedor, cotacao, proposta).setVisible(true);
        new CadastroHospitalView().setVisible(true);
        new CadastroFornecedorView().setVisible(true);
    }
}