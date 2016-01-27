/**
 * @author 902
 *
 */
package access.model;

public class AccessParams{
	private boolean access;

	private String accessType;
	private boolean anonymous;
	private Subject sub;
	private Object obj;
	private Environment env;
	private int behaviorLevel;
	private String policy;
	private String policyDescription;
	private int visibility;
	private String visibilityDescription;

	public String attributesToString(){
		return this.sub.subToString()+","+this.env.envToString();
	}

	public boolean isAccess() {
		return access;
	}
	public void setAccess(boolean access) {
		this.access = access;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public boolean isAnonymous() {
		return anonymous;
	}
	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}
	public Subject getSub() {
		return sub;
	}
	public void setSub(Subject sub) {
		this.sub = sub;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Environment getEnv() {
		return env;
	}
	public void setEnv(Environment env) {
		this.env = env;
	}
	public int getBehaviorLevel() {
		return behaviorLevel;
	}
	public void setBehaviorLevel(int behaviorLevel) {
		this.behaviorLevel = behaviorLevel;
	}
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public String getPolicyDescription() {
		return policyDescription;
	}
	public void setPolicyDescription(String policyDescription) {
		this.policyDescription = policyDescription;
	}
	public int getVisibility() {
		return visibility;
	}
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	public String getVisibilityDescription() {
		return visibilityDescription;
	}
	public void setVisibilityDescription(String visibilityDescription) {
		this.visibilityDescription = visibilityDescription;
	}

}