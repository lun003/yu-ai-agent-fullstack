package com.lcc.yuaiagent.tools;
import cn.hutool.core.io.FileUtil;

import com.lcc.yuaiagent.Constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class FileOperationTool {
    private final String FILE_DIR = FileConstant.FILE_SAVE_DIR + "/file";
    /**
     * 读取指定文件的内容。
     *
     * @param fileName 要读取的文件名（不包含 FILE_DIR 路径，方法内部会自动拼接）
     * @return 文件内容；如果读取失败，则返回错误信息字符串
     */
    @Tool(description = "Red Content from a file")
    public String redFile(@ToolParam(description = "Name of the file to red") String fileName) {
        // 拼接完整文件路径：FILE_DIR + "/" + 文件名
        String filePath = FILE_DIR + "/" + fileName;
        try {
            return FileUtil.readUtf8String(filePath);
        } catch (Exception e) {
            // 捕获读取文件过程中的异常，返回错误信息，避免异常直接抛出
            return  "Error reading file:"+ e.getMessage();
        }

    }
    @Tool(description = "Write content a  file")
    public String writeFile(
            @ToolParam() String fileName,
            @ToolParam() String content
    ){
        String filePath = FILE_DIR + "/" +fileName;
        try {
            //创建目录
            FileUtil.mkdir(FILE_DIR);
            FileUtil.writeUtf8String(content,filePath);
            return "File written successfully to:" + filePath;
        }catch (Exception e){
            return "Error writing to file: "+e.getMessage();
        }
    }
}
