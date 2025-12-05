package view;

import model.VO.Cotacao;
import model.VO.Fornecedor;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CotacaoAbertaListView extends JFrame {
    private Fornecedor fornecedor;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnCriarProposta;

    public CotacaoAbertaListView(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
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
    }

    private void criarProposta(){
    }

    private void configureWindow() {
        setTitle("Cotações Abertas - " + fornecedor.getNome());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
    }
}
