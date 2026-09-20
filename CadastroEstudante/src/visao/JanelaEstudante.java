package visao;

import java.awt.EventQueue;

import java.sql.SQLException;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.table.DefaultTableModel;

import dao.EstudanteDAO;
import modelo.Estudante;

public class JanelaEstudante extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtNome;
    private JTextField txtCurso;
    private JTextField txtNota;
    private JTextField txtBusca;
    private JTable tabela;
    private JLabel lblStatus;
    private DefaultTableModel modelo;
    // Ponte com o banco: um unico objeto serve a janela inteira.
    private final EstudanteDAO dao = new EstudanteDAO();
    // Id do estudante selecionado na tabela. Zero = nenhum selecionado.
    private int idSelecionado = 0;

    public static void main(String[] args) {

        EventQueue.invokeLater(
            new Runnable() {

                public void run() {

                    try {

                        JanelaEstudante frame =
                            new JanelaEstudante();

                        frame.setVisible(true);

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }
        );
    }

    public JanelaEstudante() {

        setTitle("Cadastro de Estudantes" );

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setBounds( 100, 100,700, 500);
        getContentPane().setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds( 20, 20, 80, 25);
        getContentPane().add(lblNome);

        txtNome =new JTextField();
        txtNome.setBounds(100,20, 220,25);
        getContentPane().add(txtNome);
        
        JLabel lblCurso = new JLabel("Curso:");
        lblCurso.setBounds( 20,55,80,25 );
        getContentPane().add(lblCurso);
        
        txtCurso =new JTextField();
        txtCurso.setBounds( 100, 55,220,25);

        getContentPane().add(txtCurso);
        JLabel lblNota =new JLabel("Nota:");
        lblNota.setBounds( 20,90, 80, 25);
        getContentPane().add(lblNota);

        txtNota = new JTextField();
        txtNota.setBounds( 100,90,100,25 );
        getContentPane().add(txtNota);
        
        JLabel lblBusca = new JLabel("Buscar:");
        lblBusca.setBounds(350,20,80,25 );
        getContentPane().add(lblBusca);
        txtBusca = new JTextField();
        txtBusca.setBounds(420,20,150,25 );
        getContentPane().add(txtBusca);
        
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds( 20,130, 100,30  );
        getContentPane().add(btnCadastrar);

        JButton btnAlterar =new JButton("Alterar");
        btnAlterar.setBounds(130, 130,100,30);
        getContentPane().add(btnAlterar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds( 240,130,100, 30 );
        getContentPane().add(btnExcluir);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(350,130, 100,30 );
        getContentPane().add(btnLimpar);

        JButton btnListar =new JButton("Listar todos");
        btnListar.setBounds( 460, 130, 110,30 );
        getContentPane().add(btnListar);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds( 580,20,80,25);
        getContentPane().add(btnBuscar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 180, 640,220 );
        getContentPane().add(scrollPane);

        tabela = new JTable();
        scrollPane.setViewportView(tabela );

        lblStatus =new JLabel("Pronto.");
        lblStatus.setBounds(20,420, 600,25 );
        getContentPane().add(lblStatus);

        modelo = new DefaultTableModel(new String[] { "ID", "Nome","Curso", "Nota" }, 0 );
        tabela.setModel(modelo);

        tabela.setRowHeight(22);
     // Impede a edicao direta na celula: alterar passa pelo formulario.
        tabela.setDefaultEditor( Object.class,null );
        btnCadastrar.addActionListener( e -> cadastrar());
        btnLimpar.addActionListener( e -> limpar());
        btnListar.addActionListener(
           
        		e -> listar()// a tabela ja abre preenchida
        );

        btnBuscar.addActionListener(
            e -> buscar()
        );

        btnAlterar.addActionListener(
            e -> alterar()
        );

        btnExcluir.addActionListener(
            e -> excluir()
        );

        tabela.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) {
                            carregarSelecionado();
                        }
                    }
                }
            );

        listar();
    }

    private void listar() {

        try {
        	preencherTabela(dao.listar()
            );
        } catch (SQLException ex) {

            erro("Erro ao listar",ex
            );
        }
    }

    private void buscar() {

        try {

            preencherTabela(dao.buscarPorNome(txtBusca.getText().trim()
                )
            );

        } catch (SQLException ex) {
            erro(
                "Erro ao buscar", ex );
        }
    }

    private void preencherTabela(
            List<Estudante> lista) {
    		modelo.setRowCount(0); // SEM ISTO A TABELA DUPLICA


        for (Estudante e : lista) {
            modelo.addRow(
                new Object[] {
                    e.getId(),
                    e.getNome(),
                    e.getCurso(),
                    e.getNota()
                }
            );
        }

        lblStatus.setText(
            lista.size() + " estudante(s) na tabela."
        );
    }

    private Estudante lerFormulario() {

        String nome = txtNome.getText().trim();
        String curso = txtCurso.getText().trim();
        String textoNota = txtNota.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome.","Aviso", JOptionPane.WARNING_MESSAGE );
            txtNome.requestFocus();
            return null;
        }

        if (curso.isEmpty()) {
            JOptionPane.showMessageDialog( this,"Informe o curso.","Aviso",JOptionPane.WARNING_MESSAGE );
            txtCurso.requestFocus();
            return null;
        }

        if (textoNota.isEmpty()) {

            JOptionPane.showMessageDialog( this,  "Informe a nota.", "Aviso",JOptionPane.WARNING_MESSAGE );
            txtNota.requestFocus();
            return null;
        }

        double nota;
        try {
            nota = Double.parseDouble(textoNota.replace(",", "." ));
        } catch (
            NumberFormatException ex) {

            JOptionPane.showMessageDialog(this, "Nota deve ser um numero!", "Aviso",JOptionPane.WARNING_MESSAGE );
            txtNota.requestFocus();
            return null;
        }

        if (nota < 0 || nota > 10) {
            JOptionPane.showMessageDialog( this, "A nota deve estar entre 0 e 10.",  "Aviso", JOptionPane.WARNING_MESSAGE );
            txtNota.requestFocus();
            return null;
        }

        return new Estudante(nome, curso, nota
        );
    }

    private void cadastrar() {
        Estudante e =lerFormulario();
        if (e == null) {
            return;  // invalido: a mensagem ja apareceu
        }
        try {
            dao.inserir(e);
            JOptionPane.showMessageDialog( this,"Estudante cadastrado com o id " + e.getId() + "." );
            	limpar();
            	listar();

        } catch (SQLException ex) {
            erro(
                "Erro ao cadastrar",
                ex
            );
        }
    }

    private void limpar() {

        idSelecionado = 0;
        txtNome.setText("");
        txtCurso.setText("");
        txtNota.setText("");
        tabela.clearSelection();
        txtNome.requestFocus();
        lblStatus.setText(
            "Formulario limpo."
        );
    }

    private void erro(
            String contexto,
            SQLException ex) {

        JOptionPane.showMessageDialog(
            this,
            contexto + ": " +
            ex.getMessage(),
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );

        lblStatus.setText(   contexto + "." );
    }

    private void carregarSelecionado() {

        int linha = tabela.getSelectedRow();
        if (linha < 0) { // -1 = nenhuma linha selecionada
            return;
        }

        idSelecionado =
            (int) modelo.getValueAt( linha, 0  );

        txtNome.setText( String.valueOf(  modelo.getValueAt(  linha,  1 )));
        txtCurso.setText( String.valueOf( modelo.getValueAt( linha, 2)));
        txtNota.setText( String.valueOf(modelo.getValueAt(linha, 3 )));
        lblStatus.setText("Editando o estudante de id " +  idSelecionado + ". Altere os campos e clique em Alterar." );
    }

    private void alterar() {

        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog( this, "Selecione primeiro uma linha da tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Estudante e = lerFormulario();
        if (e == null) {
            return;
        }
     // O id vem da SELECAO, nao do que esta digitado.
        e.setId( idSelecionado );
        try {
            dao.alterar(e);
            JOptionPane.showMessageDialog( this, "Estudante alterado." );
		            limpar();
		            listar();

        } catch (SQLException ex) {
            erro(
                "Erro ao alterar",
                ex
            );
        }
    }

    private void excluir() {
        if (idSelecionado == 0) { JOptionPane.showMessageDialog(  this,"Selecione primeiro uma linha da tabela.", "Aviso", JOptionPane.WARNING_MESSAGE );
            return;
        }

        int opcao =
            JOptionPane.showConfirmDialog(  this,"Excluir o estudante " +  txtNome.getText() +  "?",  "Confirmacao",JOptionPane.YES_NO_OPTION );
        if (opcao !=JOptionPane.YES_OPTION) {
            return;
        }

        try {
            dao.excluir( idSelecionado  );
            JOptionPane.showMessageDialog( this, "Estudante excluido." );
		            limpar();
		            listar();

        } catch (SQLException ex) {

            erro(
                "Erro ao excluir",
                ex
            );
        }
    }
}