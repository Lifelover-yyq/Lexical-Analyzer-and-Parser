package Analysis;

public class Action {
	public final boolean isshift;
	public final int state;
	public Action(boolean is,int st){
		this.isshift=is;
		this.state=st;
	}
	public String toString(){
		if(isshift)
			return "s"+state;
		else if(state==-1)
			return "accept";
		else
			return "r"+state;
	}
}