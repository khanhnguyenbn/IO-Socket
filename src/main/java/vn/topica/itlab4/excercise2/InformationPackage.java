package vn.topica.itlab4.excercise2;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InformationPackage {
	private int lengthOfMessage;
	private short cmdCode;
	private short version = 0;
	
	// save list TLV of information package
	private Map<Short, TLV> tLVMap;

	public InformationPackage(short cmdCode, Map<Short, TLV> tLVMap) {
		this.cmdCode = cmdCode;
		this.tLVMap = tLVMap;
		this.lengthOfMessage = getLengthOfMessage();
	}

	// get length of Message
	public int getLengthOfMessage() {
		// init length (8) = lengthOfMessage (= 4) + cmdCode (=2) + version (=2)
		int length = 8;

		// caculate length of TLV; 4 = length of Tag(=2) + length of Length (=2)
		for (TLV tLV : tLVMap.values()) {
			length = length + 4 + tLV.getLength();
		}
		return length;
	}

	public void setLengthOfMessage(int lengthOfMessage) {
		this.lengthOfMessage = lengthOfMessage;
	}

	public short getCmdCode() {
		return cmdCode;
	}

	public void setCmdCode(short cmdCode) {
		this.cmdCode = cmdCode;
	}

	public short getVersion() {
		return version;
	}

	public void setVersion(short version) {
		this.version = version;
	}

	public Map<Short, TLV> gettLVMap() {
		return tLVMap;
	}

	public void settLVMap(Map<Short, TLV> tLVMap) {
		this.tLVMap = tLVMap;
	}

	
	// convert information package to byte array before sending to server
	// return byte array
	public byte[] getByteArray() {

		List<Byte> bytes = new ArrayList<Byte>();

		byte[] arrLengthOfMessage = ByteBuffer.allocate(4).putInt(lengthOfMessage).array();
		for (byte b : arrLengthOfMessage) {
			bytes.add(b);
		}

		byte[] arrCmdCode = ByteBuffer.allocate(2).putShort(cmdCode).array();
		for (byte b : arrCmdCode) {
			bytes.add(b);
		}

		byte[] arrVersion = ByteBuffer.allocate(2).putShort(version).array();
		for (byte b : arrVersion) {
			bytes.add(b);
		}
		

		for (TLV tLV : tLVMap.values()) {

			byte[] arrTag = ByteBuffer.allocate(2).putShort(tLV.getTag()).array();
			for (byte b : arrTag) {
				bytes.add(b);
			}

			byte[] arrLength = ByteBuffer.allocate(2).putShort(tLV.getLength()).array();
			for (byte b : arrLength) {
				bytes.add(b);
			}

			byte[] arrValue = tLV.getValue().getBytes();
			for (byte b : arrValue) {
				bytes.add(b);
			}
		}

		byte[] arrByte = new byte[bytes.size()];

		for (int i = 0; i < arrByte.length; i++) {
			arrByte[i] = bytes.get(i).byteValue();
		}

		return arrByte;

	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(cmdCodeToString(cmdCode));
		for (TLV tLV : tLVMap.values()) {
			stringBuilder.append(" " + tagToString(tLV.getTag()) + " " + tLV.getValue());
		}

		return stringBuilder.toString();
	}

	private String cmdCodeToString(short cmdCode) {

		switch (cmdCode) {
		case Constant.AUTHEN:
			return "AUTHEN";
		case Constant.INSERT:
			return "INSERT";
		case Constant.COMMIT:
			return "COMMIT";
		case Constant.SELECT:
			return "SELECT";
		case Constant.ERROR:
			return "ERROR";

		default:
			return "";
		}

	}
	
	private String tagToString (short tag) {
		
		switch (tag) {
		case Constant.PHONE_NUMBER:
			return "PhoneNumber";
		case Constant.KEY:
			return "Key";
		case Constant.NAME:
			return "Name";
		case Constant.RESULT_CODE:
			return "ResultCode";

		default:
			return "";
		}
	}

}
