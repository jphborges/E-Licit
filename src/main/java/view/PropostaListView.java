package view;

import controller.PropostaController;
import controller.SessionManager;
import model.VO.Proposta;
import model.VO.Cotacao;
import model.VO.Fornecedor;
import model.VO.Hospital;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PropostaListView extends JFrame {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final PropostaController propostaController = new PropostaController();
    private Hospital hospital;
    private Cotacao cotacao;
    private Fornecedor fornecedor;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAceitar;
    private JButton btnRecusar;
    private List<Proposta> propostas = new ArrayList<>();

    public PropostaListView(Hospital hospital) {
        this.hospital = hospital;
        this.cotacao = null;
        if (!SessionManager.isHospital()) {
            JOptionPane.showMessageDialog(this, "Faça login como hospital.", "Sessão requerida", JOptionPane.WARNING_MESSAGE);
            new LoginView().setVisible(true);
            dispose();
            return;
        }
        initializeComponents();
        setupLayout();
        setupEvents();
        configureWindow();
    }

    public PropostaListView(Hospital hospital, Cotacao cotacao) {
        this.hospital = hospital;
        this.cotacao = cotacao;
        if (!SessionManager.isHospital()) {
            JOptionPane.showMessageDialog(this, "Faça login como hospital.", "Sessão requerida", JOptionPane.WARNING_MESSAGE);
            new LoginView().setVisible(true);
            dispose();
            return;
        }
        initializeComponents();
        setupLayout();
        setupEvents();
        loadPropostas();
        configureWindow();
    }

    public PropostaListView(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        if (!SessionManager.isFornecedor()) {
            JOptionPane.showMessageDialog(this, "Faça login como fornecedor.", "Sessão requerida", JOptionPane.WARNING_MESSAGE);
            new LoginView().setVisible(true);
            dispose();
            return;
        }
        initializeComponents();
        setupLayout();
        setupEvents();
        loadPropostas();
        configureWindow();
    }

    private void initializeComponents() {
        String[] columns = {"ID", "Cotação", "Valor Total", "Condições Pagamento", "Validade", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        btnAceitar = new JButton("Aceitar Proposta");
        btnRecusar = new JButton("Recusar Proposta");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));

        JLabel lblTitulo = new JLabel("Propostas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);

        topPanel.add(lblTitulo, BorderLayout.WEST);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(btnAceitar);
        buttonsPanel.add(btnRecusar);
        topPanel.add(buttonsPanel, BorderLayout.EAST);

        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupEvents() {
        btnAceitar.addActionListener(e -> aceitarProposta());
        btnRecusar.addActionListener(e -> recusarProposta());
    }

    private void loadPropostas(){
        try {
            if (cotacao != null) {
                propostas = propostaController.listarPorCotacao(cotacao.getId());
            } else if (fornecedor != null) {
                propostas = propostaController.listarPorFornecedor(fornecedor.getId());
                btnAceitar.setEnabled(false);
                btnRecusar.setEnabled(false);
            }
            tableModel.setRowCount(0);
            for (Proposta p : propostas) {
                tableModel.addRow(new Object[]{
                        p.getId(),
                        p.getCotacaoId(),
                        p.getValorTotal(),
                        p.getCondicoesPagamento(),
                        formatDate(p.getDataValidade()),
                        p.getStatus()
                });
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aceitarProposta(){
        if (cotacao == null) {
            JOptionPane.showMessageDialog(this, "Apenas o hospital pode aceitar propostas.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Proposta selecionada = getPropostaSelecionada();
        if (selecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma proposta.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            propostaController.aceitarProposta(cotacao, selecionada.getId());
            loadPropostas();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void recusarProposta(){
        if (cotacao == null) {
            JOptionPane.showMessageDialog(this, "Apenas o hospital pode recusar propostas.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Proposta selecionada = getPropostaSelecionada();
        if (selecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma proposta.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            propostaController.recusarProposta(selecionada.getId());
            loadPropostas();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Proposta getPropostaSelecionada() {
        int row = table.getSelectedRow();
        if (row < 0 || row >= propostas.size()) {
            return null;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        return propostas.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    private String formatDate(java.time.LocalDate date) {
        return date == null ? "" : date.format(DATE_FORMATTER);
    }

    private void configureWindow() {
        if (cotacao != null) {
            setTitle("Propostas - Cotação " + cotacao.getId());
        } else if (fornecedor != null) {
            setTitle("Minhas Propostas - " + fornecedor.getNome());
        } else {
            setTitle("Propostas");
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
    }
}
