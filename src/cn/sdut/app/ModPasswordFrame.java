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

/*创建账户窗口，输入姓名和初始金额*/
@SuppressWarnings("serial")
public class ModPasswordFrame extends JFrame implements ActionListener {

	JLabel lb_accountId;
	JTextField tf_accountId;
	JLabel lb_oldPassword;
	JTextField tf_oldPassword;
	JLabel lb_confirmPassword;
	JTextField tf_confirmPassword;
	JLabel lb_newPassword;
	JTextField tf_newPassword;
	
	JTextField tf_SecurityCode;
	JButton bt_SecurityCode;
	
	JButton bt_OK;
	JButton bt_reset;
	JScrollPane sp_message;
	JTextArea ta_message;
	
	String s=null;

	public ModPasswordFrame(String title) {
		super(title);
		init();
	}

	private void init() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setBounds(300, 300, 300, 400);
		setLayout(new FlowLayout());
		lb_accountId = new JLabel("   Acct No:  ");
		tf_accountId = new JTextField(15);
		lb_oldPassword = new JLabel("Old PASSWORD:");
		tf_oldPassword = new JTextField(15);

		lb_newPassword = new JLabel("New PASSWORD:");
		tf_newPassword = new JTextField(15);
		
		lb_confirmPassword = new JLabel("    AGAIN:   ");
		tf_confirmPassword = new JTextField(15);

		tf_SecurityCode = new JTextField(12);
		bt_SecurityCode = new JButton("    Verify   ");
		
		
		bt_OK = new JButton("OK");
		bt_reset = new JButton("CANCEL");
		ta_message = new JTextArea(5, 30);
		ta_message.setLineWrap(true);
		sp_message = new JScrollPane(ta_message);

		add(lb_accountId);
		add(tf_accountId);
		add(lb_oldPassword);
		add(tf_oldPassword);
		
		add(lb_newPassword);
		add(tf_newPassword);
		
		add(lb_confirmPassword);
		add(tf_confirmPassword);
		
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
		String str_oldPassword = tf_oldPassword.getText();	
		String str_newPassword = tf_newPassword.getText();
		String str_confirmPassword = tf_confirmPassword.getText();

		if(e.getActionCommand() == "Verify"){
			if( str_accountId == null || "".equals(str_accountId)
				|| str_oldPassword == null || "".equals(str_oldPassword)
				|| str_confirmPassword == null ||"".equals(str_confirmPassword)
				|| str_newPassword == null || "".equals(str_newPassword) ){
				
				ta_message.setText("fill all blank");
				//return;
				
			}else{
				int accountId = Integer.parseInt(str_accountId);
				AccountBiz accountBiz = new AccountBiz();
				String strReturn = accountBiz.getScodeById(accountId);
				
				if(strReturn.equals("用户不存在！")){
					ta_message.setText("wrong acct or password");
				    return;
				}else{
					s = strReturn;
					ta_message.setText("verify code");
				}
				bt_OK.setEnabled(true);
				bt_SecurityCode.setEnabled(false);
			}
			
		}
		
			if (e.getActionCommand() == "OK") {
				
				
				if( str_accountId == null || "".equals(str_accountId)
						|| str_oldPassword == null || "".equals(str_oldPassword)
						|| str_confirmPassword == null ||"".equals(str_confirmPassword)
						|| str_newPassword == null || "".equals(str_newPassword) 
						|| Scode == null || "".equals(Scode) ){
					
					ta_message.setText("fill all blank！");
				}
				else{
					if(Scode.equals(s)){
				
							if (str_newPassword.equals(str_confirmPassword)) {
								
								AccountBiz accountBiz = new AccountBiz();
								int accountId = Integer.parseInt(str_accountId);
								String strReturn = accountBiz.updatePassword(accountId,
										str_oldPassword, str_newPassword);
								ta_message.setText(strReturn);
							} else {
								ta_message.setText("inconsistent password！");
								}
					}else{
						ta_message.setText("wrong code, again");
						bt_SecurityCode.setEnabled(true);
					}
				}
				
			} 
			
		
			if (e.getActionCommand() == "CANCEL") {
				tf_accountId.setText("");
				tf_oldPassword.setText("");
				tf_confirmPassword.setText("");
				tf_newPassword.setText("");
				tf_SecurityCode.setText("");
				ta_message.setText("");
			}
		

	}
}
