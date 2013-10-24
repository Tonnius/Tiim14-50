package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.AddItemPanel;
import ee.ut.math.tvt.salessystem.ui.panels.ModifyItemPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

import com.jgoodies.looks.windows.WindowsLookAndFeel;


public class StockTab {

	private JButton addItem;
	
	private JButton modifyItem;

	private SalesSystemModel model;
	
	private SalesSystemUI ui;

	public StockTab(SalesSystemModel model, SalesSystemUI ui) {
		this.model = model;
		this.ui = ui;
	}

	// warehouse stock tab - consists of a menu and a table
	public Component draw() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		panel.setLayout(gb);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		panel.add(drawStockMenuPane(), gc);

		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(drawStockMainPane(), gc);
		return panel;
	}

	// warehouse menu
	private Component drawStockMenuPane() {
		JPanel panel = new JPanel();

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();

		panel.setLayout(gb);

		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 0;

		addItem = new JButton("Add stock");
		addItem.addActionListener( new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				createPopup("add");
			}
		});
		
		modifyItem = new JButton("Modify stock");
		modifyItem.addActionListener( new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				createPopup("modify");
			}
		});
		
		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;
		panel.add(addItem, gc);
		
		panel.add(modifyItem, gc);

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return panel;
	}


	// table of the warehouse stock
	private Component drawStockMainPane() {
		JPanel panel = new JPanel();

		JTable table = new JTable(model.getWarehouseTableModel());

		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(table);

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		panel.setLayout(gb);
		panel.add(scrollPane, gc);

		panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
		return panel;
	}

	public void createPopup(final String param) {
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				final JDialog popupDialog = new JDialog(ui, "Modify warehouse");
				popupDialog.setModal(true);
				popupDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				try 
				{
					UIManager.setLookAndFeel(new WindowsLookAndFeel());
				} catch (Exception e) {
					e.printStackTrace();
				}
				JPanel panel = new JPanel();
				panel.setLayout(new GridBagLayout());
				JPanel selectionPanel = new JPanel();
				if (param.equals("add")) {
					selectionPanel = new AddItemPanel(model, popupDialog);
				}
				else if (param.equals("modify")) {
					selectionPanel = new ModifyItemPanel(model, popupDialog);
					((ModifyItemPanel) selectionPanel).fillDialogFields();
				}
				
				panel.add(selectionPanel);
				
				popupDialog.add(panel);
				
				popupDialog.getContentPane().add(BorderLayout.CENTER, panel);
				popupDialog.pack();
				popupDialog.setLocationByPlatform(true);
				popupDialog.setVisible(true);
				popupDialog.setResizable(false);
			}
		});
	}
}	