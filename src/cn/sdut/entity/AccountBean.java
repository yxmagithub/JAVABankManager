package cn.sdut.entity;

public class AccountBean {
private int id;
private String name;
private String createDate;
private float amount;
private String phoneNum;//�绰����
private String IDcard;//���֤��
private String password;
private String isLock;//�����˻��������ʼ����δ�޸���������ֱ���޸ĺ������Ĭ���½��û�Ϊ����״̬���ڴ�״̬�£�
					//ֻ���޸��˻��������ʹ�ã����๦�ܾ������ԡ�
private String frozen;//�˻����ᣬĬ��Ϊn��ֻ�е��û��˻������쳣����������������󳬹����Σ��˻��ͻᱻ���ᣬֻ�й���Ա���Խ���
					//����ԱҲ�ɶ�ĳ���˻����ж�����ⶳ��
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

public float getAmount() {
	return amount;
}
public void setAmount(float amount) {
	this.amount = amount;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getPhoneNum() {
	return phoneNum;
}
public void setPhoneNum(String phoneNum) {
	this.phoneNum = phoneNum;
}
public String getIDcard() {
	return IDcard;
}
public void setIDcard(String iDcard) {
	this.IDcard = iDcard;
}
public String getIsLock() {
	return isLock;
}
public void setIsLock(String isLock) {
	this.isLock = isLock;
}
public String getFrozen() {
	return frozen;
}
public void setFrozen(String frozen) {
	this.frozen = frozen;
}
public String getCreateDate() {
	return createDate;
}
public void setCreateDate(String createDate) {
	this.createDate = createDate;
}



}
