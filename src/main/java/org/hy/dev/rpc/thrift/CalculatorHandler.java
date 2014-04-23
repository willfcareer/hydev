package org.hy.dev.rpc.thrift;

import java.util.HashMap;

import org.apache.thrift.TException;
import org.hy.dev.rpc.thrift.shared.SharedStruct;
import org.hy.dev.rpc.thrift.tutorial.Calculator.Iface;
import org.hy.dev.rpc.thrift.tutorial.InvalidOperation;
import org.hy.dev.rpc.thrift.tutorial.Work;

public class CalculatorHandler implements Iface {

	private HashMap<Integer, SharedStruct> log;

	public CalculatorHandler() {
		log = new HashMap<Integer, SharedStruct>();
	}

	@Override
	public SharedStruct getStruct(int key) throws TException {
	    System.out.println("getStruct(" + key + ")");
	    return log.get(key);
	}

	@Override
	public void ping() throws TException {
		System.out.println("pint()");
	}

	@Override
	public int add(int n1, int n2) throws TException {
	    System.out.println("add(" + n1 + "," + n2 + ")");
	    return n1 + n2;
	}

	@Override
	public int calculate(int logid, Work work) throws InvalidOperation, TException {
	    System.out.println("calculate(" + logid + ", {" + work.op + "," + work.num1 + "," + work.num2 + "})");
	    int val = 0;
	    switch (work.op) {
	    case ADD:
	      val = work.num1 + work.num2;
	      break;
	    case SUBTRACT:
	      val = work.num1 - work.num2;
	      break;
	    case MULTIPLY:
	      val = work.num1 * work.num2;
	      break;
	    case DIVIDE:
	      if (work.num2 == 0) {
	        InvalidOperation io = new InvalidOperation();
	        io.what = work.op.getValue();
	        io.why = "Cannot divide by 0";
	        throw io;
	      }
	      val = work.num1 / work.num2;
	      break;
	    default:
	      InvalidOperation io = new InvalidOperation();
	      io.what = work.op.getValue();
	      io.why = "Unknown operation";
	      throw io;
	    }

	    SharedStruct entry = new SharedStruct();
	    entry.key = logid;
	    entry.value = Integer.toString(val);
	    log.put(logid, entry);

	    return val;
	}

	@Override
	public void zip() throws TException {
	    System.out.println("zip()");
	}

}
