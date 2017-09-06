package com.we.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class AddressUtil {
    public static String getMACAddr() throws SocketException, UnknownHostException {
        String address = "";
        NetworkInterface netInterface = NetworkInterface
                .getByInetAddress(InetAddress.getLocalHost());
        // 获得Mac地址的byte数组
        byte[] macAddr = netInterface.getHardwareAddress();
        // 循环输出
        for (byte b : macAddr) {
            // 这里的toHexString()是自己写的格式化输出的方法，见下步。
            address += toHexString(b);
        }
        return address;
    }

    private static String toHexString(int integer) {
        // 将得来的int类型数字转化为十六进制数
        String str = Integer.toHexString((int) (integer & 0xff));
        // 如果遇到单字符，前置0占位补满两格
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }
}
