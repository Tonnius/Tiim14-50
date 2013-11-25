package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.popups.ErrorPopup;

public class ConfirmOrderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static JTextField sumField;
	private static JTextField paymentField;
	private static JTextField changeField;
	double orderSum = 0;
	static boolean returnValue;

	public static boolean isReturnValue() {
		return returnValue;
	}

	public ConfirmOrderPanel(final SalesSystemModel model,
			final JDialog popupDialog) {
		setLayout(new GridLayout(4, 2));
		setBorder(BorderFactory.createTitledBorder("Confirm order: "));

		// generate text fields
		sumField = new JTextField();
		paymentField = new JTextField();
		changeField = new JTextField();

		// button for confirming order
		JButton confirmOrderButton = new JButton("Confirm");
		confirmOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (orderSum > Double.parseDouble(paymentField.getText())) {
						ErrorPopup
								.createPopup("The amount paid is less than the charged amount!");
					} else {
						returnValue = true;
						popupDialog.dispose();
					}

				} catch (NumberFormatException ex) {
					ErrorPopup
							.createPopup("Invalid payment data, order not processed!");
					paymentField.setText("");

				}

			}
		});

		// button for closing window / canceling order confirmation
		JButton closeButton = new JButton("Cancel");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnValue = false;
				popupDialog.dispose();
			}
		});

		// calculate change amount when paymentField loses focus
		paymentField.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				changeField.setText(Double.toString(
						Double.parseDouble(paymentField.getText()) - orderSum));
			}

			public void focusGained(FocusEvent e) {
			}
		});

		// set value for the total sum of the order
		for (int i = 0; i < model.getCurrentPurchaseTableModel().getTableRows()
				.size(); i++) {
			orderSum += model.getCurrentPurchaseTableModel().getTableRows()
					.get(i).getSum();
		}
		sumField.setText(Double.toString(orderSum));
		changeField.setText("");
		// add fields to panel
		add(new JLabel("Order sum:"));
		add(sumField);

		add(new JLabel("Payment:"));
		add(paymentField);

		add(new JLabel("Change:"));
		add(changeField);

		add(confirmOrderButton);
		add(closeButton);

		sumField.setEditable(false);
		changeField.setEditable(false);

	}

}
