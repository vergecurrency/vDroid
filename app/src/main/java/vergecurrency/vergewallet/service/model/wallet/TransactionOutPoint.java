package vergecurrency.vergewallet.service.model.wallet;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class TransactionOutPoint {

	private byte[] hash;
	private int index;

	public TransactionOutPoint(byte[] hash, int index) {
		this.hash = hash;
		this.index = index;
	}

	public byte[] serialize() {
		byte[] indexBA = BigInteger.valueOf(index).toByteArray();
		byte[] result = new byte[indexBA.length + hash.length];
		System.arraycopy(hash, 0, result, 0, hash.length);
		System.arraycopy(indexBA, 0, result, hash.length, indexBA.length);
		return result;
	}

	public static TransactionOutPoint deserialize(byte[] data) {
		return new TransactionOutPoint(Arrays.copyOfRange(data, 0, 31), ByteBuffer.wrap(Arrays.copyOfRange(data, 32, 35)).getInt());
	}

	public byte[] getHash() {
		return hash;
	}

	public void setHash(byte[] hash) {
		this.hash = hash;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
