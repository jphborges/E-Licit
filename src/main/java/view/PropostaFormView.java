package view;

import model.VO.Proposta;
import model.VO.Cotacao;
import model.VO.Fornecedor;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class PropostaFormView extends JFrame {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Fornecedor fornecedor;
    private Cotacao cotacao;
    private Proposta proposta;
    private JFormattedTextField txtValorTotal;
    private JTextArea txtCondicoesPagamento;
    private JTextField txtValidade;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public PropostaFormView(Fornecedor fornecedor, Cotacao cotacao, Proposta proposta) {
        this.fornecedor = fornecedor;
        this.cotacao = cotacao;
        this.proposta = proposta;
        initializeComponents();
        setupLayout();
        setupEvents();
        loadData();
        configureWindow();
    }

    private void initializeComponents() {
        txtValorTotal = new JFormattedTextField(java.text.NumberFormat.getNumberInstance());
        txtValorTotal.setColumns(20);
        txtCondicoesPagamento = new JTextArea(5, 30);
        txtCondicoesPagamento.setLineWrap(true);
        txtCondicoesPagamento.setWrapStyleWord(true);
        txtValidade = new JTextField(20);

        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Valor Total:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        formPanel.add(txtValorTotal, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        formPanel.add(new JLabel("Condições de Pagamento:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(new JScrollPane(txtCondicoesPagamento), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Validade (dd/MM/yyyy):"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        formPanel.add(txtValidade, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(btnSalvar);
        buttonsPanel.add(btnCancelar);

        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void setupEvents() {
        btnSalvar.addActionListener(e -> salvarProposta());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void loadData() {
        if (proposta != null) {
            txtValorTotal.setValue(proposta.getValorTotal());
            txtCondicoesPagamento.setText(proposta.getCondicoesPagamento());
            if (proposta.getDataValidade() != null) {
                txtValidade.setText(proposta.getDataValidade().format(DATE_FORMATTER));
            } else {
                txtValidade.setText("");
            }
        } else {
            txtValorTotal.setValue(0);
            txtCondicoesPagamento.setText("");
            txtValidade.setText("");
        }
    }

    private void salvarProposta(){
    }

    private void configureWindow() {
        setTitle(proposta == null ? "Nova Proposta" : "Editar Proposta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
