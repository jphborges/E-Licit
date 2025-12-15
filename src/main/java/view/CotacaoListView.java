package view;

import controller.CotacaoController;
import controller.SessionManager;
import model.VO.Cotacao;
import model.VO.Hospital;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import view.PropostaListView;

public class CotacaoListView extends JFrame {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final CotacaoController cotacaoController = new CotacaoController();
    private Hospital hospital;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnNova;
    private JButton btnEditar;
    private JButton btnFechar;
    private JButton btnVerPropostas;
    private JComboBox<String> cmbFiltroStatus;
    private List<Cotacao> cotacoes = new ArrayList<>();

    public CotacaoListView(Hospital hospital) {
        this.hospital = hospital;
        if (!SessionManager.isHospital()) {
            JOptionPane.showMessageDialog(this, "Faça login como hospital.", "Sessão requerida", JOptionPane.WARNING_MESSAGE);
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
        String[] columns = {"ID", "Descrição", "Data Abertura", "Data Fechamento", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        btnNova = new JButton("Nova Cotação");
        btnEditar = new JButton("Editar");
        btnFechar = new JButton("Fechar Cotação");
        btnVerPropostas = new JButton("Ver Propostas");

        cmbFiltroStatus = new JComboBox<>(new String[]{
                "Todas",
                Cotacao.STATUS_ABERTA,
                Cotacao.STATUS_FECHADA
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));

        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroPanel.add(new JLabel("Filtrar por status:"));
        filtroPanel.add(cmbFiltroStatus);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(btnNova);
        buttonsPanel.add(btnEditar);
        buttonsPanel.add(btnFechar);
        buttonsPanel.add(btnVerPropostas);

        topPanel.add(filtroPanel, BorderLayout.WEST);
        topPanel.add(buttonsPanel, BorderLayout.EAST);

        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void setupEvents() {
        btnNova.addActionListener(e -> novaCotacao());
        btnEditar.addActionListener(e -> editarCotacao());
        btnFechar.addActionListener(e -> fecharCotacao());
        btnVerPropostas.addActionListener(e -> verPropostas());
        cmbFiltroStatus.addActionListener(e -> loadCotacoes());
    }

    private void loadCotacoes(){
        try {
            cotacoes = cotacaoController.listarPorHospital(hospital);
            String filtro = (String) cmbFiltroStatus.getSelectedItem();
            tableModel.setRowCount(0);
            for (Cotacao c : cotacoes) {
                if (!"Todas".equalsIgnoreCase(filtro) && !c.getStatus().equalsIgnoreCase(filtro)) {
                    continue;
                }
                tableModel.addRow(new Object[]{
                        c.getId(),
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

    private void novaCotacao(){
        CotacaoFormView view = new CotacaoFormView(hospital, null);
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loadCotacoes();
            }
        });
        view.setVisible(true);
    }

    private void editarCotacao(){
        Cotacao selecionada = getCotacaoSelecionada();
        if (selecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma cotação.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        CotacaoFormView view = new CotacaoFormView(hospital, selecionada);
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loadCotacoes();
            }
        });
        view.setVisible(true);
    }

    private void fecharCotacao(){
        Cotacao selecionada = getCotacaoSelecionada();
        if (selecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma cotação.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja fechar a cotação selecionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            cotacaoController.fecharCotacao(selecionada.getId());
            loadCotacoes();
        }
    }

    private void verPropostas(){
        Cotacao selecionada = getCotacaoSelecionada();
        if (selecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma cotação.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        PropostaListView view = new PropostaListView(hospital, selecionada);
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
        setTitle("Gerenciar Cotações - " + hospital.getNome());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
    }
}
