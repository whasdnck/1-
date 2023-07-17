package application;

public class Order {

	String idx="";
	String date="";
	String count1="";
	String count2="";
	String count3="";
	String sum="";
	
	public Order() {
		super();
	}
	public Order(String idx, String date, String count1, String count2, String count3, String sum) {
		super();
		this.idx = idx;
		this.date = date;
		this.count1 = count1;
		this.count2 = count2;
		this.count3 = count3;
		this.sum = sum;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCount1() {
		return count1;
	}
	public void setCount1(String count1) {
		this.count1 = count1;
	}
	public String getCount2() {
		return count2;
	}
	public void setCount2(String count2) {
		this.count2 = count2;
	}
	public String getCount3() {
		return count3;
	}
	public void setCount3(String count3) {
		this.count3 = count3;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	
	
	
}
