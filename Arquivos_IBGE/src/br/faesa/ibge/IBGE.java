package br.faesa.ibge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

public class IBGE implements WindowListener, ListSelectionListener, ActionListener{

	private JFrame frame;
	private JTextField tfdSigla;
	private JTextField tfdCdEstado;
	private JTextField tfdCodMunic;
	private JTextField tfdNomeMunic;
	private JTextField tfdPopulacao;
	private JTable table;
	private JButton btnPrimeiro;
	private JButton btnAnterior;
	private JButton btnProximo;
	private JButton btnOrdenarTabela;
	private JButton btnUltimo;
	private TableModel tableModel;
	private int linhaSel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IBGE window = new IBGE();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IBGE() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(new Dimension(450, 470));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(this);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);
		
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Munic\u00EDpio", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		topPanel.setBounds(0, 0, 434, 170);
		mainPanel.add(topPanel);
		topPanel.setLayout(null);
		
		JLabel lblSigla = new JLabel("Sigla:");
		lblSigla.setBounds(10, 20, 86, 14);
		topPanel.add(lblSigla);
		
		JLabel lblCdEstado = new JLabel("C\u00F3d. Estado:");
		lblCdEstado.setBounds(10, 50, 86, 14);
		topPanel.add(lblCdEstado);
		
		JLabel lblCdMunic = new JLabel("C\u00F3d. Munic.:");
		lblCdMunic.setBounds(10, 80, 86, 14);
		topPanel.add(lblCdMunic);
		
		JLabel lblNomeMunic = new JLabel("Nome Munic.:");
		lblNomeMunic.setBounds(10, 110, 86, 14);
		topPanel.add(lblNomeMunic);
		
		JLabel lblPopulacao = new JLabel("Popula\u00E7\u00E3o:");
		lblPopulacao.setBounds(10, 143, 86, 14);
		topPanel.add(lblPopulacao);
		
		tfdSigla = new JTextField();
		tfdSigla.setBounds(117, 17, 307, 20);
		topPanel.add(tfdSigla);
		tfdSigla.setColumns(10);
		
		tfdCdEstado = new JTextField();
		tfdCdEstado.setBounds(117, 47, 307, 20);
		topPanel.add(tfdCdEstado);
		tfdCdEstado.setColumns(10);
		
		tfdCodMunic = new JTextField();
		tfdCodMunic.setBounds(117, 77, 307, 20);
		topPanel.add(tfdCodMunic);
		tfdCodMunic.setColumns(10);
		
		tfdNomeMunic = new JTextField();
		tfdNomeMunic.setBounds(117, 107, 307, 20);
		topPanel.add(tfdNomeMunic);
		tfdNomeMunic.setColumns(10);
		
		tfdPopulacao = new JTextField();
		tfdPopulacao.setBounds(117, 140, 307, 20);
		topPanel.add(tfdPopulacao);
		tfdPopulacao.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel.setBounds(0, 171, 434, 40);
		mainPanel.add(panel);
		panel.setLayout(null);
		
		btnPrimeiro = new JButton("<<");
		btnPrimeiro.setBounds(112, 8, 49, 23);
		btnPrimeiro.addActionListener(this);
		panel.add(btnPrimeiro);
		
		btnAnterior = new JButton("<");
		btnAnterior.setBounds(171, 8, 49, 23);
		btnAnterior.addActionListener(this);
		panel.add(btnAnterior);
		
		btnProximo = new JButton(">");
		btnProximo.setBounds(230, 8, 49, 23);
		btnProximo.addActionListener(this);
		panel.add(btnProximo);
		
		btnUltimo = new JButton(">>");
		btnUltimo.setBounds(288, 8, 49, 23);
		btnUltimo.addActionListener(this);
		panel.add(btnUltimo);
		
		btnOrdenarTabela = new JButton("\u2193");
		btnOrdenarTabela.setBounds(375, 8, 49, 23);
		btnOrdenarTabela.addActionListener(this);
		panel.add(btnOrdenarTabela);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 210, 434, 221);
		mainPanel.add(scrollPane);

		tableModel = new TableModel();
		table = new JTable(tableModel);
		table.setFillsViewportHeight(true);		
		table.getSelectionModel().addListSelectionListener(this);
		scrollPane.setViewportView(table);
		configTableColumns();
	}
	
// ================================================ //	
	private void configTableColumns(){
		// SIGLA
		DefaultTableCellRenderer cr_0 = new DefaultTableCellRenderer();
		cr_0.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(cr_0);
		
		// CÓDIGO UF
		DefaultTableCellRenderer cr_1 = new DefaultTableCellRenderer();
		cr_1.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(1).setCellRenderer(cr_1);
		
		// CÓDIGO MUNICÍPIO
		DefaultTableCellRenderer cr_2 = new DefaultTableCellRenderer();
		cr_2.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(2).setCellRenderer(cr_2);

		// NOME MUNICÍPIO
		DefaultTableCellRenderer cr_3 = new DefaultTableCellRenderer();
		cr_3.setHorizontalAlignment(JLabel.RIGHT);
		table.getColumnModel().getColumn(3).setCellRenderer(cr_3);
		
		// POPULAÇÃO
		DefaultTableCellRenderer cr_4 = new DefaultTableCellRenderer();
		cr_4.setHorizontalAlignment(JLabel.RIGHT);
		table.getColumnModel().getColumn(4).setCellRenderer(cr_4);

//		// centralizar tudo
//		DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
//		cr.setHorizontalAlignment(JLabel.CENTER);
//		table.setDefaultRenderer(String.class, cr);
	}

	@Override
	public void valueChanged(ListSelectionEvent g0) {
		// lê valores da linha selecionada
		String [] rData = (String[]) tableModel.getRowData(table.getSelectedRow());
		System.out.println("\nvalueChanged(): "+rData[0]+", "+rData[1]+", "+rData[2]+", "+rData[3]+", "+rData[4]);
		// preenche formulário com valores da linha selecionada
		tfdSigla.setText(rData[0]);
		tfdCdEstado.setText(rData[1]);
		tfdCodMunic.setText(rData[2]);
		tfdNomeMunic.setText(rData[3]);
		tfdPopulacao.setText(rData[4]);
		// habilita/desabilita navegação
		linhaSel = table.getSelectedRow();
		habilitaNavegacao();
		System.out.println("valueChanged(), selectedRow: "+ linhaSel+" habilitou navegação");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnUltimo){
			linhaSel = table.getRowCount()-1;
			table.requestFocus();
			table.changeSelection(linhaSel, 0, false, false);
		}
		if (e.getSource() == btnProximo){
			linhaSel++;
			table.requestFocus();
			table.changeSelection(linhaSel, 0, false, false);
		}
		if (e.getSource() == btnAnterior){
			linhaSel--;
			table.requestFocus();
			table.changeSelection(linhaSel, 0, false, false);
		}
		if (e.getSource() == btnPrimeiro){
			linhaSel = 0;
			table.requestFocus();
			table.changeSelection(linhaSel, 0, false, false);
		}
		
		if (e.getSource() == btnOrdenarTabela){
			tableModel.ordenarDados();
			table.repaint();
			table.requestFocus();
			table.changeSelection(linhaSel, 0, false, false);
		}
	}
	
	// carrega o formulário com os valores da primeira linha da tabela
	@Override
	public void windowActivated(WindowEvent arg0) {
		// obtem a primeira linha da tabela
		String [] rData = (String[]) tableModel.getRowData(0);
		System.out.println("\nwindowActivated(): "+rData[0]+", "+rData[1]+", "+rData[2]+", "+rData[3]+", "+rData[4]);
		// carrega os campos do formulário
		tfdSigla.setText(rData[0]);
		tfdCdEstado.setText(rData[1]);
		tfdCodMunic.setText(rData[2]);
		tfdNomeMunic.setText(rData[3]);
		tfdPopulacao.setText(rData[4]);
		// seleciona a primeira linha da tabela
		linhaSel = 0;
		table.setRowSelectionInterval(0, 0);
		// posiciona o foco na primeira célula da tabela
		table.requestFocus();
		table.changeSelection(linhaSel, 0, false, false);
		// habilita navegação
		habilitaNavegacao();
		System.out.println("windowActivated(): habilitou navegação:)");
	}

	private void habilitaNavegacao(){
		System.out.println("habilitaNavegacao(): linhaSel: "+linhaSel+ " table.getRowCount(): "+table.getRowCount());	
		if (linhaSel == 0){
			System.out.println("INI--->linhaSel: "+linhaSel);
			btnPrimeiro.setEnabled(false);
			btnAnterior.setEnabled(false);
			btnProximo.setEnabled(true);
			btnUltimo.setEnabled(true);
		}else if(linhaSel == table.getRowCount()-1){
			System.out.println("FIM--->linhaSel: "+linhaSel);
			btnPrimeiro.setEnabled(true);
			btnAnterior.setEnabled(true);
			btnProximo.setEnabled(false);
			btnUltimo.setEnabled(false);
		}
		else{
			System.out.println("MEIO--->linhaSel: "+linhaSel);
			btnPrimeiro.setEnabled(true);
			btnAnterior.setEnabled(true);
			btnProximo.setEnabled(true);
			btnUltimo.setEnabled(true);
		}		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
