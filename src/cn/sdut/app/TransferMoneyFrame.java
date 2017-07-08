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
public class TransferMoneyFrame extends JFrame implements ActionListener {
	
	JLabel lb_outAccountId;
	JTextField tf_outAccountId;
	JLabel lb_password;
	JTextField tf_password;
	JLabel lb_inAccountId;
	JTextField tf_inAccountId;	
	JLabel lb_amount;
	JTextField tf_amount;
	
	JTextField tf_SecurityCode;
	JButton bt_SecurityCode;
	
    JButton bt_OK;
    JButton bt_reset;
    JScrollPane sp_message;
    JTextArea ta_message;
    
    String s=null;
	
	public TransferMoneyFrame(String title) {
		super(title);
		init();
	}

	private void init() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		setBounds(300, 300, 320, 300);
		setLayout(new FlowLayout());
		lb_outAccountId=new JLabel("Snd Acct No:");
		tf_outAccountId=new JTextField(16);
		lb_password=new JLabel("   PASSWORD:");
		tf_password=new JTextField(16);
		lb_inAccountId=new JLabel("Rec Acct No:");
		tf_inAccountId=new JTextField(16);
		
		lb_amount=new JLabel("     Amount:");
		tf_amount=new JTextField(16);
		
		tf_SecurityCode = new JTextField(12);
		bt_SecurityCode = new JButton("   Verify   ");
		
        bt_OK=new JButton("OK");
        bt_reset=new JButton("CANCEL");
        ta_message=new JTextArea(8,28);
        ta_message.setLineWrap(true);
        sp_message=new JScrollPane(ta_message);
        
        add(lb_outAccountId);
        add(tf_outAccountId);
        add(lb_password);
        add(tf_password);
        add(lb_inAccountId);
        add(tf_inAccountId);      
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
		
		String str_outAccountId=tf_outAccountId.getText();
		String str_password=tf_password.getText();
		String str_inAccountId=tf_inAccountId.getText();
		String amount = tf_amount.getText();
		String Scode = tf_SecurityCode.getText();
		
		
		if(e.getActionCommand() == "Verify"){
				if(str_outAccountId == null || "".equals(str_outAccountId)
						|| str_inAccountId == null || "".equals(str_inAccountId)
						|| str_password == null || "".equals(str_password)
						|| amount == null || "".equals(amount)){
					
					ta_message.setText("fill all blank");
					return;
					
				}else{
					int accountId = Integer.parseInt(str_outAccountId);
					AccountBiz accountBiz = new AccountBiz();
					String strReturn = accountBiz.getScodeById(accountId);
					
					if(strReturn.equals("用户不存在！")){
						ta_message.setText("acct or password wrong");
					    return;
					}else{
						s = strReturn;
						ta_message.setText("get code");
					}
					bt_OK.setEnabled(true);
					bt_SecurityCode.setEnabled(false);
				}
			
		}
		
		if(e.getActionCommand()=="OK")
		{

			if( str_outAccountId == null|| "".equals(str_outAccountId)
				|| str_inAccountId == null || "".equals(str_inAccountId)
				|| str_password == null || "".equals(str_password)
				|| amount == null || "".equals(amount) 
				|| Scode == null || "".equals(Scode) ){
				ta_message.setText("Sorry, fill all blank");
			}
			else{
				if(Scode.equals(s)){
				
					try {
						int outAccountId=Integer.parseInt(str_outAccountId);
						int inAccountId=Integer.parseInt(str_inAccountId);
						float turnAmount=Float.parseFloat(amount);
						AccountBiz accountBiz=new AccountBiz();
						String strReturn = accountBiz.turnAccount(outAccountId, str_password, inAccountId, turnAmount);
						ta_message.setText(strReturn);
					} catch (NumberFormatException e1) {
						ta_message.setText("Sorry, invalid symble");
					}
				}else{
					ta_message.setText("wrong code, again");
					bt_SecurityCode.setEnabled(true);
				}
			
			}
			
		}
		
		
		if(e.getActionCommand()=="CANCEL")
		{
			tf_outAccountId.setText("");
			tf_amount.setText("");
			tf_outAccountId.setText("");
			tf_password.setText("");
			tf_SecurityCode.setText("");
			tf_inAccountId.setText("");
			ta_message.setText("");
		}
	
		
	}

	
}
