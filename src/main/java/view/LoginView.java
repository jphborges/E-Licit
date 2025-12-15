package view;

import controller.AuthController;
import model.VO.Fornecedor;
import model.VO.Hospital;
import model.VO.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private final AuthController authController = new AuthController();
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private JButton btnCadastrarHospital;
    private JButton btnCadastrarFornecedor;

    public LoginView() {
        initializeComponents();
        setupLayout();
        setupEvents();
        configureWindow();
    }

    private void initializeComponents() {
        txtEmail = new JTextField(20);
        txtSenha = new JPasswordField(20);
        btnLogin = new JButton("Entrar");
        btnCadastrarHospital = new JButton("Cadastrar Hospital");
        btnCadastrarFornecedor = new JButton("Cadastrar Fornecedor");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitulo = new JLabel("E-Licit");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblSubtitulo = new JLabel("Plataforma de Cotações para Hospitais e Fornecedores");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(lblTitulo, gbc);

        gbc.gridy = 1;
        mainPanel.add(lblSubtitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtEmail, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtSenha, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonsPanel.add(btnLogin);

        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        registerPanel.add(btnCadastrarHospital);
        registerPanel.add(btnCadastrarFornecedor);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonsPanel, gbc);

        gbc.gridy = 5;
        mainPanel.add(registerPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupEvents() {
        // Evento do botão Login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });

        // Evento Enter no campo de senha
        txtSenha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });

        btnCadastrarHospital.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCadastroHospital();
            }
        });

        btnCadastrarFornecedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCadastroFornecedor();
            }
        });
    }

    private void realizarLogin(){
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());
        try {
            Usuario usuario = authController.login(email, senha);
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            if (usuario instanceof Hospital) {
                HospitalMainView view = new HospitalMainView((Hospital) usuario);
                view.setVisible(true);
            } else if (usuario instanceof Fornecedor) {
                FornecedorMainView view = new FornecedorMainView((Fornecedor) usuario);
                view.setVisible(true);
            }
            dispose();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCadastroHospital() {
        CadastroHospitalView view = new CadastroHospitalView();
        view.setVisible(true);
    }

    private void abrirCadastroFornecedor() {
        CadastroFornecedorView view = new CadastroFornecedorView();
        view.setVisible(true);
    }

    private void configureWindow() {
        setTitle("E-Licit - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 350));
        setResizable(false);
        pack();
        setLocationRelativeTo(null); // Centraliza a janela
    }
}
