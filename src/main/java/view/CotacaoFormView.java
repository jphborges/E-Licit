package view;


import model.VO.Cotacao;
import model.VO.Hospital;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CotacaoFormView extends JFrame {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private Hospital hospital;
    private Cotacao cotacao;
    private JTextArea txtDescricao;
    private JTextField txtDataAbertura;
    private JTextField txtDataFechamento;
    private JComboBox<String> cmbStatus;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public CotacaoFormView(Hospital hospital, Cotacao cotacao) {
        this.hospital = hospital;
        this.cotacao = cotacao;
        initializeComponents();
        setupLayout();
        setupEvents();
        loadData();
        configureWindow();
    }

    private void initializeComponents() {
        txtDescricao = new JTextArea(5, 30);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);

        txtDataAbertura = new JTextField(20);
        txtDataFechamento = new JTextField(20);

        cmbStatus = new JComboBox<>(new String[]{
                Cotacao.STATUS_ABERTA,
                Cotacao.STATUS_FECHADA
        });

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
        gbc.anchor = GridBagConstraints.NORTHEAST;
        formPanel.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(new JScrollPane(txtDescricao), gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Data Abertura:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtDataAbertura, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Data Fechamento:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtDataFechamento, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(cmbStatus, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(btnSalvar);
        buttonsPanel.add(btnCancelar);

        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void setupEvents() {
        btnSalvar.addActionListener(e -> salvarCotacao());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void loadData() {
        if (cotacao != null) {
            txtDescricao.setText(cotacao.getDescricao());
            txtDataAbertura.setText(formatDateTime(cotacao.getDataAbertura()));
            txtDataFechamento.setText(formatDateTime(cotacao.getDataFechamento()));
            cmbStatus.setSelectedItem(cotacao.getStatus());
        } else {
            LocalDateTime now = LocalDateTime.now();
            txtDataAbertura.setText(formatDateTime(now));
            txtDataFechamento.setText("");
            cmbStatus.setSelectedItem(Cotacao.STATUS_ABERTA);
        }
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime == null ? "" : dateTime.format(DATE_TIME_FORMATTER);
    }

    private void salvarCotacao(){
    }

    private void configureWindow() {
        setTitle(cotacao == null ? "Nova Cotação" : "Editar Cotação");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}

