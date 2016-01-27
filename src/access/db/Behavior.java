package access.db;

public class Behavior{
	private int uid;
	private Double trust;
	private int rewardFa;
	private int punishFa;
	
	private final static int DEFAULT_REWARD_FACTOR_WEIGHT = 1;
	private final static int DEFAULT_PUNISH_FACTOR_WEIGHT =5;
	private final static int TRUST_CONSTANT = 1;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public Double getTrust(){
		this.setTrust();
		return trust;
	}
	public void setTrust() {
		int i = this.rewardFa*DEFAULT_REWARD_FACTOR_WEIGHT - this.punishFa*DEFAULT_PUNISH_FACTOR_WEIGHT;
		this.trust = (double) (i)/TRUST_CONSTANT;
	}
	
	public int getRewardFa() {
		return rewardFa;
	}
	public void setRewardFa(int rewardFa) {
		this.rewardFa = rewardFa;
	}	
	public int getPunishFa() {
		return punishFa;
	}
	public void setPunishFa(int punishFa) {
		this.punishFa = punishFa;
	}
	
	public static void addTrust(int uid){
		SQLServer.reward(uid);
	}
	public static void minusTrust(int uid){
		SQLServer.punish(uid);
	}
}