package org.wangfy.dev.network.address;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

public class ShowAddress {

	public static String[] getAllLocalHostIP() {
		List<String> res = new ArrayList<String>();
		Enumeration netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				Enumeration nii = ni.getInetAddresses();
				while (nii.hasMoreElements()) {
					ip = (InetAddress) nii.nextElement();
					if (ip.getHostAddress().indexOf(":") == -1) {
						res.add(ip.getHostAddress());
						System.out.println("本机的ip=" + ip.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return (String[]) res.toArray(new String[0]);
	}

	/**
	 * 获取本机所有物理地址
	 * 
	 * @return
	 */
	private static String[] getAllLocalMac() {
		List<String> res = new ArrayList<String>();
		Enumeration netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				UUID.randomUUID(); 
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				if (ni.isLoopback() || ni.isPointToPoint() || ni.isVirtual())
					continue;
				byte[] mac = ni.getHardwareAddress();
				StringBuilder sb = new StringBuilder();
				if (mac != null) {
					for (byte b : mac) {
						final String hex = Integer.toHexString(0xFF & b);
						sb.append(hex);
						sb.append("-");
					}
					sb.deleteCharAt(sb.length() - 1);
					res.add(sb.toString());
					System.out.println("mac:" + sb.toString());
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return (String[]) res.toArray(new String[0]);
	}

	public static void getSpecificHostMac() throws SocketException, UnknownHostException{
		InetAddress addr = InetAddress.getByName("10.10.13.29");
		NetworkInterface networkInterface = NetworkInterface.getByInetAddress(addr);
		byte[] mac = networkInterface.getHardwareAddress();
		StringBuilder sb = new StringBuilder();
		if (mac != null) {
			for (byte b : mac) {
				final String hex = Integer.toHexString(0xFF & b);
				sb.append(hex);
				sb.append("-");
			}
			sb.deleteCharAt(sb.length() - 1);
			System.out.println("mac:" + sb.toString());
		}
	}
	public static void main(String[] args) throws UnknownHostException, SocketException {
//		getAllLocalHostIP();
//		getAllLocalMac();
		getSpecificHostMac();
	}
}
