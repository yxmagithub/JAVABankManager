package cn.sdut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import cn.sdut.Util.DBConn;
import cn.sdut.Util.Md5;
import cn.sdut.entity.AccountBean;

public class AccountDao {
	Connection con = DBConn.getConnnection();

	/***************���ݿ����*********************/
	@SuppressWarnings("resource")
	public int addAcount(AccountBean account) {
		// ���˻���������һ����¼��ÿ���ֶε�ֵȡ�Դ������ķ�װ����
		//�����ʺ�
		//String sqlInsert = "insert into account values(?,?,?,?)";
		String sqlInsert = "insert into account(name,amount,password,phoneNum,IDcard) values(?,?,?,?,?)";
		PreparedStatement pst=null;
		ResultSet rs=null;
		int countId = 0;
		try {
			pst = con.prepareStatement(sqlInsert); /*ʵ����PreparedStatement�ӿڵ�ʵ�����ʵ��*/
			pst.setString(1, account.getName());

			//pst.setDate(2, new java.sql.Date(account.getCreateDate().getTime()));
			//ʱ�䲻�û�ȡֱ�������ݿ��Զ����
			pst.setFloat(2, account.getAmount());
			pst.setString(3, Md5.getMd5(account.getPassword()));//����MD5����
			pst.setString(4, account.getPhoneNum());
			pst.setString(5, account.getIDcard());
			pst.executeUpdate();
			
			//��øռ����������¼������
			pst=con.prepareStatement("select id from account",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			rs = pst.executeQuery();
			rs.last();
			countId=rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{			
			try {
				rs.close();
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return countId;
	}

	/***************���ݿ�ɾ��***************************************************/
	public int delAccount(int accountId) {// �����˺�ɾ����¼
		String sqlInsert = "delete from account where id=?";
		PreparedStatement pst;
		int count = 0;
		try {
			pst = con.prepareStatement(sqlInsert);
			pst.setInt(1, accountId);
			count = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/***************���ݿ����******************************************/
	public int modPassword(int accountId,String newPassword) {
		// ���ʺ�ΪaccountId�ļ�¼���������ΪnewPassword
		String sqlInsert = "update account set isLock='n',password=? where id=?";
		PreparedStatement pst;
		int count = 0;
		try {
			pst = con.prepareStatement(sqlInsert);
			pst.setString(1, Md5.getMd5(newPassword));
			pst.setInt(2, accountId);
			count = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public int modMoney(int accountId,float newAmount) {
		// ���ʺ�ΪaccountId�ļ�¼�Ľ�����ΪnewPassword
		String sqlInsert = "update account set amount=? where id=?";
		PreparedStatement pst;
		int count = 0;
		try {
			pst = con.prepareStatement(sqlInsert);
			pst.setFloat(1, newAmount);
			pst.setInt(2, accountId);
			count = pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public int setFrozen_Y(int accountId) {
		// ���ʺ�ΪaccountId���˺Ŷ���״̬����Ϊy��������û�
		String sqlInsert = "update account set frozen='y' where id=?";
		PreparedStatement pst;
		int count = 0;
		try{
			pst = con.prepareStatement(sqlInsert);
			pst.setInt(1, accountId);
			count = pst.executeUpdate();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
		
	}
	
	public int setFrozen_N(int accountId) {
		// ���ʺ�ΪaccountId���˺Ŷ���״̬����Ϊn���ⶳ���û�
		String sqlInsert = "update account set frozen='n' where id=?";
		PreparedStatement pst;
		int count = 0;
		try{
			pst = con.prepareStatement(sqlInsert);
			pst.setInt(1, accountId);
			count = pst.executeUpdate();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
		
	}

	/***************���ݿ��ѯ*******************************************/
	public AccountBean selectById(int accountId) {
		//�����˺�id�õ��˻���ϸ��Ϣ
		String sqlInsert = "select * from  account where id=?";
		AccountBean account =null;
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sqlInsert);

			pst.setInt(1, accountId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				account= new AccountBean();
				account.setId(rs.getInt(1));
				account.setName(rs.getString(2));
				//account.setCreateDate(new java.util.Date(rs.getDate(3)
				//		.getTime()));//////////////////////////////////////////����ʱ��Ļ�ȡ��Ҫ�޸�
				//account.setCreateDate( DateToUtil.getUtilDate(rs.getTimestamp(3)) );
				account.setAmount(rs.getFloat(3));
				account.setPassword(rs.getString(4));
				account.setPhoneNum(rs.getString(5));
				account.setIDcard(rs.getString(6));
				account.setCreateDate(cn.sdut.Util.DateFormat.YMDDate(rs.getTimestamp(7)) );
				account.setIsLock(rs.getString(8));
				account.setFrozen(rs.getString(9));
				

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}

	public AccountBean selectByIdAndPassword(int accountId, String password) {
		//�����˺�id������õ��˻���ϸ��Ϣ
		
		String sqlInsert = "select * from  account where id=? and password=?";
		AccountBean account = null;
		PreparedStatement pst;
		try {
			pst = con.prepareStatement(sqlInsert);
			pst.setInt(1, accountId);
			pst.setString(2, Md5.getMd5(password));
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				account = new AccountBean();
				account.setId(rs.getInt(1));
				account.setName(rs.getString(2));
				account.setCreateDate(cn.sdut.Util.DateFormat.YMDDate(rs.getTimestamp(3)));
				account.setAmount(rs.getFloat(4));
				account.setPassword(rs.getString(5));
				account.setPhoneNum(rs.getString(6));
				account.setIDcard(rs.getString(7));
				account.setIsLock(rs.getString(8));
				account.setFrozen(rs.getString(9));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if (account.getPassword().equals(Md5.getMd5("666666"))){
//			account = null;
//		}
		return account;
	}

}
