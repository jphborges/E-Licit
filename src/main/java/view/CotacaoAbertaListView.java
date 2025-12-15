package view;

import controller.CotacaoController;
import controller.SessionManager;
import model.VO.Cotacao;
import model.VO.Fornecedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CotacaoAbertaListView extends JFrame {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final CotacaoController cotacaoController = new CotacaoController();
    private Fornecedor fornecedor;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnCriarProposta;
    private List<Cotacao> cotacoes = new ArrayList<>();

    public CotacaoAbertaListView(Fornecedor fornecedor) {
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
        loadCotacoes();
        configureWindow();
    }

    private void initializeComponents() {
        String[] columns = {"ID", "Hospital", "Descrição", "Data Abertura", "Data Fechamento", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        btnCriarProposta = new JButton("Criar Proposta");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));

        JLabel lblTitulo = new JLabel("Cotações Abertas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);

        topPanel.add(lblTitulo, BorderLayout.WEST);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(btnCriarProposta);
        topPanel.add(buttonsPanel, BorderLayout.EAST);

        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupEvents() {
        btnCriarProposta.addActionListener(e -> criarProposta());
    }

    private void loadCotacoes(){
        try {
            cotacoes = cotacaoController.listarAbertas();
            tableModel.setRowCount(0);
            for (Cotacao c : cotacoes) {
                tableModel.addRow(new Object[]{
                        c.getId(),
                        c.getHospitalId(),
                        c.getDescricao(),
                        formatDateTime(c.getDataAbertura()),
                        formatDateTime(c.getDataFechamento()),
                        c.getStatus()
                });
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void criarProposta(){
        Cotacao selecionada = getCotacaoSelecionada();
        if (selecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma cotação aberta.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        PropostaFormView view = new PropostaFormView(fornecedor, selecionada, null);
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loadCotacoes();
            }
        });
        view.setVisible(true);
    }

    private Cotacao getCotacaoSelecionada() {
        int row = table.getSelectedRow();
        if (row < 0 || row >= cotacoes.size()) {
            return null;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        return cotacoes.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    private String formatDateTime(java.time.LocalDateTime dateTime) {
        return dateTime == null ? "" : dateTime.format(DATE_TIME_FORMATTER);
    }

    private void configureWindow() {
        setTitle("Cotações Abertas - " + fornecedor.getNome());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
    }
}
