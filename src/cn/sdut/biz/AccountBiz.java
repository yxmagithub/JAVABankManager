package cn.sdut.biz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import cn.sdut.Util.DateFormat;
import cn.sdut.Util.OperTypeConvert;
import cn.sdut.Util.SecurityCode;
import cn.sdut.dao.AccountDao;
import cn.sdut.dao.OperLogDao;
import cn.sdut.entity.AccountBean;
import cn.sdut.entity.OperLogBean;

/**
 * ���й���ҵ���ࣺ
 * ����������ȡ�ת�ˡ���ѯ���޸����롢�����ȷ���
 */
public class AccountBiz {
	AccountDao accountDao = new AccountDao();// ���ݿ�� ���˻����Ĳ�������
	OperLogDao operlogDao = new OperLogDao();// ���ݿ�� ��������־�� �Ĳ�������

	/*
	 * ���� 
	 * ��1������һ�ʻ�account���������������������ڣ���ǰʱ�䣩������ʼ���루666666���� 
	 * ��2����Account�������Ӽ�¼��
	 * ��3���������־��������һ����¼�� 
	 * ��4�������ַ������󣨿�������˺ţ���ʼ���루��ʾ��Ϣ�����޸ĳ���Ϊ6λ�����������������ڣ���
	 */
	public String createAccount(String name, float initAmount, String phoneNum, String IDcard) {
		AccountBean account = new AccountBean();
		account.setName(name);
		account.setAmount(initAmount);

		account.setPhoneNum(phoneNum);
		account.setIDcard(IDcard);
		
		
		//account.setCreateDate(date);
		String initPassword = "666666";
		//initPassword = Md5.getMd5(initPassword);/////////////////////////////
		account.setPassword(initPassword);
		int accountId = accountDao.addAcount(account);

		OperLogBean log = new OperLogBean();
		log.setAccoutId(accountId);
		log.setTypeId(1);
		log.setAmount(initAmount);
		java.util.Date date = new java.util.Date();
		//log.setOperDate(date);
		log.setOldPassword(initPassword);
		log.setNewPassword(initPassword);

		String strDate = DateFormat.YMDDate(date);
		@SuppressWarnings("unused")
		int count2 = operlogDao.addLog(log);
		String strReturn = "AcctNo:" + accountId + "\nPassword��" + initPassword
				+ ",change it ASAP\n" + "open amount:" + initAmount + "\nopen date��"
				+ strDate;
		return strReturn;
	}

	// ���
	/*
	 * ��1�����˻����и���������˺��ҵ��˻����� 
	 * ��2��ȡ���ö���Ľ�����money��ֵ 
	 * ��3�������ʻ���¼ 
	 * ��4������һ��������־��¼
	 * ��5�������ַ������󣨴洢�Ľ��˺������н�����ʱ�䣩
	 */

	public String saveMoney(int accountId, float money) {
		AccountBean account = accountDao.selectById(accountId);
		String strReturn = null;
		if (account != null) {
			if(account.getIsLock().equals("n")){
				float initMoney = account.getAmount();
				float newMoney=initMoney + money;
				accountDao.modMoney(accountId,newMoney);
	
				OperLogBean log = new OperLogBean();
				log.setAccoutId(accountId);
				log.setAmount(money);
				log.setNewPassword(account.getPassword());
				log.setOldPassword(account.getPassword());
				Date date = new java.util.Date();
				//log.setOperDate(date);
				log.setTypeId(2);
	
				operlogDao.addLog(log);
				strReturn = "Name��" + account.getName() + "\nbefore:" + initMoney
						+ "\ndeposit:" + money + "\n" + "balance:"
						+ newMoney+ "\ndate:"
						+ DateFormat.YMDDate(date);
			}else{
				
				strReturn = "�Բ��������˺�û�м�����޸����뼤�";
			}
			
		} else {
			strReturn = "�Բ�����������˺Ų���ȷ�����޴��ˣ�";
		}
		return strReturn;
	}

	// ȡ��
	/*
	 * ��1�����˻����и���������˺ź������ҵ��˻����� 
	 * ��2��ȡ���ö���Ľ���ȥmoney��ֵ 
	 * ��3�������ʻ���¼ 
	 * ��4������һ��������־��¼
	 * ��5�������ַ���������ȡ�Ľ��˺������еĽ�����ʱ�䣩��
	 */

	public String getMoney(int accountId, String password, float money) {
		AccountBean account = accountDao.selectByIdAndPassword(accountId,
				password);
		String strReturn = null;
		
		float newMoney;
		
		if (account != null) {
			float initMoney = account.getAmount();
			
			if(account.getIsLock().equals("n")){
			
					if (money<=initMoney){
						newMoney=initMoney - money;
						accountDao.modMoney(accountId, newMoney);
			
						OperLogBean log = new OperLogBean();
						log.setAccoutId(accountId);
						log.setAmount(money);
						log.setNewPassword(account.getPassword());
						log.setOldPassword(account.getPassword());
						Date date = new java.util.Date();
						//log.setOperDate(date);
						log.setTypeId(3);
			
						operlogDao.addLog(log);
						strReturn = "Name��" + account.getName() + "\nbefore:" + initMoney
								+ "\nout money:" + money + "\n" + "balance:"
								+newMoney + "\ndate:"
								+ DateFormat.YMDDate(date);
						}else{
							strReturn = "�Բ���������Ľ���������������룡";			
						}
					
			}else{
				strReturn = "�Բ��������˺�û�м�����޸����뼤�";	
			}
			
		} else {
			strReturn = "�Բ�����������˺ź����벻��ȷ�����޴��ˣ�";
		}
		return strReturn;
	}

	// ת��
	/*
	 * ��1�����˻����и���������˺ź������ҵ�ת���˻����� 
	 * ��2������������˺��ҵ�ת���˻����� 
	 * ��3��ת���˺��еĽ���ȥת�ʽ��
	 * ��4��ת���˺��еĽ�����ת�ʽ�� 
	 * ��5�� ����2��������־��¼���ֱ��¼ת����ת�������
	 * ��6�������ַ�������ת�ʵĽ�ת���˺����еĽ����β�����ʱ�䣩
	 */

	public String turnAccount(int outAccountId, String password,
			int inAccountId, float money) {
		AccountBean outAccount = accountDao.selectByIdAndPassword(outAccountId,
				password);
		AccountBean inAccount = accountDao.selectById(inAccountId);
		String strReturn = null;
		
		if (outAccount != null && inAccount != null){
				float outInitMoney = outAccount.getAmount();
				float inInitMoney = inAccount.getAmount();//��������
				
				if(outAccount.getIsLock().equals("n")){
					if (money <= outInitMoney){
							outAccount.setAmount(outInitMoney - money);
							inAccount.setAmount(inInitMoney + money);
							accountDao.modMoney(outAccountId, outAccount.getAmount());
							accountDao.modMoney(inAccountId, inAccount.getAmount());
	
							OperLogBean log = new OperLogBean();
							log.setAccoutId(outAccountId);
							log.setAmount(money * (-1));
							log.setNewPassword(outAccount.getPassword());
							log.setOldPassword(outAccount.getPassword());
							Date date = new java.util.Date();
							//log.setOperDate(date);//����дʱ�䣬���ݿ��Զ����
							log.setTypeId(4);
	
							operlogDao.addLog(log);
	
							log.setAccoutId(inAccountId);
							log.setAmount(money);
							log.setNewPassword(inAccount.getPassword());
							log.setOldPassword(inAccount.getPassword());
	
							log.setTypeId(4);
							operlogDao.addLog(log);
	
						    strReturn = "ת���˺�:" + outAccountId + " �˻�����:"
									+ outAccount.getName() + "\nԭ�н�" + outInitMoney + " ת���Ľ��:"
									+ money + " ���н��:" + outAccount.getAmount() + "\nת���˺�:"
									+ inAccountId + " �˻�����:" + inAccount.getName() + "\nԭ�н�"
									+ inInitMoney + " ת��Ľ��:" + money + " ���н��:"
									+ inAccount.getAmount() + "\n���β�����ʱ��:"
									+ DateFormat.YMDDate(date);
					}else {
						strReturn = "�Բ���������Ľ���������������룡";	
					}
				}else{
					strReturn = "�Բ��������˺�û�м�����޸����뼤�";
				}
				
		} else {
			strReturn = "�Բ�����������˺ź����벻��ȷ�����޴��ˣ�";
		}
		return strReturn;
	}


	// ��ѯ
	/*
	 * ��1�����˻����и���������˺ź������ѯ�˻������ܲ鵽�Ļ������������򣬷���null 
	 * ��2�������˺Ų�ѯ������־����ȡ������Ϣ
	 * ��3�������ַ��������˻���Ϣ����־��Ϣ�����˺ţ��������������ڣ����н�{�������ͣ�����������ʱ�䣬�����룬������}��
	 */
	public String selectAccount(int accountId, String password) {
		AccountBean account = accountDao.selectByIdAndPassword(accountId,
				password);
		if (account != null) {
			if(account.getIsLock().equals("n")){
			
					ArrayList<OperLogBean> list = operlogDao
							.selectByAccountId(accountId);
					StringBuffer sbuf = new StringBuffer();
					sbuf.append("�˺�:" + accountId + "   ����:" + account.getName() + "\n");
					sbuf.append("�������ڣ�" + account.getCreateDate()
							+ "\n");
					sbuf.append(" ���н�" + account.getAmount() + "\n������¼��\n");
					sbuf.append("��������  ���\t\t����\t        ��ע\n");
					sbuf.append("-----------------------------------------------------------------------------------------------\n");
					Iterator<OperLogBean> it = list.iterator();
					while (it.hasNext()) {
						OperLogBean log = it.next();
						///////////////////
						sbuf.append(OperTypeConvert.typeIdToName(log.getTypeId())
								+ "        ");
						sbuf.append(log.getAmount() + "   ");
						sbuf.append(log.getOperDate() + "  ");
						//sbuf.append(log.getOldPassword() + "  ");
						//sbuf.append(log.getNewPassword() + "\n");
						if(!log.getNewPassword().equals(log.getOldPassword())){
							sbuf.append("   �޸Ĺ�����\n");
						}else{
							sbuf.append("   ************\n");
						}
					}
					return sbuf.toString();
			}else{
				return "�Բ��������˺�û�м�����޸����뼤�";
			}
					
		} else {
			return "�û��������벻��ȷ";
		}
	}

	// �޸�����
	/*
	 * ��1�����˻����и���������˺ź������ѯ�˻������ܲ鵽�Ļ������������򣬷���false 
	 * ��2�������˻����������
	 * ��3���ڲ�����־��������һ����¼ 
	 * ��4�������ַ���������ʾ�������true��false
	 */
	public String updatePassword(int accountId, String oldPassword,
			String newPassword) {
		AccountBean account = accountDao.selectByIdAndPassword(accountId,
				oldPassword);
		String strReturn = null;
		if (account != null) {
			int count = accountDao.modPassword(accountId, newPassword);
			if (count > 0) {
				OperLogBean log = new OperLogBean();
				log.setAccoutId(accountId);
				log.setTypeId(6);
				log.setOldPassword(oldPassword);
				log.setNewPassword(newPassword);
				//log.setOperDate(new java.util.Date());///////////////����дʱ�䣬���ݿ��Զ�д��///////////////////////////
				log.setAmount(0);
				operlogDao.addLog(log);
				strReturn = "�����޸ĳɹ���";
			} else {
				strReturn = "�����޸Ĳ��ɹ���";
			}
		} else {
			strReturn = "�û��������벻��ȷ��";
		}
		return strReturn;
	}

	// ����
	/*
	 * ��1�����˻����и���������˺ź������ѯ�˻������ܲ鵽�Ļ������������򣬷���null 
	 * ��2�����˻���Ǯȫ��ȡ���� 
	 * ��3�������˻��ӱ���ɾ��
	 * ��4��������־������2����¼ 
	 * ��5�����ز����Ľ��
	 */

	public String destroyAccount(int accountId, String password) {
		AccountBean account = accountDao.selectByIdAndPassword(accountId,
				password);
		String strReturn = null;
		if (account != null) {
			float amount = account.getAmount(); // �õ��˻��еĽ��
			accountDao.delAccount(accountId); // ɾ���˻�
			OperLogBean log = new OperLogBean();
			log.setAccoutId(accountId);
			log.setTypeId(3);
			log.setAmount(amount);
			log.setNewPassword(account.getPassword());
			log.setOldPassword(account.getPassword());
			//log.setOperDate(new java.util.Date());///////////����дʱ�䣬���ݿ��Զ����////////////////////////////
			operlogDao.addLog(log);

			log.setTypeId(7);
			log.setAmount(0);
			operlogDao.addLog(log);

			strReturn = "�ʺţ�" + accountId + "ȡ���ֽ�" + amount + "�������ɹ���";
		} else {
			strReturn = "�û��������벻��ȷ��";
		}
		return strReturn;
	}
	
	public String getScodeById(int accountId){
		
		AccountBean account = accountDao.selectById(accountId);
		String strReturn = null;
		if (account != null) {
			String phone = account.getPhoneNum();
			try {
				strReturn = SecurityCode.sendGet(phone, "������ʾ����������֤���ǣ�");
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
		}else{
			strReturn = "�û������ڣ�";
		}
		
		return strReturn;
	}
	
}
