package com.we.util;

public class ShellUtil {
    public static void executeCommands(String command) throws Exception {
        /* 创建空进程 */
        //Process process = null;
        
        /* 给文件执行权限 */
/*        String accessCommand = "chmod 777 " + shPath;
        process = Runtime.getRuntime().exec(accessCommand);
        process.waitFor();*/
        
        /* 执行主程序 */
        Runtime.getRuntime().exec(command).waitFor();
    }
}
