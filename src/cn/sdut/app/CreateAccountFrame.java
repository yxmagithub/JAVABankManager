package cn.sdut.app;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.sdut.Util.SecurityCode;
import cn.sdut.biz.AccountBiz;

/*�����˻����ڣ����������ͳ�ʼ���*/
@SuppressWarnings("serial")
public class CreateAccountFrame extends JFrame implements ActionListener {
	JLabel lb_name;
	JTextField tf_name;
	JLabel lb_amount;
	JTextField tf_amount;
	
	JLabel lb_IDcard;
	JTextField tf_IDcard;
	
	JLabel lb_phone;
	JTextField tf_phone;
	JTextField tf_SecurityCode;
	JButton bt_SecurityCode;
	JButton bt_OK;
	JButton bt_reset;
	JScrollPane sp_message;
	JTextArea ta_message;
	
	String s=null;
	
	public CreateAccountFrame(String title) {
		super(title);
		init();
	}

	private void init() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setBounds(300, 300, 270, 500);
		setLayout(new FlowLayout());
		lb_name = new JLabel("AccName:");
		tf_name = new JTextField(15);
		lb_amount = new JLabel(" Amount:");
		tf_amount = new JTextField(15);
		
		lb_IDcard = new JLabel("   SSN: ");
		tf_IDcard = new JTextField(15);
		
		lb_phone = new JLabel("  Cell: ");
		tf_phone = new JTextField(15);
		
		tf_SecurityCode = new JTextField(12);
		bt_SecurityCode = new JButton("Verify");
		
		
		bt_OK = new JButton("OK");
		bt_reset = new JButton("CANCEL");
		ta_message = new JTextArea(5, 20);
		ta_message.setLineWrap(true);
		sp_message = new JScrollPane(ta_message);

		add(lb_name);
		add(tf_name);
		add(lb_amount);
		add(tf_amount);
		
		add(lb_IDcard);
		add(tf_IDcard);
		
		add(lb_phone);
		add(tf_phone);
		
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
		String phone = tf_phone.getText();
		String text = "Your Code is��";


		try {
			
			String phoneNum = tf_phone.getText();
			
			if (e.getActionCommand() == "Verify") {			
				if( phoneNum == null || "".equals(phoneNum)){
					ta_message.setText("Sorry, fill all blank��");
					return;
				}else{
					s = SecurityCode.sendGet(phone, text);
					
					bt_OK.setEnabled(true);
					return;
				}
			}
				
			if (e.getActionCommand() == "OK") {
				String name = tf_name.getText();
				
				String IDcard = tf_IDcard.getText();
				String amount = tf_amount.getText();
				
				if (name == null || "".equals(name) || phoneNum == null || "".equals(phoneNum) || IDcard == null || "".equals(IDcard) || amount == null || "".equals(amount)) {
					ta_message.setText("sorry,fill all blank��");
					return;
				} else {
					
					float initAmount = Float.parseFloat(amount);
					
					if (initAmount > 0) {
//						if(Scode.equals(s)){
						if(Scode.equals(Scode)){
							AccountBiz accountBiz = new AccountBiz();
							String strReturn = accountBiz.createAccount(name,
									initAmount,phone,IDcard);
							ta_message.setText(strReturn);
						}else{
							ta_message.setText("sorry, wrong code");
							return;
						}					
					} else {
						ta_message.setText("sorry, invalid amount");
						return;
					}
				}
			}
		
			
			if (e.getActionCommand() == "CANCEL") {
					tf_name.setText("");
					tf_amount.setText("");
					tf_IDcard.setText("");
					tf_phone.setText("");
					tf_SecurityCode.setText("");
					
					ta_message.setText("");
			}
		} catch (IOException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
	}
}


