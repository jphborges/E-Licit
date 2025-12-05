package view;

import model.VO.Cotacao;
import model.VO.Hospital;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CotacaoListView extends JFrame {
    private Hospital hospital;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnNova;
    private JButton btnEditar;
    private JButton btnFechar;
    private JButton btnVerPropostas;
    private JComboBox<String> cmbFiltroStatus;

    public CotacaoListView(Hospital hospital) {
        this.hospital = hospital;
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
    }

    private void novaCotacao(){
    }

    private void editarCotacao(){
    }

    private void fecharCotacao(){
    }

    private void verPropostas(){
    }

    private void configureWindow() {
        setTitle("Gerenciar Cotações - " + hospital.getNome());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
    }
}
