package cn.sdut.app;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	public MainFrame(String title) {
		super(title);
		init();
	}
	JLabel label_info;
	JButton bt_createAccount;
	JButton bt_saveMoney;
	JButton bt_getMoney;
	JButton bt_turnAccount;
	JButton bt_select;
	JButton bt_updatePassword;
	JButton bt_destroyAccount;
	JButton bt_exit;
	private void init() {
		label_info=new JLabel("----Welcome Songjiang Bank Manager----");
		label_info.setHorizontalAlignment(JLabel.CENTER);
		bt_createAccount=new JButton("OPEN");
		bt_saveMoney=new JButton("DEPOSIT");
		bt_getMoney=new JButton("WITHDRAW");
		bt_turnAccount=new JButton("TRANSFER");
		bt_select=new JButton("INQUERY");
		bt_updatePassword=new JButton("PROFILE");
		bt_destroyAccount=new JButton("CLOSE");
		bt_exit=new JButton("EXIT");
		add(label_info);		
		add(bt_createAccount);
		add(bt_saveMoney);
		add(bt_getMoney);
		add(bt_turnAccount);
		add(bt_select);
		add(bt_updatePassword);
		add(bt_destroyAccount);
		add(bt_exit);
		
		bt_createAccount.addActionListener(this);
		bt_saveMoney.addActionListener(this);
		bt_getMoney.addActionListener(this);
		bt_turnAccount.addActionListener(this);
		bt_select.addActionListener(this);
		bt_updatePassword.addActionListener(this);
		bt_destroyAccount.addActionListener(this);
		bt_exit.addActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(300, 300, 500, 300);
		setLayout(new GridLayout(9,1));
		setVisible(true);
			}

	public static void main(String[] args) {
		new MainFrame("SongJiang Bank Manager");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="OPEN")
		{
//			open account
			new CreateAccountFrame("OPEN"); 
		}
		else
		{
			if(e.getActionCommand()=="DEPOSIT")
			{
				new DepositMoneyFrame("DEPOSIT");
			}
			else
			{
				if(e.getActionCommand()=="WITHDRAW")
				{
					new GetMoneyFrame("WITHDRAW");
				}
				else
				{
					if(e.getActionCommand()=="TRANSFER")
					{
						new TransferMoneyFrame("TRANSFER");
					}
					else
					{
						if(e.getActionCommand()=="INQUIRY")
						{
							new InquiryAccountFrame("INQUIRY BALANCE");
						}
						else
						{
							if(e.getActionCommand()=="PROFILE")
							{
								new ModPasswordFrame("CHANGE PASSWORD");
							}
							else
							{
								if(e.getActionCommand()=="CLOSE")
								{
									new CloseAccountFrame("CLOSE ACCOUNT");
								}
								else
								{									
									System.exit(0);
								}
							}
						}
					}
				}
			}
		}	
	}
}
