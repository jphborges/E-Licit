package view;

import controller.SessionManager;
import model.VO.Fornecedor;
import javax.swing.*;
import java.awt.*;

public class FornecedorMainView extends JFrame {
    private Fornecedor fornecedor;
    private JMenuBar menuBar;
    private JMenu menuCotacoes;
    private JMenu menuPropostas;
    private JMenu menuPerfil;
    private JMenu menuSair;

    public FornecedorMainView(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        if (!SessionManager.isFornecedor()) {
            JOptionPane.showMessageDialog(this, "Faça login como fornecedor.", "Sessão requerida", JOptionPane.WARNING_MESSAGE);
            new LoginView().setVisible(true);
            dispose();
            return;
        }
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

        JMenuItem itemCotacoesAbertas = new JMenuItem("Ver Cotações Abertas");
        itemCotacoesAbertas.addActionListener(e -> abrirCotacoesAbertas());

        JMenuItem itemMinhasPropostas = new JMenuItem("Minhas Propostas");
        itemMinhasPropostas.addActionListener(e -> abrirMinhasPropostas());

        JMenuItem itemPerfil = new JMenuItem("Dados do Fornecedor");
        itemPerfil.addActionListener(e -> mostrarPerfil());

        JMenuItem itemLogout = new JMenuItem("Logout");
        itemLogout.addActionListener(e -> logout());

        menuCotacoes.add(itemCotacoesAbertas);
        menuPropostas.add(itemMinhasPropostas);
        menuPerfil.add(itemPerfil);
        menuSair.add(itemLogout);

        menuBar.add(menuCotacoes);
        menuBar.add(menuPropostas);
        menuBar.add(menuPerfil);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);
    }

    private void setupMenu() {
        JLabel lblBemVindo = new JLabel("Bem-vindo, " + fornecedor.getNome() + "!");
        lblBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
        lblBemVindo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblDescricao = new JLabel("Use o menu acima para visualizar cotações e gerenciar suas propostas.");
        lblDescricao.setHorizontalAlignment(SwingConstants.CENTER);
        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(lblBemVindo, BorderLayout.NORTH);
        panel.add(lblDescricao, BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);
    }

    private void abrirCotacoesAbertas() {
        CotacaoAbertaListView view = new CotacaoAbertaListView(fornecedor);
        view.setVisible(true);
    }

    private void abrirMinhasPropostas() {
        PropostaListView view = new PropostaListView(fornecedor);
        view.setVisible(true);
    }

    private void mostrarPerfil() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(fornecedor.getNome()).append("\n");
        sb.append("Email: ").append(fornecedor.getEmail()).append("\n");
        sb.append("CNPJ: ").append(fornecedor.getCnpj()).append("\n");
        sb.append("Catálogo: ").append(fornecedor.getCatalogoProdutos()).append("\n");

        JOptionPane.showMessageDialog(this, sb.toString(), "Dados do Fornecedor", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        dispose();
        SessionManager.encerrarSessao();
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
    }

    private void configureWindow() {
        setTitle("E-Licit - Painel do Fornecedor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
