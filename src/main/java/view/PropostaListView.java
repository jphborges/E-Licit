package view;

import model.VO.Proposta;
import model.VO.Cotacao;
import model.VO.Hospital;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class PropostaListView extends JFrame {
    private Hospital hospital;
    private Cotacao cotacao;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnAceitar;
    private JButton btnRecusar;

    public PropostaListView(Hospital hospital) {
        this.hospital = hospital;
        this.cotacao = null;
        initializeComponents();
        setupLayout();
        setupEvents();
        configureWindow();
    }

    public PropostaListView(Hospital hospital, Cotacao cotacao) {
        this.hospital = hospital;
        this.cotacao = cotacao;
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
    }

    private void aceitarProposta(){
    }

    private void recusarProposta(){
    }

    private void configureWindow() {
        setTitle("Propostas" + (cotacao != null ? " - Cotação " + cotacao.getId() : ""));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
    }
}
