/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;

public abstract class CommonFilterImpl implements CommonFilter {
	protected ArrayList<PipedInputStream> in = new ArrayList<PipedInputStream>();
	protected ArrayList<PipedOutputStream> out = new ArrayList<PipedOutputStream>();
	protected static int pipe1 = 0, pipe2 = 1;
	private static int pipeMax = 2;

	public CommonFilterImpl() {
		this.pipedStream(pipeMax);
	}
	
	private void pipedStream(int pipeMax) {
		for (int i = 0; i < pipeMax; i++) {
			in.add(new PipedInputStream());
			out.add(new PipedOutputStream());
		}
	}

	public void connectOutputTo(CommonFilter nextFilter, int PortNo) throws IOException {
		out.get(PortNo).connect(nextFilter.getPipedInputStream().get(PortNo));
	}
	public void connectInputTo(CommonFilter previousFilter, int PortNo) throws IOException {
		in.get(PortNo).connect(previousFilter.getPipedOutputStream().get(PortNo));
	}
	public ArrayList<PipedInputStream> getPipedInputStream() {
		return in;
	}
	public ArrayList<PipedOutputStream> getPipedOutputStream() {
		return out;
	}
	
	abstract public boolean specificComputationForFilter() throws IOException;
	// Implementation defined in Runnable interface for thread
	public void run() {
		try {
			specificComputationForFilter();
		} catch (IOException e) {
			if (e instanceof EOFException) return;
			else System.out.println(e);
		} finally {
			closePorts();
		}
	}
	private void closePorts() {
		try {
			((Closeable) out).close();
			((Closeable) in).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
