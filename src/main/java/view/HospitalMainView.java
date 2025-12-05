package view;

import model.VO.Hospital;
import javax.swing.*;
import java.awt.*;

public class HospitalMainView extends JFrame {
    private Hospital hospital;
    private JMenuBar menuBar;
    private JMenu menuCotacoes;
    private JMenu menuPropostas;
    private JMenu menuPerfil;
    private JMenu menuSair;

    public HospitalMainView(Hospital hospital) {
        this.hospital = hospital;
        initializeComponents();
        setupMenu();
        configureWindow();
    }

    private void initializeComponents() {
        menuBar = new JMenuBar();
        menuCotacoes = new JMenu("Cotações");
        menuPropostas = new JMenu("Propostas");
        menuPerfil = new JMenu("Perfil");
        menuSair = new JMenu("Sair");

        JMenuItem itemMinhasCotacoes = new JMenuItem("Minhas Cotações");
        itemMinhasCotacoes.addActionListener(e -> abrirMinhasCotacoes());

        JMenuItem itemVerPropostas = new JMenuItem("Ver Propostas por Cotação");
        itemVerPropostas.addActionListener(e -> abrirPropostasPorCotacao());

        JMenuItem itemPerfil = new JMenuItem("Dados do Hospital");
        itemPerfil.addActionListener(e -> mostrarPerfil());

        JMenuItem itemLogout = new JMenuItem("Logout");
        itemLogout.addActionListener(e -> logout());

        menuCotacoes.add(itemMinhasCotacoes);
        menuPropostas.add(itemVerPropostas);
        menuPerfil.add(itemPerfil);
        menuSair.add(itemLogout);

        menuBar.add(menuCotacoes);
        menuBar.add(menuPropostas);
        menuBar.add(menuPerfil);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);
    }

    private void setupMenu() {
        JLabel lblBemVindo = new JLabel("Bem-vindo, " + hospital.getNome() + "!");
        lblBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
        lblBemVindo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblDescricao = new JLabel("Use o menu acima para gerenciar suas cotações e propostas.");
        lblDescricao.setHorizontalAlignment(SwingConstants.CENTER);
        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(lblBemVindo, BorderLayout.NORTH);
        panel.add(lblDescricao, BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);
    }

    private void abrirMinhasCotacoes() {
        CotacaoListView view = new CotacaoListView(hospital);
        view.setVisible(true);
    }

    private void abrirPropostasPorCotacao() {
        JOptionPane.showMessageDialog(this,
                "Funcionalidade de visualizar propostas por cotação em desenvolvimento.",
                "Em Desenvolvimento",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarPerfil() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(hospital.getNome()).append("\n");
        sb.append("Email: ").append(hospital.getEmail()).append("\n");
        sb.append("CNPJ: ").append(hospital.getCnpj()).append("\n");
        sb.append("Endereço: ").append(hospital.getEndereco()).append("\n");

        JOptionPane.showMessageDialog(this, sb.toString(), "Dados do Hospital", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        dispose();
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
    }

    private void configureWindow() {
        setTitle("E-Licit - Painel do Hospital");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
