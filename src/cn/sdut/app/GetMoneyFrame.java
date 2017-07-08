package cn.sdut.app;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.sdut.biz.AccountBiz;

/*创建取款窗口*/
@SuppressWarnings("serial")
public class GetMoneyFrame extends JFrame implements ActionListener {

	JLabel lb_accountId;
	JTextField tf_accountId;
	JLabel lb_password;
	JTextField tf_password;
	JLabel lb_amount;
	JTextField tf_amount;
	
	JTextField tf_SecurityCode;
	JButton bt_SecurityCode;
	
	JButton bt_OK;
	JButton bt_reset;
	JScrollPane sp_message;
	JTextArea ta_message;
	
	String s=null;

	public GetMoneyFrame(String title) {
		super(title);
		init();
	}

	private void init() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setBounds(300, 300, 260, 300);
		setLayout(new FlowLayout());
		lb_accountId = new JLabel(" Acct No:");
		tf_accountId = new JTextField(15);
		lb_password = new JLabel("Password:");
		tf_password = new JTextField(15);
		lb_amount = new JLabel(" Amount:");
		tf_amount = new JTextField(15);
		
		tf_SecurityCode = new JTextField(12);
		bt_SecurityCode = new JButton("  Verify");
		
		bt_OK = new JButton("OK");
		bt_reset = new JButton("CANCEL");
		ta_message = new JTextArea(5, 20);
		ta_message.setLineWrap(true);
		sp_message = new JScrollPane(ta_message);

		add(lb_accountId);
		add(tf_accountId);
		add(lb_password);
		add(tf_password);
		add(lb_amount);
		add(tf_amount);
		
		add(tf_SecurityCode);
		add(bt_SecurityCode);
		
		add(bt_OK);
		add(bt_reset);
		add(sp_message);
		setVisible(true);

		bt_OK.addActionListener(this);
		bt_reset.addActionListener(this);
		bt_SecurityCode.addActionListener(this);
		
		bt_OK.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String Scode = tf_SecurityCode.getText();
		String str_accountId = tf_accountId.getText();
		String str_password = tf_password.getText();
		String amount = tf_amount.getText();
		
		
		
		if(e.getActionCommand() == "Verify"){
			if(str_accountId == null || "".equals(str_accountId)
					|| str_password == null || "".equals(str_password)
					|| amount == null || "".equals(amount)){
				
				ta_message.setText("fill all blank");
				//return;
				
			}else{
				int accountId = Integer.parseInt(str_accountId);
				AccountBiz accountBiz = new AccountBiz();
				String strReturn = accountBiz.getScodeById(accountId);
				
				if(strReturn.equals("No Acct！")){
					ta_message.setText("Wrong Password");
				    return;
				}else{
					s = strReturn;
					ta_message.setText("Pls Verify");
				}
				bt_OK.setEnabled(true);
				bt_SecurityCode.setEnabled(false);
			}
			
		}
		
		if (e.getActionCommand() == "OK") {

			if (str_accountId == null || "".equals(str_accountId)
					|| str_password == null || "".equals(str_password)
					|| amount == null || "".equals(amount)
					|| Scode == null || "".equals(Scode)) {
				ta_message.setText("sorry, fill all blank");	
			} else {
				
					if(Scode.equals(s)){
				
						float saveAmount = 0.0f;
						try {
							saveAmount = Float.parseFloat(amount);
							if (saveAmount < 0) {
								ta_message.setText("Sorry, negative amount");
							} else {
								int accountId = Integer.parseInt(str_accountId);
								AccountBiz accountBiz = new AccountBiz();
								String strReturn = accountBiz.getMoney(accountId,
										str_password, saveAmount);
								ta_message.setText(strReturn);
							}
						} catch (NumberFormatException e1) {
							ta_message.setText("Sorry, only number！");
						}
					}else{
						ta_message.setText("pls verify again");
						bt_SecurityCode.setEnabled(true);
					}
			}
		} 
			
		if (e.getActionCommand() == "CANCEL") {
				tf_accountId.setText("");
				tf_password.setText("");
				tf_amount.setText("");
				tf_SecurityCode.setText("");
				ta_message.setText("");
			}
		

	}

}
