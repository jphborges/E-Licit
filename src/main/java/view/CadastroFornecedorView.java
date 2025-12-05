package view;

import model.VO.Fornecedor;
import javax.swing.*;
import java.awt.*;

public class CadastroFornecedorView extends JFrame {
    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JTextField txtCnpj;
    private JTextArea txtCatalogo;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public CadastroFornecedorView() {
        initializeComponents();
        setupLayout();
        setupEvents();
        configureWindow();
    }

    private void initializeComponents() {
        txtNome = new JTextField(25);
        txtEmail = new JTextField(25);
        txtSenha = new JPasswordField(25);
        txtCnpj = new JTextField(25);
        txtCatalogo = new JTextArea(5, 25);
        txtCatalogo.setLineWrap(true);
        txtCatalogo.setWrapStyleWord(true);

        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        mainPanel.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        mainPanel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        mainPanel.add(txtSenha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("CNPJ:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        mainPanel.add(txtCnpj, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        mainPanel.add(new JLabel("Catálogo:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(new JScrollPane(txtCatalogo), gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(btnSalvar);
        buttonsPanel.add(btnCancelar);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void setupEvents() {
        btnSalvar.addActionListener(e -> salvarFornecedor());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void salvarFornecedor(){
    }

    private void configureWindow() {
        setTitle("Cadastro de Fornecedor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }
}
