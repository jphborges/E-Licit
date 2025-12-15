package view;

import controller.AuthController;
import model.VO.Hospital;

import javax.swing.*;
import java.awt.*;

public class CadastroHospitalView extends JFrame {
    private final AuthController authController = new AuthController();
    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JTextField txtCnpj;
    private JTextField txtEndereco;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public CadastroHospitalView() {
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
        txtEndereco = new JTextField(25);
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
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Endereço:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        mainPanel.add(txtEndereco, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(btnSalvar);
        buttonsPanel.add(btnCancelar);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void setupEvents() {
        btnSalvar.addActionListener(e -> salvarHospital());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void salvarHospital(){
        String nome = txtNome.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());
        String cnpj = txtCnpj.getText().trim();
        String endereco = txtEndereco.getText().trim();

        Hospital hospital = new Hospital();
        hospital.setNome(nome);
        hospital.setEmail(email);
        hospital.setSenha(senha);
        hospital.setCnpj(cnpj);
        hospital.setEndereco(endereco);

        try {
            authController.registrarHospital(hospital);
            JOptionPane.showMessageDialog(this, "Hospital cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configureWindow() {
        setTitle("Cadastro de Hospital");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }
}

