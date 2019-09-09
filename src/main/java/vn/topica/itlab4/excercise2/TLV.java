package vn.topica.itlab4.excercise2;

public class TLV {
	
	private short tag;
	private short length;
	private String value;
	
	
	
	public TLV(short tag, String value) {
		this.tag = tag;
		this.value = value;
		this.length = getLength();
	}
	
	public short getTag() {
		return tag;
	}
	public void setTag(short tag) {
		this.tag = tag;
	}
	public short getLength() {
		return (short) value.getBytes().length;
	}
	public void setLength(short length) {
		this.length = length;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
